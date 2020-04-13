pipeline {
   agent any
    tools {
      maven 'mvn3.5.4'
    }
    
    triggers {
      cron 'H/30 H * * * '
    }


   stages {
      stage('Checkout') {
         steps {
            //cleanWs() 
            echo 'In Checkout Stage'
            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/v4m5h1/devops-web-hackathon.git']]])
         }
      }
      stage('Build') {
         steps {
            echo 'In Build stage'
            sh label: '', script: 'mvn --version && mvn clean package'
         }
      }
      stage('Analyze') {
         steps {
            echo 'In Analyze Stage'
            sh label: '', script: 'mvn --version && mvn -P metrics pmd:pmd test sonar:sonar'
         }
      }
      stage('Archive') {
         steps {
            echo 'In Archive stage'
         }
      }
      stage('Deploy') {
         steps {
            echo 'In Deploy stage'
         }
      }
   }
}
