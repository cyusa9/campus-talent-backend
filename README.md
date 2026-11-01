# CampusTalent

- **Name**: KWIZERA Pacifique
- **Student ID**: 28054
- **Project Name**: campus-talent

## Steps to Run the API

1. Make sure **Java 17+** and **PostgreSQL** are installed and running on `localhost:5432`.
2. Create a PostgreSQL database named `campustalent`.
3. Verify the database credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/campustalent
   spring.datasource.username=postgres
   spring.datasource.password=12345
   ```
4. Open a terminal in the project root folder and start the application:
   ```bash
   mvn spring-boot:run
   ```
5. The API will be accessible at `http://localhost:8080`.
