FROM nginx:1.22-alpine

RUN apk add --no-cache openssh-client git

RUN mkdir -p -m 0700 ~/.ssh && ssh-keyscan github.com >> ~/.ssh/known_hosts

RUN --mount=type=ssh git clone git@github.com:Tyatyushkin/jenkins-config.git && \
cp jenkins-config/index.html /usr/share/nginx/html/

CMD ["nginx", "-g", "daemon off;"]
