pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {
        stage('GIT') {
            steps {
                git branch: 'yasminegheribi-G1-stationsky',
                    url: 'https://github.com/miranitta/5DS5-G1-stationsky.git'
            }
        }

        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                
                     withSonarQubeEnv('sonarscanner') { // Replace 'SonarQubeServer' with your actual SonarQube server name in Jenkins
                        withCredentials([string(credentialsId: 'sonartoken', variable: 'SONAR_TOKEN')]) {
                            sh '''mvn sonar:sonar \
                                -Dsonar.projectKey=Devops-CICD \
                                -Dsonar.login=${SONAR_TOKEN}'''
                        
                    }
                }
               
            }
        }
    }
}
