function Mover() {
    this.xPos = 0;
    this.yPos = 0;
    this.targetX = 0;
    this.targetY = 0;
    this.mag = 0;
    this.speed = 0;
    this.radius = 0;

    this.mouseDeadzone = 5;
    this.enableMouse = true;
    this.enableKeys = false;
    this.expMovement = true;

    // Mouse and Keyboard tracking variables
    this.keys = [false, false, false, false, false, false, false, false]; // Key Order= Left; Up; Right, Down, A, W, D, S
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
        if (this.enableKeys) document.addEventListener('keyup', this.onKeyup);
        if (this.enableKeys) document.addEventListener('keydown', this.onKeydown);
        if (this.enableMouse) document.addEventListener('wheel', this.onMouseWheel);
        if (this.enableMouse) document.addEventListener('click', this.onClick);
        if (this.enableMouse) document.addEventListener('mouseleave', this.onMouseLeave);
        if (this.enableMouse) document.addEventListener('mousemove', this.onMouseMove);
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

        // TODO Exponential Movement
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
    this.onKeydown = function(e) {
        e.preventDefault();
        console.log("Keydown Event: " + e.which);

        switch (e.which) {
            case 37: // Left Arrow
                keys[0] = true;
                console.log("Left Arrow Pressed");
                break;
            case 65: // 'A' Key
                keys[4] = true;
                console.log("\'A\' Key Pressed");
                break;
            case 38: // Up Arrow
                keys[1] = true;
                console.log("Up Arrow Pressed");
                break;
            case 87: // 'W' Key
                keys[5] = true;
                console.log("\'W\' Key Pressed");
                break;
            case 39: // Right Arrow
                keys[2] = true;
                console.log("Right Arrow Pressed");
                break;
            case 68: // 'D' Key
                keys[6] = true;
                console.log("\'D\' Key Pressed");
                break;
            case 40: // Down Arrow
                keys[3] = true;
                console.log("Down Arrow Pressed");
                break;
            case 83: // 'S' Key
                keys[7] = true;
                console.log("\'S\' Key Pressed");
                break;
        }
    };

    // On Key Released
    this.onKeyup = function(e) {
        e.preventDefault();

        switch (e.which) {
            case 37: // Left Arrow
                keys[0] = false;
                console.log("Left Arrow Released");
                break;
            case 65: // 'A' Key
                keys[4] = false;
                console.log("\'A\' Key Released");
                break;
            case 38: // Up Arrow
                keys[1] = false;
                console.log("Up Arrow Released");
                break;
            case 87: // 'W' Key
                keys[5] = false;
                console.log("\'W\' Key Released");
                break;
            case 39: // Right Arrow
                keys[2] = false;
                console.log("Right Arrow Released");
                break;
            case 68: // 'D' Key
                keys[6] = false;
                console.log("\'D\' Key Released");
                break;
            case 40: // Down Arrow
                keys[3] = false;
                console.log("Down Arrow Released");
                break;
            case 83: // 'S' Key
                keys[7] = false;
                console.log("\'S\' Key Released");
                break;
        }
    };

    // On Mouse Click
    this.onClick = function(e) {
        e.preventDefault();
        console.log("Click Event: " + e.which);

        player.radius = player.defaultRadius;
    };

    // On Mouse Leave Screen
    this.onMouseLeave = function(e) {
        this.mouseOnScreen = false;
        //this.setCoords(0, 0);
        this.targetX = 0;
        this.targetY = 0;
        this.updateMag();
    };

    // On Mouse Move
    this.onMouseMove = function(e) {
        this.mouseOnScreen = true;
        this.mouseX = e.clientX;
        this.mouseY = e.clientY;
        console.log("mx: " + e.clientX + "  my: " + e.clientY);
    };

    // On Mouse Wheel
    this.onMouseWheel = function(e) {
        console.log("Wheel Event: " + e.which);
        e.preventDefault();

        if (e.deltaY > 0) this.radius += player.sizeIncrement;
        else if (this.radius - player.sizeIncrement >= 0) this.radius -= player.sizeIncrement;
    };
}


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




