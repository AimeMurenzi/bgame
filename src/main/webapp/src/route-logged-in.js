/**
 * @Author: Aimé
 * @Date:   2021-05-12 13:14:11
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:13:27
 */

import React from 'react'; 
import { isLoginValid } from './session-manager';
import { Route, Redirect } from "react-router-dom";


function LoggedInRoute({component:Component, ...rest}){
    return (
        <Route {...rest} render={
            (props)=>isLoginValid()?
            (<Component {...props} />):
            (<Redirect to={{pathname:"/login", state:{from:props.location} }}/>)
        }/>
    );
}
export default LoggedInRoute;