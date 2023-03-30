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
