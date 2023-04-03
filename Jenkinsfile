pipeline {
    agent any
    stages {
        stage('back') {
            agent {
                 dockerfile {
                        dir './backend/otmz-api'
                        additionalBuildArgs '-t back'
                 }
            }
            steps {
                echo 'back'
            }
        }
         stage('front') {
            agent {
                 dockerfile {
                        dir './frontend'
                        additionalBuildArgs '-t front'
                 }
            }
            steps {
                echo 'front'
            }
         }
         stage('ai') {
            agent {
                  dockerfile {
                        dir './ai'
                        additionalBuildArgs '-t ai'
                  }
            }
            steps {
                echo 'ai'
            }
         }
         stage('run') {
            agent any
            steps {
                sh 'docker ps --filter "label=otmz" | xargs docker stop | xargs docker rm'
                sh 'docker run -d -v --label otmz back:/app -p 8081:8080 back'
                sh 'docker run -d -p --label otmz 3000:3000 front'
                sh 'docker run -d -p --label otmz 8000:8000 ai'
            }
         }
    }
}
