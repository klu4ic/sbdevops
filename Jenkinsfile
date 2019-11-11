pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -Djar.finalName=myCustomName -f /var/jenkins_home/workspace/java/spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui/pom.xml clean install' 
            }
        }
    }
}
