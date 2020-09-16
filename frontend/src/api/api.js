/* eslint-disable prettier/prettier */
import axios from "axios";

const SERVER_URL = "http://localhost:9000/api";
//const SERVER_URL = "/api";

const instance = axios.create({
    baseURL: SERVER_URL,

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

    getAllWorkPlaces() {
        return instance.get("getAllWorkPlaces");
    }

    getTimeSheet(userId, year, month) {
        return instance.get("getTimeSheet/" + userId + "/" + year + "/" + month);
    }

    postTimeSheet(userId, from, to, placeId, year, month, day) {
        var bodyFormData = new FormData();
        bodyFormData.set('userId', userId);
        bodyFormData.set('from', from);
        bodyFormData.set('to', to);
        bodyFormData.set('placeId', placeId);
        bodyFormData.set('year', year);
        bodyFormData.set('month', month + 1);
        bodyFormData.set('day', day);
        return instance.post("postAttendance", bodyFormData);
    }

    deleteTimeSheet(userId, year, month, day) {
        var bodyFormData = new FormData();
        bodyFormData.set('userId', userId);
        bodyFormData.set('year', year);
        bodyFormData.set('month', month + 1);
        bodyFormData.set('day', day);
        return instance.post("deleteAttendance", bodyFormData);
    }

    changePassword(userId, oldPass, newPass) {
        var bodyFormData = new FormData();
        bodyFormData.set('userId', userId);
        bodyFormData.set('oldPass', oldPass);
        bodyFormData.set('newPass', newPass);
        return instance.post("changePassword", bodyFormData);
    }

    saveWorkPlace(workPlace) {
        return instance.post("updateWorkPlace", workPlace);
    }

    newWorkPlace(workPlace) {
        var bodyFormData = new FormData();
        bodyFormData.set('name', workPlace.name);
        bodyFormData.set('isActive', workPlace.isActive);
        return instance.post("newWorkPlace", bodyFormData);
    }

    getReport(id, year, month) {
        return instance.get("getReport/" + id + "/" + year + "/" + month);
    }
}

export default new Api();