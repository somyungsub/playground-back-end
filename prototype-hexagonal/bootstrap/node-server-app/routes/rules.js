const express = require('express');
const router = express.Router();
const {connectMongo, closeMongo} = require('../db/mongo');
const {connectRedis, closeRedis} = require('../db/redis');
// const {sumTest, multiTest, minusTest} = require('../contents/calc');
const calc = require('../contents/calc');
// const java2 = require('node-java');
// const java = require('java');


const { exec } = require('child_process');

router.get('/', async function (req, res, next) {
  initFunction();
  console.log(" init ", globalThis);
  res.send('OK');
});

router.get('/java/:name', async function (req, res, next) {
  process.chdir(__dirname); // 실행 파일이 있는 디렉토리로 변경
  // exec('java -cp path/to/your.jar ExampleClass argument', (error, stdout, stderr) => {
  // exec('java -cp /Users/myungsubso/Desktop/test/hexagonal-test/bootstrap/node-server-app/java/ JavaTest2 abc', (error, stdout, stderr) => {
  exec('java -cp ../java/ JavaTest2 abc', (error, stdout, stderr) => {
    if (error) {
      console.error(`Error: ${error.message}`);
      return;
    }
    console.log(`Output: ${stdout}`);
    res.send(stdout);
  });

  // java.classpath.push("../java/");
  // java.classpath.push(__dirname + "/../java/");
  // console.log("__dirname : ", __dirname);
  // java.classpath.push("/Users/myungsubso/Desktop/test/hexagonal-test/bootstrap/node-server-app/java/JavaTest");
  // java.import("JavaTest").sayHello("안녕~", (err, result) => {
  //   console.log(result);
  //   res.send(result);
  // });
});

router.get('/mongo/:name', async function (req, res, next) {
  const request = {sample_name: req.params.name};
  console.log("request : ", request);
  await fetchSampleMongo(request)
    .then(r => {
      testEval();
      const result1 = evalContent('rule_1', 50, 100);
      const result2 = testFunction({a:500, b: 10});
      const result3 = testFunction({a:1000, b: 5});


      testEval2();
      const result4 = evalContent('rule_3', 50, 25);

      res.send({
        data: {
          ...r,
          result1,
          result2,
          result3,
          result4,
        }
      });
    }).catch(e => {
      console.error(e);
      next(e);
    }).finally(()=>{
      // closeMongo();
    });
});

router.get('/redis/:id', async function (req, res, next) {
  // const request = {sample_id: Number(req.params.id)};
  console.log("request : ", req);
  await fetchSampleRedis(req.params.id)
    .then(r => {
      testEval();
      const result1 = evalContent('rule_1', 50, 100);
      const result2 = testFunction({a:500, b: 10});
      const result3 = testFunction({a:1000, b: 5});

      testEval2();
      const result4 = evalContent('rule_3', 20, 10);

      let parse = JSON.parse(r);
      res.send({
        data: {
          ...parse,
          result1,
          result2,
          result3,
          result4
        }
      });
    }).catch(e => {
      console.error(e);
      next(e);
    }).finally(()=>{
      closeRedis();
    });
});

router.post('/redis', async function (req, res, next) {
  try {
    console.log("redis post req : ", req.body);

    const redis = await connectRedis(); // Redis 연결
    console.log("Connected to Redis");

    // 요청 본문에서 데이터 추출
    const { id, content } = req.body;

    if (!id || !content) {
      return res.status(400).json({ message: "id와 content가 필요합니다." });
    }

    // Redis에 데이터 저장
    const data = { ruleId: id, content };
    await redis.hSet('sample', id, JSON.stringify(data));

    // 성공 응답
    res.status(200).json({ message: "데이터가 성공적으로 Redis에 저장되었습니다." });
  } catch (error) {
    console.error("Error in Redis operation:", error);
    next(error);
  } finally {
    // Redis 연결 종료 (await 추가로 연결 종료를 보장)
    await closeRedis();
    console.log("Redis connection closed");
  }
});


function initFunction() {
  console.log("initFunction");
  Object.assign(globalThis, calc);
}


async function fetchSampleMongo(request) {
  const db = await connectMongo(); // db 객체 가져오기
  const collection = db.collection('samples'); // 조회할 컬렉션 선택
  const sampleData = await collection
    .find(request)
    .toArray();

  console.log('sampleData : ', sampleData);

  return sampleData;
}

async function fetchSampleRedis(id) {
  const redis = await connectRedis(); // db 객체 가져오기
  const sampleData = await redis.hGet('sample', id);

  console.log('sampleData : ', sampleData);

  return sampleData;
}

function addEval(functionName, content) {
  const result = `globalThis.${functionName} = ${content}`;
  eval(result);
}

function testEval() {
  addEval('rule_2', `
    function rule_2(a,b,c) {
        return a + b + c;
    }
  `)
  addEval('rule_1', `
    function rule_1(a,b) {
        let c = 100;
        return rule_2(a,b,c);
    }
  `)
}


function testFunction(params) {
  const rule_2 = new Function('params', `
        const {a,b,c} = params;
        return a + (b * c);
  `);
  const rule_1 = new Function('params', 'rule_2', `
        params.c = 1000;
        return rule_2(params);
  `);

  return rule_1(params, rule_2);
}

function evalContent(functionName, ...args) {
  if (!globalThis.hasOwnProperty('sumTest')) {
    initFunction();
  }
  return globalThis[functionName](...args);
}


// function funcContent(content, params, args) {
//   // const content = 'return a+b';
//   const dynamicFunc = new Function(args, content);
//   return dynamicFunc(params);
// }
// function parse() {
//   const content = 'function add(a,b) {return a+b}';
// }
//
// function writeFile() {
// }
//
// function readFile() {
//
// }

module.exports = router;
