pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.50.4:8081"
        NEXUS_REPOSITORY = "maven-central-repository"
        NEXUS_CREDENTIAL_ID = "nexusCredential"
        DOCKER_IMAGE = 'khiarianwar/anwarkhiari_5ds5'
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

        stage('Package Stage') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker Image...'
                script {
                    // Ensure the JAR is copied to the Docker context
                    sh 'cp target/gestion-station-ski-1.0.jar .'
                    sh "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
    steps {
        echo 'Pushing Docker Image to Docker Hub...'
        script {
            withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                // Use credentials without Groovy interpolation
                sh '''
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                '''
                sh "docker push ${DOCKER_IMAGE}"
            }
        }
    }
}

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarscanner') {
                    withCredentials([string(credentialsId: 'sonartoken', variable: 'SONAR_TOKEN')]) {
                        sh '''
                            mvn sonar:sonar \
                                -Dsonar.projectKey=Devops-CICD \
                                -Dsonar.login=${SONAR_TOKEN}
                        '''
                    }
                }
            }
        }
    }
}
