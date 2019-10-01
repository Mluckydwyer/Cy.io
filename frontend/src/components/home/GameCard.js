import React from 'react';
import '../../css/home/GameCard.css';
import placeholder from '../../img/placeholder.png';

function GameCard() {
    return (
        <div className="col-md-4">
            <div className="card mb-4 shadow-sm card-cyio">
                <img className="card-img-top" src={placeholder} width="100%" height="225"/>
                    <div className="card-body">
                        <p className="card-text">Here is a placeholder where a game would go. This would be its
                            description.</p>
                        <div className="d-flex justify-content-between align-items-center">
                            <div className="btn-group">
                                <button type="button" className="btn btn-sm btn-outline-primary-cyio">Play</button>
                                <button type="button" className="btn btn-sm btn-outline-secondary-cyio">Edit</button>
                            </div>
                            <small className="text-muted">1,000+ Online</small>
                        </div>
                    </div>
            </div>
        </div>
    );
}

export default GameCard;