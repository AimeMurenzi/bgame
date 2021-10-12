/**
 * @Author: Aimé
 * @Date:   2021-05-11 01:17:01
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:13:36
 */

import { getToken } from "./session-manager";

export const formatText = (mainString, ...argumentsArray) => {
  for (const key in argumentsArray) {
    if (argumentsArray.hasOwnProperty(key)) {
      const element = argumentsArray[key];
      mainString = mainString.replace("{" + key + "}", element);

    }
  }
  return mainString;
}
export const path = (method, location) => {
  let options = {
    method: method,
    headers: {
      "Authorization": "Bearer " + getToken()
    }
  };
  return [location, options];
}

let resourcePrefix = "http://localhost:8080/bgame";
// let resourcePrefix = "/bgame";
export const userResource = (resourcePath) => {
  return resourcePrefix.concat(resourcePath);
}
export const POST = (apiPath, body) => {
  let options = {
    method: "POST", 
    headers: { 
      Accept: "application/json",
      'Content-Type':'application/json'
    },
    body: body
  }
  return request(apiPath, options);
}
export const request = (apiPath, options) => {
  return fetch(userResource(apiPath), options)
  .then(response=>Promise.all([response.ok, response.text(), response.headers.entries()]))
  .then(
    ([responseOK, body, responseHeaderEntries])=>{
      return [responseOK, body, responseHeaderEntries];
    }
  ); 
}
export const GET = (apiPath) => {
  let options = {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + getToken()
    }
  };
  return request(apiPath, options);
}

export const addClasses = (...classList) => {
  return classList.filter(falsy => !!falsy).join(" ");
}
