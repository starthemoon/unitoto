# unitoto

## Set up a background development environment

- Install [Tomcat](https://tomcat.apache.org) in Eclipse
- Install [Maven](http://maven.apache.org) and [Maven Repository](http://mvnrepository.com) in Eclipse
- Install [Zookeeper](https://zookeeper.apache.org)
- Import `Unitoto-api` , `Unitoto-service` and `Unitoto-web` to Eclipse
- Run `Zookeeper`
- Run `unitoto/Unitoto-service/src/main/java/com/avenger/declare/Start.java`
- Import `Unitoto-web` to `Tomcat`
- Run `Tomcat`

## Run background in terminal

### Run Start.java

- Input `cd /Users/James/Documents/GitHub/unitoto/Unitoto-service/target/Unitoto-service-0.0.1-SNAPSHOT.jar`
- Input `java -Djava.ext.dirs=/Users/James/Documents/workspace/Unitoto/Unitoto-service/target/lib -cp Unitoto-service-0.0.1-SNAPSHOT.jar com.avenger.declare.Start`

### Run Tomcat

- Copy `Unitoto-service-0.0.1-SNAPSHOT.ja` to `/Users/James/Documents/apache-tomcat-8.0.9/webapps`
- Input `cd /Users/James/Documents/apache-tomcat-8.0.9/bin`
- Input `sudo sh startup.sh`

## Set up a foreground development environment

- Download [Node.js](https://nodejs.org)
- Input `npm i`
- Input `npm run dev`
