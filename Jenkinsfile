pipeline {
    agent any
    stages {
        stage('back') {
            agent {
                dockerfile { 
                     args '-t back' 
                     dir './backend/otmz-api'
                }
            }
            steps {
                echo 'back'
            }
        }
         stage('front') {
            agent {
                 dockerfile {
                      args '-t front'
                      dir './frontend'
                 }
            }
            steps {
                echo 'front'
            }
         }
         stage('ai') {
            agent {
                  dockerfile {
                        args '-t ai'
                        dir './ai'
                  }
            }
            steps {
                echo 'ai'
            }
         }
    }
}