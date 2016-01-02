# Spring Security with Spring Boot Stateless Authentication Example

### 개요

`Spring Boot` 기반, `Spring Security` 인증 예제다.

1. `ID` + `Password` 기반의 Basic 인증 후, Token이 발급됨
2. 이후 Client는 발급된 Token으로, Header 기반 인증이 가능함
3. `Stateless Session` 이므로, AWS 환경에서 Auto Scaling 이나, MSA 인증 서버 등에서 활용 가능 하겠음.
4. 예제에서는 발급된 Token은 Ehecache에 저장하였으나, Production Level에서는 Redis, memecached 등이 활용 가능하겠음

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



