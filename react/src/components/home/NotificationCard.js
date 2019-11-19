import React, {Component} from 'react';

class NotificationCard extends Component{
    constructor(props){
        super(props);
        this.state = {
            ws:null
        };
    }
    
    
    connect = () => {
        var ws = new WebSocket('ws://localhost:8080/notifications');
        
        ws.onopen = () => {
            
            console.log("Connecting to notifications socket");
    
            this.setState({ ws: ws });
        };
    
        ws.onerror = err => {
            console.error(
                "Socket encountered error: ",
                err.message,
                "Closing socket"
            );
        
            ws.close();
        };
        
        ws.onmessage = () => {
            
        }
        /*var socket = new SockJS('http://localhost:8080/notifications');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {  
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/notifications',function (frame) {
                console.log("Notification received");
                showSnackbar(frame.body);
            });
        });*/
    };
    componentDidMount() {
        this.connect();
    }
    
    check = () => {
        const { ws } = this.state;
        if (!ws || ws.readyState === WebSocket.CLOSED) this.connect(); //check if websocket instance is closed, if so call `connect` function.
    };
    
    render() {
        return (
            <div>
                {JSON.parse(this.ws)} 
            </div>
        )
    }
           

}


export function showSnackbar(text) {
    let sb = document.getElementById("snackbar");
    sb.innerHTML = text;
    sb.className = "show";
    setTimeout(function () {
        sb.className = sb.className.replace("show", "");
    }, 3000)
}

export default NotificationCard;