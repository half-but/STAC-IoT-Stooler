let mongoose = require("mongoose");
let uuid = require('node-uuid');

let loginModel

exports.createModel = () => {
    //create Schema -> login database
    let LoginUserSchema = mongoose.Schema({
        id : {type : String, require : true, unique : true},
        password : {type : String, require : true},
        uuid : {type : String, require : true},
        date : {type : String, require : true}
    });

    loginModel = mongoose.model("SignData", LoginUserSchema);
}

function getDate(){
    let date = new Date();
    console.log(date);
    return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
}
//회원가입
//para id, pw
exports.signUp = (req,res) => {
    let id = req.body.id || req.query.id;
    let pw = req.body.pw || req.query.pw;

    if(!id || !pw){
        res.sendStatus(400);
        return;
    }

    let userUUID = uuid.v4();

    let user = new loginModel({"id" : id,"password" : pw,"uuid" : userUUID, "date" : getDate()});

    user.save(err => {
        if(err){
            res.sendStatus(400);
        }else{
            res.cookie("cookie",userUUID);
            res.sendStatus(200);
        }
    });
}

//로그인
//para id, pw
exports.signIn = (req,res) => {
    let id = req.body.id||req.query.id;
    let pw = req.body.pw||req.query.pw;

    if(!id || !pw){
        res.sendStatus(400);
        return;
    }

    let userUUID = uuid.v4();

    loginModel.find({"id" : id, "password" : pw}, (err, results) => {
        if(err){
            res.sendStatus(500);
            throw err;
        }

        if(results.length > 0){
            loginModel.update({"id" : id, "password" : pw}, {$set : {"uuid" : userUUID}}, (err) => {
                if(err){
                    res.sendStatus(500);
                    throw err;
                }else{
                    res.cookie("cookie",userUUID);
                    res.sendStatus(200);
                }
            });
        }else{
            res.sendStatus(400);
        }
    });
}

exports.getID = (userUUID, callback) => {
    loginModel.find({"uuid" : userUUID}, (err, results) => {
        if(err){
            callback(null);
        }
        if(results.length > 0){
            callback(results[0].id, results[0].date);
        }else{
            callback(null);
        }
    });
}

