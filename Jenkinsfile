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
            steps {
                sh 'docker build -t ai ./ai'
            }
         }
    }
}