/**
 * @Author: Aimé
 * @Date:   2021-05-17 00:52:34
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-10-12 20:21:46
 */

import { useEffect, useState } from "react";
import { DEFINE } from "../definitions";
import ectoplasm from "../images/ectoplasm.png";
import strange from "../images/strange.png";
import upgrade from "../images/upgrade.png";
import wood from "../images/wood.png";
import { formatText, GET } from "../utils";
import Builder from "./builder";

function Capital({ location: { pathname, state: { coordinate } }, ...rest }) {
    console.log(JSON.stringify(coordinate));
    const [capital, setCapital] = useState({});
    const glossyBTN = (id, innerText) => {
        return <div key={id} className="glossyBTN">
            <span></span>
            <span id={id}>
                {innerText}
            </span>
        </div>
    }

    const getOwnerInfo = (storedResources) => {
        if (!!storedResources) {
            return [
                glossyBTN("cWood", "wood: " + storedResources.BUILDMAT1),
                glossyBTN("cIron", "ectoplasm: " + storedResources.BUILDMAT2),
                glossyBTN("cClay", "strange: " + storedResources.BUILDMAT3)
            ]
        }
        return "";
    }


    useEffect(() => {
        if (!!coordinate.capital.id)
            try {
                GET(formatText("/api/capitals/{0}/", coordinate.capital.id))
                    .then(([responseOK, body]) => {
                        if (responseOK) {
                            let result = JSON.parse(body);
                            if (!!capital)
                                setCapital(result);
                        } else {
                            //TODO handle request errors
                        }
                    });
            } catch (error) {
            }
    }, []);
    const [popupState, setPopupState] = useState(false);
    const togglePopup = () => {
        setPopupState(!popupState);
    }
    const updateCapitals = (capital) => {

        let capitalItems = [];

        if (!!capital.id) {
            console.log(JSON.stringify(capital));
            capitalItems.push(

                <div key={capital.capitalName}>
                    <h3>
                        Name:{capital.capitalName} <button>Edit</button>
                    </h3>
                    <h4 >{"Level " + capital.level}</h4>
                    <h4 > {getOwnerInfo(capital.storedResources)}
                    </h4>
                </div>
            );
            if (!!capital.buildings) {
                console.log(JSON.stringify(capital.buildings));
            }
        }

        return capitalItems.length > 0 ? capitalItems : ""
    }
    const buildingSetup=(resourceTypeProduced)=>{
        switch (resourceTypeProduced) {
            case "BUILDMAT1":
                return <div className="resourceImg">
                <img src={wood} alt="wood"/>
                <img className="upgrade" src={upgrade} alt="upgrade"/>
            </div>
            case "BUILDMAT2":
                return <div className="resourceImg">
                <img src={ectoplasm} alt="ectoplasm"/>
                <img className="upgrade" src={upgrade} alt="upgrade"/>
            </div>
        
            case "BUILDMAT3":
                return <div className="resourceImg">
                <img src={strange} alt="strange"/>
                <img className="upgrade" src={upgrade} alt="upgrade"/>
            </div>
        
            default:
                break;
        } 
    }
    const buildings = (capital) => {
        if (!!capital && !!capital.buildings && !!capital.buildings.MATPRODUCER) {
            let materialProducers = capital.buildings.MATPRODUCER;
            let materialProducersElements = [];
            if (Array.isArray(materialProducers) && materialProducers.length > 0) {
                materialProducers.forEach((materialProducer, i) => {
                    materialProducersElements.push(
                        <div className="buildingItems" key={i.toString() + materialProducer.resourceTypeProduced}>
                            {buildingSetup(materialProducer.resourceTypeProduced.toUpperCase())}
                            {DEFINE(materialProducer.resourceTypeProduced.toUpperCase())}
                        </div>
                    );
                });
                if (materialProducersElements.length > 0) {
                    return <div>
                        {materialProducersElements}
                    </div>
                }
            }
        }

        return <div>

        </div>
    }
    return (
        <div>
            <h2>Town</h2>
            {updateCapitals(capital)}
            <div>
                <button onClick={togglePopup}>build</button>
            </div>
            <h3>Buildings</h3>
            {buildings(capital)}
            <Builder town={capital} popupState={popupState} togglePopup={togglePopup} />
        </div>);
}
export default Capital;