let mongoose = require("mongoose");
let connect = require("./Connect");

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



}