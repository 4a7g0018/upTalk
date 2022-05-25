.PHONY: help
.DEFAULT_GOAL := help


$(info $(docker images --format '{{.Repository}}:{{.Tag}}' | grep 'sql'))


help: ## This help message
ifeq ($(DETECTED_OS), Windows)
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "%-30s %s\n", $$1, $$2}' $(MAKEFILE_LIST)
else
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}' $(MAKEFILE_LIST)
endif

rebuild: ## Rebuild Docker Image
	docker compose down
	docker run -d -p 61234:5432 -e POSTGRES_PASSWORD=123456 -e POSTGRES_USER=postgres -e POSTGRES_DB=up_talk --rm --name sql_for_build postgres:14-alpine
	docker compose build --no-cache --force-rm
	docker stop sql_for_build


up: ## Start Docker compose
	docker compose up -d

down: ## Stop Docker compose
	docker compose down -d
