#!/bin/bash

loyalty_environment=${1:-"DEV"}
image_tag=$2

if [[ ${loyalty_environment^^} == "DEV" ]]; then
  export ARTIFACT_REGISTRY="docker-snapshots"
elif [[ ${loyalty_environment^^} == "INT" ]]; then
  export ARTIFACT_REGISTRY="docker-releases"
  if [[ -z $image_tag ]]; then
    echo "docker image from INT environment requires image tag"
    exit
  fi
  export IMAGE_TAG=$image_tag
else
  echo "Invalid environment choose between DEV / INT"
  exit
fi

running=""
running=$(docker ps -f "name=loyalty_service_local" -q)
if [[ -z $running ]]; then

  echo "no running container found, starting new one"
  active=""
  active=$(gcloud auth print-access-token --verbosity=none --project=pt-cicd)
  if [[ -z $active ]]; then
    echo "no active access-token, logging in"
    gcloud auth login --impersonate-service-account=168670161043-compute@developer.gserviceaccount.com --project=pt-cicd -q --verbosity=none
  fi

  gcloud auth print-access-token --impersonate-service-account 168670161043-compute@developer.gserviceaccount.com \
    --verbosity=none -q --project=pt-cicd | docker login -u oauth2accesstoken --password-stdin https://europe-west1-docker.pkg.dev
  docker-compose up -d --quiet-pull --no-recreate
fi
