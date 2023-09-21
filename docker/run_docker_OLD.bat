@echo off
setlocal EnableDelayedExpansion
set running=""
FOR /F %%i IN ('docker ps -f "name=loyalty_service_local" -q') DO set running=%%i
IF NOT [%running%]==[""] GOTO EXIT

:AUTHENTICATION
set active=""
FOR /F  %%i IN ('gcloud auth list --filter="status:ACTIVE" --format="value(account)"') DO set active=%%i
IF NOT [%active%]==[""] GOTO DOCKER-COMPOSE
call gcloud auth login --impersonate-service-account=168670161043-compute@developer.gserviceaccount.com --project=pt-cicd --brief --quiet

:DOCKER-COMPOSE
gcloud auth print-access-token --impersonate-service-account 168670161043-compute@developer.gserviceaccount.com --project=pt-cicd | ^
docker login -u oauth2accesstoken --password-stdin https://europe-west1-docker.pkg.dev
docker-compose up -d --quiet-pull --no-recreate

:EXIT
endlocal