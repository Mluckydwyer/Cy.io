let canvas; // HTML canvas
let g; // canvas graphics object

import { Config } from './controllers/config.mjs';
import { Socket } from './controllers/socket.mjs';
import { Player } from './controllers/player.mjs';
import { Controller } from './controllers/controller.mjs';
import getRequest from "./libs/requests.mjs";


const framerate = 60;

const serverUrl = window.location.protocol + "//" + window.location.hostname + ":8080";
// const serverUrl = "http://coms-309-nv-4.misc.iastate.edu:8081"; // Dev server for testing

export let player;
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
    player = new Player().init(config); // Create main player
    await refreshConfig('res/javascript/game-config-test.json');
    document.getElementById("submit-un").addEventListener("click", onPlayClick);
}

function onPlayClick() {
    getUsername();
    join(jsonGameData).then(function () {
        setTimeout(function () {
            document.getElementById("leaderboard").classList.remove("hide");
            document.getElementById("loader").classList.remove("show");
            document.getElementById("loader").classList.add("hide");
            document.getElementById("backdrop").classList.remove("show");
            document.getElementById("backdrop").classList.add("hide");
            document.getElementById("submit-un").removeEventListener("click", onPlayClick);

            setTimeout(function () {
                setInterval(run, 1000 / framerate); // Set game clock tick for logic and drawing
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
                console.log("Leaderboard update received");
            });
        }).catch(function () {
            console.log("Leaderboard websocket Failed to connect");
        });

        // Notification
        notificationSocket = new Socket();
        notificationSocket.init(json.notificationWs);
        await notificationSocket.connect().then(function () {
            notificationSocket.subscribe(json.notificationSub, function (frame) {
                console.log("Notification received");
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
                //console.log("Player Data update received");
                //console.log(frame.body); // TODO update player data
            });
        }).catch(function () {
            console.log("Player Data websocket Failed to connect");
        });

        playerDataSocket.sendMessage({
            username: player.name,
            playerId: player.playerId,
            
        });
    }
}

// When done loading, run the setup function
window.onload = setup;

// Refresh teh game config file
async function refreshConfig(json) {
    await config.load(json);
    controller.config(config);
    player.config(config);
}

// Main function that handles all game logic and graphics (called FPS times per second)
function run() {
    player.mover.update(controller);
    draw();
}

// Main draw function that calls all draw functions such as players
function draw() {
    // Clear screen
    g.clearRect(0, 0, canvas.width, canvas.height);

    // Draw player
    player.draw(g);
}

// On Window Size Change
function resizeCanvas() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
}

function updateLeaderboard(leaders) {
    let json = JSON.parse(leaders); // parse leaderboard message

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
        leaderScore.innerText = leader.score;
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
    }
    controller.chatShown = !controller.chatShown;
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
    chatSocket.sendChatMessage(chatbox.value);
    chatbox.value = "";
}

function getUsername() {
    document.getElementById("loader").classList.remove("hide");
    document.getElementById("loader").classList.add("show");
    document.getElementById("name-pop").classList.remove("show");
    document.getElementById("name-pop").classList.add("hide");


    if (document.getElementById("username-input").value !== "") {
        player.name = document.getElementById("username-input").value;
    }
}