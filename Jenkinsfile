#!/usr/bin/env groovy

node {
    deleteDir()
    sh "git clone --depth 1 https://github.wdf.sap.corp/MA/cloud-s4-sdk-pipeline.git pipelines"
    load './pipelines/s4sdk-pipeline.groovy'
}