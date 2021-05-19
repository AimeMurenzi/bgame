/**
 * @Author: Aimé
 * @Date:   2021-05-12 12:53:38
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:02:22
 */
import React from "react"; 
import { NavLink, Redirect, Route } from "react-router-dom";
import { isLoginValid } from "./session-manager";

function Home() {
    return (
        <Route render={
            () => isLoginValid() ?
                <Redirect to={{ pathname: "/world" }} /> :
                <div>
                    <h1>Welcome To BGAME</h1><br/>
                    <NavLink to="/login">Login</NavLink>
                </div> 
        } />

    );
}
export default Home;