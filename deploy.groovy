pipeline {
    agent any

    stages {
        stage('Clone repo') {
            steps {
                sshagent(credentials: ['repo']) {
                    echo 'Clone repo'
                    git([url: 'git@github.com:Tyatyushkin/jenkins-dockerfile.git', branch: 'main'])
                }
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}