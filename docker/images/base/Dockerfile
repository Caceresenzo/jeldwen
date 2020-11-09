FROM debian

RUN apt-get update
RUN apt-get install -y git zsh curl wget nano nginx
RUN apt-get install -y openjdk-11-jdk-headless maven

RUN apt-get install -y nodejs npm
RUN npm install npm@latest -g

RUN sh -c "$(wget -O- https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"

COPY pom.xml /tmp
RUN cd /tmp && mvn install