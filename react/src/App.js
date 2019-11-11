import React, {Component} from 'react';
import './css/base/Base.css';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import TopNavbar from './components/base/TopNavbar';
import StaticBanner from './components/base/StaticBanner';
import GameCardList from './components/home/GameCardList';
import BottomFooter from './components/base/BottomFooter';
import Login from "./components/login/Login";
import PrivateRoute from "./routes/PrivateRoute";
import { AuthContext } from "./routes/auth";


class App extends Component{
    constructor(props){
        super(props);
        this.state = {
            authTokens: null,
        }
    }
    render(){
        return(
            <AuthContext.Provider value = {false}>
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
}
export default App;