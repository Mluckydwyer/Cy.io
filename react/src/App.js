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


function App(props){
    
    const [authTokens, setAuthTokens] = useState();
    
    const setTokens = (data) => {
        localStorage.setItem("tokens", JSON.stringify(data));
        setAuthTokens(data);
    }   
    
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
                        <Route path="/login">
                            <Login />
                        </Route>
                        <PrivateRoute path="/user"/>
                    </Switch>
                    <BottomFooter/>
                </div>
            </Router>
        </AuthContext.Provider>
    );
}
export default App;