function Mover() {
    this.xPos = 0;
    this.yPos = 0;
    this.targetX = 0;
    this.targetY = 0;
    this.mag = 0;
    this.speed = 5;
    this.radius = 0;

    this.mouseDeadzone = 5;
    this.enableMouse = true;
    this.enableKeys = true;
    this.expMovement = false;

    // Mouse and Keyboard tracking variables
    this.keys = [false, false, false, false, false, false, false, false]; // Key Order= Left; Up; Right, Down, A, W, D, S
    this.xBound = 0;
    this.yBound = 0;
    this.mouseX = 0;
    this.mouseY = 0;
    this.lastMouseX = 0;
    this.lastMouseY = 0;
    this.mouseOnScreen = true;

    this.inputs = {
        KEYBOARD: 'keyboard',
        MOUSE: 'mouse'
    };
    this.inputSource = this.inputs.MOUSE; // Last used input source and one used to update movement

    this.init = function () {
        this.initListeners();
    };

    // Establish event listeners
    this.initListeners = function () {
        // Control Listeners
        if (this.enableKeys) document.addEventListener('keyup', this);
        if (this.enableKeys) document.addEventListener('keydown', this);
        if (this.enableMouse) document.addEventListener('wheel', this);
        if (this.enableMouse) document.addEventListener('click', this);
        if (this.enableMouse) document.addEventListener('mouseleave', this);
        if (this.enableMouse) document.addEventListener('mousemove', this);
    };

    // Update movement vector magnitude
    this.updateMag = function mag() {
        this.mag = Math.sqrt(Math.pow(this.targetX, 2) + Math.pow(this.targetY, 2));
    };

    // Set the target movement coords
    this.setCoords = function (x, y) {
        this.targetX = x;
        this.targetY = y;
        this.updateMag();
    };

    this.centerPos = function () {
        this.xPos = canvas.width / 2 + this.radius / 2;
        this.yPos = canvas.height / 2 + this.radius / 2;
    };

    this.updateVectorKeys= function () {
        // TODO
    };

    // Normalize movement vector
    this.normalize = function() {
        this.updateMag();

        this.targetX /= this.mag;
        this.targetY /= this.mag;
        if (this.mag === 0) {
            this.targetX = 0;
            this.targetY = 0;
        }
        this.updateMag();
    };

    // Update movement based on current input
    this.update = function() {
        this.updateInputSource(); // Update input source

        // Use the current input source to get current movement data
        switch (this.inputSource) {
            case 'keyboard':
                this.checkKeys(); // Keyboard input
                break;
            case 'mouse':
                this.checkMouse(); // Mouse input data
                break;
        }

        this.move(); // Execute calculated move
    };

    // Move player
    this.move = function () {
        this.xPos += this.targetX * this.speed;
        this.yPos += this.targetY * this.speed;

        // Check Boundaries
        if (this.xPos > )

        console.log("x: " + this.targetX + "  y: " + this.targetY + " s: " + this.speed)
    };

    // Check for current input source
    this.updateInputSource = function () {
        // Mouse
        if (this.enableMouse && this.mouseOnScreen) {
            let mouseMoved = this.mouseX !== this.lastMouseX || this.mouseY !== this.lastMouseY;
            if (mouseMoved) this.inputSource = this.inputs.MOUSE;
        }

        // Keyboard
        if (this.enableKeys) {
            let anyKeyPressed = false;
            for (let key in this.keys) {
                if (key) anyKeyPressed = true;
            }

            if (anyKeyPressed) this.inputSource = this.inputs.KEYBOARD;
        }
    };

    // TODO NOT FULLY IMPLEMENTED
    // Handles Keyboard input
    this.checkKeys = function () {
        // Keyboard Controls
        if ((this.keys[0] || this.keys[4]) && (this.targetX + this.radius / 2 >= 0))
            this.targetX -= this.speed; // Left
        if ((this.keys[1] || this.keys[5]) && (this.targetX + this.radius / 2 >= 0))
            this.targetY -= this.speed; // Up
        if ((this.keys[2] || this.keys[6]) && (this.targetX + this.radius / 2 <= canvas.width))
            this.targetX += this.speed; // Right
        if ((this.keys[3] || this.keys[7]) && (this.targetX + this.radius / 2 <= canvas.height))
            this.targetY += this.speed; // Down


        this.normalize();
    };

    // Handles mouse input
    this.checkMouse = function () {
        // Mouse Controls
        let deltaX = this.mouseX - this.xPos;
        let deltaY = this.mouseY - this.yPos;

        this.setCoords(deltaX, deltaY);
        if (this.mag <= this.mouseDeadzone) {
            this.setCoords(0, 0);
        }

        if (this.expMovement) {
            //Exponential 75
            this.targetX /= 75;
            this.targetY /= 75;
        } else {
            // Linear
            this.normalize();
        }
    };

    /*
        Begin Listener function to listen for browser events
     */

    // On Key Pressed
    this.onkeydown = function(e) {
        e.preventDefault();
        console.log("Keydown Event: " + e.which);

        switch (e.which) {
            case 37: // Left Arrow
                this.keys[0] = true;
                console.log("Left Arrow Pressed");
                break;
            case 65: // 'A' Key
                this.keys[4] = true;
                console.log("\'A\' Key Pressed");
                break;
            case 38: // Up Arrow
                this.keys[1] = true;
                console.log("Up Arrow Pressed");
                break;
            case 87: // 'W' Key
                this.keys[5] = true;
                console.log("\'W\' Key Pressed");
                break;
            case 39: // Right Arrow
                this.keys[2] = true;
                console.log("Right Arrow Pressed");
                break;
            case 68: // 'D' Key
                this.keys[6] = true;
                console.log("\'D\' Key Pressed");
                break;
            case 40: // Down Arrow
                this.keys[3] = true;
                console.log("Down Arrow Pressed");
                break;
            case 83: // 'S' Key
                this.keys[7] = true;
                console.log("\'S\' Key Pressed");
                break;
        }
    };
}

// On Key Released
this.onKeyup = function(e) {
    e.preventDefault();

    switch (e.which) {
        case 37: // Left Arrow
            this.keys[0] = false;
            console.log("Left Arrow Released");
            break;
        case 65: // 'A' Key
            this.keys[4] = false;
            console.log("\'A\' Key Released");
            break;
        case 38: // Up Arrow
            this.keys[1] = false;
            console.log("Up Arrow Released");
            break;
        case 87: // 'W' Key
            this.keys[5] = false;
            console.log("\'W\' Key Released");
            break;
        case 39: // Right Arrow
            this.keys[2] = false;
            console.log("Right Arrow Released");
            break;
        case 68: // 'D' Key
            this.keys[6] = false;
            console.log("\'D\' Key Released");
            break;
        case 40: // Down Arrow
            this.keys[3] = false;
            console.log("Down Arrow Released");
            break;
        case 83: // 'S' Key
            this.keys[7] = false;
            console.log("\'S\' Key Released");
            break;
    }
};

Mover.prototype.handleEvent = function (event) {
    console.log(event.type);
    let method = 'on' + event.type;
    if (this[method]) {
        this[method](event);
    }
};

// On Mouse Click
Mover.prototype.onclick = function(event) {
    event.preventDefault();
    console.log("Click Event: " + event.which);

    player.radius = player.defaultRadius;
};

// On Mouse Leave Screen
Mover.prototype.onmouseleave = function(event) {
    this.mouseOnScreen = false;
    //this.setCoords(0, 0);
    this.targetX = 0;
    this.targetY = 0;
    this.updateMag();
};

// On Mouse Move
Mover.prototype.onmousemove = function(event) {
    this.mouseOnScreen = true;
    this.mouseX = event.clientX;
    this.mouseY = event.clientY;
};

// On Mouse Wheel
Mover.prototype.onwheel = function(event) {
    console.log("Wheel Event: " + event.which);
    event.preventDefault();

    if (event.deltaY > 0) this.radius += player.sizeIncrement;
    else if (this.radius - player.sizeIncrement >= 0) this.radius -= player.sizeIncrement;
};

// var mover = function (x, y) {
//     if (!x) x = 0;
//     if (!y) y = 0;
//
//     this.targetX = x;
//     this.targetY = y;
//     this.targetMag = mag(x, y);
//     this.mouseMovement = true;
//     const mouseDeadzone = 5;
//     const enableMouse = false;
//     const enableKeys = true;
//     const expMovement = true;
//
//     var keys = [false, false, false, false, false, false, false, false]; // Key Order: Left, Up, Right, Down, A, W, D, S
//     var mouseX;
//     var mouseY;
//     var mouseOnScreen = true;
// };




