pipeline {
    agent any
    stages {
        stage('back') {
            steps {
                sh 'docker build -t back ./backend/otmz-api'
            }
        }
         stage('front') {
            steps {
                sh 'docker build -t front ./frontend'
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