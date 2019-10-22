function Config() {
    // Initializes values from default value config
    this.init = async function () {
        const response = await fetch('res/javascript/game-config-default.json');
        this.load(await response.text());
        return this;
    };

    this.load = function (json) {
        json = JSON.parse(json);
        Object.keys(json).forEach(key => this[key] = json[key]); // Parse the JSON config file
    };
}

module.exports = Config;