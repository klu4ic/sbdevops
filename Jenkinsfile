pipeline {
    agent any
    stages {
        
        stage('Remove Dir') {
            
            steps {
            deleteDir()
            
            }
        }
        
        stage('Git Checkout') {
          steps {
                sh 'git clone https://github.com/klu4ic/spring-boot.git'
          }
        }
        
        stage('Build') {
            agent {
                docker { 
                      image 'maven:3-alpine' 
                      args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn -f /var/jenkins_home/workspace/Maven_1/spring-boot/spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui/pom.xml clean install'
            }
        }
        
         stage("Upload") {
           steps {
               
                 nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'maven-releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'spring-boot/spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui/target/spring-boot-smoke-test-web-ui-2.2.1.BUILD-SNAPSHOT.jar']], mavenCoordinate: [artifactId: 'spring-boot-smoke-test-web-ui-2.2.1', groupId: 'spring-boot-artifact', packaging: 'jar', version: 'build-${BUILD_NUMBER}']]]
              
               
           }
        }
        
        
  
            
    }
}
