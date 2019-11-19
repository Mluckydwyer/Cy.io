import React from 'react';
import '../../css/base/StaticBanner.css'
import {
    Link
} from "react-router-dom";
function StaticBanner() {
    

    return (
        <section className="jumbotron text-center main-content">
            <div className="container">
                <h1 className="jumbotron-heading">Cy.io Hosted Games</h1>
                <p className="lead">Cy.io Platform. See blow for games currently hosted on our platform!</p>
                <p>
                    <Link to="/login" className="btn my-2 btn-prim-cyio raised-btn" id="login-btn" >Login</Link>
                </p>
            </div>
        </section>
    );
}

export default StaticBanner;