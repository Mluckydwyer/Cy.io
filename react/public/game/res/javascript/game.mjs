import getRequest from "./libs/requests.mjs";

let canvas; // HTML canvas
let g; // canvas graphics object

// import { Mover } from './res/javascript/libs/mover.mjs';
import { Config } from './controllers/config.mjs';
import { Socket } from './controllers/socket.mjs';
import { Message } from './objects/message.mjs';
import { Player } from './controllers/player.mjs';
import { Controller } from './controllers/controller.mjs';

const framerate = 60;
const serverUrl = window.location.protocol + "//" + window.location.hostname + ":8080";
// const serverUrl = "http://coms-309-nv-4.misc.iastate.edu:8081"; // Dev server for testing

export let player;
let controller, config;
let chatSocket, leaderboardSocket, notificationSocket, playerDataSocket;
let gameId;

// Initial Setup
async function setup() {

    // Check URL for a game id, if present proceed with game loading, if not, redirect ot home page
    let urlParams = new URLSearchParams(window.location.search);
    if (!urlParams.has('gameId')) { // Is game ide present
        //window.location.href = "/"; // redirect ot home page
    }
    gameId = urlParams.get('gameId'); // get game id
    join(); // initiate join handshake

    // Setup HTML drawing canvas for drawing to screen
    canvas = document.getElementById("canvas");
    g = canvas.getContext('2d');
    resizeCanvas(); // Fit canvas to window
    window.addEventListener('resize', resizeCanvas); // Window resize listener

    // Game Elements
    config = await new Config().init();
    controller = new Controller().init(config, canvas, false);
    player = new Player().init(config); // Create main player
    await refreshConfig('res/javascript/game-config-test.json'); // TODO await???

    setInterval(run, 1000 / framerate); // Set game clock tick for logic and drawing
    controller.enable();
}

/*
    Get and connect to all websocket endpoints to join game server
 */
function join() {
    showSnackbar("Getting Game Data");
    // Get server info of server running instance of teh game we want to play
    getRequest(serverUrl + "/find?gameId=" + gameId).then(async function (response) {
        let json = JSON.parse(response);
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
                    // TODO update leaderboard on screen
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
                    console.log("Player Data update received");
                    console.log(frame.body); // TODO update player data
                });
            }).catch(function () {
                console.log("Player Data websocket Failed to connect");
            });
        }
    });
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

function showSnackbar(text) {
    let sb = document.getElementById("snackbar");
    sb.innerHTML = text;
    sb.className = "show";
    setTimeout(function () {
        sb.className = sb.className.replace("show", "");
    }, 3000)
}

export function toggleChat() {
    let chat = document.getElementById("chat");
    if (chat.classList.contains("show")) chat.classList.remove("show");
    else {
        chat.classList.add("show");
        document.getElementById("chat-box").focus();
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