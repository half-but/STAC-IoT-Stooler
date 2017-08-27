let stoolData = require("./StoolData");
let databaseBoot = require("./DatabaseBoot");

//databaseBoot.connectDB();
//stoolData.createModel();

let temp = "12:30:12";
let temp1 = "2000-02-13 ";
let date = new Date(temp1 + temp);
console.log(date);