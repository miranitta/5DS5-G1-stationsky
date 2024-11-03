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
        EMAIL_RECIPIENT = 'abdelml623@gmail.com' // Replace with the actual email address
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

        stage('Run JUnit Tests') {
            steps {
                echo 'Running JUnit Tests...'
                sh 'mvn -Dtest=SkierServiceImplTestJUnit test'
            }
        }

        stage('Run Mockito Tests') {
            steps {
                echo 'Running Mockito Tests...'
                sh 'mvn -Dtest=SkierServiceImplTestMockito test'
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

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker Image...'
                script {
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
                        sh '''
                            echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        '''
                        sh "docker push ${DOCKER_IMAGE}"
                    }
                }
            }
        }

        stage('Install Docker Compose') {
            steps {
                script {
                    // Commandes pour vérifier l'installation de Docker Compose
                    sh '''
                        # Vérifier l'installation
                        docker-compose --version
                    '''
                }
            }
        }

        stage('Run Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }

        stage('NEXUS') {
            steps {
                script {
                    if (fileExists('pom.xml')) {
                        sh "mvn deploy"
                    } else {
                        error 'pom.xml not found in the current directory.'
                    }
                }
            }
        }

        stage('Grafana Prometheus') {
            steps {
                sh 'docker start prometheus'
                sh 'docker start grafana'
            }
        }

        // Add the Send Email Notification stage
        stage('Send Email Notification') {
            steps {
                echo 'Sending Email Notification...'
                mail to: "${env.EMAIL_RECIPIENT}",
                     subject: "Jenkins Pipeline: Build ${currentBuild.fullDisplayName}",
                     body: "The build ${currentBuild.fullDisplayName} has finished with status: ${currentBuild.currentResult}"
            }
        }
    }

    post {
        always {
            cleanWs() // Cleans the workspace after build
        }
        success {
            echo 'Pipeline completed successfully!'
            mail to: "${env.EMAIL_RECIPIENT}",
                 subject: "Jenkins Pipeline Success: ${currentBuild.fullDisplayName}",
                 body: "Good news! The build ${currentBuild.fullDisplayName} completed successfully."
        }
        failure {
            echo 'Pipeline failed. Please check the logs.'
            mail to: "${env.EMAIL_RECIPIENT}",
                 subject: "Jenkins Pipeline Failure: ${currentBuild.fullDisplayName}",
                 body: "The build ${currentBuild.fullDisplayName} failed. Please check the Jenkins logs for details."
        }
    }
}
