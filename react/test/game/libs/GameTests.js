let expect = require('chai').expect;
let fetchMock = require('fetch-mock');
require('isomorphic-fetch');
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

import {updateLeaderboard, toggleChat} from '../../../public/game/res/javascript/game.mjs';
let testLeaders = '[{"name":"Riesha","score":843,"playerId":"309836a5-975b-4662-be68-30b37b686683"},{"name":"Coudio","score":839,"playerId":"6661a0fe-5527-477c-b04e-356376c69b5c"},{"name":"Brodi","score":817,"playerId":"0ab4604d-3957-445c-abec-1909bed080d1"},{"name":"Melissa","score":808,"playerId":"b935ca11-cd69-4e7c-b597-88a645c2f58f"},{"name":"Calvin","score":797,"playerId":"51b56f68-e9d8-4db7-974f-bd32e07f0540"}]';
let leadersCount = 5;

describe('Main Game', function() {

    // add a test hook
    beforeEach(function() {});

    afterEach(function () {
        fetchMock.reset();
    });

    it('#updateLeaderboard()', function () {
        let lb = document.getElementById("lb-list");
        updateLeaderboard();

        expect(lb.childElementCount).to.equal(leadersCount);
    });

    it('#toggleChat()', function() {
        let chat = document.getElementById("chat");
        toggleChat();

        expect(chat.classList).contains("show");

        toggleChat();
        expect(chat.classList).not.contains("hide");
    });

});