pipeline {
    agent {label 'agent1'}
    options {
        skipDefaultCheckout(true)
    }
    stages {
        stage ('GitClone') {
            steps {
                cleanWs()
                git branch: "main", url: "https://github.com/fabienAstia/chefDoeuvre.git" 
            }
        }
        stage ('BDD') {
            steps {
                echo 'lauching BDD deployment'
                script {
                    withCredentials([usernamePassword(
                        credentialsId: "pg-personalities-ddl",
                        usernameVariable: "ddl_username",
                        passwordVariable: "ddl_password"
                    )]) {
                        sh 'chmod -R +x deployment/'
                        sh 'deployment/deploy-db.sh'
                    }
                }
            }
        }
        stage ('API') {
            steps {
                echo 'lauching API deployment'
                script {
                        withCredentials([usernamePassword(
                        credentialsId: "pg-personalities-dml",
                        usernameVariable: "dml_username",
                        passwordVariable: "dml_password"
                    )]) {
                        sh 'deployment/deploy-api.sh'
                    }
                }
            }
        }
        stage ('Front'){
            steps {
                echo 'lauching deployment of Front'
                sh 'deployment/deploy-front.sh'
            }
        }
    }
    post {
        success {
            echo "✅ All jobs succeeded"
        }
        failure {
            echo "❌ One step failed"
        }
        always {
            echo "🕒 Pipeline ended at: ${new Date()}"
        }
    }
}
