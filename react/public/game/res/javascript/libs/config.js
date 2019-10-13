function Config() {
    this.config = null;

    // ----- Default Values -----
    // --- Game ---
    this.gameID = "xxxxxxxx-xxxx-Mxxx-Nxxx-xxxxxxxxxxxx";
    this.title = "Title";
    this.authors = [];

    // --- Players ---
    // Shape
    this.playerShape = "circle";
    this.playerSize = 0;
    this.playerColor = "blue";
    this.playerOutline = "#003300";

    this.enemyShape = "circle";
    this.enemySize = 0;
    this.enemyColor = "red";
    this.enemyOutline = "#003300";

    // Movement
    this.playerSpeed = 0;
    this.movementStyle = "linear";

    this.load = function (json) {
        this.config = JSON.parse(json); // Parse the JSON config file
        // --- Game ---
        let game = config["Game"];
        this.gameID = game["Game ID"];
        this.title = game["Title"];
        this.authors = game["Authors"];

        // --- Players ---
        // Shape
        let shape = config["Shape"];
        this.playerShape = shape["Player Shape"];
        this.playerSize = shape["Player Size"];
        this.playerColor = shape["Player Color"];
        this.playerOutline = shape["Player Outline"];

        this.enemyShape = shape["Enemy Shape"];
        this.enemySize = shape["Enemy Size"];
        this.enemyColor = shape["Enemy Color"];
        this.enemyOutline = shape["Enemy Outline"];

        // Movement
        let move = config["Shape"];
        this.playerSpeed = move["Default Speed"];
        this.movementStyle = move["Movement Style"];
    };
}