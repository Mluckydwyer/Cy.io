import {Entity} from "./controllers/entity.mjs";
import { Config } from './controllers/config.mjs';
import { Socket } from './controllers/socket.mjs';
import { Player } from './controllers/player.mjs';
import { Controller } from './controllers/controller.mjs';
import getRequest from "./libs/requests.mjs";

let canvas; // HTML canvas
let g; // canvas graphics object
const framerate = 40;

let serverUrl = window.location.protocol + "//" + window.location.hostname + ":8080";
// const serverUrl = "http://coms-309-nv-4.misc.iastate.edu:8081"; // Dev server for testing

//export let clientPlayer;
let players, entities;
let controller, config;
let chatSocket, leaderboardSocket, notificationSocket, playerDataSocket;
let gameId;
let jsonGameData;

// Initial Setup
async function setup() {

    // Check URL for a game id, if present proceed with game loading, if not, redirect ot home page
    let urlParams = new URLSearchParams(window.location.search);
    if (!urlParams.has('gameId')) { // Is game ide present
        //window.location.href = "/"; // redirect ot home page
    }
    gameId = urlParams.get('gameId'); // get game id
    document.getElementById("username-input").focus(); // Set username input to focus
    pullAndWait(); // Pull the join game data nd wait till user input

    // Setup HTML drawing canvas for drawing to screen
    canvas = document.getElementById("canvas");
    g = canvas.getContext('2d');
    resizeCanvas(); // Fit canvas to window
    window.addEventListener('resize', resizeCanvas); // Window resize listener

    // Game Elements
    config = await new Config().init();
    controller = new Controller().init(config, canvas, false);

    // Setup Client Player
    players = []; // create player list
    entities = [];
    let clientPlayer = new Player().init(config, true, true); // Create main player
    players.push(clientPlayer);

    // Load config file and parse it once loaded
    await refreshConfig('res/javascript/game-config-test.json');
    document.getElementById("submit-un").addEventListener("click", onPlayClick);
}

function onPlayClick() {
    getUsername();
    join(jsonGameData).then(function () {
        setTimeout(function () {
            document.getElementById("loader").classList.remove("show");
            document.getElementById("loader").classList.add("hide");
            document.getElementById("backdrop").classList.remove("show");
            document.getElementById("backdrop").classList.add("hide");
            document.getElementById("submit-un").removeEventListener("click", onPlayClick);

            setInterval(cullStalePlayers, 1000);

            setTimeout(function () {
                setInterval(run, 1000 / framerate); // Set game clock tick for logic and drawing
                setInterval(sendPlayerData, 1000 / framerate); // Send player data to server in milliseconds between send
                document.getElementById("leaderboard").classList.remove("hide");
                controller.enable();
            }, 1000);
        }, 1000);
    });
}

function pullAndWait() {
    // Get server info of server running instance of teh game we want to play
    getRequest(serverUrl + "/find?gameId=" + gameId).then(async function (response) {
        jsonGameData = JSON.parse(response);
        document.getElementById("game-title-text").innerText = jsonGameData.json.gameTitle;
    });
}

async function sendPlayerData() {
    playerDataSocket.sendPlayerDataMessage("PLAYER_MOVEMENT", getClientPlayer());
}

function cullStalePlayers() {
    for (let i = 0; i < players.length; i++) {
        let player = players[i];
        if (new Date().getTime() - player.lastUpdate >= 1000) {
            let index = players.indexOf(player);
            players.splice(index, 1);
        }
    }
}

function parsePlayerMovement(playerId, payload) {
    if (getClientPlayer().playerId !== playerId) {
        let otherPlayer = getPlayerById(playerId);
        if (otherPlayer == null) {
            addPlayer(playerId);
            otherPlayer = getPlayerById(playerId);
        }

        otherPlayer.mover.xPos = parseFloat(payload.xPos);
        otherPlayer.mover.yPos = parseFloat(payload.yPos);
        otherPlayer.mover.targetX = parseFloat(payload.targetX);
        otherPlayer.mover.targetY = parseFloat(payload.targetY);
        otherPlayer.mover.speed = parseInt(payload.speed);
        otherPlayer.mover.size = parseInt(payload.size);
        otherPlayer.score = parseInt(payload.score);
        otherPlayer.color = payload.color;
        otherPlayer.name = payload.name;
        otherPlayer.lastUpdate = new Date().getTime();
    }
}

/*
    Get and connect to all websocket endpoints to join game server
 */
async function join(json) {
    showSnackbar("Connecting to Game");

    // If successful, connect oto all web sockets
    if (json.success) {
        json = json.json; // outer message success can be shed once read for cleaner code

        // Chat Socket
        chatSocket = new Socket();
        chatSocket.init(json.chatWs);
        await chatSocket.connect().then(function () {
            chatSocket.subscribe(json.chatSub, function (frame) {
                // TODO Update chat on screen
                incomingChat(frame.body);
            });
        }).catch(function () {
            console.log("Chat websocket Failed to connect");
        });

        // Leaderboard Socket
        leaderboardSocket = new Socket();
        leaderboardSocket.init(json.leaderboardWs);
        await leaderboardSocket.connect().then(function () {
            leaderboardSocket.subscribe(json.leaderboardSub, function (frame) {
                updateLeaderboard(frame.body);
                // console.log("Leaderboard update received");
            });
        }).catch(function () {
            console.log("Leaderboard websocket Failed to connect");
        });

        // Notification
        notificationSocket = new Socket();
        notificationSocket.init(json.notificationWs);
        await notificationSocket.connect().then(function () {
            notificationSocket.subscribe(json.notificationSub, function (frame) {
                // console.log("Notification received");
                showSnackbar(frame.body);
            });
        }).catch(function () {
            console.log("Notification websocket Failed to connect");
        });

        //PlayerData Socket
        playerDataSocket = new Socket();
        playerDataSocket.init(json.playerDataWs);
        await playerDataSocket.connect().then(function () {
            playerDataSocket.subscribe(json.playerDataSub, function (frame) {
                let json = JSON.parse(frame.body);
                for (let i = 0; i < json.length; i++) {
                    let packet = json[i];
                    switch (packet.type) {
                        case "JOIN":
                            if (packet.playerId === getClientPlayer().playerId) {
                                console.log("Join handshake successful");
                            } else {
                                addPlayer(packet.playerId);
                            }
                            break;
                        case "LEAVE":
                            removePlayerById(packet.playerId);
                            break;
                        case "PLAYER_MOVEMENT":
                            parsePlayerMovement(packet.playerId, packet.payload);
                            //console.log("Player Data: " + frame.body);
                            break;
                        case "ENTITY":
                            if (i === 0) entities = []; // if json packet of entities first in list clear list to update
                            entities.push(new Entity().init(packet));
                            break;
                    }
                }
            });
        }).catch(function () {
            console.log("Player Data websocket Failed to connect");
        });

        playerDataSocket.sendPlayerDataMessage("JOIN", getClientPlayer());
    }
}

// When done loading, run the setup function
window.onload = setup;

// On page leave (refresh or close
window.onunload = leave;

function leave() {
    playerDataSocket.sendPlayerDataMessage("LEAVE", getClientPlayer());
    playerDataSocket.disconnect();
    chatSocket.disconnect();
    notificationSocket.disconnect();
    leaderboardSocket.disconnect();
}

// Refresh teh game config file
async function refreshConfig(json) {
    await config.load(json);
    controller.config(config);
    let clientPlayer = getClientPlayer();
    clientPlayer.config(config);
}

// Main function that handles all game logic and graphics (called FPS times per second)
function run() {
    checkEntityCollisions();
    movePlayers();

    draw();
}

// Main draw function that calls all draw functions such as players
function draw() {
    // Clear screen
    g.clearRect(0, 0, canvas.width, canvas.height);
    if (controller.darkmode) {
        g.fillStyle = "#333";
        g.fillRect(0, 0, canvas.width, canvas.height);
    }

    // Draw entities
    for (let i = 0; i < entities.length; i++) {
        let e = entities[i];
        if (e.xPos - e.size < canvas.width && e.yPos - e.size < canvas.height) e.draw(g);
    }

    // Draw players
    for (let i = 0; i < players.length; i++) {
        players[i].draw(g);
    }
}

// Update all players positions
function movePlayers() {
    for (let i = 0; i < players.length; i++) {
        if (players[i].playerId === getClientPlayer().playerId) players[i].mover.update(controller);
            //if (controller.mouseOnScreen) players[i].mover.update(controller);
        else players[i].mover.update();
    }

}

// Check for collisions
function checkEntityCollisions() {
    let player = getClientPlayer();
    player.mover.checkPlayerCollisions(entities, players);
    for (let index in player.mover.collisions) {
        let object = player.mover.collisions[index];
        if (!player.perviousInteractions.includes(object.id)) sendEntityUpdate(object);
    }
}

// Get the current player using the client
function getClientPlayer() {
    for (let index in players) {
        let player = players[index];
        if (player.isClientPlayer) return player;
    }
}

// Get a player by their player id
function getPlayerById(playerId) {
    for (let index in players) {
        let player = players[index];
        if (playerId === player.playerId) return player;
    }
}

// Add a player
function addPlayer(playerId) {
    let newPlayer = new Player().init(null, false);
    newPlayer.playerId = playerId;
    players.unshift(newPlayer);
}

// Remove a player by their player id
function removePlayerById(playerId) {
    for (let index in players) {
        let player = players[index];
        if (playerId === player.playerId) {
            players.remove(player);
            return;
        }
    }
}

// On Window Size Change
function resizeCanvas() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
}

export function updateLeaderboard(leaders) {
    let json = JSON.parse(leaders); // parse leaderboard message
    let formatter = new Intl.NumberFormat();

    // Clear leaderboard
    let lb = document.getElementById("lb-list");
    lb.innerHTML = "";

    // Create leaderbaord title
    let title = document.createElement("div");
    title.classList.add("l-title");
    let titleText = document.createElement("span");
    titleText.classList.add("l-title");
    titleText.innerHTML = "<b>Leaderboard</b>";
    title.appendChild(titleText);
    lb.appendChild(title);

    // Iterate over leaders and add each to the board
    for (let index in json) {
        let leader = json[index];
        let leaderDiv = document.createElement("div");
        leaderDiv.classList.add("leader");
        lb.appendChild(leaderDiv);

        let leaderName = document.createElement("span");
        leaderName.classList.add("l-name");
        leaderName.innerText = leader.name;
        leaderDiv.appendChild(leaderName);

        let leaderScore = document.createElement("span");
        leaderScore.classList.add("l-score");
        leaderScore.innerText = formatter.format(parseInt(leader.score));
        leaderDiv.appendChild(leaderScore);
    }
}

function showSnackbar(text) {
    let sb = document.getElementById("snackbar");
    sb.innerHTML = text;
    sb.className = "show";
    setTimeout(function () {
        sb.className = sb.className.replace("show", "");
    }, 3000)
}

function setFocusFixed( elm ){
    let savedTabIndex = elm.getAttribute('tabindex');
    elm.setAttribute('tabindex', '-1');
    elm.focus();
    elm.setAttribute('tabindex', savedTabIndex)
}

export function toggleChat() {
    let chat = document.getElementById("chat");
    if (chat.classList.contains("show")) chat.classList.remove("show");
    else {
        chat.classList.add("show");
        setFocusFixed(document.getElementById("chat-box"));
        document.getElementById("chat-box").focus();
    }
    controller.chatShown = !controller.chatShown;
}

export function toggleDarkMode() {
    controller.darkmode = !controller.darkmode;
}

export function toggleMovementStyle() {
    getClientPlayer().mover.expMovement = !getClientPlayer().mover.expMovement;
}

export function incomingChat(message) {
    let chatList = document.getElementById("chat-list");
    let span = document.createElement('span');
    let json = JSON.parse(message);

    span.innerText = json.text;
    chatList.appendChild(span);
    console.log("Message Received: " + message);

    setTimeout(function () {
        chatList.removeChild(span);
    }, 10000)
}

export function sendChat() {
    let chatbox = document.getElementById("chat-box");
    chatSocket.sendChatMessage(getClientPlayer().name, chatbox.value);
    chatbox.value = "";
}

function sendEntityUpdate(entity) {
    let clientPlayer = getClientPlayer();
    playerDataSocket.sendPlayerDataMessage("ENTITIES", clientPlayer, entity);
    clientPlayer.perviousInteractions.push(entity.id);
    clientPlayer.score += entity.scoreValue;
}

function getUsername() {
    document.getElementById("loader").classList.remove("hide");
    document.getElementById("loader").classList.add("show");
    document.getElementById("name-pop").classList.remove("show");
    document.getElementById("name-pop").classList.add("hide");


    if (document.getElementById("username-input").value !== "") {
        getClientPlayer().name = document.getElementById("username-input").value;
    }
}

window.onunload = closingCode;
window.onbeforeunload = closingCode;
function closingCode(){
    leaderboardSocket.disconnect();
    chatSocket.disconnect();
    notificationSocket.disconnect();
    playerDataSocket.disconnect();
}