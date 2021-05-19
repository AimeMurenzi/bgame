/**
 * @Author: Aimé
 * @Date:   2021-05-09 00:41:17
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-05-19 00:09:08
 */

export const getToken = () => {
  return sessionStorage.getItem("token") || null;
}
export const removeToken = () => {
  sessionStorage.removeItem("token");
}
export const setToken = (token) => {
  sessionStorage.setItem("token", token);
}
export const isLoginValid = () => {
  try {
    let token = sessionStorage.getItem("token");
    let exp = Number(JSON.parse(atob(token.split(".")[1])).exp);
    let now = Math.floor(new Date().getTime() / 1000);
    if (!isNaN(exp) && (now < exp)) {
      return true;
    }
  } catch (error) {
  }
  return false;
}