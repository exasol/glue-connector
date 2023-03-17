---
name: Custom issue template
about: Reminder to publish new version to AWS Marketplace
title: Publish to AWS Marketplace
labels: ''
assignees: ''

---

A [new version](https://github.com/exasol/glue-connector/releases) of this project is released. It should be pushed to the AWS Marketplace ECR Registry.

- [ ] Check that the corresponding Docker image is pushed to the AWS ECR Registry in [Prepare AWS Marketplace Action](workflows/prepare_aws_marketplace_release.yml)
- [ ] Follow the [instructions](https://github.com/exasol/glue-connector/blob/main/doc/developers_guide/developers_guide.md#publishing-the-connector-to-aws-marketplace) in developer guide to publish the new version to the AWS Marketplace
