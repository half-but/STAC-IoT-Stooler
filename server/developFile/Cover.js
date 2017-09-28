let mongoose = require("mongoose");
let connect = require("./connect");
let stoolData = require("./StoolData");

let connectModel;

exports.setConnectModel = (model) => {
    connectModel = model;
}

exports.seatCover = (req, res) => {
    let ssid = req.query.ssid;
    let date = req.query.date;

    console.log("seat cover", ssid, date);

    let data = new connectModel({"ssid" : ssid, "date" : date, "time" : "", "color" : "", "id" : ""});

    data.save(err => {
        if (err) {
            console.log("seat save error");
            res.sendStatus(400);
            throw err;
        } else {
            console.log("seat save success");
            res.sendStatus(200);
        }
    });
}

exports.saveData = (req, res) => {
    let ssid = req.query.ssid;
    let date = req.query.date;
    let color = req.query.color;
    let time = req.query.time;

    console.log("save data", ssid, date, color, time);

    connectModel.find({"ssid" : ssid, "date" : date}, (err, results) => {
        if(err){
            res.sendStatus(500);
            throw err;
        }

        console.log("find for save result", results);

        if (results.length > 0){

            if (results[0].id) {
                console.log("save data", results[0].id, date, color, time, res);
                stoolData.saveData(results[0].id, date, color, time, res);
                connectModel.remove({ "ssid": coverSSID, "date": date }, err => {
                    if (err) {
                        throw err;
                    }
                });
                res.sendStatus(200);
            } else {
                console.log("update data");
                connectModel.update({"ssid": coverSSID, "date": date }, { "color": color, "time": time }, err => {
                    if (err) {
                        res.sendStatus(500);
                        throw err;
                    }
                    res.sendStatus(200);
                });
            }
        }else{
            res.sendStatus(400);
        }
    });
}

exports.connectCheckForCover = (req, res) => {
    let ssid = req.query.ssid;
    let date = req.query.date;

    console.log("connect check for cover", ssid, date);

    connectModel.find({"ssid" : ssid, "date" : date}, (err, result) => {
        if(err){
            res.sendStatus(500);
            throw err;
        }
        if(result.length > 0){
            if(result[0].id){
                res.sendStatus(200);
            }else{
                res.sendStatus(400);
            }
        }else{
            res.sendStatus(400);
        }
    });
}