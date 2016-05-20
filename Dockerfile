FROM jboss/base-jdk:8

ENV JAVA_APP_JAR frontend-0.0.1-SNAPSHOT-fat.jar

EXPOSE 8080
WORKDIR /app/
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar $JAVA_APP_JAR -cluster"]
ADD . /contents

USER root
RUN yum -y install maven 
RUN cd /contents && mvn clean package
RUN mv /contents/target/frontend-0.0.1-SNAPSHOT-fat.jar /app
