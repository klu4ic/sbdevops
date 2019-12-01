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
                sh 'mvn -f /var/jenkins_home/workspace/BuildApp/spring-boot/spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui/pom.xml clean install'
                        
            }
            
        }
        
         stage("Upload jar") {
           steps {
               
                 nexusPublisher nexusInstanceId: 'nexus', 
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
        
        stage("Build & Upload Docker Container") {
            
             agent {
                docker { 
                      image 'woahbase/alpine-ansible:x86_64' 
                      args '-v /opt/docker/volumes/ansible/ansible-data:/var/opt -v /opt/docker/volumes/ansible/ansible-cache:/home/alpine'
                    
                }
            }
            
            steps {
            
             sh 'cd /var/opt && ansible-playbook deploy_role.yml --tags "docker-build " --limit aws_devtools --extra-vars "tagvar=build-${BUILD_NUMBER}"'
           
            }
        }
        
     stage ("Clean WorkSpace"){
           
          steps{
          
              cleanws()
          }
     }       
        
        
      stage ("Deploy"){
            steps{
                script {
                    build job: '/CI DEPLOY'
//                    ${env.select_deploy_job}
                  
                }
            }
        }  
  
            
    }
}
