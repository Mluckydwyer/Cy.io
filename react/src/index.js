import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import './css/base/Base.css';
import TopNavbar from './components/base/TopNavbar';
import StaticBanner from './components/base/StaticBanner';
import GameCardList from './components/home/GameCardList';
import BottomFooter from './components/base/BottomFooter';
import Login from "./components/login/Login";

ReactDOM.render(
    <Router>
        <div>
            <TopNavbar/>
            <Switch>
                <Route path="/login">
                    <Login/>
                </Route>
                
                <Route path="/">
            
                    <main role="main">
                        <StaticBanner/>
                        <GameCardList/>
                    </main>
                    
                </Route>
            </Switch>
            <BottomFooter/>
        </div>
    </Router>
    , document.getElementById('root'));


// ReactDOM.render(
//
// , document.getElementById('card-root'));

