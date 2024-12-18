pipeline {
    agent any
    stages {
        stage('Checkout Code') {
            steps {
                git(url: 'https://github.com/MBAREK0/Hunter-s-League.git', branch: 'main')
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh """
                    sonar-scanner \
                    -Dsonar.projectKey=Hunters-League \
                    -Dsonar.sources=src \
                    -Dsonar.host.url=http://localhost:9000 \
                    -Dsonar.login=squ_f64f16fd9d5e6d2a043c7062e2ded4f7f99beda9
                    """
                }
            }
        }
    }
}
