#!/usr/bin/env groovy

/**
 * @ Maintainer sudheer veeravalli<veersudhir83@gmail.com>
 * Plugins Needed in Jenkins: Ant, Maven, Ansible, Artifactory, SonarQube,
 * PMD, JUnit, Jacoco, HTML Publisher,
 */

/* Only keep the 10 most recent builds. */
def projectProperties = [
        buildDiscarder(logRotator(artifactDaysToKeepStr: '20', artifactNumToKeepStr: '20', daysToKeepStr: '20', numToKeepStr: '20')),
        [$class: 'GithubProjectProperty', projectUrlStr: 'https://github.com/veersudhir83/devops-web-hackathon.git/']
        //,pipelineTriggers([pollSCM('H/10 * * * *')])
]

properties(projectProperties)

try {
    if (!isUnix()) {
        sh "echo 'Not a Unix mode'"
    } else {
        node {
            def mvnHome
            def mvnAnalysisTargets = '-P metrics pmd:pmd test javadoc:javadoc '
            def antHome
            def artifactoryPublishInfo
            def artifactoryServer
            def isArchivalEnabled = params.IS_ARCHIVAL_ENABLED
            // Enable if you want to archive files and configs to artifactory
            def isSonarAnalysisEnabled = params.IS_ANALYSIS_ENABLED // Enable if you want to analyze code with sonarqube
            def isDeploymentEnabled = params.IS_DEPLOYMENT_ENABLED // Enable if you want to deploy code on app server
            def isSeleniumTestingEnabled = params.IS_SELENIUM_TESTING_ENABLED // Enable if you want to generate reports
            def isReportsEnabled = params.IS_REPORTS_ENABLED // Enable if you want to generate reports

            def appName = 'devops-web-hackathon'// application name currently in progress
            def appEnv  // application environment currently in progress
            def artifactName = appName // name of the war/jar/ear in artifactory
            def artifactExtension = "jar" // extension of the war/jar/ear - for both target directory and artifactory
            def artifactoryRepoName = 'DevOps' // repo name in artifactory
            def artifactoryAppName = appName // application name as per artifactory

            def buildNumber = env.BUILD_NUMBER
            def workspaceRoot = env.WORKSPACE
            def artifactoryTempFolder = 'downloadsFromArtifactory'
            // name of the local temp folder where file(s) from artifactory get downloaded
            def sonarHome
            def SONAR_HOST_URL = 'http://localhost:9000'

            // Logic for Slack Notification Service
            def slackBaseUrl = 'https://defaultgrouptalk.slack.com/services/hooks/jenkins-ci/'
            def slackChannel = '#hackathon'
            def slackTeamDomain = 'defaultgrouptalk'
            def slackMessagePrefix = "Hackathon Job ${env.JOB_NAME}:${env.BUILD_NUMBER}"
            def slackTokenCredentialId = 'ecd292a7-bf0e-45c9-b599-aeb317ce2170'
            // replace with right one from jenkins credentials details

            // color can be good, warning, danger or anything
            slackSend baseUrl: "${slackBaseUrl}", channel: "${slackChannel}", color: "good", message: "${slackMessagePrefix} -> Build Started", teamDomain: "${slackTeamDomain}", tokenCredentialId: "${slackTokenCredentialId}"


            if (isArchivalEnabled) {
                // Artifactory server id configured in the jenkins along with credentials
                artifactoryServer = Artifactory.server 'ArtifactoryOSS-5.4.3'
            }

            // functions to use for artifactory operations
            def uploadMavenArtifactUnix = """{
                "files": [
                    {
                        "pattern": "${workspaceRoot}/${appName}/target/${artifactName}.${artifactExtension}",
                        "target": "generic-local/Applications/${artifactoryRepoName}/${artifactoryAppName}/app/${
                buildNumber
            }/"
                    }
                ]
            }"""

            stage('Tool Setup') {
                // ** NOTE: These tools must be configured in the jenkins global configuration.
                try {
                    mvnHome = tool name: 'mvn3.3.9', type: 'maven'
                    antHome = tool name: 'ant1.9.8', type: 'ant'
                    ansible = tool name: 'ansible1.5', type: 'org.jenkinsci.plugins.ansible.AnsibleInstallation'

                    if (isSonarAnalysisEnabled) {
                        sonarHome = tool name: 'sonar-scanner-3.0.3.778', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                    }
                    slackSend color: "good", message: "${slackMessagePrefix} -> Tool Setup Complete"

                } catch (exc) {
                    slackSend color: "danger", message: "${slackMessagePrefix} -> Tool Setup Failed"
                    error "Failure in Tool Setup stage: ${exc}"
                }
            }

            stage('Checkout') {
                try {
                    //cleanWs() // cleanup workspace before build starts

                    // Checkout codes from repository
                    dir('devops-web-hackathon') {
                        git url: 'https://github.com/veersudhir83/devops-web-hackathon.git',
                                branch: 'master'
                    }

                    if (isSeleniumTestingEnabled) {
                        dir('devops-hackathon-test-suite') {
                            git url: 'https://github.com/veersudhir83/devops-hackathon-test-suite.git',
                                    branch: 'master'
                        }
                    }

                    dir('downloadsFromArtifactory') {
                        // created folder for artifactory
                    }

                    slackSend color: "good", message: "${slackMessagePrefix} -> Checkout Complete"
                } catch (exc) {
                    slackSend color: "danger", message: "${slackMessagePrefix} -> Checkout Failed"
                    error "Failure in Checkout stage: ${exc}"
                }
            }

            stage('Build') {
                try {
                    dir('devops-web-hackathon/') {
                        sh "'${mvnHome}/bin/mvn' clean package"
                        sh "cp ./target/${appName}*.${artifactExtension} ./target/${appName}.${artifactExtension}"
                    }
                    slackSend color: "good", message: "${slackMessagePrefix} -> Build Complete"
                } catch (exc) {
                    slackSend color: "danger", message: "${slackMessagePrefix} -> Build Failed"
                    error "Failure in Build stage: ${exc}"
                }
            }

            stage('Analysis') {
                try {
                    if (isSonarAnalysisEnabled) {
                        mvnAnalysisTargets = "${mvnAnalysisTargets} sonar:sonar"
                    }

                    dir('devops-web-hackathon/') {
                        sh "'${mvnHome}/bin/mvn' ${mvnAnalysisTargets}"
                    }

                    slackSend color: "good", message: "${slackMessagePrefix} -> Analysis Complete"
                } catch (exc) {
                    slackSend color: "danger", message: "${slackMessagePrefix} -> Analysis Failed"
                    error "Failure in Analysis stage: ${exc}"
                }
            }

            stage('Publish') {
                try {
                    if (isArchivalEnabled) {
                        echo 'Publish Artifacts & appConfig.json in progress'
                        dir('devops-web-hackathon/') {
                            if (fileExists('target/devops-web-hackathon.jar')) {
                                // upload artifactory and also publish build info
                                artifactoryPublishInfo = artifactoryServer.upload(uploadMavenArtifactUnix)
                                artifactoryPublishInfo.retention maxBuilds: 5
                                // and publish build info to artifactory
                                artifactoryServer.publishBuildInfo(artifactoryPublishInfo)
                            } else {
                                error 'Publish: Failed during file upload/publish to artifactory'
                            }
                        }
                    }
                    slackSend color: "good", message: "${slackMessagePrefix} -> Archival Complete"
                } catch (exc) {
                    slackSend color: "danger", message: "${slackMessagePrefix} -> Archival Failed"
                    error "Failure in Publish stage: ${exc}"
                }
            }

            stage('Deployment') {
                if (isDeploymentEnabled) {
                    echo 'Deploy application using ansible'
                    try {
                        ansiblePlaybook installation: 'ansible1.5', playbook: 'devops-web-hackathon/configuration_scripts/app-service-deploy.yml'
                        slackSend color: "good", message: "${slackMessagePrefix} -> Deployment Complete"
                    } catch (exc) {
                        slackSend color: "danger", message: "${slackMessagePrefix} -> Deployment Failed"
                        error "Failure in Deployment stage: ${exc}"
                    }
                }
            }

            stage('Build and Run Test Suite') {
                if (isSeleniumTestingEnabled) {
                    try {
                        dir('devops-hackathon-test-suite/build/') {
                            sh "'${antHome}/bin/ant'"
                            sh '''
                                chown -R jenkins:jenkins *
                                chmod 777 -R *
                            '''
                            wrap([$class: 'Xvfb', additionalOptions: '', assignedLabels: '', displayName: 99, displayNameOffset: 0, installationName: 'Default', screen: '1024x768x8', timeout: 20]) {
                                sh '''
                                    # Xvfb :99 -screen 0 1024x768x8 > /dev/null
                                    java -jar test.jar LINUX CHROME
                                '''
                            }
                        }
                        slackSend color: "good", message: "${slackMessagePrefix} -> Test Suite Run Complete"
                    } catch (exc) {
                        slackSend color: "danger", message: "${slackMessagePrefix} -> Test Suite Run Failed"
                        error "Failure in Build and Run Test Suite stage: ${exc}"
                    }
                }
            }

            stage('Generate Reports') {
                if (isReportsEnabled) {
                    try {
                        junit '**/devops-web-hackathon/target/surefire-reports/*.xml'
                        //jacoco()
                        //publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'devops-web-maven/target/site/apidocs', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])

                        slackSend color: "good", message: "${slackMessagePrefix} -> Generate Reports Complete"
                    } catch (exc) {
                        slackSend color: "warning", message: "${slackMessagePrefix} -> Generate Reports Failed"
                        error "Failure in Generate Reports stage: ${exc}"
                    }
                }
            }
        }
    }
} catch (exc) {
    error "Caught: ${exc}"
}