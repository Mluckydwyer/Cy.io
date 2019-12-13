import React from 'react';
import './css/base/Base.css';
import {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import TopNavbar from './components/base/TopNavbar';
import StaticBanner from './components/base/StaticBanner';
import GameCardList from './components/home/GameCardList';
import BottomFooter from './components/base/BottomFooter';
import Login from "./components/login/Login";
import PrivateRoute from "./routes/PrivateRoute";
import InfoProvider from "./routes/auth";
import User from "./components/login/User";
import Signup from "./components/login/Signup";

/*import NotificationCard from './components/home/NotificationCard';*/


class App extends Component {
    //const [user, updateUserInfo] = useState(JSON.parse(localStorage.getItem("info")));
    
/*    signup = localStorage.getItem("success");
    
    if(signup) {
        localStorage.removeItem("success");
    }*/
    render() {
        return (
            <InfoProvider>
                <Router>
                    <div>
                        <TopNavbar/>
                        <Switch>
                            <Route exact path="/">
                                <main role="main">
                                    <StaticBanner/>
                                    <GameCardList/>
                                </main>
                            </Route>
                            <Route path="/login" component={Login}/>
                            <PrivateRoute path="/user">
                                <User />
                            </PrivateRoute>
                            <Route path="/signup">
                                <Signup/>
                            </Route>
                        </Switch>
                        <BottomFooter/>
                    </div>
                </Router>
            </InfoProvider>
        );
    }
}
export default App;