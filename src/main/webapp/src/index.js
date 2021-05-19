/**
 * @Author: Aimé
 * @Date:   2021-05-09 00:24:09
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:13:21
 */

import React from "react"; 
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";

window.onload = function () {
  ReactDOM.render(
    <App />,
    document.getElementById("root")
  );
}