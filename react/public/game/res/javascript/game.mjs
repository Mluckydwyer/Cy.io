let canvas; // HTML canvas
let g; // canvas graphics object

// import { Mover } from './res/javascript/libs/mover.mjs';
import { Config } from './controllers/config.mjs';
import { Socket } from './controllers/socket.mjs';
import { Player } from './controllers/player.mjs';
import { Controller } from './controllers/controller.mjs';

const framerate = 60;
let player, controller, config, socket;

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
    socket = new Socket().init('http://localhost:3000/game');
    socket.connect();
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

