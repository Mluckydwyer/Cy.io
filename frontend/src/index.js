import React from 'react';
import ReactDOM from 'react-dom';
import './css/base/Base.css';
import TopNavbar from './components/base/TopNavbar';
import ScrollingBanner from './components/base/ScrollingBanner';
import StaticBanner from './components/base/StaticBanner';
import GameCardList from './components/home/GameCardList';
import BottomFooter from './components/base/BottomFooter';

ReactDOM.render(
    <div>
        <TopNavbar/>

        <main role="main">
            <StaticBanner/>
            <GameCardList/>
        </main>

        <BottomFooter/>
    </div>
    , document.getElementById('root'));


let gamesReq = new XMLHttpRequest();
gamesReq.onreadystatechange = function() {
    if (gamesReq.readyState === 4 && gamesReq.status === 200) { // On sucessful response
        console.log(gamesReq.responseText);
        let parsed = JSON.parse(gamesReq.responseText);

    } else if (gamesReq.status >= 400){ // Handle errors
        console.log("An error occurred when contacting Cy.io APIs");
    }
};
gamesReq.open("GET", "http://localhost:8080/gamelist");
gamesReq.send();

// ReactDOM.render(
//
// , document.getElementById('card-root'));

