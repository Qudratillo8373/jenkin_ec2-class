resource "aws_instance" "web" {
  ami           = data.aws_ami.ubuntu.id
  instance_type = "t3.micro"
  associate_public_ip_address  = true   # false
  availability_zone  = "us-east-1a"
  key_name  = aws_key_pair.deployer.key_name
  user_data = file("wordpress.sh")
  vpc_security_group_ids = [aws_security_group.december.id] 
}

