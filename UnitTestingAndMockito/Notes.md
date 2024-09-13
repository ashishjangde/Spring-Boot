
./mvnw package -DskipTests
java -D"spring.profiles.active=prod" -jar target/UnitTestingAndMockito-0.0.1-SNAPSHOT.jar