pipeline {
    agent any
    tools {
        jdk 'jdk11'
        maven "maven_3.9.6"
    }
    stages {
        stage("Build Maven"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/SophiaIncode/weather-service-suite']])  
                bat 'mvn clean install -DskipTests'
            }
        }
        stage("Build Docker Image"){
            steps{
                script{
                    bat 'docker build -t soophia/weather-forecast-devops .'
                }
            }
        }
        stage("Push image to Docker Hub"){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerimagehubpsw', variable: 'hubpassword')]) {
                    echo "Password: ${hubpassword}"    
                    bat "docker login -u soophia -p ${hubpassword}"
}

                bat 'docker push soophia/weather-forecast-devops'
                }
            }
        }
    }
}