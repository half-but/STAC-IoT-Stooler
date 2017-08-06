let stoolData = require("./StoolData");
let databaseBoot = require("./DatabaseBoot");

databaseBoot.connectDB();
stoolData.createModel();

for(let i = 1; i < 12; i ++){
    stoolData.saveData("qudcks9191","2017-" + i + "-30 12:30:00","ffffff","10분");
}

for(let i = 1; i < 30; i ++){
    stoolData.saveData("qudcks9191","2017-08-02 12:"+i+":00","ffffff","10분");
}