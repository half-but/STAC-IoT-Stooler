let mongoose = require("mongoose");
let stoolData = require("./StoolData");
let cover = require("./Cover");
let mobile = require("./Mobile");

let connectModel;
exports.connectModel = connectModel;

exports.createModel = () => {
    let ConnectUserSchema = mongoose.Schema({
        ssid: { type: String, require: true },
        date: { type: String, require: true },
        id: String,
        color: String,
        time: String
    });

    connectModel = mongoose.model("connectModel", ConnectUserSchema);

    cover.setConnectModel(connectModel);
    mobile.setConnectModel(connectModel);
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
    dateCheck(req, res, cover.seatCover);
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
    dateCheck(req, res, mobile.findAP);
}

exports.connectCheckForMobile = (req, res) => {
    dateCheck(req, res, mobile.connectCheckForMobile);
}
