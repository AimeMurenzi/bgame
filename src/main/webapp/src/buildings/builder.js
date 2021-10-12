/**
 * @Author: Aimé
 * @Date:   2021-10-09 13:46:29
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-10-11 15:10:20
 */
import { useEffect, useState } from "react";
import { getToken, isLoginValid } from "../session-manager";
import { addClasses, formatText, GET } from "../utils";


function Builder({town:Town,popupState, togglePopup}) {
    const [town, setCapital] = useState({});
    
    useEffect(() => {
        if (!!Town.id) 
        try {
            GET(formatText("/api/capitals/{0}/", Town.id))
                .then(([responseOK, body]) => {
                    if (responseOK) {
                        let result = JSON.parse(body);
                        if (!!town)
                            setCapital(result);
                    } else {
                        //TODO handle request errors
                    }
                });
        } catch (error) {
        }
    }, []);
    const buildWarehouse=()=>{
        
    }
    const buildService=(buildMaterial)=>{
        if (isLoginValid()) {
            let apiPath = formatText("/api/build/mine/{0}/in/{1}",buildMaterial,  Town.id);
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
                        console.log(body);
                    } else {
                        //TODO handle world request errors
                    }
                });
        }
    }
    const buildWood=()=>{
        buildService("mat1")
    }
    const buildEcto=()=>{
        buildService("mat2")
    }
    const buildStrange=()=>{
        buildService("mat3")
    }

   
    return (
        <div className={popupState ? addClasses("popup-outer popup-outer-show") : addClasses("popup-outer")}>
            <div className="popup-inner">
                <div onClick={togglePopup} className="button-close">X</div>
               <div>
                  <button onClick={buildWarehouse}>build Warehouse</button>
                  <button onClick={buildWood}>build Wood service</button>
                  <button onClick={buildEcto}>build Ectoplasm service</button>
                  <button onClick={buildStrange}>build Strange service</button>
               </div>
            </div>
        </div>
    );
}
export default Builder;