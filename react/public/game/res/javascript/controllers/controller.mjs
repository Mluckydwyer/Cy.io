import {toggleChat, sendChat} from "../game.mjs";
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
    this.chatShown = false;

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
        if (this.isEnabled && this.keyboardEnabled && !this.chatShown) {
            event.preventDefault();
            // console.log("Keydown Event: " + event.which);
            this.inputSource = this.inputs.KEYBOARD;

            switch (event.which) {
                case 37: // Left Arrow
                    this.keys[0] = true;
                    break;
                case 65: // 'A' Key
                    this.keys[4] = true;
                    break;
                case 38: // Up Arrow
                    this.keys[1] = true;
                    break;
                case 87: // 'W' Key
                    this.keys[5] = true;
                    break;
                case 39: // Right Arrow
                    this.keys[2] = true;
                    break;
                case 68: // 'D' Key
                    this.keys[6] = true;
                    break;
                case 40: // Down Arrow
                    this.keys[3] = true;
                    break;
                case 83: // 'S' Key
                    this.keys[7] = true;
                    break;
            }

            // console.log(this.keys);
        } else if (this.chatShown && event.which === 27) { // escape key pressed
            toggleChat();
        }
    };

    this.keyReleased = function (event) {
        if (this.isEnabled && this.keyboardEnabled && !this.chatShown) {
            event.preventDefault();
            // console.log("Keydown Event: " + event.which);
            this.inputSource = this.inputs.KEYBOARD;

            switch (event.which) {
                case 37: // Left Arrow
                    this.keys[0] = false;
                    break;
                case 65: // 'A' Key
                    this.keys[4] = false;
                    break;
                case 38: // Up Arrow
                    this.keys[1] = false;
                    break;
                case 87: // 'W' Key
                    this.keys[5] = false;
                    break;
                case 39: // Right Arrow
                    this.keys[2] = false;
                    break;
                case 68: // 'D' Key
                    this.keys[6] = false;
                    break;
                case 40: // Down Arrow
                    this.keys[3] = false;
                    break;
                case 83: // 'S' Key
                    this.keys[7] = false;
                    break;
                case 84: // 'T' Key
                    toggleChat();
            }

        } else if (this.chatShown) {
            switch (event.which) {
                case 27: // 'Escape' Key
                    toggleChat();
                    break;
                case 13: // 'Enter' Key
                    sendChat();
                    toggleChat();
                    break;
            }
        }
    };

    this.click = function (event) {
        if (this.isEnabled && this.mouseEnabled) {
            event.preventDefault();
            //console.log("Click Event: " + event.which);
            this.inputSource = this.inputs.MOUSE;
        }
    };

    this.mouseLeave = function (event) {
        if (this.isEnabled && this.mouseEnabled) {
            event.preventDefault();
            this.mouseOnScreen = false;
            this.inputSource = this.inputs.MOUSE;
        }
    };

    this.mouseMove = function (event) {
        if (this.isEnabled && this.mouseEnabled) {
            event.preventDefault();
            this.mouseOnScreen = true;
            this.inputSource = this.inputs.MOUSE;
            this.mouseX = event.clientX;
            this.mouseY = event.clientY;
            //console.log("MM X: " + this.mouseX + " Y: " + this.mouseY);
        }
    };

    this.mouseWheel = function (event) {
        //console.log("scroll");
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
        document.addEventListener("keydown", this.keyPressed.bind(this), false);
        document.addEventListener("keyup", this.keyReleased.bind(this), false);
        canvas.addEventListener("mouseleave", this.mouseLeave.bind(this), false);
        document.addEventListener("mousemove", this.mouseMove.bind(this), false);
        canvas.addEventListener("wheel", this.mouseWheel.bind(this), false);
        document.addEventListener("click", this.click.bind(this), false);

        //window.addEventListener("handleEvent", this.defaultEventHandler.bind(this), false);
        // canvas.addEventListener("mousemove", this.mouseMove, false);
    };
}

