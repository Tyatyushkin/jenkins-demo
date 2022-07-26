pipeline {
    agent any

    stages {
        stage('Clone repo') {
            steps {
                echo 'Clone repo'
                git([url: 'git@github.com:Tyatyushkin/jenkins-dockerfile.git', branch: 'main', credentialsId: 'repo'])

            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                sh 'docker build -t test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}