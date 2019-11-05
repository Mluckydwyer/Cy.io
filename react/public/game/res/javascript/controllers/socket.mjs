// import SockJS from "../libs/";

export function Socket() {

    this.SECURED_CHAT = '/secured/chat';
    this.SECURED_CHAT_HISTORY = '/secured/history';
    this.SECURED_CHAT_ROOM = '/secured/room';
    this.SECURED_CHAT_SPECIFIC_USER = '/secured/user/queue/specific-user';

    this.socket = null;
    this.isConnected = false;
    this.url = "";

    this.init = function (url) {
        this.url = url;
        return this;
    };
    
    this.connect = function () {
        let socket = new SockJS(this.url);
        let stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            let url = stompClient.ws._transport.url;
            console.log(stompClient.ws._transport.url);
            url = url.replace("ws://localhost:8080/spring-security-mvc-socket/secured/room/",  "");
            url = url.replace("/websocket", "");
            url = url.replace(/^[0-9]+\//, "");
            console.log("Your current session is: " + url);
            this.sessionId = url;
        });

        this.socket = stompClient;
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

    this.sendMessage = function (message) {
        let to = message.to;
        let from = message.from;

        let msg = {
            'from': (from === undefined || from === null ) ? to : from,
            'to': (to === undefined || to === null ) ? "ALL" : to,
            'text': message.text
        };

        console.log(JSON.stringify(msg));
        this.socket.send(this.url, {}, JSON.stringify(msg));
    };
    
}