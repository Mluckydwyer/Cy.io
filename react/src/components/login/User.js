import React from 'react';
import {useAuth} from "../../routes/auth";

function User(){
    const { setAuthTokens } = useAuth();
    
    function logOut(){
        setAuthTokens();
        localStorage.removeItem("token");
        localStorage.removeItem("info");
    }
    
    return (
        <div>
            <div>User Page</div>
            
            <button onClick={logOut}>Log out</button>
        </div>
    );
}

export default User;