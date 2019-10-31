import { Mover } from "./mover.mjs";
// export { Player };

export function Player() {
    // Object that handles player movement and input

    let mover;
    let name = "";

    this.init = function (config) {
        this.mover = new Mover().init();
        this.name = "";
        this.config(config);
        return this;
    };

    this.config = function (config) {
        this.mover.config(config);
        return this;
    };

    // Draw the player TODO allow more than circles
    this.draw = function(g) {
        console.log("X: " + this.mover.xPos + " Y: " + this.mover.yPos + " R: " + this.mover.size);
        g.beginPath(); // Draw a path that is an unfilled circle
        g.arc(this.mover.xPos, this.mover.yPos, this.mover.size, 0, 2 * Math.PI, false);
        g.fillStyle = 'blue'; // Fill it with blue
        g.fill();
        g.lineWidth = 5;
        g.strokeStyle = '#003300'; // Have an outline of dark green
        g.stroke();
    };
}