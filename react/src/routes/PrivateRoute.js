import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { useAuth } from "../routes/auth";

function PrivateRoute({ children, ...rest }) {
    const {authTokens} = useAuth();
    

    
    return (
        <Route
            {...rest}
            render={({location}) =>
                authTokens ? (
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