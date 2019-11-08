// import SockJS from "../libs/";

export function Socket() {

    this.SECURED_CHAT = '/secured/chat';
    this.SECURED_CHAT_HISTORY = '/secured/history';
    this.SECURED_CHAT_ROOM = '/secured/room';
    this.SECURED_CHAT_SPECIFIC_USER = '/secured/user/queue/specific-user';
    this.APPLICATION_PREFIX = "/app";

    this.socket = null;
    this.isConnected = false;
    this.url = "";
    this.sessionId = "";
    this.subscriptions = [];

    this.init = function (url) {
        this.url = url;
        return this;
    };
    
    this.connect = function () {
        let connectPromise = new Promise((function (resolve, reject) {
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

        return connectPromise;
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

        this.sendMessage(msg);
    };

    this.sendMessage = function (message, endpoint="/app/SendMessage") {
        this.socket.send(endpoint, {}, JSON.stringify(message));
    }
    
}