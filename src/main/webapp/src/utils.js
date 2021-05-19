/**
 * @Author: Aimé
 * @Date:   2021-05-11 01:17:01
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:13:36
 */

export const formatText = (mainString, ...argumentsArray) => {
  for (const key in argumentsArray) {
    if (argumentsArray.hasOwnProperty(key)) {
      const element = argumentsArray[key];
      mainString = mainString.replace("{" + key + "}", element);

    }
  }
  return mainString;
}

let resourcePrefix = "/bgame";
export const userResource = (resourcePath) => {
  return resourcePrefix.concat(resourcePath);
}
export const GET = (apiPath, options) => {
  return fetch(userResource(apiPath), options)
    //source: https://github.com/github/fetch/issues/203#issuecomment-335786498
    .then(response => Promise.all([response.ok, response.text(), response.headers.entries()]))
    .then(([responseOK, body, responseHeaderEntries]) => {
      return [responseOK, body, responseHeaderEntries];
    });
}

export const addClasses = (...classList) => {
  return classList.filter(falsy => !!falsy).join(" ");
}
