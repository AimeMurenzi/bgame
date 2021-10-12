/**
 * @Author: Aimé
 * @Date:   2021-05-11 00:20:40
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-07-21 21:51:43
 */
import React, { useEffect, useState } from "react";
import Builder from "./buildings/builder";
import Popup from "./popup-div";
import { getToken, isLoginValid, removeToken } from "./session-manager";
import { SSE } from "./sse";
import { addClasses, formatText, GET, userResource } from "./utils";
import WorldCoordinate from "./world-coordinate"; 


function World(props) {
    const [world, setWorld] = useState(new Map());
    const [coordinate, setClickedCoordinate] = useState({});
    let mountedFlag = true;
    const logout = () => {
        removeToken();
        props.history.push("/");
    }

    
    let eventSource ;
    const startSSE = () => { 
        let sseOptions = {};
        sseOptions.headers = {};
        sseOptions.headers["Last-Event-ID"] = -1;
        // sseOptions.headers["Authorization"] = "Bearer " + getToken();
        let source = new SSE(userResource("/api/sse/subscribe", sseOptions)); 
        source.addEventListener('world-state', function (e) {
            try {
                console.log("e.data");
                updateWorld(e.data);
                // document.getElementById("bob").innerHTML = "<h1> " + e.data + "</h1>";
                sseOptions.headers["Last-Event-ID"] = e.id;
            } catch (ee) {
                console.log("exception " + e.data + "\n" + ee);
            }
        });
        source.stream();

        // eventSource = new EventSource(userResource("/api/sse/subscribe"))
        // eventSource.onmessage = e => {updateWorld(e.data);
        // console.log(e.data);
        // }
    }
     
    const updateWorld = (worldStateJsonString) => {
        if (!!mountedFlag) {
            let newWorldState = new Map();
            let wordStateArray = JSON.parse(worldStateJsonString);
            wordStateArray.forEach(coordinate => {
                newWorldState.set(formatText("{0}:{1}", coordinate.x, coordinate.y), coordinate);
            });
            setWorld(newWorldState);
        }
    }
    useEffect(() => {
        if (!!mountedFlag) {
            startSSE();
            loadWord();
        }
        return () => { mountedFlag = false }
    }, [mountedFlag]);
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
                GET("/api/world")
                    .then(([responseOK, body]) => {
                        if (responseOK) {
                            updateWorld(body);
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