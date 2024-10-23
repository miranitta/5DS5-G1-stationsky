pipeline {
    agent any

    tools {
        maven "M2_HOME"
        jdk "JAVA_HOME"
    }

    environment{
        NEXUS_VERSION="nexus3"

        NEXUS_PROTOCOL="http"

        NEXUS_URL="192.168.50.4:8081"

        NEXUS_REPOSITORY="maven-releases"

        NEXUS_CREDENTIAL_ID="nexusCredential"


    }

    stages {
        stage('GIT') {
            steps {
                echo "Getting Project from Git"
                git branch: 'ilyesmarghli-G1-stationsky', url: 'https://github.com/miranitta/5DS5-G1-stationsky.git'
            }
        }


        stage('MVN BUILD') {
                steps {
                    sh 'mvn clean package'
                }
            }



        stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.projectKey=5DS5:G1:staionsky -Dsonar.host.url=http://192.168.50.4:9000 -Dsonar.login=squ_30e9a55af118eb0b2867ad85a7144dce2b391900'
            }
        }

        stage('PUBLISH TO NEXUS'){
            steps {
                script{
                pom = readMavenPom file: "pom.xml";
                filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length}";
                artifactPath = filesByGlob[0].path;
                artifactExists = fileExists artifactPath;

                if (artifactExists) {
                    echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version: ${pom.version}";

                    nexusArtifactUploader(
                        nexusVersion: NEXUS_VERSION,
                        protocol: NEXUS_PROTOCOL,
                        nexusUrl: NEXUS_URL,
                        groupId: pom.groupId,
                        version: pom.version,
                        repository: NEXUS_REPOSITORY,
                        credentialsId: NEXUS_CREDENTIAL_ID,
                        artifacts: [
                            [artifactId: pom.artifactId,
                             classifier: '',
                             file: artifactPath,
                             type: pom.packaging
                            ]
                        ]
                    );

                } else {
                    error "*** File: ${artifactPath}, could not be found";
                }
                }
            }
        }
    }

    post {
        success {
            echo 'Build was successful!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
