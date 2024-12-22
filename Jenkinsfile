pipeline {
    agent any
    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }
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
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t huntersleagueimage:latest .'
                }
            }
        }


        stage('Run Docker Container') {
           steps {
                 script {
                     sh '''
                     if [ $(docker ps -q -f name=huntersleague-container) ]; then
                         docker stop huntersleague-container
                         docker rm huntersleague-container
                     fi
                     '''
                     sh 'docker run -d --name huntersleague-container -p 8000:8080 huntersleagueimage:latest'
                 }
           }
        }
    }
}
