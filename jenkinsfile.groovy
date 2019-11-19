properties([
    pipelineTriggers([
        GenericTrigger(
            causeString: 'Push to master', 
            genericVariables: [[
                defaultValue: '',
                key: 'ref', 
                regexpFilter: '', 
                value: '$.ref'
            ]], 
            printContributedVariables: true, 
            printPostContent: true, 
            regexpFilterExpression: 'master$', 
            regexpFilterText: '$ref', 
            silentResponse: true, 
            token: '71B6B68DFC8C34F3G5R3F5F1'
        )
    ])
])

node {
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
    
    
    
    stage ("Save Artifact") {
    archiveArtifacts artifacts: "spring-boot/LICENSE.txt", fingerprint: true
    }

}
