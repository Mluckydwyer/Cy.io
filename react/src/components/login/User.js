import React, {useContext} from 'react';
import {Redirect} from "react-router-dom";
import {AuthContext} from "../../routes/auth.js";

function User(){
    const info = useContext(AuthContext);
    
    function logOut(){
        localStorage.removeItem("token");
        localStorage.removeItem("info");
        info.setUserInfo({userName:"", gamesOwned:""});
        info.setLogin(false);
        
    }
    
    if (!info.state.isLoggedIn) {
        return <Redirect to="/"/>;
    }
    else if (info.state.userInfo !== null) {
        return (
            <div>
                <h1>Welcome {info.state.user.userName}</h1>
                <h3>Games owned: {info.state.user.gamesOwned}</h3>
                <button onClick={logOut}>Log out</button>
            </div>
        );
    }
    else return null;
}

export default User;