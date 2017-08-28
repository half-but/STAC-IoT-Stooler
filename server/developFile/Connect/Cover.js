let mongoose = require("mongoose");
let connect = require("./Connect");
let stoolData = require("./StoolData");

let connectModel = connect.connectModel;

function seatCover(req, res) {
    let ssid = req.query.ssid;
    let date = req.query.date;

    console.log("seat cover", ssid, date);

    let data = new connectModel({"ssid" : ssid, "date" : date, "time" : "", "color" : "", "id" : id});

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

function saveData(req, res) {
    let ssid = req.query.ssid;
    let date = req.query.date;
    let color = req.query.color;
    let time = req.query.time;

    console.log("save data", ssid, date, color, time);

    connectModel.find({"ssid" : ssid, "date" : date}, (err, result) => {
        if(err){
            res.sendStatus(500);
            throw err;
        }

        console.log("find for save result", result);

        if (result.length > 0){
            console.log("find result id", id);

            if (results[0].id) {
                stoolData.saveData(results[0].id, date, color, time, res);
                connectModel.remove({ "ssid": coverSSID, "date": date }, err => {
                    if (err) {
                        throw err;
                    }
                });
                res.sendStatus(200);
            } else {
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

function connectCheckForCover(req, res) {
    let ssid = req.query.ssid;
    let date = req.query.date;

    console.log("connect check for cover", ssid, date);

    connectModel.find({"ssid" : ssid, "date" : date}, (err, result) => {
        if(err){
            res.sendStatus(500);
            throw err;
        }

        if(result.length > 0){

        }else{
            
        }
    });
}