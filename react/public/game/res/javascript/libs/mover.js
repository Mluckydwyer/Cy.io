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

    this.init = function (radius) {
        this.initListeners();
        this.radius = radius;
    };

    this.config = function (config) {

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

    this.updateVectorKeys = function () {
        // TODO
    };

    // Normalize movement vector
    this.normalize = function () {
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
    this.update = function (controller) {
        this.targetX = controller.targetX;
        this.targetY = controller.targetY;

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

        // console.log("x: " + this.targetX + "  y: " + this.targetY + " s: " + this.speed)
    };

    // Handles Keyboard input
    this.checkKeys = function () {
        keys = this.keys;

        // Keyboard Controls
        if ((keys[0] || keys[4]) && (this.targetX + this.radius >= 0))
            this.targetX -= this.speed; // Left

        if ((keys[1] || keys[5]) && (this.targetY + this.radius >= 0))
            this.targetY -= this.speed; // Up

        if ((keys[2] || keys[6]) && (this.targetX + this.radius <= canvas.width))
            this.targetX += this.speed; // Right

        if ((keys[3] || keys[7]) && (this.targetY + this.radius <= canvas.height))
            this.targetY += this.speed; // Down

        if (!(keys[0] || keys[4] || keys[2] || keys[6]))
            this.targetX = 0; // If no x-coord keys pressed, reset target

        if (!(keys[1] || keys[5] || keys[3] || keys[7]))
            this.targetY = 0; // If no y-coord keys pressed, reset target

        this.normalize();
        // console.log("x: " + this.xPos + " y: " + this.yPos);
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

}





