library identifier: 'example-lib@master', retriever: modernSCM(
        [$class: 'GitSCMSource',
         remote: 'https://github.com/Tyatyushkin/jenkins-library-example.git'])


pipeline {
    agent any

    stages {

        stage('Clone repo') {
            steps {
                echo 'Clone repo'
                git([url: 'git@github.com:Tyatyushkin/jenkins-dockerfile.git', branch: 'main', credentialsId: 'repo'])

            }
        }
        stage('Build') {
            steps {
                sshagent(credentials: ['appkey']) {
                    echo 'Build docker image..'

                    sh 'DOCKER_BUILDKIT=1 docker build --ssh default=$SSH_AUTH_SOCK -t nginx .'
                }
            }
        }
    }
    post {
        always {
            cleanWs(deleteDirs: true)
        }
    }
}