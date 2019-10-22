let expect = require('chai').expect;
let fetchMock = require('fetch-mock');
require('isomorphic-fetch');
let Config = require('../../../public/game/res/javascript/libs/config');

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

let testConfig = {
    "players": {
        "shape": {
            "defaultSize": 30
        },
        "movement": {
            "defaultSpeed": 5
        }
    }
};


describe('Config Files', function() {

    // add a test hook
    beforeEach(function() {
    });

    afterEach(function () {
        fetchMock.reset();
    });

    it('#init()', async function () {
        fetchMock.mock('res/javascript/game-config-default.json', fakeDefaultConfig);

        let config = new Config();
        config = await config.init();

        expect(config.game).to.have.all.keys(fakeDefaultConfig.game);
        expect(config.players).to.have.all.keys(fakeDefaultConfig.players);
        expect(config.players.movement).to.have.all.keys(fakeDefaultConfig.players.movement);
        expect(config.players.shape).to.have.all.keys(fakeDefaultConfig.players.shape);
    });

    it('#load()', function()  {
        let config = new Config();
        config.load(JSON.stringify(testConfig));

        expect(config.players.shape.defaultSize).to.equal(testConfig.players.shape.defaultSize);
        expect(config.players.movement.defaultSpeed).to.equal(testConfig.players.movement.defaultSpeed);
    });

});