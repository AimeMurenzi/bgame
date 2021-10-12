/**
 * @Author: Aimé
 * @Date:   2021-05-12 13:03:31
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:07:17
 */
import React  from "react";
import { Route, Redirect } from "react-router-dom";
import { isLoginValid } from "./session-manager";

function DefaultRoute({component:Component, ...rest}) { 
    return (
        <Route {...rest} render={
            // (props)=>isLoginValid()?
            // <Redirect to={{pathname:"/world"}}/>:<Component {...props}/>
            (props)=><Component {...props}/>
        } />
    );
}
export default DefaultRoute;