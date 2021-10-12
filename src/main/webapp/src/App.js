/**
 * @Author: Aimé
 * @Date:   2021-05-09 00:26:35
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-18 23:58:46
 */
import React, { useEffect } from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import DefaultRoute from "./route-default";
import Home from "./home";
import "./index.css";
import LoggedInRoute from "./route-logged-in";
import Login from "./login";
import NavigationBar from "./navigation-bar";
import World from "./world";
import Capital from "./buildings/capital";
import { getToken } from "./session-manager";
import { SSE } from "./sse";
import { userResource } from "./utils";




// source.stream();
// let source;
// let sseOptions = {};
// sseOptions.headers = {};
// sseOptions.headers["Last-Event-ID"] = -1;
// sseOptions.headers["Authorization"] = "Bearer " + getToken();
// source = new SSE(userResource("/api/sse/subscribe"), sseOptions);
// source.addEventListener('world-state', function (e) {
//     try {
//         console.log(e.data);
//         // updateWorld(e.data);
//         sseOptions.headers["Last-Event-ID"] = e.id;
//     } catch (ee) {
//         console.log("exception " + e.data + "\n" + ee);
//     }
// });

// source.stream();
function App(params) {
// useEffect(
// ()=>{
//   console.log("here");
//     const source = new EventSource("http;//localhost:8080/bgame/api/sse/subscribe");

//     source.onmessage = function logEvents(event) {      
//        console.log(event.data)    ;
//     }
// source.addEventListener("world-state",(tt)=>{
// console.log(tt.data);
// });

// },[]

// );


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
                            <LoggedInRoute path="/capital" component={Capital} />
                        </Switch>
                    </div>

                </div>
            </BrowserRouter>
        </div>
    );
}
export default App;
