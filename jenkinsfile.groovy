properties([pipelineTriggers([cron('*/5 * * * *')])])

node() {
    stage("average") {
        sh "uptime"
        deleteDir()
    }
    
    stage("gitclone") { 
            sh "git clone https://github.com/klu4ic/spring-boot.git"
            
    }

    stage ("Dir") {
        def workDir = sh(returnStdout: true, script: "pwd").trim()
        sh "cd $workDir && cd spring-boot && ls -lh"
    }
}    

    input("Please approve deploy to.")
node(){
    
    
    stage ("Save Artifact") {
    archiveArtifacts artifacts: "spring-boot/LICENSE.txt", fingerprint: true
    }

}
