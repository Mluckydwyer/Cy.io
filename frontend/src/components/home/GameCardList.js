import React, {Component} from 'react';
import '../../css/home/GameCardList.css';
import GameCard from "./GameCard";

class GameCardList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cards: [],
            isLoading: false,
            error: null,
        };
    }
    
    componentDidMount() {
        const API = 'http://coms-309-nv-4.misc.iastate.edu:8080/gamelist';
        this.setState({isLoading : true});
        fetch(API)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                else {
                    throw new Error('Something went wrong...');
                }
            })
            .then(data => {
                this.setState({ cards:data, isLoading : false })
            })
            .catch(error => this.setState({error, isLoading : false}));
        
    }
    
    render() {
        
        console.log(this.state);
        console.log(this.state.cards[0]);
        const cards = this.state.cards.map((card)=>
            <GameCard key = {card.gameID}
                      value = {card}/>
        );
        console.log(cards);
        return cards;
    }
}


export default GameCardList;


/*
let gamesReq = new XMLHttpRequest();
gamesReq.onreadystatechange = function() {
    if (gamesReq.readyState === 4 && gamesReq.status === 200) { // On successful response
        console.log(gamesReq.responseText);
        let parsed = JSON.parse(gamesReq.responseText);
        
    } else if (gamesReq.status >= 400){ // Handle errors
        console.log("An error occurred when contacting Cy.io APIs");
    }
};
gamesReq.open("GET", "http://localhost:8080/gamelist");
gamesReq.send();*/
