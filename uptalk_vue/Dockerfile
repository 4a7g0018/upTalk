FROM node:18-alpine
MAINTAINER S.W.-Chen (swchen357951@gmail.com)
WORKDIR /app/frontend

COPY package*.json ./
RUN npm install

EXPOSE 8080
CMD ["npm", "run", "serve"]
