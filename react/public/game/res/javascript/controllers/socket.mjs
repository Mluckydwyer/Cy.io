// import SockJS from "../libs/";

export function Socket() {

    const serverUrl = window.location.protocol + "//" + window.location.hostname + ":8080";

    this.socket = null;
    this.isConnected = false;
    this.url = "";
    this.sessionId = "";
    this.subscriptions = [];
    this.sendEndpoint = "";

    this.init = function (url) {
        this.sendEndpoint = "/app/" + url;
        this.url = serverUrl + url;
        return this;
    };
    
    this.connect = function () {
        return new Promise((function (resolve, reject) {
            let socket = new SockJS(this.url);
            let stompClient = Stomp.over(socket);
            this.socket = stompClient;
            this.socket.reconnect_delay = 5000;

            this.socket.connect({}, (function (frame) {
                let url = stompClient.ws._transport.url;
                console.log(stompClient.ws._transport.url);
                url = url.replace("ws://localhost:8080/spring-security-mvc-socket/secured/room/",  "");
                url = url.replace("/websocket", "");
                url = url.replace(/^[0-9]+\//, "");
                console.log("Your current session is: " + url);
                this.sessionId = url;
                this.isConnected = true;
                resolve();
            }).bind(this), function (frame) {
                reject();
            });
        }).bind(this));
    };

    this.disconnect = function () {
        if (!this.isConnected || this.socket === null) {
            console.log("Cannot disconnect from unconnected socket at " + this.url);
            return;
        }

        this.socket.disconnect();
        this.isConnected = false;
        console.log("Socket disconnected at " + this.url);
    };

    this.subscribe = function (endpoint, onMessage=function (frame) {
        console.log(JSON.parse(frame.body));
    }) {
        this.subscriptions.push(this.socket.subscribe(endpoint, onMessage, {}));
    };

    this.sendPlayerDataMessage = function (player) {
        let msg = {
            name: player.name,
            xPos: player.mover.xPos,
            yPos: player.mover.yPos,
            xTarget: player.mover.xTarget,
            yTarget: player.mover.yTarget,
            speed: player.mover.speed,
            size: player.mover.size
        };

        this.sendMessage(msg);
    };

    this.sendChatMessage = function (message) {
        let to = message.to;
        let from = message.from;

        let msg = {
            'from': (from === undefined || from === null ) ? to : from,
            'to': (to === undefined || to === null ) ? "ALL" : to,
            'text': message.text
        };

        this.sendMessage(msg, endpoint);
    };

    this.sendMessage = function (message, endpoint=) {
        this.socket.send(endpoint, {}, JSON.stringify(message));
    }
    
}