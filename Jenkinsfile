pipeline {
    agent { label 'agent1' }
	options { skipStagesAfterUnstable()}
    stages {
        stage('Build') { 
            tools {
                maven 'maven-3.8.2'
            }
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
        stage('Deliver') {
            steps {
                sh 'mvn package'
            }
        }
    }
}