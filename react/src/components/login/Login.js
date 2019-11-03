import React, {Component} from 'react';
import '../../css/login/Login.css'

class Login extends Component{
    constructor(props) {
        super(props);
        this.state = {
            userName: "",
            password: "",
        };
        this.loginRequest = this.loginRequest.bind(this);
        this.handleChangeUser = this.handleChangeUser.bind(this);
        this.handleChangePass = this.handleChangePass.bind(this);
        
    }
    
    handleChangeUser(event){
        this.setState({userName: event.target.value});
        console.log(this.state.userName);
    }
    handleChangePass(event){
        this.setState({password: event.target.value});
    }
    
    loginRequest(event) {
        const API = 'http://coms-309-nv-4.misc.iastate.edu:8080/auth/login';
        this.setState({isLoading : true});
        fetch(API, {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(({
                userName:this.state.userName,
                password:this.state.password,
            })),
            credentials: "include",
            
        })
            .then(res => res.json())
            .then(console.log);
    }
    
    render(){
        return (
            <div>
                <form onSubmit={this.loginRequest}>
                    <label>UserName
                        <input type="text" name = "userName" value={this.state.userName} onChange={this.handleChangeUser}/>
                    </label>
                    <label>Password
                        <input type="password" name = "password" value={this.state.password} onChange={this.handleChangePass}/>
                    </label>
                    <label>
                        <input type="submit" value="Submit" />
                    </label>
                </form>
            </div>
        )
    }
}
export default Login;