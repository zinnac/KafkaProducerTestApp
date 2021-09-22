pipeline {
    agent { label 'agent1' }
    stages {
        stage('Build') { 
            tools {
                maven 'Maven 3.2.5'
            }
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
    }
}