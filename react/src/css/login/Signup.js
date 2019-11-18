import React from 'react';
import {useAuth} from "../../routes/auth";

function Signup(){
    const { setAuthTokens } = useAuth();
    
    function logOut(){
        setAuthTokens();
        localStorage.removeItem("token");
    }
    
    return (
        <div>
            <div>User Page</div>
            
            <button onClick={logOut}>Log out</button>
        </div>
    );
}

export default Signup;