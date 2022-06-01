.PHONY: help
.DEFAULT_GOAL := help

help: ## This help message
ifeq ($(DETECTED_OS), Windows)
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "%-30s %s\n", $$1, $$2}' $(MAKEFILE_LIST)
else
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}' $(MAKEFILE_LIST)
endif

build: stop ## Build Project
	docker compose -f .docker/docker-compose.postgres.yml up -d
	docker compose -f .docker/docker-compose.backend.yml -f .docker/docker-compose.frontend.yml build
	docker compose -f .docker/docker-compose.postgres.yml down

start: ## Start Service
	docker compose -f .docker/docker-compose.backend.yml -f .docker/docker-compose.frontend.yml -f .docker/docker-compose.postgres.yml up -d

stop: ## Stop Service
	docker compose -f .docker/docker-compose.backend.yml -f .docker/docker-compose.frontend.yml -f .docker/docker-compose.postgres.yml down

restart: stop start ## Restart Service

prune: ## Remove all unused image, network
	docker image prune -f
	docker network prune -f
