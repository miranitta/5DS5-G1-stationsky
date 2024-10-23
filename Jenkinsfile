pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {
                echo "Getting Project from Git"
                git branch: 'ilyesmarghli-G1-stationsky', url: 'https://github.com/miranitta/5DS5-G1-stationsky.git'
            }
        }


        stage('MVN CLEAN') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('MVN COMPILE') {
            steps {
                sh 'mvn compile'
            }
        }


        stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.projectKey=5DS5:G1:staionsky -Dsonar.host.url=http://192.168.50.4:9000 -Dsonar.login=squ_30e9a55af118eb0b2867ad85a7144dce2b391900'
            }
        }

        stage('NEXUS'){
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }
    }

    post {
        always {
            // Archive the built JAR files
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
        }
        success {
            echo 'Build was successful!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
