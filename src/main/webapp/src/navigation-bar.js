/**
 * @Author: Aimé
 * @Date:   2021-05-15 00:14:29
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:13:15
 */

import { NavLink } from "react-router-dom"; 

function NavigationBar(props) {

    return (
        <header>
            <NavLink exact activeClassName="active" to="/">Home</NavLink>
            <NavLink activeClassName="active" to="/world">World</NavLink>
            {props.loggedIn ? <NavLink activeClassName="active" to="/logout">Logout</NavLink> :
                <NavLink activeClassName="active" to="/login">Login</NavLink>
            } 
        </header>
    );
}
export default NavigationBar;