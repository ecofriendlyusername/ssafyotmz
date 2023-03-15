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
    }
}