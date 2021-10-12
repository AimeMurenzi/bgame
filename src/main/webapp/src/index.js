/**
 * @Author: Aimé
 * @Date:   2021-05-09 00:24:09
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-07-21 17:53:22
 */

 
import React from "react"; 
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import { SSE } from "./sse";

window.onload = function () { 
//   let sseOptions = {};
// sseOptions.headers = {};
// sseOptions.headers["Last-Event-ID"] = -1;
// // sseOptions.headers["Authorization"] = "Bearer " + getToken();
// let source = new SSE("/bgame/api/sse/subscribe", sseOptions);
// console.log("qksflksjfjmsj");
// source.addEventListener('world-state', function (e) {
//     try {
//         console.log("e.data");
//         console.log(e.data);
//         // document.getElementById("bob").innerHTML = "<h1> " + e.data + "</h1>";
//         sseOptions.headers["Last-Event-ID"] = e.id;
//     } catch (ee) {
//         console.log("exception " + e.data + "\n" + ee);
//     }
// });

// source.stream();
  ReactDOM.render(
    <App />,
    document.getElementById("root")
  );
}