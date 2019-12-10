export { Entity };

function Entity() {
    this.xPos = 0;
    this.yPos = 0;
    this.size = 0;
    this.color = "#000000";
    this.scoreValue = 0;
    this.id = "";

    this.init = function (entity) {
        this.xPos = entity.xPos;
        this.yPos = entity.yPos;
        this.size = entity.size;
        this.color = entity.color;
        this.scoreValue = entity.scoreValue;
        this.id = entity.id;
        return this;
    };

    this.draw = function (g) {
        g.beginPath(); // Draw a path that is an unfilled circle
        g.arc(this.xPos, this.yPos, this.size, 0, 2 * Math.PI, false);
        g.fillStyle = this.color; // Fill it with color
        g.fill(); // fill it in
        g.lineWidth = 1;
        g.strokeStyle = '#333000'; // Have an outline of dark grey
        g.stroke();
    }
}