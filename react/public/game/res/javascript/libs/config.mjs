export { Config };
import getConfig from "./requests.mjs";

let defaultConfigUri = 'res/javascript/game-config-default.json';

function Config() {
    // Initializes values from default value config
    this.init = async function () {
        await this.load(defaultConfigUri);
        return this;
    };

    this.load = async function (uri) {
        let json = await getConfig(uri);
        json = JSON.parse(json);
        //this.addProps(json, json);
        Object.keys(json).forEach(key => this[key] = json[key]); // Parse the JSON config file
    };

    this.addProps = function (json, obj) {
        Object.keys(obj).forEach(function (key) {
            if (Object.keys(json[key])) {
                this.addProps(json, json[key]);
            } else {
                this[key] = json[key];
            }
        })
    }
}

//module.exports = Config;