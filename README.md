### README
#### Задание
Написать Jenkins pipeline, в котором происходит сборка сервиса из первого
репозитория.
Сборка запускается командой docker build, при этом нужно безопасно передать в
Dockerfile deploy key второго репозитория, чтобы внутри выполнить git clone этого
репозитория.

#### Решение
0. Создаем основые конфигурационные файлы **Dockerfile**, **index.html**, **deploy.groovy**

1. Создаем репозитории с помощью консольной утилиты github
``` 
gh repo create jenkins-demo --public -c
gh repo create jenkins-dockerfile --private -c
gh repo create jenkins-config --private -c 
```
2. Создаем ключи
```
ssh-keygen -t rsa -b 4096 -N "" -f repo
ssh-keygen -t rsa -b 4096 -N "" -f appkey
```
3. Заливаем deploy key в репозитории и складываем в них уже созданные **Dockerfile** и **index.html**
```
gh repo deploy-key add -R Tyatyushkin/jenkins-config appkey.pub
gh repo deploy-key add -R Tyatyushkin/jenkins-dockerfile repo.pub
```
4. Создаем c помощью terraform instance в репозитори [scw-terraform](https://github.com/Tyatyushkin/scw-terraform)
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
5. На создавшийся instance устанавливаем Jenkins и Docker. Устанавливаем на jenkins plugin **ssh agent**, создаем креды и наш пайплайн из данного репозитория. 
Так же создаем пользователя view для демонастрации работы.


6. Демонстрация работы pipeline в [Jenkins](http://51.158.71.201:8080)
```
login: view
password: 
```
7. Запускаем созданный образ
```
docker run --name nginx -p 80:80 -d nginx
```
8. Проверяем запущенный контейнер с [nginx](http://51.158.71.201)