let stoolData = require("./StoolData");
let databaseBoot = require("./DatabaseBoot");

databaseBoot.connectDB();
stoolData.createModel();

stoolData.saveData("qudcks9191", "2017-09-08", "443322", "08:56");
stoolData.saveData("qudcks9191", "2017-09-09", "443322", "04:32");
stoolData.saveData("qudcks9191", "2017-09-11", "443322", "09:21");
stoolData.saveData("qudcks9191", "2017-09-07", "443322", "02:31");
stoolData.saveData("qudcks9191", "2017-09-10", "443322", "08:21");
stoolData.saveData("qudcks9191", "2017-09-03", "443322", "08:53");

