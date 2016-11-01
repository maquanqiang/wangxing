echo mvn package begin
mvn clean && mvn compile && mvn install -DskipTests
echo mvn package end