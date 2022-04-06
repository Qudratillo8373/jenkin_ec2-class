properties([
    parameters([
        choice(choices: ['dev', 'qa', 'prod'], description: 'Choose an Environment', name: 'environment'),
        choice(choices: ['destroy', 'apply'], description: 'Choose the Action', name: 'action')
        ])
    ])

if( params.environment == 'dev' ) {
    vpc_region = "us-east-1"
}

else if( params.environment == 'qa' ) {
    vpc_region = "us-east-2"
}

else {
    vpc_region = "us-west-2"
}

node {
    stage('Pull Code'){
        git branch: 'main', url: 'https://github.com/Qudratillo8373/jenkin_ec2-class.git'
    }

    withCredentials([usernamePassword(credentialsId: 'aws-key', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')])  {
        withEnv(['AWS_REGION=us-east-1']) {
            stage('Terraform Init'){
                sh """
                
                    terraform init
                """
            }

            stage('Terraform Plan'){
                sh """
                    terraform plan -var-file ${params.environment}.tfvars
                """
            }

            if(params.action == 'apply'){
                stage('Terraform Apply'){
                    sh """
                        terraform apply -var-file ${params.environment}.tfvars -auto-approve
                    """
                }
            }

            else {
                stage('Terraform Destroy'){
                    sh """
                        terraform destroy -var-file ${params.environment}.tfvars -auto-approve
                    """
                }
            }
        }
    }
}