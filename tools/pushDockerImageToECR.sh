#!/usr/bin/env bash

set -eo pipefail

if ! command -v xmlstarlet &>/dev/null; then
	echo "xmlstarlet tool is not available, please install it to continue."
	exit 1
fi

TAG=$(mvn --file pom.xml -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec)
echo "==> Preparing AWS release for tag '$TAG' version"

function validate() {
	if [[ -z "$AWS_REGION" ]]; then
		echo "Missing 'AWS_REGION' environment variable, please set it and run again."
		exit 1
	fi
	if [[ -z "$MARKETPLACE_ECR_REPO_NAME" ]]; then
		echo "Missing 'MARKETPLACE_ECR_REPO_NAME' repository name environment variable, please set it and run again."
		exit 1
	fi
	if [[ -z "$MARKETPLACE_ECR_ACCOUNT_ID" ]]; then
		echo "Missing 'MARKETPLACE_ECR_ACCOUNT_ID' account id environment variable, please set it and run again."
		exit 1
	fi
}

function ecr_login() {
	echo "==> Logging into AWS ECR"
	aws ecr get-login-password --region "$AWS_REGION" | docker login --username AWS --password-stdin "$MARKETPLACE_ECR_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com"
}

function prepare_artifacts() {
	echo "==> Preparing artifacts/"
	mkdir -p artifacts
	rm -f artifacts/*

	local release_timestamp=$(date '+%Y-%m-%d %H:%M:%S')

	cat <<EOF >artifacts/config.json
{
    "releasetimestamp": "$release_timestamp",
    "version": "$TAG",
    "connectiontype": "MARKETPLACE",
    "classname": "com.exasol.glue.DefaultSource",
    "publishername": "Exasol AG",
    "connectortype": "SPARK",
    "description": "An AWS Glue Connector for accessing Exasol database.",
    "supportinformation": "Please check connector user guide for more information."
}
EOF

	echo "==> Downloading artifact for tag '$TAG' from releases"
	local artifact_name="exasol-glue-connector-$TAG-assembly.jar"
	curl -JLO "https://github.com/exasol/glue-connector/releases/download/$TAG/$artifact_name"
	mv "$artifact_name" artifacts/
}

function prepare_docker_image() {
	echo "==> Preparing docker image"

	cat <<EOF >Dockerfile
        FROM amazonlinux:latest
	COPY ./artifacts  ./jars
EOF
	docker build -t "$MARKETPLACE_ECR_REPO_NAME:$TAG" .
	docker tag "$MARKETPLACE_ECR_REPO_NAME:$TAG" "$MARKETPLACE_ECR_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$MARKETPLACE_ECR_REPO_NAME:$TAG"
}

function push_docker_image() {
	echo "==> Pushing docker image to AWS marketplace"
	docker push "$MARKETPLACE_ECR_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$MARKETPLACE_ECR_REPO_NAME:$TAG"
}

validate
ecr_login
prepare_artifacts
prepare_docker_image
push_docker_image
