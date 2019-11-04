// import SockJS from "../libs/";

export function Socket() {

    this.socket = null;
    this.isConnected = false;
    this.url = "";

    this.init = function (url) {
        this.url = url;
        return this;
    };
    
    this.connect = function (onOpen=function () {
        console.log('Socket opened at ' + this.url);
    }, onMessage=function (m) {
        console.log("Message received at " + this.url + ":\n" + m.data);
    }, onClose=function () {
        console.log("Socket closed at " + this.url);
    }) {
        let sock = new SockJS(this.url);
        sock.onopen = onOpen;
        sock.onclose = onClose;
        sock.onmessage = onMessage;
        stompClient = Stomp.over(sock);
        stompClient.connect({}, function () {
            this.isConnected = true;
            console.log("Connected over STOMP at " + this.url);
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
    }
    
}