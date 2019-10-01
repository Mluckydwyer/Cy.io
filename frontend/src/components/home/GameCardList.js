import React from 'react';
import '../../css/home/GameCardList.css';
import GameCard from '../../components/home/GameCard.js';

function GameCardList() {
    return (
        <div className="games-list-cyio py-5 bg-light">
            <div className="container">
                <div className="row" id='card-root'>
                    {
                        Object.keys(this.props.games).map(key => {
                            return <GameCard>{this.props.games[key]}</GameCard>
                        })
                    }
                </div>
            </div>
        </div>
    );
}

export default GameCardList;