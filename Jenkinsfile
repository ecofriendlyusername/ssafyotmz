pipeline {
    agent any
    stages {
        stage('back') {
            agent {
                dockerfile { 
                     dir './backend/otmz-api'
                     args '-t back'
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
                      args '-t front'
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
                        args '-t ai'
                  }
            }
            steps {
                echo 'ai'
            }
         }
    }
}