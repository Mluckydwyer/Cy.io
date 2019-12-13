import {Mover} from "./mover.mjs";

// export { Player };

export function Player() {
    // Object that handles player movement and input

    let mover;
    let name = "";
    let playerId = "";
    let color = "";
    let isClientPlayer = false;
    let lastUpdate;

    this.init = function (config=null, loadConfig=false, isClientPlayer=false) {
        this.mover = new Mover().init();
        this.name = "You";
        this.playerId = this.generateUUID();
        this.color = this.generateRandomColor();
        this.isClientPlayer = isClientPlayer;
        lastUpdate = new Date().getTime();
        if (loadConfig) this.config(config);
        return this;
    };

    this.config = function (config) {
        this.mover.config(config);
        this.color = this.generateRandomColor(); // overwrite config color
        return this;
    };

    // Draw the player TODO allow more than circles
    this.draw = function(g) {
        // console.log("Name: " + this.name + " X: " + this.mover.xPos + " Y: " + this.mover.yPos + " R: " + this.mover.size);
        g.beginPath(); // Draw a path that is an unfilled circle
        g.arc(this.mover.xPos, this.mover.yPos, this.mover.size, 0, 2 * Math.PI, false);
        g.fillStyle = this.color; // Fill it with color
        g.fill();
        g.lineWidth = 1;
        g.strokeStyle = '#333'; // Have an outline of dark green
        g.stroke();
    };

    // Generate a unique UUID for the playerId
    this.generateUUID = function () {
        let dt = new Date().getTime();
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            let r = (dt + Math.random() * 16) % 16 | 0;
            dt = Math.floor(dt / 16);
            return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
        });
    };

    // Generate a random color
    this.generateRandomColor = function() {
        let letters = '0123456789ABCDEF';
        let color = '#';
        for (let i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    };
}