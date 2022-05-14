FROM node:lts-alpine

ENV HOST=0.0.0.0

WORKDIR /app/frontend

COPY ../package*.json /app/frontend

RUN npm install -g @vue/cli
RUN npm install

EXPOSE 8080
CMD ["npm", "run", "serve"]
