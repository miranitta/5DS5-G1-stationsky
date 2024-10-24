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
    stage('Upload to Nexus') {
            steps {
                script {
                    echo "Deploying to Nexus..."
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: "192.168.50.4:8081",
                        groupId: 'tn.esprit.spring',
                        artifactId: '5DS5-G1-stationsky',
                        version: '1.0',
                        repository: "maven-central-repository",
                        credentialsId: "nexusCredential",
                        artifacts: [
                            [
                                artifactId: '5DS5-G1-stationsky',
                                classifier: '',
                                file: 'target/5DS5-G1-stationsky.jar', 
                                type: 'jar'
                            ]
                        ]
                    )
                    echo "Deployment to Nexus completed!"
                }
            }
        }
    }
}
