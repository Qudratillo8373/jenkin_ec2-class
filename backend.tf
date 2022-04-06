terraform {
    backend "s3" {
        bucket = "elasticbeanstalk-ca-central-1-062967708344"
        key = "terraform_vpc/us-east-1/class/dev/infrastructure.tfstate"
        region = "us-east-1"
    }
}