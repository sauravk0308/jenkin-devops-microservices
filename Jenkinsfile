pipeline {
    agent any

    stages {
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t myapp:latest .'
            }
        }

        stage('Stop Old Container') {
            steps {
                sh '''
                docker stop myapp || true
                docker rm myapp || true
                '''
            }
        }

        stage('Run Container') {
            steps {
                sh 'docker run -d --name myapp -p 8080:8080 myapp:latest'
            }
        }
    }
}
