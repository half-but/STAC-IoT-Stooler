let mongoose = require("mongoose");
let uuid = require('node-uuid');

let loginModel

exports.createModel = () => {
    //create Schema -> login database
    let LoginUserSchema = mongoose.Schema({
        id : {type : String, require : true, unique : true},
        password : {type : String, require : true},
        uuid : {type : String, require : true}
    });

    loginModel = mongoose.model("SignData", LoginUserSchema);
}

//회원가입
//para id, pw
exports.signUp = (req,res) => {
    let id = req.body.id || req.query.id;
    let pw = req.body.pw || req.query.pw;

    console.log(id, pw, "님의 회원가입");

    if(!id || !pw){
        console.log("id, pw 입력 오류");
        res.sendStatus(400);
        return;
    }

    let userUUID = uuid.v4();
    console.log(userUUID);

    let user = new loginModel({"id" : id,"password" : pw,"uuid" : userUUID});

    user.save(err => {
        if(err){
            console.log("회원가입 데이터 중복")
            res.sendStatus(400);
        }else{
            console.log("회원가입 성공");
            res.cookie("Set-Cookie",userUUID);
            res.sendStatus(200);
        }
    });
}

//로그인
//para id, pw
exports.signIn = (req,res) => {
    let id = req.body.id||req.query.id;
    let pw = req.body.pw||req.query.pw;

    console.log(id, pw, "님의 로그인");

    if(!id || !pw){
        console.log("id, pw 입력 오류");
        res.sendStatus(400);
        return;
    }

    let userUUID = uuid.v4();

    console.log(userUUID);

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
                    console.log("로그인 성공");
                    res.cookie("Set-Cookie",userUUID);
                    res.sendStatus(200);
                }
            });
        }else{
            console.log("로그인 실패");
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
            callback(results[0].id);
        }else{
            callback(null);
        }
    });
}

