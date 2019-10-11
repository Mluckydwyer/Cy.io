let canvas; // HTML canvas
let g; // canvas graphics object

//const serverURL = 'https://io.mluckydwyer.com/node:3000';
const framerate = 60;
let player;

// Objects

function Player() {
    this.sizeIncrement = 3;
    this.defaultRadius = 30;
    this.defaultSpeed = 5;
    this.defaultName = "Tim";

    // Object that handles player movement and input
    this.mover = new Mover(canvas.width, canvas.height);
    this.mover.init();
    this.mover.speed = this.defaultSpeed; // Set player speed
    this.mover.radius = this.defaultRadius; // Set player size TODO Only circles right now

    this.name = this.defaultName;

    // Draw the player TODO allow more than circles
    this.draw = function(g) {
        g.beginPath(); // Draw a path that is an unfilled circle
        g.arc(player.mover.xPos, player.mover.yPos, player.mover.radius, 0, 2 * Math.PI, false);
        g.fillStyle = 'blue'; // Fill it with blue
        g.fill();
        g.lineWidth = 5;
        g.strokeStyle = '#003300'; // Have an outline of dark green
        g.stroke();
    };
}

// Initial Setup
function setup() {
    // Setup HTML drawing canvas for drawing to screen
    canvas = document.getElementById("canvas");
    g = canvas.getContext('2d');

    // Window resize listener
    document.addEventListener('resize', resizeCanvas);

    // Fit canvas to window
    resizeCanvas();

    // Create main player
    player = new Player();

    // Set game clock tick for logic and drawing
    setInterval(run, 1000 / framerate);
}

// When done loading, run the setup function
window.onload = setup;

// Main function that handles all game logic and graphics (called FPS times per seconds)
function run() {
    player.mover.update();
    draw();
    //console.log("mx: " + player.mover.mouseX + "  my: " + player.mover.mouseY);
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

    // TEST RECT
    g.rect(500, 500, 25, 25);
}

// On Window Size Change
function resizeCanvas() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
}

