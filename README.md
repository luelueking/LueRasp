# LueRasp
### Quick Start
- add params before start jar
```
-Dfile.encoding=UTF-8 -noverify -Xbootclasspath/p:agent.jar -javaagent:agent.jar
```
### Ability
- RCE Defense
- SQLi Defense (todo test)

### Java-Sec-Code靶机测试

#### RCE

<img src="https://i.328888.xyz/2023/04/09/icsDIt.png" alt="icsDIt.png" border="0" width="50%" height="50%"/>



#### Sqli

`http://localhost:8080/sqli/jdbc/vuln?username=abc'or'1'='1`

<img src="https://i.328888.xyz/2023/04/09/icsUGJ.png" alt="icsUGJ.png" border="0" />

