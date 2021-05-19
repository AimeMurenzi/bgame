/**
 * @Author: Aimé
 * @Date:   2021-05-11 00:20:40
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:11:47
 */
import React, { useEffect, useState } from "react";
import Popup from "./popup-div";
import { getToken, isLoginValid, removeToken } from "./session-manager";
import { addClasses, formatText, GET } from "./utils";
import WorldCoordinate from "./world-coordinate";

function World(props) {
    const [world, setWorld] = useState(new Map());
    const [coordinate, setClickedCoordinate] = useState({});
    const logout = () => {
        removeToken();
        props.history.push("/");
    }
    const updateWorld = (newWorld) => {
        setWorld(newWorld);
    }
    useEffect(() => {
        loadWord();
    });
    const coordinateCallBack = (coordinate) => {
        setClickedCoordinate(coordinate);
        togglePopup();
    }
    const [popupState, setPopupState] = useState(false);
    const togglePopup = () => {
        setPopupState(!popupState);
    }
    const loadWord = () => {
        try {
            if (isLoginValid()) {
                let apiPath = "/api/world";
                let options = {
                    method: "GET",
                    headers: {
                        "Authorization": "Bearer " + getToken()
                    }
                };
                GET(apiPath, options)
                    .then(([responseOK, body]) => {
                        if (responseOK) {
                            let result = JSON.parse(body);
                            let newWorld = new Map();
                            result.forEach(element => {
                                newWorld.set(formatText("{0}:{1}", element.x, element.y), element);
                            });
                            updateWorld(newWorld);
                        } else {
                            //TODO handle world request errors
                        }
                    });
            }
        } catch (error) {
        }

    }

    const makeRows = (world) => {
        let worldSurfaces = [];
        for (let [key, value] of world) {
            let cell = <WorldCoordinate coordinate={value} coordinateCallBack={coordinateCallBack} key={key} />;

            worldSurfaces.push(cell);
        }
        let rows = world.size / 2;
        let cols = world.size / 2;
        let container = <div id="container">
            {worldSurfaces}
        </div>
        return container;
    };

    return (
        <div>
            <input type="button" value="Logout" className={addClasses("button")} onClick={logout} />
            <Popup coordinate={coordinate} popupState={popupState} togglePopup={togglePopup} />
            {makeRows(world)}


        </div>

    );
}
export default World;