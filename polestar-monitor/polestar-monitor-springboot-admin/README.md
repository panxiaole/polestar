```bash
mvn package docker:build
docker run --name polestar-monitor-springboot-admin -d -p 8765:8765 polestar/polestar-monitor-springboot-admin
```
