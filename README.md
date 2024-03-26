# Course Project
<p align="center">
  <img src="./project.gif" alt="GIF of the Swagger Docs" />
</p>  
  
## Project Specification
- **Language:** Java 21;
- **Framework:** Spring v. 3.1.9;
- Spring Data JPA;
- Lombok;
- **SGBD/Database:** MySQL;
- **Migrations:** Flyway;
- **Docs:** Open API Swagger;
- **Email**: JavaMail API & Mailgun;
- **Report Generator:** OpenPDF.
## Development Tools
- **IDE:** IntelliJ;
- **Database client:** DBeaver;
- **Diagram tool:** Lucidchart;
- **Requests:** Swagger UI / HTTPie.
## Deploy - AWS
- Database: Using AWS RDS;
- Spring App: Using AWS ECS (Fargate) + EC2 load balancer;
- [Deploy Link](http://courseproject-1675530516.sa-east-1.elb.amazonaws.com)
## Running Locally - Instructions
You can choose either to run with Docker Compose or to run two containers separated, both options
are described below:  
### Running option #1 - Locally with Docker Compose
Just fill the fields with the correct information and run the command in the terminal 
inside the project root folder and you will have both containers running.
```shell
MYSQL_PASSWORD=mysql JAVA_TOOL_OPTIONS="-Dspring.datasource.url=\"jdbc:mysql://db:3306/db\" AND OTHERS VM OPTIONS" docker-compose up
```
### Running option #2 - Locally with Docker
#### Database creation on Docker
```shell
docker run -d -p 3306:3306 --name mysql \
-e MYSQL_ROOT_PASSWORD=mysql -e MYSQL_USER=app -e MYSQL_DATABASE=db \
-e MYSQL_PASSWORD=mysql mysql/mysql-server:latest
```
#### Project build
```shell
docker build -t courses .
```
#### Running
```shell
docker run -p 8080:8080 -e JAVA_TOOL_OPTIONS="VM_OPTIONS" --name courses courses
```
### Observation
For security concerns URLs, users and passwords do not have the current values in the ```application.properties``` file, instead they are passed via VM Options.