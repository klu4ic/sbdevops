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
        
        stage("Upload") {
           steps {
               
               nexusArtifactUploader artifacts: [[artifactId: 'web-ui', classifier: '', file: 'spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui/target/spring-boot-smoke-test-web-ui-2.2.1.BUILD-SNAPSHOT.jar', type: 'jar']], credentialsId: 'nexus-credentials', groupId: 'xz', nexusUrl: '192.168.33.10:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'maven-releases', version: '${BUILD_VERSION}'
               
           }
        }
    }
}
