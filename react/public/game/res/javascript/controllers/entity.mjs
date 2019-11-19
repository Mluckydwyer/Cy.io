export { Entity };

function Entity() {
    let xPos;
    let yPos;
    let size;
    let color;
    let id;

    this.draw = function (g) {
        g.beginPath(); // Draw a path that is an unfilled circle
        g.arc(this.xPos, this.yPos, this.size, 0, 2 * Math.PI, false);
        g.fillStyle = '#' + this.color; // Fill it with color
        g.fill();
        g.lineWidth = 1;
        g.strokeStyle = '#003300'; // Have an outline of dark green
        g.stroke();
    }
}