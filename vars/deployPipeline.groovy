pipeline {
    agent any

    options {
        // Only keep the 10 most recent builds
        buildDiscarder(logRotator(numToKeepStr:'10'))
    }

    stages {
        stage('deploy') {
            steps {
                timeout(time: 30, unit: 'MINUTES') {
                    sh 'echo "make deploy"'
                }
            }
        }
    }

    post {
        always {
          echo "Send notifications for result: ${currentBuild.result}"
        }
    }
}}