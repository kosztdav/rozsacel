/* eslint-disable prettier/prettier */
import axios from "axios";

const SERVER_URL = "http://localhost:9000/api";

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 1000
});

class Api {

    getAllUsers() {
        return instance.get("users");
    }

    doesUserExist(userName) {
        return instance.get("checkUser/" + userName);
    }

    getUserData(userName, password) {
        return instance.get("login", {
            params: {
                userName,
                password
            }
        })
    }
}

export default new Api();