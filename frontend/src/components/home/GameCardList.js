import React, {Component} from 'react';
import '../../css/home/GameCardList.css';
import GameCard from "./GameCard";

class GameCardList extends Component {
    constructor() {
        super();
        this.state = {
            cards: [],
        };
    }
    
    componentDidMount() {
        const API = 'http://localhost:8080/gamelist';
        fetch(API)
            .then(results => {
                return results.json();
            })
            .then(data => this.setState({ cards: data.cards}));
    }
    
    render() {
        const {cards} = this.state;
        console.log(cards);
        return GameCard();
    }
}


export default GameCardList;


/*
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
gamesReq.send();*/
