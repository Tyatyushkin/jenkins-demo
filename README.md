### README

``` 
gh repo create jenkins-demo --public -c
gh repo create jenkins-dockerfile --private -c
gh repo create jenkins-config --private -c 
```
Создаем ключи
```
ssh-keygen -t rsa -b 4096 -N "" -f repo
ssh-keygen -t rsa -b 4096 -N "" -f appkey
```
Заливаем deploy key
```
gh repo deploy-key add -R Tyatyushkin/jenkins-config appkey.pub
gh repo deploy-key add -R Tyatyushkin/jenkins-dockerfile repo.pub
```
Создаем c помощью terraform у нашего провайдера instance
```
resource "scaleway_instance_ip" "ip_jenkins" {
  zone = var.zone
}

resource "scaleway_instance_server" "jenkins" {
  type              = "DEV1-S"
  image             = "ubuntu_focal"
  tags              = ["jenkins", "public"]
  zone              = var.zone
  name              = "Jenkins"
  enable_dynamic_ip = "false"
  ip_id             = scaleway_instance_ip.ip_jenkins.id
}
```
На создавшийся instance устанавливаем Jenkins и Docker. 
