/**
 * @Author: Aimé
 * @Date:   2021-05-09 00:26:35
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:04:52
 */

import React, { useState } from "react";
import { Route } from "react-router";
import { isLoginValid, setToken } from "./session-manager";
import { addClasses, formatText, POST, request } from "./utils";
function Login({ component: Component, ...rest }) {
    const [error, setError] = useState(null);
    const username = InputChangeListener("");
    const password = InputChangeListener("");
    const createAccount = (username, password) => {
        const account={
            name:username,
            password:password 
        };
        let apiPath = "/api/users/create/account"; 
        POST(apiPath, JSON.stringify(account))
            .then(([responseOK, body]) => {
                if (responseOK) {
                    rest.history.push("/login");
                } else {
                    setError(body);
                }
            });
    }
    const login = (username, password) => {
        let apiPath = "/api/users/login";
        let usernamePasswordBase64 = window.btoa(formatText("{0}:{1}", username.value, password.value));
        let options = {
            method: "GET",
            headers: {
                "Accept": "text/plain",
                "Authorization": "Basic " + usernamePasswordBase64
            }
        }; 
        request (apiPath,options)
            .then(([responseOK, body, responseHeaderEntries]) => {
                if (responseOK) {
                    setToken(body);
                    if (isLoginValid()) {
                        try {
                            const path = rest.location.state.from.pathname;
                            if (typeof path === "undefined")
                                rest.history.push("/");
                            else
                                rest.history.push(path);
                        } catch (error) {
                            rest.history.push("/");
                        }

                    }
                } else {
                    setError(body);
                }
            });
    }
    const handleLoginButton = () => {
        setError(null);
        console.log(rest.location.pathname);
        login(username, password);
    }
    const handleCreateAccountButton = () => {
        setError(null);
        createAccount(username.value,password.value);
    }
    const toLogin = () => {
        rest.history.push("/login");
    }
    const toCreateAccount = () => {
        rest.history.push("/create-account");
    }
    const generateContext = (pathname) => {
        switch (pathname) {
            case "/create-account":
                return (<div>
                    <input type="button" value="Create Account" className={addClasses("button")} onClick={handleCreateAccountButton} /><br /><br />
                    <input type="button" value="Login" className={addClasses("button")} onClick={toLogin} />
                </div>);

            default:
                return (<div>
                    <input type="button" value="Login" className={addClasses("button")} onClick={handleLoginButton} /><br /><br />
                    <input type="button" value="Create Account" className={addClasses("button")} onClick={toCreateAccount} />
                </div>);
        }
    }
    return (
        <Route {...rest} render={
            (props) => isLoginValid() ?
                <Component {...props} /> :
                <div>
                    {error && <><small style={{ color: 'red' }}>{error}</small><br /></>}<br />
                    <div>
                        Username<br />
                        <input type="text" {...username} />
                    </div>
                    <div>
                        Password<br />
                        <input type="text" {...password} />
                    </div>
                    {generateContext(props.location.pathname)}
                </div>
        } />
    );
}
const InputChangeListener = (initValue) => {
    const [value, setValue] = useState(initValue);
    const handleValueChange = (changedElement) => { setValue(changedElement.target.value); }
    return {
        value,
        onChange: handleValueChange
    }
}
export default Login;