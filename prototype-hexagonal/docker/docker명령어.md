```shell
## 도커컴포즈 업/다운
$ docker-compose -f 파일명 up -d
$ docker-compose -f 파일명 down

## 몽고디비 : docker exec -it 컨테이너명 mongo --port 27017
$ docker exec -it local-mongo-test mongo --port 27017


## docker run --name mongo -d -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=password mongo

## 순차 실행, 트랜잭션을 위해 레플리카셋 설정 필요
$ docker run -d --name local-mongo -p 27017:27017 mongo:4.4-bionic --replSet rs0
$ docker exec -it local-mongo mongo --port 27017
## ok 1 리턴 확인
$ rs.initiate()
$ exit

## 유저생성, 접속을 위해 유저생성필요 -> successfully added user 메시지 리턴 확인

$ docker exec -it local-mongo mongo admin --eval "db.createUser({user: 'root', pwd: 'password', roles: [{role: 'root', db: 'admin'}]})"



## 카프카 파티션 수정
$ docker exec -it local-kafka bash

## bitnami 기준 -> /opt/bitnami/kafka/bin 위치
$ kafka-topics.sh --alter --topic producer-test-kafka-topic --bootstrap-server localhost:9092 --partitions 3
$ kafka-topics.sh --alter --topic spread-topic --bootstrap-server localhost:9092 --partitions 5

```


