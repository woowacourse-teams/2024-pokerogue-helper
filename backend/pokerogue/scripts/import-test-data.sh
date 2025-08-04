#!/bin/bash

# MongoDB 연결 정보
MONGO_HOST=${MONGO_HOST:-localhost}
MONGO_PORT=${MONGO_PORT:-27017}
MONGO_USERNAME=${TEST_DB_USERNAME:-root}
MONGO_PASSWORD=${TEST_DB_PASSWORD:-password}
MONGO_DB=${MONGO_DB:-pokerogue}
DATA_DIR=${DATA_DIR:-./src/test/resources/data}

# MongoDB 컨테이너가 준비될 때까지 대기
echo "Waiting for MongoDB to be ready..."
for i in {1..30}; do
    docker exec pokerogue-db mongosh --host $MONGO_HOST --port $MONGO_PORT --authenticationDatabase admin --username $MONGO_USERNAME --password $MONGO_PASSWORD --eval "db.adminCommand('ping')" > /dev/null 2>&1
    if [ $? -eq 0 ]; then
        echo "MongoDB is ready!"
        break
    fi
    echo "Waiting for MongoDB... ($i/30)"
    sleep 2
done

# 데이터 파일을 컨테이너로 복사
echo "Copying data files to MongoDB container..."
docker cp $DATA_DIR/. pokerogue-db:/tmp/

# 각 컬렉션에 데이터 삽입
echo "Importing data into MongoDB..."

# 영어 데이터 삽입 (기존 컬렉션 drop)
echo "Importing English ability data..."
docker exec pokerogue-db mongoimport --host $MONGO_HOST --port $MONGO_PORT --authenticationDatabase admin --username $MONGO_USERNAME --password $MONGO_PASSWORD --db $MONGO_DB --collection ability --file /tmp/ability-json-all-en.json --jsonArray --drop

echo "Importing English biome data..."
docker exec pokerogue-db mongoimport --host $MONGO_HOST --port $MONGO_PORT --authenticationDatabase admin --username $MONGO_USERNAME --password $MONGO_PASSWORD --db $MONGO_DB --collection biome --file /tmp/biome-json-all-en.json --jsonArray --drop

echo "Importing English move data..."
docker exec pokerogue-db mongoimport --host $MONGO_HOST --port $MONGO_PORT --authenticationDatabase admin --username $MONGO_USERNAME --password $MONGO_PASSWORD --db $MONGO_DB --collection move --file /tmp/move-json-all-en.json --jsonArray --drop

echo "Importing English pokemon data..."
docker exec pokerogue-db mongoimport --host $MONGO_HOST --port $MONGO_PORT --authenticationDatabase admin --username $MONGO_USERNAME --password $MONGO_PASSWORD --db $MONGO_DB --collection pokemon --file /tmp/pokemon-json-all-en.json --jsonArray --drop

# 한국어 데이터 추가 (append)
echo "Importing Korean ability data..."
docker exec pokerogue-db mongoimport --host $MONGO_HOST --port $MONGO_PORT --authenticationDatabase admin --username $MONGO_USERNAME --password $MONGO_PASSWORD --db $MONGO_DB --collection ability --file /tmp/ability-json-all-ko.json --jsonArray

echo "Importing Korean biome data..."
docker exec pokerogue-db mongoimport --host $MONGO_HOST --port $MONGO_PORT --authenticationDatabase admin --username $MONGO_USERNAME --password $MONGO_PASSWORD --db $MONGO_DB --collection biome --file /tmp/biome-json-all-ko.json --jsonArray

echo "Importing Korean move data..."
docker exec pokerogue-db mongoimport --host $MONGO_HOST --port $MONGO_PORT --authenticationDatabase admin --username $MONGO_USERNAME --password $MONGO_PASSWORD --db $MONGO_DB --collection move --file /tmp/move-json-all-ko.json --jsonArray

echo "Importing Korean pokemon data..."
docker exec pokerogue-db mongoimport --host $MONGO_HOST --port $MONGO_PORT --authenticationDatabase admin --username $MONGO_USERNAME --password $MONGO_PASSWORD --db $MONGO_DB --collection pokemon --file /tmp/pokemon-json-all-ko.json --jsonArray

# Type matching 데이터 삽입
echo "Importing type matching data..."
docker exec pokerogue-db mongoimport --host $MONGO_HOST --port $MONGO_PORT --authenticationDatabase admin --username $MONGO_USERNAME --password $MONGO_PASSWORD --db $MONGO_DB --collection typeMatching --file /tmp/type-matching-all.json --jsonArray --drop

echo "Data import completed successfully!"

# 삽입된 데이터 확인
echo "Verifying imported data..."
docker exec pokerogue-db mongosh --host $MONGO_HOST --port $MONGO_PORT --authenticationDatabase admin --username $MONGO_USERNAME --password $MONGO_PASSWORD --eval "
use $MONGO_DB;
print('Collection counts:');
db.getCollectionNames().forEach(function(c) {
    var count = db[c].countDocuments();
    print('  ' + c + ': ' + count + ' documents');
});
"