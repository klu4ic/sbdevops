pipeline {
    agent any
    stages {
        
        stage('Remove Dir') {
            
            steps {
            deleteDir()
            
            }
        }
        
     stage('hello AWS') {
       steps {

                withAWS(credentials: 'ansible', region: 'us-east-1') {
                    sh 'echo "Hello World"'
               
               }
            }
        }   
        
        
        stage('Git Checkout') {
          steps {
              sh 'echo ${JOB_NAME}'
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
                sh 'mvn clean install -f /var/jenkins_home/workspace/BuildApplication/spring-boot/spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui/pom.xml'
                        
            }       
        }
        
         stage("Upload jar") {
           steps {
               
                 nexusPublisher nexusInstanceId: 'nexus-server', 
                     nexusRepositoryId: 'maven-repository', 
                     packages: [[$class: 'MavenPackage', 
                     mavenAssetList: [[classifier: '', 
                     extension: 'jar', 
                     filePath: 'spring-boot/spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui/target/spring-boot-smoke-test-web-ui-2.2.1.BUILD-SNAPSHOT.jar']], 
                     mavenCoordinate: [artifactId: 'spring-boot-smoke-test-web-ui-2.2.1', 
                     groupId: 'spring-boot-artifact', 
                     packaging: 'jar', 
                     version: 'build-${BUILD_NUMBER}']]]
                 
               
           }
        }
        

        
    stage("Build image") {
        steps {
            script {
             
  docker.withRegistry('https://556838424422.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:ansible') {
    sh '''
      docker build -t 556838424422.dkr.ecr.us-east-1.amazonaws.com/web-ui-app:${BUILD_NUMBER} -t 556838424422.dkr.ecr.us-east-1.amazonaws.com/web-ui-app:latest -f /var/jenkins_home/docker/Dockerfile .
      docker push 556838424422.dkr.ecr.us-east-1.amazonaws.com/web-ui-app:${BUILD_NUMBER} && docker push 556838424422.dkr.ecr.us-east-1.amazonaws.com/web-ui-app:latest
    '''
 }
  }
      }      
    }
        

  
        
             stage ("Clean WorkSpace"){
                steps{
                    cleanWs()
                  
                }
             }   
        
        
       
      stage ("Deploy"){
            steps{
                script {
                    build job: '/CI-Deploy'
                    build job: '/QA-Deploy'
//                   build job: '/x-Docker-Deploy'
                  
                }
            }
        }  
  
            
    }
}
