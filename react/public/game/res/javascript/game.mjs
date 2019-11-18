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

let player, controller, config;
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



    // Testing
    // chatSocket = new Socket();
    // //chatSocket.init('http://localhost:8080' + "/chat");
    // chatSocket.init('http://coms-309-nv-4.misc.iastate.edu:8081' + "/chat");
    // await chatSocket.connect().then(function () {
    //     chatSocket.subscribe("/topic/chat");
    //     chatSocket.sendMessage({text: "This is a test message"}, "/app/chat");
    //     let msg = {
    //         to: "Matt",
    //         from: "Tom",
    //         text: "<3"
    //     };
    //     chatSocket.sendChatMessage(msg, "/app/chat");
    //     //chatSocket.sendPlayerDataMessage(player);
    // }).catch(function () {
    //     console.log("Chat websocket Failed to connect");
    // });


    // leaderboardSocket = new Socket();
    // leaderboardSocket.init('http://localhost:8080' + "/leaderboard");
    // await leaderboardSocket.connect().then(function () {}).catch(function () {
    //     console.log("Chat websocket Failed to connect");
    // });


    // let SECURED_CHAT = '/secured/chat';
    // let SECURED_CHAT_HISTORY = '/secured/history';
    // let SECURED_CHAT_ROOM = '/secured/room';
    // let SECURED_CHAT_SPECIFIC_USER = '/secured/user/queue/specific-user';

    // let socket = new SockJS('http://localhost:8080' + SECURED_CHAT);
    // let sc = Stomp.over(socket);
    // let sessionID = "";
    //
    // sc.connect({}, function (frame) {
    //         let url = stompClient.ws._transport.url;
    //         url = url.replace("ws://localhost:8080/spring-security-mvc-socket/secured/room/", "");
    //         url = url.replace("/websocket", "");
    //         url = url.replace(/^[0-9]+\//, "");
    //         console.log("Your current session is: " + url);
    //         sessionID = url;
    // });

    // sc.subscribe('secured/user/queue/specific-user'
    //     + '-user' + that.sessionId, function (msgOut) {
    //     //handle messages
    // });
}

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
                    console.log(frame.body);
                });
                sendTestChatMessage();
            }).catch(function () {
                console.log("Chat websocket Failed to connect");
            });

            // Leaderboard Socket
            leaderboardSocket = new Socket();
            leaderboardSocket.init(json.leaderboardWs);
            await leaderboardSocket.connect().then(function () {
                leaderboardSocket.subscribe(json.leaderboardSub, function (frame) {
                    // TODO update leaderboard on screen
                });
            }).catch(function () {
                console.log("Leaderboard websocket Failed to connect");
            });

            // Notification
            notificationSocket = new Socket();
            notificationSocket.init(json.notificationWs);
            await notificationSocket.connect().then(function () {
                notificationSocket.subscribe(json.notificationSub, function (frame) {
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
                    console.log(frame.body); // TODO update player data
                });
            }).catch(function () {
                console.log("Player Data websocket Failed to connect");
            });
        }
    });
}

function sendTestChatMessage() {
    let message = new Message();
    message.to = "ALL";
    message.from = "User1";
    message.text = "This is a test message";
    chatSocket.sendChatMessage(message);
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
