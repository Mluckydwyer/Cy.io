import React, { createContext, useContext, Component } from 'react';

export const AuthContext = createContext();

export function useAuth() {
    return useContext(AuthContext);
}

function loginCheck() {
    if(localStorage.getItem("token")!==null){
        return true;
    }
    else return false;
}
class InfoProvider extends Component {
    constructor(props){
        super(props);
        this.state = {
            user : JSON.parse(localStorage.getItem("info")),
            isLoggedIn: loginCheck(),
        };
        this.setTokens = this.setTokens.bind(this);
        this.updateInfo = this.updateInfo.bind(this);
        this.userInfo = this.userInfo.bind(this);
    }
    
    setTokens = (data) => {
        localStorage.setItem("tokens", JSON.stringify(data));
    };
    
    updateLogin = (data) => {
      this.setState({isLoggedIn: data});  
    };
    updateInfo = (data) =>{
        this.setState({user: data});
    };
    
    userInfo(){
        
        const API = 'http://coms-309-nv-4.misc.iastate.edu:8080/user/me';
        fetch(API, {
            method: 'get',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("token")).accessToken,
            },
            
        }).then(response => {
            if (response.ok) {
                return response.json();
            }
            else {
                throw new Error('Something went wrong...');
            }
        }).then(data => {
            this.setState({user: data});
            localStorage.setItem("info", JSON.stringify(data));
            return data;
        });
        
        
    }
    
    render() {
        return (
            <AuthContext.Provider value = {
                {
                    state: this.state,
                    setUserInfo: this.updateInfo,
                    setAuthTokens: this.setTokens,
                    userInfo: this.userInfo,
                    setLogin: this.updateLogin,
                }}>
                
                {this.props.children}
                
            </AuthContext.Provider>
        );
    }
}
export default InfoProvider;