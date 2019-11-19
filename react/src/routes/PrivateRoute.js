import React from 'react';
import { Route, Redirect } from 'react-router-dom';

function PrivateRoute({ children, ...rest }) {
    const authTokens = localStorage.getItem("token");
    

    
    return (
        <Route
            {...rest}
            render={({location}) =>
                !(authTokens === null) ? (
                    children
                ) : (
                    <Redirect
                        to={{
                            pathname: "/login",
                            state: {from: location}
                        }}
                    />
                    )
            }
        />
        );
}

export default PrivateRoute;