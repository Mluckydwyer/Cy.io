export { Controller };

function Controller() {
    this.isEnabled = false;

    // Handles which input is being used
    this.inputs = {
        KEYBOARD: 'keyboard',
        MOUSE: 'mouse'
    };
    this.inputSource = this.inputs.MOUSE; // Last used input source and one used to update movement

    // Mouse and Keyboard tracking variables
    this.keys = [false, false, false, false, false, false, false, false]; // Key Order= Left; Up; Right, Down, A, W, D, S
    this.mouseX = 0;
    this.mouseY = 0;
    this.mouseOnScreen = false;
    this.mouseEnabled = false;
    this.keyboardEnabled = false;

    this.init = function (config, canvas, enable) {
        this.config(config);
        this.addEventListeners(canvas); // Listen for mouse of keyboard events
        if (enable) this.enable(); // Enable control
        return this;
    };

    this.config = function (config) {
        this.mouseEnabled = config.players.movement.allowMouse;
        this.keyboardEnabled = config.players.movement.allowKeyboard;
        return this;
    };

    // Enable Game Controller
    this.enable = function () {
        this.isEnabled = true;
    };

    // Disable Game Controller
    this.disable = function () {
        this.isEnabled = false;
    };

    this.defaultEventHandler = function (event) {
        if (this.isEnabled) {
            console.log(event.type);
            let method = 'on' + event.type;
            if (this[method]) {
                this[method](event);
            }
        }
    };

    this.keyPressed = function (event) {
        if (this.isEnabled && this.keyboardEnabled) {
            event.preventDefault();
            console.log("Keydown Event: " + event.which);
            this.inputSource = this.inputs.KEYBOARD;

            switch (event.which) {
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

            // console.log(this.keys);
        }
    };

    this.keyReleased = function (event) {
         if (this.isEnabled && this.keyboardEnabled) {
            event.preventDefault();
            console.log("Keydown Event: " + event.which);
            this.inputSource = this.inputs.KEYBOARD;

            switch (event.which) {
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

        }
    };

    this.click = function (event) {
        if (this.isEnabled && this.mouseEnabled) {
            event.preventDefault();
            console.log("Click Event: " + event.which);
            this.inputSource = this.inputs.MOUSE;
        }
    };

    this.mouseLeave = function (event) {
        if (this.isEnabled && this.mouseEnabled) {
            this.mouseOnScreen = false;
            this.inputSource = this.inputs.MOUSE;
        }
    };

    this.mouseMove = function (event) {
        if (this.isEnabled && this.mouseEnabled) {
            this.mouseOnScreen = true;
            this.inputSource = this.inputs.MOUSE;
            this.mouseX = event.clientX;
            this.mouseY = event.clientY;
            //console.log("MM X: " + this.mouseX + " Y: " + this.mouseY);
        }
    };

    this.mouseWheel = function (event) {
        console.log("scroll");
        if (this.isEnabled && this.mouseEnabled) {
            event.preventDefault();
            this.inputSource = this.inputs.MOUSE;
        }
    };

    // Add all prototypes to handle browser events
    this.addEventListeners = function (canvas) {
        // Controller.prototype.handleEvent = this.defaultEventHandler; // Handle all default events
        // Controller.prototype.keydown = this.keyPressed(); // On Mouse Click
        // Controller.prototype.keyup = this.keyReleased(); // On Mouse Click
        // Controller.prototype.mouseleave = this.mouseLeave; // On Mouse Leave Screen
        // Controller.prototype.mousemove = this.mouseMove; // On Mouse Move
        // Controller.prototype.wheel = this.mouseWheel; // On Mouse Wheel
        canvas.addEventListener("keydown", this.keyPressed.bind(this), false);
        canvas.addEventListener("keyup", this.keyReleased.bind(this), false);
        canvas.addEventListener("mouseleave", this.mouseLeave.bind(this), false);
        canvas.addEventListener("mousemove", this.mouseMove.bind(this), false);
        canvas.addEventListener("wheel", this.mouseWheel.bind(this), false);
        canvas.addEventListener("click", this.click.bind(this), false);

        //window.addEventListener("handleEvent", this.defaultEventHandler.bind(this), false);
        // canvas.addEventListener("mousemove", this.mouseMove, false);
    };
}

