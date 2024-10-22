pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {
        stage('GIT') {
            steps {
                git branch: 'anwarkhiari-G1-stationsky',
                    url: 'https://github.com/miranitta/5DS5-G1-stationsky'
            }
        }

        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Nexus Stage') {
            steps {
                sh 'mvn install'
                sh 'mvn clean deploy -DskipTests=true'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                
                     withSonarQubeEnv('sonarscanner') { // Replace 'SonarQubeServer' with your actual SonarQube server name in Jenkins
                        withCredentials([string(credentialsId: 'projetdev', variable: 'SONAR_TOKEN')]) {
                            sh '''mvn sonar:sonar \
                                -Dsonar.projectKey=Devops-CICD \
                                -Dsonar.login=${SONAR_TOKEN}'''
                        
                    }
                }
               
            }
        }
    }
}
