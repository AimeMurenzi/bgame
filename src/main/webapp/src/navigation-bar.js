/**
 * @Author: Aimé
 * @Date:   2021-05-15 00:14:29
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:13:15
 */

import { NavLink } from "react-router-dom"; 
import { isLoginValid } from "./session-manager";

function NavigationBar(props) {
    const getLoggedInButton=()=>{
        return isLoginValid()?<NavLink activeClassName="active" to="/logout">Logout</NavLink> :
                <NavLink activeClassName="active" to="/login">Login</NavLink>;
    }

    return (
        <header>
            <NavLink exact activeClassName="active" to="/">Home</NavLink>
            {getLoggedInButton()} 
            <NavLink activeClassName="active" to="/world">World</NavLink>
            <NavLink activeClassName="active" to="/capitals">My Capital</NavLink> 
        </header>
    );
}
export default NavigationBar;