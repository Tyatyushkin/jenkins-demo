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
                sshagent(credentials: ['appkey']) {
                    echo 'Testing..'
                    echo '$SSH_AUTH_SOCK'
                    sh 'docker build --version'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}