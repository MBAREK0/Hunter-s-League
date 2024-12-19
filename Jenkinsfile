pipeline {
  agent any
  stages {
    stage('Clone Repository') {
      steps {
        git(branch: 'main', url: 'https://github.com/MBAREK0/Hunter-s-League.git')
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
          waitForQualityGate true
        }

      }
    }

  }
}