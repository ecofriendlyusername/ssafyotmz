# O't MZ 포팅 메뉴얼

## I.개요

### 1. 프로젝트 개요
-	 AI 기술 기반 모바일 패션 스타일링 서비스O’t MZ
-	사용자가 착용한 옷 이미지를 기반으로 스타일 분석 및 유사 스타일 추천
-	디지털 옷장
-	사용자가 등록한 옷 이미지를 기반으로 스타일 분석
-	OpenVidu를 이용한 사용자 간 실시간 소통

### 2. 프로젝트 사용 도구
이슈 관리 : JIRA
형상 관리 : Gitlab
커뮤니케이션 : Notion, Mattermost, Discode
디자인 : Figma
UCC : Movavi
CI/CD : Jenkins

### 3. 개발환경
openJDK : 17
Node.js : 18.13.0
Python: 3.8.6
Vue/cli : 5.0.8
SERVER : AWS EC2 Ubuntu 20.04.3 LTS
DB : MariaDB, Redis

### 4. 외부 서비스
Redis : 
KakaoAPI : 
OpenVidu: 

### 5. 핵심 키들
Vue : .env.local
Spring : application.yml 
 
## II. 빌드

### 1. 환경변수 형태

.env:
VUE_APP_AI_URL = 
VUE_APP_API_URL = 
VUE_APP_URL = 
VUE_APP_KAKAO_JAVASCRIPT_API_URL = 
VUE_APP_IMG = 

.application.yml
spring:
  profiles:
    active: 
  jpa:
    show-sql: 
    generate-ddl: 
  servlet:
    multipart:
      max-request-size: 
      max-file-size: 
server:
  servlet:
    context-path: 
jwt:
  secret: 
oauth:
  kakao:
    authenticationUrl: 
    authorizationUrl: 
    grantType: 
    clientId: 

### 2. 빌드하기
#### 1) Vue.js
	#build stage
FROM node:18.13.0-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm cache clean --force
RUN npm ci
COPY . .
RUN npm run build

EXPOSE 3000
CMD ["npm", "run", "serve"]


#### 2) spring-boot
	FROM openjdk:17 AS builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN microdnf install findutils
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM openjdk:17
COPY --from=builder build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod","-jar", "/app.jar"]

#### 3) fastAPI
#build stage
FROM python:3.8
WORKDIR /ai
COPY . .
RUN pip install -r req.txt
RUN pip install python-multipart
RUN apt-get update && apt-get install ffmpeg libsm6 libxext6  -y

EXPOSE 8000
CMD ["uvicorn","main:app","--reload", "--host", "0.0.0.0", "--port", "8000"]

### 3. 배포하기
Nginx 설정
```
user www-data;
worker_processes auto;
pid /run/nginx.pid;
include /etc/nginx/modules-enabled/*.conf;

events {
        worker_connections 768;
        # multi_accept on;
}

http {

        ##
        # Basic Settings
        ##

        sendfile on;
        tcp_nopush on;
        tcp_nodelay on;
        keepalive_timeout 65;
        types_hash_max_size 2048;
        # server_tokens off;

        # server_names_hash_bucket_size 64;
        # server_name_in_redirect off;

        include /etc/nginx/mime.types;
        default_type application/octet-stream;

        ##
        # SSL Settings
        ##

        ssl_protocols TLSv1 TLSv1.1 TLSv1.2 TLSv1.3; # Dropping SSLv3, ref: POODLE
        ssl_prefer_server_ciphers on;

        ##
        # Logging Settings
        ##

        access_log /var/log/nginx/access.log;
        error_log /var/log/nginx/error.log;

        ##
        # Gzip Settings
        ##

        gzip on;

server {
listen 80 default_server;
server_name www.j8C105.p.ssafy.io;
return 301 https://$host$request_uri;
}

server {
listen 443 ssl;
server_name www.j8C105.p.ssafy.io;
ssl_certificate /etc/letsencrypt/live/j8c105.p.ssafy.io/fullchain.pem;
ssl_certificate_key /etc/letsencrypt/live/j8c105.p.ssafy.io/privkey.pem;
ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
ssl_ciphers HIGH:!aNULL:!MD5;

location / {
proxy_pass http://localhost:3000/;
}

location /api/v1/ {
proxy_pass http://localhost:8081/api/v1/;
}

location /ai/v1/ {
proxy_pass http://localhost:8000/ai/v1/;
}

location /openvidu/ {
proxy_pass http://localhost:82/;
}
}
        # gzip_vary on;
        # gzip_proxied any;
        # gzip_comp_level 6;
        # gzip_buffers 16 8k;
        # gzip_http_version 1.1;
        # gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;

        ##
        # Virtual Host Configs
        ##

        include /etc/nginx/conf.d/*.conf;
        include /etc/nginx/sites-enabled/*;
}
```
이후 sudo service nginx start

### 4. 서비스 이용 방법
#### 가) 카카오API
준비 ; 카카오 API 등록
- 내 애플리케이션 → 애플리케이션 추가하기
- Admin키.env 파일에 추가
- 플랫폼 등록

#### 나) Redis
1. dockerhub에서 redis 이미지 pull
2. docker run redis

#### 다) OpenVidu
1. dockerhub에서 openvidu 이미지 pull
2. docker run -p 4443:4443 --rm -e OPENVIDU_SECRET=MY_SECRET openvidu/openvidu-dev:2.26.0

