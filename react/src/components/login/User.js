import React, {useState} from 'react';
import {useAuth} from "../../routes/auth";
import {Redirect} from "react-router-dom";

function User(props){
    const { setAuthTokens } = useAuth();
    const loginCheck = localStorage.getItem("token");
    const [isLoggedIn, setLoggedIn] = useState(loginCheck);
    
    
    function logOut(){
        setAuthTokens();
        localStorage.removeItem("token");
        localStorage.removeItem("info");
        setLoggedIn(false);
        
    }
    
    if(!isLoggedIn){
        return <Redirect to="/" />;
    }
    return (
        <div>
            <h1>Welcome {props.userInfo.userName}</h1>
            <h3>Games owned: {props.userInfo.gamesOwned}</h3>
            <button onClick={logOut}>Log out</button>
        </div>
    );
}

export default User;