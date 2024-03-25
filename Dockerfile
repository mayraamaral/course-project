FROM openjdk:21-oracle

COPY . .
COPY src/main/resources /app/src/main/resources

RUN ./mvnw clean package -Dmaven.test.skip=true

WORKDIR /app

RUN cp /target/courses-0.0.1-SNAPSHOT.jar /app/courses.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "coursesS.jar"]
