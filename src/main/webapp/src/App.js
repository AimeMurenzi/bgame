/**
 * @Author: Aimé
 * @Date:   2021-05-09 00:26:35
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-18 23:58:46
 */
import React from "react";
import { BrowserRouter, Switch, Route} from "react-router-dom";
import DefaultRoute from "./route-default";
import Home from "./home";
import "./index.css";
import LoggedInRoute from "./route-logged-in";
import Login from "./login";
import NavigationBar from "./navigation-bar";
import World from "./world";

function App(params) {
    return ( 
        <div>
            <BrowserRouter>
                <div>
                    <h1>BGAME</h1>
                    <div className="header">
                        <NavigationBar />
                    </div>
                    <div>
                        <Switch>
                            <Route exact path="/" component={Home} />
                            <DefaultRoute path="/login" component={Login} />
                            <DefaultRoute path="/create-account" component={Login} />
                            <LoggedInRoute path="/world" component={World} />
                        </Switch>
                    </div>

                </div>
            </BrowserRouter>
        </div>
    );
}
export default App;
