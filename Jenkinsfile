pipeline {

    agent any

    tools{

        maven 'maven-3.9.6'

    }

    stages {

        stage('Source') {

            steps {

                echo "Fetching changes from github project repository"

                git branch: 'main',

                    changelog: false,

                    poll: false,

                    url: 'https://github.com/PechMivan/CI-CD_TASK'

            }

        }

        stage('Test') {

            steps {

                echo "Running tests..."

                bat 'mvn test'

            }

        }

        stage('Report') {

            steps{

                echo "Generating report..."

                bat 'mvn jacoco:report'

            }

        }

        stage('SonarQube analysis') {

            environment{

                scannerHome = tool "sonarqube-5.0.1.3006"

            }

            steps{

                echo "Analyzing code using SonarQube..."

                withSonarQubeEnv(credentialsId: 'sonartoken', installationName: 'sonarserver') {

                    bat 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar -Pcoverage'

                }

            }

        }

        stage('Build') {

            steps {

                    echo "Creating the WAR file..."

                    bat 'mvn package -DskipTests'

            }

        }

        stage('Deploy') {

            steps {

                deploy adapters: [tomcat9(credentialsId: 'TomcatCredentials', path: '', url: 'http://localhost:8080/')], war : 'target/*.war'

            }

        }

    }

    post {

        success {

            jacoco(

              execPattern: 'target/*.exec',

              classPattern: 'target/classes',

              sourcePattern: 'src/main/java',

              exclusionPattern: 'src/test*'

            )

        }

    }

}