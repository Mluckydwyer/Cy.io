import React, {useState} from 'react';
import {useAuth} from "../../routes/auth";
import {Redirect} from "react-router-dom";

function User(){
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
            <div>User Page</div>
            
            <button onClick={logOut}>Log out</button>
        </div>
    );
}

export default User;