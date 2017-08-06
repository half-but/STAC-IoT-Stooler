let mongoose = require("mongoose");
let stoolData = require("./StoolData");
let sign = require("./Sign");

let connectModel;

exports.createModel = () => {
    let ConnectUserSchema = mongoose.Schema({
        ssid : {type : String, require : true},
        date : {type : String, require : true},
        id : String,
        color : String,
        time : String
    });

    connectModel = mongoose.model("connectModel", ConnectUserSchema);
}

function isDate (date){
    console.log(date)
    if(new Date(date) == "Invalid Date"){
        return false;
    }else{
        return true;
    }
}

//para : seatSSID, date
//return : 
exports.seatCover = (req,res) => {
    delectOldData();
    let coverSSID = req.query.coverSSID;
    let date = req.query.date;
    
    if (!isDate(date)){
        res.sendStatus(400);
        return;
    }

    let data = new connectModel({"ssid":coverSSID, "date":date, "id": "" ,  "color": "", "time": ""});

    data.save(err => {
        if(err){
            res.sendStatus(400);
            throw err;
        }else{
            res.sendStatus(200);
        }
    });
}

//para : seatSSID, userUUID

exports.findAP = (req,res) => {
    let coverSSID = req.query.coverSSID;
    let date = req.query.date;
    let userUUID = req.cookies["Set-Cookie"];

    console.log(date);
    sign.getID(userUUID ,(userID) => {
        console.log(userID);
        if(userID == null){
            res.sendStatus(400);
            return;
        }

        connectModel.find({"ssid":coverSSID}, (err,results) => {
            if(err){
                res.sendStatus(500);
                throw err;
            }
            if(results.length > 0){
                saveUserData(results, date, res, userID);
            }else{
                res.sendStatus(400);
            }
        });
    });
}

function saveUserData(results, date, res, userID){
    for(let i = 0; i < results.length; i++){
        console.log(compareDate(new Date(date), new Date(results[i].date)));
        if(compareDate(new Date(date), new Date(results[i].date))){
            console.log(results[i].time);
            if(results[i].time){
                stoolData.saveData(userID, results[i].date, results[i].color, results[i].time, res);
                connectModel.remove({"ssid" : results[i].ssid, "date" : results[i].date}, err => {
                    if(err){
                        throw err;
                    }
                });
            }else{
                connectModel.update({"ssid":results[i].ssid, "date": results[i].date}, {$set : {"id" : userID}}, (err) => {
                    if(err){
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

function compareDate(userDate, databaseDate){
    if(databaseDate <= userDate){
        databaseDate.setSeconds(databaseDate.getSeconds() + 30);
        if(databaseDate >= userDate){
            return true;
        }
    }
    return false;
}

function delectOldData(){
    let today = new Date();
    today.setDate(today.getDate() - 7);
    console.log(today);
    console.log("delect Data");

    connectModel.find({}, (err, results) => {
        for(let i=0;i<results.length;i++){
            if(new Date(results[i].date) <= today){
                connectModel.remove({"ssid" : results[i].ssid, "date" : results[i].date}, err => {
                    if(err){
                        throw err;
                    }
                });
            }
        }
    });
}

exports.saveData = (req, res)  => {
    let color = req.query.color;
    let time = req.query.time;
    let date = req.query.date;
    let coverSSID = req.query.coverSSID;

    if (!isDate(date)){
        res.sendStatus(400);
        return;
    }

    console.log(color, time, date, coverSSID);

    connectModel.find({"ssid":coverSSID, "date": date}, (err,results) => {
        if(err){
            res.sendStatus(500);
            throw err;
        }

        console.log(results);

        if(results.length > 0){
            if(results[0].id){
                stoolData.saveData(results[0].id, date, color, time, res);
                connectModel.remove({"ssid" : coverSSID, "date" : date}, err => {
                    if(err){
                        throw err;
                    }
                });
                res.sendStatus(200);
            }else{
                connectModel.update({"ssid" : coverSSID, "date" : date}, {"color" : color, "time" : time}, err => {
                    if(err){
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
