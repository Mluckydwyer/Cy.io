export { Mover };

function Mover() {
    this.width = 0; // TODO implement fixed game size independent of canvas size
    this.height = 0; // TODO implement fixed game size independent of canvas size
    this.xPos = 0;
    this.yPos = 0;
    this.targetX = 0;
    this.targetY = 0;
    this.mag = 0;
    this.speed = 5;
    this.size = 0;
    this.radiusOffset = 0;
    this.keys = [];
    this.collisions = [];

    this.mouseDeadzone = 5;
    this.expMovement = false; // TODO Make enum

    this.init = function () {
        return this;
    };

    this.config = function (config) {
        this.size = config.players.shape.playerSize;
        this.speed = config.players.movement.playerSpeed;
        this.expMovement = (config.players.movement.movementStyle === 'exponential');
        this.width = config.gameplay.width;
        this.height = config.gameplay.height;

        this.centerPos();
        return this;
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

    this.setKeys = function (keys) {
        this.keys = keys;
    };

    this.centerPos = function () {
        this.xPos = canvas.width / 2 + this.size / 2;
        this.yPos = canvas.height / 2 + this.size / 2;
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
    this.update = function (controller=null) {
        if (controller !== null) {
            if (!controller.mouseOnScreen) { // If mouse has left the screen don't update position
                this.setCoords(0, 0); // Set new mouse coords
                return;
            }
            this.setCoords(controller.mouseX, controller.mouseY); // Set new mouse coords
            this.setKeys(controller.keys);

            // Use the current input source to get current movement data
            switch (controller.inputSource) {
                case 'keyboard':
                    this.checkKeys(); // Keyboard input
                    break;
                case 'mouse':
                    this.checkMouse(); // Mouse input data
                    break;
            }
        }

        this.move(); // Execute calculated move
    };

    // Move player
    this.move = function () {
        this.xPos += this.targetX * this.speed;
        this.yPos += this.targetY * this.speed;
    };

    this.checkPlayerCollisions = function (entities, players) {
        for (let i = 0; i < entities.length; i++) {
            let entity = entities[i];
            if (this.isColliding(this.xPos, this.yPos, this.size, entity.xPos, entity.yPos, entity.size, this.radiusOffset)) this.collisions.push(entity);
        }
        // TODO ADD PLAYER COLLISION DETECTION
    };

    this.isTouching = function(xPos, yPos) {
        //return (xPos < this.xPos + this.size && xPos > this.xPos - this.size) && (xPos < this.xPos + this.size && xPos > this.xPos - this.size);
        return this.isColliding(xPos, yPos, 0, this.xPos, this.yPos, this.size);
    };

    this.isColliding = function (xPos1, yPos1, size1, xPos2, yPos2, size2, sizeOffset=0) {
        return Math.sqrt(Math.pow(xPos1 - xPos2, 2) + Math.pow(yPos1 - yPos2, 2)) <= size1 + size2 + sizeOffset;
    };

    // Handles Keyboard input
    this.checkKeys = function () {
        let keys = this.keys;

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

        // console.log("X: " + this.targetX + " Y: " + this.targetY);
        this.normalize();
    };

    // Handles mouse input
    this.checkMouse = function () {
        // Mouse Controls
        let deltaX = this.targetX - this.xPos;
        let deltaY = this.targetY - this.yPos;

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

// export { Mover };
// module.exports = Mover;
