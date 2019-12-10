import React, {useState} from 'react';
import { useAuth } from "../../routes/auth";
import '../../css/login/Admin.css';
import { Redirect } from "react-router-dom";
import {userInfo} from "./User";

function Admin(){
    const [isLoggedIn, setLoggedIn] = useState(false);
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    
    const { setAuthTokens } = useAuth();
    //const referer = props.location.state.referer || '/';
    
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
        console.log(localStorage.getItem("tokens"));
    }

    function deleteUser() {
        let text = document.getElementById("dg-text");
        let token = localStorage.getItem("token");
        const url = 'http://coms-309-nv-4.misc.iastate.edu:8080/deleteuser?user=' + text;
        fetch(url, {
            method: 'post',
            headers: {
                'Authorization': 'Bearer ' + token,
            },
            credentials: "include",
        }).then(response => {
            if (response.ok) {
                showSnackbar(response.text());
            }
            else {
                throw new Error('Something went wrong...');
            }
        });
    }

    function deleteGame() {
        let text = document.getElementById("du-text");
        let token = localStorage.getItem("token");
        const url = 'http://coms-309-nv-4.misc.iastate.edu:8080/deletegame?game=' + text;
        fetch(url, {
            method: 'post',
            headers: {
                'Authorization': 'Bearer ' + token,
            },
            credentials: "include",
        }).then(response => {
            if (response.ok) {
                showSnackbar(response.text());
            }
            else {
                throw new Error('Something went wrong...');
            }
        });
    }

    function toggleAdmin() {
        let text = document.getElementById("ta-text");
        let token = localStorage.getItem("token");
        const url = 'http://coms-309-nv-4.misc.iastate.edu:8080/toggleadmin?user=' + text;
        fetch(url, {
            method: 'post',
            headers: {
                'Authorization': 'Bearer ' + token,
            },
            credentials: "include",
        }).then(response => {
            if (response.ok) {
                showSnackbar(response.text());
            }
            else {
                throw new Error('Something went wrong...');
            }
        });
    }

    function showSnackbar(text) {
        let sb = document.getElementById("snackbar");
        sb.innerHTML = text;
        sb.className = "show";
        setTimeout(function () {
            sb.className = sb.className.replace("show", "");
        }, 3000)
    }

    if(isLoggedIn) {
        return (<div>
            <div id="snackbar"></div>
            <form onSubmit={loginRequest}>
                <label>Delete Game
                    <input id="dg-text" type="text"/>
                    <button onClick={deleteUser}>Delete</button>
                </label>
                <label>Delete User
                    <input id="du-text" type="text"/>
                    <button onClick={deleteGame}>Delete</button>
                </label>
                <label>Toggle Admin (User)
                    <input id="ta-text" type="text" />
                    <button onClick={toggleAdmin}>Delete</button>
                </label>
            </form>
        </div>)
    }   

    }


export default Admin;