import React from 'react';
import '../../css/base/TopNavbar.css';

function TopNavbar() {
    return (
        <header>
            <div className="collapse bg-dark nav-cyio" id="navbarHeader">
                <div className="container">
                    <div className="row">
                        This will show ehn the hamburger icon is pressed!
                    </div>
                </div>
            </div>
            <div className="navbar navbar-dark bg-dark shadow-sm nav-cyio">
                <div className="container d-flex justify-content-between">
                    <a href="#" className="navbar-brand d-flex align-items-center">
                        <i className="fas fa-chess" width="40" height="40" style={{paddingRight: "10px"}}></i>
                        <strong>Cy.io</strong>
                    </a>
                    <button className="navbar-toggler nav-arrow-cyio" type="button" data-toggle="collapse"
                            data-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false"
                            aria-label="Toggle navigation">
                        {/* <span class="navbar-toggler-icon"></span>*/}
                        <i className="fas fa-angle-down nav-arrow-cyio"></i>
                    </button>
                </div>
            </div>
        </header>
    );
}

export default TopNavbar;
