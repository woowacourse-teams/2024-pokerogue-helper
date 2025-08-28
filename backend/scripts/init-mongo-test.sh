#!/bin/bash
set -e

# MongoDB 초기화 스크립트
# Docker 컨테이너 내부에서 실행됨

echo "Initializing MongoDB test database..."

# MongoDB가 준비될 때까지 대기
until mongosh --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" --eval "db.adminCommand('ping')" &>/dev/null; do
    echo "Waiting for MongoDB to be ready..."
    sleep 2
done

echo "MongoDB is ready. Importing test data..."

# 데이터베이스 생성 및 권한 설정
mongosh --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" <<EOF
use pokerogue-test;
db.createUser({
  user: "testuser",
  pwd: "testpass",
  roles: [
    { role: "readWrite", db: "pokerogue-test" }
  ]
});
EOF

# 테스트 데이터 임포트
echo "Importing English ability data..."
mongoimport --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" --db pokerogue-test --collection ability --file /docker-entrypoint-initdb.d/data/ability-json-all-en.json --jsonArray --drop

echo "Importing English biome data..."
mongoimport --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" --db pokerogue-test --collection biome --file /docker-entrypoint-initdb.d/data/biome-json-all-en.json --jsonArray --drop

echo "Importing English move data..."
mongoimport --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" --db pokerogue-test --collection move --file /docker-entrypoint-initdb.d/data/move-json-all-en.json --jsonArray --drop

echo "Importing English pokemon data..."
mongoimport --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" --db pokerogue-test --collection pokemon --file /docker-entrypoint-initdb.d/data/pokemon-json-all-en.json --jsonArray --drop

# 한국어 데이터 추가 (append)
echo "Importing Korean ability data..."
mongoimport --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" --db pokerogue-test --collection ability --file /docker-entrypoint-initdb.d/data/ability-json-all-ko.json --jsonArray

echo "Importing Korean biome data..."
mongoimport --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" --db pokerogue-test --collection biome --file /docker-entrypoint-initdb.d/data/biome-json-all-ko.json --jsonArray

echo "Importing Korean move data..."
mongoimport --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" --db pokerogue-test --collection move --file /docker-entrypoint-initdb.d/data/move-json-all-ko.json --jsonArray

echo "Importing Korean pokemon data..."
mongoimport --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" --db pokerogue-test --collection pokemon --file /docker-entrypoint-initdb.d/data/pokemon-json-all-ko.json --jsonArray

# Type matching 데이터 삽입
echo "Importing type matching data..."
mongoimport --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" --db pokerogue-test --collection typeMatching --file /docker-entrypoint-initdb.d/data/type-matching-all.json --jsonArray --drop

echo "Data import completed successfully!"

# 데이터 확인
mongosh --host localhost --authenticationDatabase admin --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" --eval "
use pokerogue-test;
print('Collection counts:');
db.getCollectionNames().forEach(function(c) {
    var count = db[c].countDocuments();
    print('  ' + c + ': ' + count + ' documents');
});
"