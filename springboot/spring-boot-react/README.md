### Relevant Articles:

## Compile the Spring Boot
~/Downloads/softwares/apache-maven-3.8.6/bin/mvn clean install

## Run Spring Boot Backend App
java -DPN_LD_SDK_KEY=sdk-9224e8f4-1f79-4ca4-b4b0-1db49c6f77cb -jar target/spring-boot-react-2.7.4.jar

## Run React App
1. Go to frontend folder
2. run `npm install`
3. run `npm start`
4. Open Browser and go to the URL [http://localhost:3000/payments]
5. use following data points.
   1. `Tenant`: tenant1
   2. `none`: fake-valid-nonce