pipeline {
  agent any
  stages {
    stage('checkout code ') {
      agent any
      steps {
        git(url: 'https://github.com/MBAREK0/Hunter-s-League.git', branch: 'main')
      }
    }
   stage('SonarQube Analysis') {
        steps {
            withSonarQubeEnv('sonarqube') {
                sh """
                sonar-scanner \
                -Dsonar.projectKey=project_key \
                -Dsonar.sources=src \
                -Dsonar.host.url=http://<sonar_ip>:9000 \
                -Dsonar.login=your_sonar_token
                """
        }
    }
}