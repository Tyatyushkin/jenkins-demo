pipeline {
    agent any

    stages {
        stage('Clone repo') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'repo', passphraseVariable: '', usernameVariable: '')]) {
                    echo 'Clone repo'
                    //sh '''
                    //    [ -d ~/.ssh ] || mkdir ~/.ssh && chmod 0700 ~/.ssh
                    //    ssh-keyscan -t rsa,dsa github.com >> ~/.ssh/known_hosts
                    //     '''
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