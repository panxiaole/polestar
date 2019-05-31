#Swagger2启动器
* #####自动配置Swagger2，自定义属性在application.yml定义即可，例：
```yaml
swagger:
  enable: true
  title: 系统管理
  description: 系统管理模块接口文档
  version: 1.0
  basePackage: com.github.panxiaole.polestar.biz.sys.controller
  termsOfServiceUrl: https://github.com/panxiaole/polestar
  contact:
    name: 潘小乐
    email: panxiaole@haier.com
    url: https://github.com/panxiale

```

* #####引入该启动器但是不想开启swagger文档页面，配置
```yaml
swagger:
  enable: false
```
或
```yaml
swagger:
  production: true
```

* #####集成了开源项目swagger-bootstrap-ui,替换了原swagger-ui,访问地址
```yaml
    http://hostname:port/doc.html
```
