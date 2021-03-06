import React, {Component} from 'react';
import '../../css/home/GameCard.css';
import placeholder from '../../img/placeholder.png';

class GameCard extends Component {
    
    constructor(props){
        super(props);
        this.state = {title : props.value.title, blurb : props.value.blurb, about : props.value.about, gameID : props.value.gameID, creatorID : props.value.creatorID, thumbnailID : props.thumbnailID,};
    }
    
    
    render() {
        const card = (
            <div className="col-md-4">  
                <div className="card mb-4 shadow-sm card-cyio">
                    <img className="card-img-top" src={this.state.thumbnailID} width="100%" height="225" alt={placeholder}/>
                    <div className="card-body">
                        <p className="card-text">{this.state.title}</p>
                        <div className="d-flex justify-content-between align-items-center">
                            <div className="btn-group">
                                <a href="/game?gameId=123-abc"><button type="button" className="btn btn-sm btn-outline-primary-cyio">Play</button></a>
                                <button type="button" className="btn btn-sm btn-outline-secondary-cyio">Edit</button>
                            </div>
                            <small className="text-muted">1,000+ Online</small>
                        </div>
                    </div>
                </div>
            </div>
        );
        console.log(this.state);
        return card;
    }
}
export default GameCard;