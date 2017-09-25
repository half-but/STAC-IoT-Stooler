let express = require("express");
let app = express();
let bodyParser = require("body-parser");
let cookieParser = require("cookie-parser");

let databaseBoot = require("./DatabaseBoot");
let sign = require("./Sign");
let connect = require("./connect");
let stoolData = require("./StoolData");

let router = express.Router();

app.use(bodyParser.urlencoded({ extended : false}));

app.use(cookieParser());

//임베디드 전용 - 커버에 앉았을 때
router.route("/seatCover").get(connect.seatCover);
//임베디드 전용 - 커버에서 데이터 받아 저장
router.route("/saveData").get(connect.saveData);
//임베디드 전용 - 모바일 클라이언트와 커버가 연결 되어 있는지 확인 (진동을 줄지 안 줄지)
router.route("/connectCheckForCover").get(connect.connectCheckForCover);

//모바일 클라이언트 - AP를 탐색 하였을 때
router.route("/findAP").get(connect.findAP);
//모바일 클라이언트 - 커버랑 연결 되어 있는지 확인
router.route("/connectCheckForMobile").get(connect.connectCheckForMobile);

//회원가입
router.route("/signUp").post(sign.signUp);
//로그인
router.route("/signIn").post(sign.signIn);

//최근 데이터
router.route("/getIssueData").get(stoolData.getIssueData);
//캘린더 데이터
router.route("/getCalendarData").get(stoolData.getCalendartData);

router.route("/shotData").get(stoolData.shotData);


app.use("/",router);


app.listen(9024,() => {
    console.log("Stooler Server Service Start");
    databaseBoot.connectDB();
    sign.createModel();
    connect.createModel();
    stoolData.createModel();
});