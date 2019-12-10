import React from 'react';
import ReactDOM from 'react-dom';
import './css/base/Base.css';
import App from './App';
import {BrowserRouter} from "react-router-dom";


ReactDOM.render(
    <BrowserRouter>
        <App />
    </BrowserRouter>
    , document.getElementById('root'));


// ReactDOM.render(
//
// , document.getElementById('card-root'));

