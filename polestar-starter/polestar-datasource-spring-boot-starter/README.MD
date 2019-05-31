### 引用该starter配置样例
```yaml
# datasource
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/polestar?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnectForPools=true&serverTimezone=GMT%2B8
    username: root
    password: welcome
    driver-class-name: com.mysql.cj.jdbc.Driver
# Mybatis-plus
mybatis-plus:
  type-aliases-package: com.github.panxiaole.**.model
  type-enums-package: com.github.panxiaole.**.enumeration
  mapper-locations:
    - classpath*:mybatis/mapper/*.xml
  configuration:
    jdbc-type-for-null: 'null'
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)

```
