/**
 * @Author: Aimé
 * @Date:   2021-05-18 15:26:28
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-18 23:47:46
 */

import React from "react";
import { getToken, isLoginValid } from "./session-manager";
import { addClasses, formatText,GET } from "./utils";
function Popup({ coordinate, popupState, togglePopup, ...rest }) {

    const claimLand = (land) => {
        if (isLoginValid()) {
            let apiPath = formatText("/api/world/claim/{0}/{1}",land.x,land.y);
            let options = {
                method: "GET",
                headers: {
                    "Authorization": "Bearer " + getToken()
                }
            }; 
            GET(apiPath, options)
                .then(([responseOK, body]) => {
                    if (responseOK) {
                        togglePopup();
                    } else {
                        //TODO handle world request errors
                    }
                });
        }
    }
    const checkLandState = (coordinate) => {
        if (coordinate.coordinateType === "LAND") {
            if (!!coordinate.free && coordinate.coordinateType === "LAND") {
                return <button className="button" onClick={() => claimLand(coordinate)}>claim land</button>
            } else {
                return <li>{coordinate.capital.capitalName}</li>
            }
        }
    }
    return (
        <div className={popupState ? addClasses("popup-outer popup-outer-show") : addClasses("popup-outer")}>
            <div className="popup-inner">
                <div onClick={togglePopup} className="button-close">X</div>
                <ul>
                    <li>Location [ {coordinate.x},{coordinate.y} ]</li>
                    <li>{coordinate.coordinateType}</li>
                    {checkLandState(coordinate)}
                </ul>
            </div>
        </div>
    );
}
export default Popup;