For launching
-------------------------

Fill in all fields in src/main/resources/META-INF/application.properties.

For initialization database use liquibase scripts. Just do `mvn install`.

URL: http://localhost:8888/get_document/?email=Vitalii.Tytarenko@gmail.com&doc_format=xls&filter=2017-01-01


Workflow
-------------------------
1. Get request with parameters:
- email
- document format (optional)
- date filter (optional)
2. Do query to DB
3. Get result data
4. Generate document
5. Send document through SMTP to email


Technologies
-------------------------

- Java 8
- MySQL
- Liquibase
- Hibernate 5
- Spring Framework 4
- Spring MVC
- Apache POI
- JavaMail API
- Log4j
- Maven
- Tomcat