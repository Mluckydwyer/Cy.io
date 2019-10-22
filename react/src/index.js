import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from "react-router-dom";
import './css/base/Base.css';
import TopNavbar from './components/base/TopNavbar';
import ScrollingBanner from './components/base/ScrollingBanner';
import StaticBanner from './components/base/StaticBanner';
import GameCardList from './components/home/GameCardList';
import BottomFooter from './components/base/BottomFooter';

ReactDOM.render(
    <BrowserRouter>
        <div>
            <TopNavbar/>
            
            <main role="main">
                <StaticBanner/>
                <GameCardList/>
            </main>
            
            <BottomFooter/>
        </div>
    </BrowserRouter>
    , document.getElementById('root'));


// ReactDOM.render(
//
// , document.getElementById('card-root'));

