# Course Project
## Project Specification
- **Language:** Java 21;
- **Framework:** Spring v. 3.1.9;
- Spring Data JPA;
- **SGBD/Database:** MySQL;
- **Migrations:** Flyway;
- **Docs:** Open API Swagger;
- Lombok.
## Development Tools
- **IDE:** IntelliJ;
- **Database client:** DBeaver;
- **Diagram tool:** Lucidchart;
- **Requests:** Swagger UI.
## Instructions
### Locally
Database creation on Docker:  
```shell
docker run -d -p 3306:3306 --name mysql \
-e MYSQL_ROOT_PASSWORD=mysql -e MYSQL_USER=root -e MYSQL_DATABASE=db \
-e MYSQL_PASSWORD=mysql mysql/mysql-server:latest
```
### Accessing MySQL shell to create a user with privileges 
```shell
docker exec -it mysql bash -l
```
```shell
mysql -u root -p
```
### Creating the user and granting privileges
```sql
CREATE USER 'your_name'@'%' IDENTIFIED BY 'your_password';
GRANT ALL ON *.* TO 'your_name'@'%' WITH GRANT OPTION;
```
### Observation
For security concerns, the datasource URL, user and password do not have the current values in the ```application.properties``` file, instead they are passed via VM Options.