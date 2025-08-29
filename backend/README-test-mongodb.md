# Test MongoDB Docker Setup

## 개요
테스트용 MongoDB를 Docker로 실행하고 테스트 데이터를 자동으로 임포트하는 설정입니다.

## 구성 요소
- **docker-compose-test.yml**: MongoDB 테스트 컨테이너 설정
- **scripts/init-mongo-test.sh**: MongoDB 초기화 스크립트 (컨테이너 내부 실행)
- **scripts/import-test-data-docker.sh**: 데이터 임포트 스크립트 (호스트에서 실행)

## 사용 방법

### 1. MongoDB 컨테이너 시작
```bash
# backend 디렉토리에서 실행
docker-compose -f docker-compose-test.yml up -d
```

### 2. 데이터 임포트 (옵션 1: 수동 임포트)
컨테이너가 완전히 시작된 후 (약 30초):
```bash
./scripts/import-test-data-docker.sh
```

### 3. 데이터 임포트 (옵션 2: 자동 임포트)
컨테이너 시작 시 자동으로 데이터가 임포트됩니다.
init-mongo-test.sh가 /docker-entrypoint-initdb.d에 마운트되어 있어 최초 실행 시 자동 실행됩니다.

## 연결 정보
- **Host**: localhost
- **Port**: 27018 (기본 MongoDB 포트 27017과 충돌 방지)
- **Database**: pokerogue-test
- **Username**: root
- **Password**: example
- **Connection String**: `mongodb://root:example@localhost:27018/pokerogue-test?authSource=admin`

## 테스트 애플리케이션 설정
application-test.yml에서 다음과 같이 설정되어 있습니다:
```yaml
spring:
  data:
    mongodb:
      uri: mongodb://root:example@localhost:27018/pokerogue-test?authSource=admin
```

## 컨테이너 관리

### 상태 확인
```bash
docker-compose -f docker-compose-test.yml ps
```

### 로그 확인
```bash
docker-compose -f docker-compose-test.yml logs -f mongodb-test
```

### 데이터베이스 접속
```bash
docker exec -it pokerogue-test-db mongosh --username root --password example --authenticationDatabase admin
```

### 컨테이너 중지
```bash
docker-compose -f docker-compose-test.yml down
```

### 컨테이너 및 데이터 삭제
```bash
docker-compose -f docker-compose-test.yml down -v
```

## 임포트되는 데이터
- ability (영어/한국어)
- biome (영어/한국어)
- move (영어/한국어)
- pokemon (영어/한국어)
- typeMatching

## 주의사항
- 포트 27018이 사용 가능한지 확인하세요
- 테스트 환경에서만 사용하세요 (프로덕션 환경에는 적합하지 않음)
- 볼륨이 생성되므로 데이터는 컨테이너를 중지해도 유지됩니다
- 초기 데이터를 다시 임포트하려면 볼륨을 삭제하거나 import-test-data-docker.sh를 다시 실행하세요