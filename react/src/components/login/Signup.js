import React, {useState} from 'react';
import {Redirect} from "react-router-dom";

function Signup(){
    const signupCheck = localStorage.getItem("success");
    const [signup, setSignup] = useState(signupCheck);
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    
    function handleChangeUser(event){
        setUserName(event.target.value);
        console.log(event.target.value);
    }
    function handleChangePass(event){
        setPassword(event.target.value);
    }
    function handleChangeEmail(event) {
        setEmail(event.target.value);
        console.log(event.target.value);
    }
    
    function signupRequest() {
        const API = 'http://coms-309-nv-4.misc.iastate.edu:8080/auth/signup';
        fetch(API, {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(({
                userName: userName,
                email: email,
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
            localStorage.setItem("success",JSON.stringify(data));
            setSignup(true);
            return data;
        }).then(console.log);
        console.log(localStorage.getItem("success"));
    }
    
    if(signup){
        localStorage.removeItem("success");
        return <Redirect to="/" />;
    }
    return (
        <div>
            <form onSubmit={signupRequest}>
                <label>E-mail
                    <input type="email" name="Email" value={email} onChange={handleChangeEmail} />
                </label>
                <label>UserName
                    <input type="text" name = "userName" value={userName} onChange={handleChangeUser}/>
                </label>
                <label>Password
                    <input type="password" name = "password" value={password} onChange={handleChangePass}/>
                </label>
                <label>
                    <input type="submit" value="Submit" />
                </label>
            </form>
        
        </div>
    )
}
export default Signup;