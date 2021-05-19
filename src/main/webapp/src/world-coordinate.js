/**
 * @Author: Aimé
 * @Date:   2021-05-17 00:56:43
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:13:45
 */

import React from 'react-dom';
import { addClasses } from "./utils";


function WorldCoordinate({ coordinate, coordinateCallBack, ...rest }) {

    const occupiedCheck = () => {
        if (!coordinate.free) {
            return <div>
                {coordinate.capital.capitalName}
            </div>
        }
    }
    const handleOnClick = () => {
        coordinateCallBack(coordinate);
    }

    return (
        <div className={addClasses("grid-item", coordinate.coordinateType.toLowerCase())}
            onClick={handleOnClick}
        >
            {occupiedCheck()}
        </div>
    );
}
export default WorldCoordinate;