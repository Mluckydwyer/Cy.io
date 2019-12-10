import React, {useState} from 'react';
import '../../css/login/Login.css';
import {Link, Redirect} from "react-router-dom";
import {userInfo} from "./userInfo";

function Login(){
    const loginCheck = localStorage.getItem("token");
    const [isLoggedIn, setLoggedIn] = useState(loginCheck);
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    
    //const { setAuthTokens } = useAuth();
    //const referer = props.location.state.referer || '/';
    
    
    function handleChangeUser(event){
        setUserName(event.target.value);
        console.log(event.target.value);
    }
    function handleChangePass(event){
        setPassword(event.target.value);
    }
    
    function loginRequest() {
        const API = 'http://coms-309-nv-4.misc.iastate.edu:8080/auth/login';
        fetch(API, {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(({
                userName: userName,
                password: password,
            })),
            credentials: "include",
        
        }).then(response => {
            if (response.ok) {
                return response.json();
            }
            else {
                throw new Error('Something went wrong...');
            }
        }).then(data => {
            localStorage.setItem("token",JSON.stringify(data));
            setLoggedIn(true);
            return data;
        }).then(console.log);
        console.log(localStorage.getItem("token"));
    }
    
    
    if(isLoggedIn) {
        userInfo();
        return <Redirect to="/user" />;
    }   
    
        return (
            <div>
                {/*<form onSubmit={loginRequest}>*/}
                    <label>Username
                        <input type="text" name = "Username" value={userName} onChange={handleChangeUser}/>
                    </label>
                    <label>Password
                        <input type="password" name = "Password" value={password} onChange={handleChangePass}/>
                    </label>
                    <label>
                        {/*<input type="submit" value="Submit" />*/}
                        <a onClick={loginRequest} style={{marginLeft: "2vw"}} className="btn my-2 btn-prim-cyio raised-btn">Login</a>
                    </label>
                {/*</form>*/}
                <div>
                    <Link to="/signup" className="btn my-2 btn-prim-cyio raised-btn" >Sign Up</Link>
                </div>
            </div>
        )       
    }
export default Login;