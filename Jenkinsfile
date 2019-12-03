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
        
        stage('Deploy') {
            agent {
                docker { 
                      image 'woahbase/alpine-ansible:x86_64' 
                      args '-v /opt/docker/volumes/ansible/ansible-data/:/var/opt -v /opt/docker/volumes/ansible/ansible-cache/:/home/alpine/'
                     
                      
                }
            }
            steps {
                 sh 'env'
                sh 'cd /var/opt && ansible-playbook deploy_role.yml --tags "jar-deploy" --limit ${JOB_NAME} --extra-vars "jserverport=$paramport choise_artifact=$Select_artifact_version_ARTIFACT_URL"'
                sh 'cd /var/opt && ansible-playbook deploy_role.yml --tags "docker-deploy" --limit ${JOB_NAME}'
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
                //    build job: '/QI-Deploy'
                  
                }
            }
        }  
  
            
    }
}
