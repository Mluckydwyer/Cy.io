export function Socket() {

    const serverUrl = window.location.protocol + "//" + window.location.hostname + ":8080";
    // const serverUrl = "http://coms-309-nv-4.misc.iastate.edu:8080"; // Dev server for testing

    this.socket = null;
    this.isConnected = false;
    this.url = "";
    this.sessionId = "";
    this.subscriptions = [];
    this.sendEndpoint = "";

    this.init = function (url) {
        this.sendEndpoint = "/app" + url;
        this.url = serverUrl + url;
        return this;
    };
    
    this.connect = function () {
        return new Promise((function (resolve, reject) {
            let socket = new SockJS(this.url);
            let stompClient = Stomp.over(socket);
            stompClient.debug = () => {}; // Stop debug messages so they don't clog browser console
            this.socket = stompClient;
            this.socket.reconnect_delay = 5000;

            this.socket.connect({}, (function (frame) {
                let url = stompClient.ws._transport.url;
                this.url = url;
                console.log("Connected to Socket at: " + stompClient.ws._transport.url);
                url = url.replace("ws://localhost:8080/spring-security-mvc-socket/secured/room/",  "");
                url = url.replace("/websocket", "");
                url = url.replace(/^[0-9]+\//, "");
                // console.log("Your current session is: " + url);
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
        this.sendDisconnectMessage();
        this.socket.disconnect();
        this.isConnected = false;
        console.log("Socket disconnected at: " + this.url);
    };

    this.subscribe = function (endpoint, onMessage=function (frame) {
        console.log(JSON.parse(frame.body));
    }) {
        this.subscriptions.push(this.socket.subscribe(endpoint, onMessage, {}));
    };

    this.sendPlayerDataMessage = function (type, player, entity=null) {
        let msg = {
            type: type,
            playerId: player.playerId,
            payload: null
        };

        switch (type) {
            case "JOIN":
                msg.payload = {username: player.name};
                break;
            case "LEAVE":
                msg.payload = {};
                break;
            case "PLAYER_MOVEMENT":
                msg.payload = {
                    xPos: player.mover.xPos,
                    yPos: player.mover.yPos,
                    targetX: player.mover.targetX,
                    targetY: player.mover.targetY,
                    speed: player.mover.speed,
                    size: player.mover.size,
                    color: player.color,
                    name: player.name
                };
                break;
            case "ENTITIES":
                msg.payload = {id: entity.id};
                break;
        }

        this.sendMessage(msg);
    };

    this.sendChatMessage = function (playerName, message) {
        this.sendMessage("[" + playerName + "]: " + message);
    };

    this.sendDisconnectMessage = function () {
        this.sendMessage({
            type: "LEAVE",
            playerId: player.playerId,
            payload: {}
        });
    };

    this.sendMessage = function (message, endpoint=this.sendEndpoint) {
        // console.log("Sending to endpoint: " + endpoint + "\nMessage: " + JSON.stringify(message));
        this.socket.send(endpoint, {}, JSON.stringify(message));
    }
    
}