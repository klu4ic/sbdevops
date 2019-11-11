pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }

        environment {
          // This can be nexus3 or nexus2
          NEXUS_VERSION = "nexus3"
          // This can be http or https
          NEXUS_PROTOCOL = "http"
          // Where your Nexus is running
          NEXUS_URL = "172.17.0.3:8081"
          // Repository where we will upload the artifact
          NEXUS_REPOSITORY = "repository-example"
          // Jenkins credential id to authenticate to Nexus OSS
          NEXUS_CREDENTIAL_ID = "nexus-credentials"
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

