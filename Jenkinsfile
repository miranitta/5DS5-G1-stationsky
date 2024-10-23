pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
     environment {
        DOCKER_IMAGE = 'rinedlazreg-g1-stationsky'  // Dynamic Docker image name
        IMAGE_TAG = 'latest'  // Image tag (e.g., 'latest' or version)
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
        stage('Static Analysis SonarCloud') {
            agent { label 'agent1' }
            environment {
            SONAR_URL = "https://sonarcloud.io" // URL de SonarCloud
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-cloud-credentials', variable: 'SONAR_TOKEN')]) {
                    sh '''
                         mvn sonar:sonar \
                        -Dsonar.login=${SONAR_TOKEN} \
                        -Dsonar.host.url=${SONAR_URL} \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                        -Dsonar.organization=miranitta
                        '''
                }
            }
        }



stage('Upload to Nexus') {
            agent { label 'agent1' }
            steps {
                script {
                    echo "Deploying to Nexus..."
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: "192.168.33.11:9001",
                        groupId: 'tn.esprit.spring',
                        artifactId: '5DS5-G1-stationsky',
                        version: '1.0',
                        repository: "maven-central-repository",
                        credentialsId: "nexus-credentials",
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

 stage('Build Docker Image') {
            agent { label 'agent1' }
            steps {
                script {
                    def nexusUrl = "http://192.168.33.11:9001"
                    def groupId = "tn.esprit.spring"
                    def artifactId = "5DS5-G1-stationsky"
                    def version = "1.0"

                    sh """
                        docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} \
                        --build-arg NEXUS_URL=${nexusUrl} \
                        --build-arg GROUP_ID=${groupId} \
                        --build-arg ARTIFACT_ID=${artifactId} \
                        --build-arg VERSION=${version} .
                    """
                }
            }
        }
 stage('Push Docker Image') {
            agent { label 'agent1' }
            environment {
                DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
            }
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        sh "docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}"
                        sh "docker push $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}"
                    }
                }
            }
        }




        
    }
}
