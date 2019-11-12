let canvas; // HTML canvas
let g; // canvas graphics object

// import { Mover } from './res/javascript/libs/mover.mjs';
import { Config } from './controllers/config.mjs';
import { Socket } from './controllers/socket.mjs';
import { Message } from './objects/message.mjs';
import { Player } from './controllers/player.mjs';
import { Controller } from './controllers/controller.mjs';

const framerate = 60;
let player, controller, config, chatSocket;

// Initial Setup
async function setup() {
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
    chatSocket = new Socket();
    //chatSocket.init('http://localhost:8080' + chatSocket.SECURED_CHAT_ROOM);
    chatSocket.init('http://coms-309-nv-4.misc.iastate.edu:8080' + chatSocket.SECURED_CHAT_ROOM);
    await chatSocket.connect().then(function () {
        chatSocket.subscribe("/room/public");
        chatSocket.sendMessage({text: "This is a test message"});
        let msg = {
            to: "Matt",
            from: "Tom",
            text: "<3"
        };
        chatSocket.sendChatMessage(msg);
        chatSocket.sendPlayerDataMessage(player);
    }).catch(function () {
        console.log("Chat websocket Failed to connect");
    });


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

function sendTestChatMessage() {
    let message = new Message();
    message.to = "ALL";
    message.from = "User1";
    message.text = "This is a test message";
    chatSocket.sendMessage(message);
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

