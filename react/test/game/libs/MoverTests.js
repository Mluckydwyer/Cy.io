let expect = require('chai').expect;
import { Config } from '../../../public/game/res/javascript/controllers/config.mjs';
import { Mover } from '../../../public/game/res/javascript/controllers/mover.mjs';
let jsdom = require("jsdom");
let { JSDOM } = jsdom;
let fs = require('fs');
let path = require('path');
let filePath = path.join(__dirname, '../../../public/game/index-temp.html');

fs.readFile(filePath, {encoding: 'utf-8'}, function(err,data){
    if (!err) {
        const { window } = new JSDOM(data);

        function copyProps(src, target) {
            Object.defineProperties(target, {
                ...Object.getOwnPropertyDescriptors(src),
                ...Object.getOwnPropertyDescriptors(target),
            });
        }

        global.window = window;
        global.document = window.document;
        global.navigator = {
            userAgent: 'node.js',
        };
        global.requestAnimationFrame = function (callback) {
            return setTimeout(callback, 0);
        };
        global.cancelAnimationFrame = function (id) {
            clearTimeout(id);
        };
        copyProps(window, global);

    } else {
        console.log(err);
    }
});

let fakeDefaultConfig = {
    "game": {
        "gameId": "xxxxxxxx-xxxx-Mxxx-Nxxx-xxxxxxxxxxxx",
        "title": "Title",
        "authors": []
    },
    "players": {
        "shape": {
            "playerShape": "circle",
            "playerSize": 0,
            "playerColor": "blue",
            "playerOutline": "#003300",
            "enemyShape": "circle",
            "enemySize": 0,
            "enemyColor": "red",
            "enemyOutline": "#003300"
        },
        "movement": {
            "playerSpeed": 0,
            "movementStyle": "linear",
            "allowKeyboard": true,
            "allowMouse": true
        }
    }
};

describe('Mover Lib', function() {

    // add a test hook
    beforeEach(function() {});

    afterEach(function () {});

    it('#normalize()', function() {
        let config = new Config();
        config.load(JSON.stringify(fakeDefaultConfig));
        let mover = new Mover().init(config);
        mover.targetX = 10;
        mover.targetY = 62;
        mover.normalize();

        expect(mover.targetX).to.be.closeTo(0.1592324388, 0.01);
        expect(mover.targetY).to.be.closeTo(0.9872411207, 0.01);
        expect(mover.mag).to.equal(1);
    });

    it('#updateMag()', function() {
        let config = new Config();
        config.load(JSON.stringify(fakeDefaultConfig));
        let mover = new Mover().init(config);
        mover.targetX = 10;
        mover.targetY = 62;
        mover.updateMag();

        expect(mover.mag).to.be.closeTo(62.80127387, 0.01);
    });

    it('#move()', function() {
        let config = new Config();
        config.load(JSON.stringify(fakeDefaultConfig));
        let mover = new Mover().init(config);
        mover.targetX = 10;
        mover.targetY = 62;
        mover.speed = 5;
        mover.move();

        expect(mover.xPos).to.equal(50);
        expect(mover.yPos).to.equal(310);
    });

});