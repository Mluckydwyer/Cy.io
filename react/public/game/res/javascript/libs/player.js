function Player() {
    // Object that handles player movement and input

    this.init = function (config) {
        this.mover = new Mover().init(config);
        this.name = "";
        this.config(config);
        return this;
    };

    this.config = function (config) {
        return this;
    };

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