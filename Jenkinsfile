pipeline {
    agent any
    stages {
        stage('back') {
            agent {
                dockerfile { 
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
                        dir './ai'
                  }
            }
            steps {
                echo 'ai'
            }
         }
    }
}