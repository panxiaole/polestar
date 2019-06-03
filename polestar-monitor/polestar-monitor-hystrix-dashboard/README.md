```bash
mvn package docker:build
docker run --name polestar-monitor-hystrix-dashboard -d -p 9001:9001 polestar/polestar-monitor-hystrix-dashboard
```
