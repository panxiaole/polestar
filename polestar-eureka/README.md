```bash
mvn package docker:build
docker run --name polestar-eureka -d -p:8761:8761 polestar/polestar-eureka
```
