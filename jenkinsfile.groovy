properties([
    buildDiscarder(
        logRotator(
            artifactDaysToKeepStr: '14',
            artifactNumToKeepStr: '5',
            daysToKeepStr: '14',
            numToKeepStr: '10'
        )
    ),
    parameters([
        choice(
            name: 'BRANCH',
            choices: 'master\nstable\nrelease',
            description: 'Choise master, stable, release'
        )
    ]),
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
		 
		ansiColor('xterm') {
            printlnGreen "ttexttt"
        }
        deleteDir()
    }
    
    stage("gitclone") { 
		

       sh "git clone https://github.com/klu4ic/spring-boot.git"
     //	 checkout scm       
    }

    stage ("Dir") {
        def workDir = sh(returnStdout: true, script: "pwd").trim()
        sh "cd $workDir && cd spring-boot && ls -lh"
    }
    
    
    
    stage ("Save Artifact") {
    archiveArtifacts artifacts: "spring-boot/LICENSE.txt", fingerprint: true
    }

}

def printlnGreen(text) {
    println "\033[1;4;37;42m$text\033[0m"
}
