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
        git(branch: 'main', url: 'https://github.com/MBAREK0/Hunter-s-League.git')
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
          waitForQualityGate true
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

    stage('Send Test Email') {
      steps {
        script {
          emailext(
            subject: "Test Email from Jenkins Pipeline",
            body: "This is a test email sent as part of the pipeline to validate email functionality.",
            to: 'elaadraouimbarek2023@gmail.com'
          )
        }

      }
    }

  }
  post {
    success {
      emailext(subject: "Pipeline Passed: ${env.JOB_NAME} #${env.BUILD_NUMBER}", body: """
                      <h3>Pipeline Passed</h3>
                      <p>The pipeline has completed successfully.</p>
                      <p>Job Name: ${env.JOB_NAME}</p>
                      <p>Build Number: ${env.BUILD_NUMBER}</p>
                      <p>Build URL: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                      """, mimeType: 'text/html', to: 'elaadraouimbarek2023@gmail.com')
    }

    failure {
      emailext(subject: "Pipeline Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}", body: """
                      <h3>Pipeline Failed</h3>
                      <p>The pipeline has failed.</p>
                      <p>Job Name: ${env.JOB_NAME}</p>
                      <p>Build Number: ${env.BUILD_NUMBER}</p>
                      <p>Build URL: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                      """, mimeType: 'text/html', to: 'elaadraouimbarek2023@gmail.com')
    }

  }
}