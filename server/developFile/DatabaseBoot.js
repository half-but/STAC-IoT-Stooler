let mongoose = require("mongoose");

let database;

let loginModel;
let connnectModel;
let dataModel

exports.connectDB = () => {
    let databaseUrl = "mongodb://localhost:27017/local";

    mongoose.Promise = global.Promise;
    mongoose.connect(databaseUrl);
    database = mongoose.connection;

    database.on("error", console.error.bind(console, "mongoose connection error"));

    database.on("open", () => {
        console.log("success database boot");
    });

    database.on("dissconnected",() => {
        console.log("dissconnected database so after 5 second retry connect database");
        setInterval(exports.connectDB, 5000);
    });
}