#build stage
FROM node:12.18.1-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# production stage
# FROM nginx:stable-alpine as production-stage
# COPY --from=build-stage /app/dist /usr/share/nginx/html
# nginx 설정 파일을 nginx 이미지로 옮기는 명령어
# COPY --from=build-stage /app/deploy_conf/nginx.conf  
# /etc/nginx/conf.d/default.conf
EXPOSE 3000
CMD ["npm", "run", "serve"]
