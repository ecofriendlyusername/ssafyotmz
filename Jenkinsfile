pipeline {
    agent any
    stages {
        stage('back') {
            agent {
                 dockerfile {
                        dir './backend/otmz-api'
                        additionalBuildArgs '-t back -l otmz'
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
                        additionalBuildArgs '-t front -l otmz'
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
                        additionalBuildArgs '-t ai -l otmz'
                  }
            }
            steps {
                echo 'ai'
            }
         }
         stage('run') {
            agent any
            steps {
                sh 'docker run -d -v -l otmz back:/app -p 8081:8080 back'
                sh 'docker run -d -p -l otmz 3000:3000 front'
                sh 'docker run -d -p -l otmz 8000:8000 ai'
            }
         }
    }
}
