import React, {useState} from 'react';
import './css/base/Base.css';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import TopNavbar from './components/base/TopNavbar';
import StaticBanner from './components/base/StaticBanner';
import GameCardList from './components/home/GameCardList';
import BottomFooter from './components/base/BottomFooter';
import Login from "./components/login/Login";
import PrivateRoute from "./routes/PrivateRoute";
import { AuthContext } from "./routes/auth";
import User from "./components/login/User";
import Signup from "./components/login/Signup";
/*import NotificationCard from './components/home/NotificationCard';*/


function App(){
    const userInfo = JSON.parse(localStorage.getItem("info"));
    
    const [authTokens, setAuthTokens] = useState();
    
    const setTokens = (data) => {
        localStorage.setItem("tokens", JSON.stringify(data));
        setAuthTokens(data);
    };   
    
    return(
        <AuthContext.Provider value = {{authTokens, setAuthTokens: setTokens}}>
            <Router>
                <div>
                    <TopNavbar/>
                    <Switch>
                        <Route exact path="/">
                            <main role="main">
                                <StaticBanner/>
                                <GameCardList/>
                            </main>
                        </Route>
                        <Route path="/login" component = {Login}/>
                        <PrivateRoute path="/user">
                            <User userInfo = {userInfo}/>
                        </PrivateRoute>
                        <Route path="/signup">
                            <Signup />
                        </Route>
                    </Switch>
                    <BottomFooter/>
                </div>
            </Router>
        </AuthContext.Provider>
    );
}
export default App;