pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    stages {
        stage('Checkout') {
            steps {
                git(
                    url: 'https://github.com/miranitta/5DS5-G1-stationsky.git', 
                    branch: 'rinedlazreg-G1-stationsky',
                    credentialsId: 'github-credentials'
                )
            }
        }

       stage('Clean, Build & Test') {
            agent { label 'agent1' }
            steps {
                sh '''
                    mvn clean install
                    mvn jacoco:report
                '''
            }
        }
<<<<<<< HEAD
=======

>>>>>>> da4d98671c580d2de67a5bc9a17c92b9730a293a
        stage('Static Analysis') {
            agent { label 'agent1' }
            environment {
                SONAR_URL = "http://192.168.33.11:9000/"
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-credentials', variable: 'SONAR_TOKEN')]) {
                    sh '''
                         mvn sonar:sonar \
                        -Dsonar.login=${SONAR_TOKEN} \
                        -Dsonar.host.url=${SONAR_URL} \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.coverage.jacoco.xmlReportPaths=/target/site/jacoco/jacoco.xml
                    '''
                }
            }
        }
    }
}
