pipeline {
    agent any
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/NabilElHakimi/nbtechfront.git'
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
                    sh './mvnw sonar:sonar'
                }
            }
        }
        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
//         stage('Build Docker Image') {
//             steps {
//                 sh 'docker build -t nabilhakimi/testwithdocker:latest .'
//             }
//         }
//         stage('Push Docker Image') {
//             steps {
//                 withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
//                     sh 'docker push nabilhakimi/testwithdocker:latest'
//                 }
//             }
//         }
    }
}
