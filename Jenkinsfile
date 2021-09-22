pipeline {
    agent { label 'agent1' }
    stages {
        stage('Build') { 
            tools {
                maven 'maven-3.8.2'
            }
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}