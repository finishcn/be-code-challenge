FROM openjdk:17
MAINTAINER liyu
WORKDIR /app

ADD pong.jar /app/

ENTRYPOINT ["sh","-c","java -server -Xms256m -Xmx512m -Xmn256m -jar /app/pong.jar -Duser.timezone=Asia/Shanghai"]