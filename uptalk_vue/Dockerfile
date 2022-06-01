# syntax = docker/dockerfile:experimental
# Stage 0
FROM node:lts-alpine AS builder
WORKDIR /app
COPY . /app
RUN --mount=type=cache,target=/app/node_modules,id=node_modules,sharing=locked \
    --mount=type=cache,target=/root/.npm,id=npm_cache \
    npm install && \
    npm run build

# Stage 1
FROM nginx:alpine AS uptalk-frontend
COPY --from=builder /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
