#!/usr/bin/groovy

def call(body) {
     // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    pipeline {
        agent any

        options {
            // Only keep the 10 most recent builds
            buildDiscarder(logRotator(numToKeepStr:'10'))
        }

        stages {
            stage('build') {
                steps {
                    timeout(time: 15, unit: 'MINUTES') {
                        sh 'echo make build'
                    }
                }
            }
            stage('test') {
                steps {
                    timeout(time: 15, unit: 'MINUTES') {
                        sh 'echo make test'
                    }
                }
            }
        }

        post {
            always {
              echo "Send notifications for result: ${currentBuild.result}"
            }
        }
    }
}