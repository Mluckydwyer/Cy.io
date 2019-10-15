let canvas; // HTML canvas
let g; // canvas graphics object

const framerate = 60;
let player, controller, config;

// Initial Setup
function setup() {
    // Setup HTML drawing canvas for drawing to screen
    canvas = document.getElementById("canvas");
    g = canvas.getContext('2d');
    resizeCanvas(); // Fit canvas to window
    window.addEventListener('resize', resizeCanvas); // Window resize listener

    // Game Elements
    config = new Config().init().then(function (config) {
        this.config = config;
        controller = new Controller().init(config, false);
        player = new Player().init(config); // Create main player
        controller.then(function (controller) {
            controller.enable();
        });
        setInterval(run, 1000 / framerate); // Set game clock tick for logic and drawing
    });
}

// When done loading, run the setup function
window.onload = setup;

function refreshConfig(json) {
    config.load(json);
    controller.config(config);
    player.config(config);
}

// Main function that handles all game logic and graphics (called FPS times per second)
function run() {
    player.mover.update(controller);
    draw();
}

// TODO Run setup and establish a server connection
function connect() {
    console.log("Establishing a connection to the server");
    socket.emit('connection', {playerName: player.name});

    socket.on('connection', function (data) {
        console.log(data);
    });
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

