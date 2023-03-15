pipeline {
    agent any
    stages {
        stage('back') {
            agent {
                dockerfile { 
                     tag 'back' 
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
                      tag 'front'
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
                        tag 'ai'
                        dir './ai'
                  }
            }
            steps {
                echo 'ai'
            }
         }
    }
}