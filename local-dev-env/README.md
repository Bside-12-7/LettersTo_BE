# local-dev 환경 실행

```shell
# 생성
cd local-dev-env
docker-compose up -d

# 종료
docker-compose down

# 정지
docker-compose stop

# 시작
docker-compose start
```

## mysql shell 열기

```shell
docker exec -it letters_to-db mysql -uroot -p
```
