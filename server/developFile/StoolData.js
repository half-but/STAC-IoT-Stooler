let mongoose = require("mongoose");
let sign = require("./Sign");

let dataControlModel;

exports.createModel = ()=>{
    let stoolDataSchema = mongoose.Schema({
        id : {type: String, require : true},
        date : {type: String, require : true},
        color : {type: String, require : true},
        time : {type: String, require : true}
    })

    stoolDataModel = mongoose.model("stoolData",stoolDataSchema);
}

exports.saveData = (id, date, color, time) =>{
    console.log(color, time);
    let data = new stoolDataModel({"id" : id, "date" : date, "color" : color, "time" : time});

    data.save(err => {
        if(err){
            throw err;
        }
    });
}

//데이터 보기
exports.getWeekData = (req, res) => {
    let userUUID = req.cookies["Set-Cookie"];
    sign.getID(userUUID, (userID) => {
        if(!userID){
            res.sendStatus(400);
            return;
        }

        console.log(userID);

        stoolDataModel.find({"id" : userID}, (err, results) => {
            if(err){
                res.sendStatus(500);
                throw err;
            }

            if(results.length > 0){
                let dataArray = new Array();
                for(let i=0; i<results.length; i++){
                    if(checkWeekData(results[i].date)){
                        dataArray.push({"date":results[i].date, "color":results[i].color, "time":results[i].time});
                    }
                }
                console.log(dataArray);
                res.status(200).send(JSON.stringify(dataArray));
            }else{
                res.sendStatus(400);
            }
        });
    });
}

function checkWeekData(date){
    let startDate = new Date();
    let finishDate = new Date();
    startDate.setDate(startDate.getDate() - 7);
    console.log(startDate, new Date(date), finishDate);
    if(startDate <= new Date(date) && new Date(date) <= finishDate){
        return true;
    }else{
        return false;
    }
}

exports.getCalendatData = (req, res) => {
    let userUUID = req.cookies["Set-Cookie"];
    let selectDate = req.query.date;

    sign.getID(userUUID, (userID) => {
        if(!userID){
            res.sendStatus(400);
            return;
        }

        stoolDataModel.find({"id": userID},(err, results) => {
            if(err){
                res.sendStatus(500);
                throw err;
            }

            if(results.length > 0){
                let dataArray = new Array();
                for(let i=0; i<results.length; i++){
                    if(checkSelectData(results[i].date, selectDate)){
                        dataArray.push({"date":results[i].date, "color":results[i].color, "time":results[i].time});
                    }
                }
                res.status(200).send(JSON.stringify(dataArray));
            }else{
                res.sendStatus(204);    
            }
        });
        
    });
}

function checkSelectData(databaseDate, selectDate){
    let startDate = new Date(selectDate);
    let finishDate = new Date(selectDate);
    finishDate.setDate(finishDate.getDate() + 1);

    console.log(startDate, finishDate, new Date(databaseDate));

    if(startDate <= new Date(databaseDate) && new Date(databaseDate) < finishDate){
        return true;
    }else{
        return false;
    }
}