let mongoose = require("mongoose");
let stoolData = require("./StoolData");
let sign = require("./Sign");
let cover = require("./Connect/Cover");
let mobile = require("./Connect/Mobile");

let connectModel;

exports.createModel = () => {
    let ConnectUserSchema = mongoose.Schema({
        ssid: { type: String, require: true },
        date: { type: String, require: true },
        id: String,
        color: String,
        time: String
    });

    connectModel = mongoose.model("connectModel", ConnectUserSchema);
}

function delectOldData() {
    let today = new Date();
    today.setDate(today.getDate() - 7);
    console.log(today, "delect Data");

    connectModel.find({}, (err, results) => {
        for (let i = 0; i < results.length; i++) {
            if (new Date(results[i].date) <= today) {
                connectModel.remove({ "ssid": results[i].ssid, "date": results[i].date }, err => {
                    if (err) {
                        throw err;
                    }
                });
            }
        }
    });
}

function dateCheck(req, res, callback) {
    let date = req.query.date;
    console.log("date check", date);

    if (new Date(date) == "Invalid Date") {
        res.sendStatus(400);
        console.log("date send error");
        return;
    } else {
        callback(req, res);
    }
}

//para : ssid, date
//200, 400
exports.seatCover = (req, res) => {
    delectOldData();
    dateCheck(req, res);
}

//para : ssid, date
//200, 400, 500
exports.connectCheckForCover = (req, res) => {
    dateCheck(req, res, cover.connectCheckCover);
}

//para : ssid, date, color, time
//200, 400, 500
exports.saveData = (req, res) => {
    dateCheck(req, res, cover.saveData);
}

//para : seatSSID, userUUID


exports.findAP = (req, res) => {
    let coverSSID = req.query.coverSSID;
    let date = req.query.date;
    let userUUID = req.cookies["Set-Cookie"];

    console.log(date);
    sign.getID(userUUID, (userID) => {
        console.log(userID);
        if (userID == null) {
            res.sendStatus(400);
            return;
        }

        connectModel.find({ "ssid": coverSSID }, (err, results) => {
            if (err) {
                res.sendStatus(500);
                throw err;
            }
            if (results.length > 0) {
                saveUserData(results, date, res, userID);
            } else {
                res.sendStatus(400);
            }
        });
    });
}

function saveUserData(results, date, res, userID) {
    for (let i = 0; i < results.length; i++) {
        console.log(compareDate(new Date(date), new Date(results[i].date)));
        if (compareDate(new Date(date), new Date(results[i].date))) {
            console.log(results[i].time);
            if (results[i].time) {
                stoolData.saveData(userID, results[i].date, results[i].color, results[i].time, res);
                connectModel.remove({ "ssid": results[i].ssid, "date": results[i].date }, err => {
                    if (err) {
                        throw err;
                    }
                });
            } else {
                connectModel.update({ "ssid": results[i].ssid, "date": results[i].date }, { $set: { "id": userID } }, (err) => {
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

function compareDate(userDate, databaseDate) {
    if (databaseDate <= userDate) {
        databaseDate.setSeconds(databaseDate.getSeconds() + 30);
        if (databaseDate >= userDate) {
            return true;
        }
    }
    return false;
}
