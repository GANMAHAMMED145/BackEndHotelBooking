pipeline {
    agent any

    environment {
        JAVA_HOME = tool 'JDK17'
        MAVEN_HOME = tool 'Maven3'
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout([$class: 'GitSCM',
                    branches: [[name: '*/master']],
                    extensions: [],
                    userRemoteConfigs: [[url: 'https://github.com/GANMAHAMMED145/BackEndHotelBooking']]
                ])
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t mahammed145/residentialhotelbookingproject .'
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                   withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerhubpwd')]) {
                   sh "echo ${dockerhubpwd} | docker login -u mahammed145 --password-stdin"
}

                    sh 'docker push mahammed145/residentialhotelbookingproject'
                }
            }
        }
    }
}
