pipeline {
    agent any
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/MBAREK0/Hunter-s-League.git'
            }
        }
        stage('Set Maven Wrapper Permissions') {
            steps {
                sh 'chmod +x ./mvnw'
            }
        }
        stage('Build Project') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                     sh './mvnw sonar:sonar -Dsonar.host.url=http://sonarqube:9000'
                }
            }
        }
        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate   : true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t myapp:latest .'
                }
            }
        }
    }
}
