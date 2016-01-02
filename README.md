# Spring Security with Spring Boot Stateless Authentication Example

### Overview

This project is about `Stateless Athentication` with `Spring Boot` and `Spring Security`.

1. Basic authentication with `ID` and `Password` as usal way.
2. After basic athentication `Token` is generated for specific client.
3. With generated `Token`, client can access any REST api in server.
3. Basically application works with `Stateless Session` , it can be applied like auto-scaling group in AWS environment or MSA architecture.
4. In this example, generated `Token` is stored Ehecache which is temporary memory based cache, but in production level Redis will be good replacement for any purpose.

### Dependecy
```gradle
dependencies {
    compile('org.springframework.boot:spring-boot-starter-data-jpa')
   	compile('org.springframework.boot:spring-boot-starter-jdbc')
   	compile('org.projectlombok:lombok:1.16.6')
   	compile('org.springframework.boot:spring-boot-starter-security')
   	compile('org.springframework.boot:spring-boot-starter-web')
       compile("org.lazyluke:log4jdbc-remix:0.2.7")
   	compile('net.sf.ehcache:ehcache-core:2.6.9') {
   		exclude group: 'commons-logging'
   	}
   	runtime('com.h2database:h2')
   	testCompile('org.springframework.boot:spring-boot-starter-test')
}
```



