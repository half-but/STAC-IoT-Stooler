let mongoose = require("mongoose");
let connect = require("./connect");
let sign = require("./Sign");

let connectModel;

exports.setConnectModel = (model) => {
    connectModel = model;
}

exports.connectCheckForMobile = (req, res) => {
    let date = req.query.date;
    let coverSSID = req.query.ssid;

    let cookieStr = req.headers["set-cookie"][0];
    cookieStr = cookieStr.split(";")[0];
    let userUUID = cookieStr.split("=")[1];

    sign.getID(userUUID, (id) => {
        console.log("connect check for mobile", id, date, coverSSID);

        connectModel.find({"id": id, "ssid" : coverSSID, "date" : date}, (err, result) => {
            if(err){
                res.sendStatus(500);
                throw err;
            }

            console.log(result);

            if(result.length > 0){
                console.log("connect check connected");
                res.sendStatus(400);
            }else{
                console.log("connect check disconnect");
                res.sendStatus(200);
            }
        });
    });
}

exports.findAP = (req, res) => {
    let coverSSID = req.query.ssid;
    let date = req.query.date;

    let cookieStr = req.headers["set-cookie"][0];
    cookieStr = cookieStr.split(";")[0];
    let userUUID = cookieStr.split("=")[1];

    sign.getID(userUUID, (id) => {
        console.log("find ap", id, coverSSID, date);
        if(id == null){
            res.sendStatus(400);
            return;
        }
        
        connectModel.find({"ssid" : coverSSID}, (err, results) => {
            if(err){
                res.sendStatus(500);
                throw err;
            }
            if (results.length > 0) {
                saveUserData(results, date, res, id);
            } else {
                console.log("find ap fail");
                res.sendStatus(400);
            }
        });
    });
}

let saveUserData = (results, date, res, id) => {
    for (let i = 0; i < results.length; i++) {
        if (compareDate(date, results[i].date)) {
            if (results[i].time) {
                console.log("data save", userID, results[i].date, results[i].color, results[i].time);
                stoolData.saveData(userID, results[i].date, results[i].color, results[i].time, res);
                connectModel.remove({ "ssid": results[i].ssid, "date": results[i].date }, err => {
                    if (err) {
                        throw err;
                    }
                });
            } else {
                console.log("find ap success");
                connectModel.update({ "ssid": results[i].ssid, "date": results[i].date }, { $set: { "id": id } }, (err) => {
                    if (err) {
                        throw err;
                    }
                });
            }
            res.sendStatus(200);
            return;
        }
    }
    res.sendStatus(400);
}

function compareDate(mobileDateString, coverDateString) {
    let mobileDate = new Date(mobileDateString);
    let coverDate = new Date(coverDateString);
    if (mobileDate >= coverDate) {
        coverDate.setSeconds(coverDate.getSeconds() + 30);
        if (mobileDate <= coverDate) {
            return true;
        }
    }
    return false;
}