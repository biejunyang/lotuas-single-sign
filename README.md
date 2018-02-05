# spring-boot + spring datajpa + hibernate + hibernateTemplate + jdbcTemplate

#1、pom.xml文件：	
	#1.1、修改代码后自动生效依赖，Reload Java classes without restarting the container
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-devtools</artifactId>
		</dependency>
	
	#1.2、Spring Boot程序打成war，单独部署到其他web容器是，需要去掉Sprint Boot内嵌的tomcat依赖
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
	
    #1.3、servlet api依赖，只是在编译阶段     
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
			<scope>provided</scope>
		</dependency>
		
	#1.4、Spring Boot内嵌的jetty服务器依赖
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-jetty</artifactId>
		</dependency>
		
	#1.5、Spring Boot打成jar时运行jsp,打包时将jsp文件拷贝到BOOT-INF目录下  
		<resources>  
            <resource>
                <directory>src/main/webapp</directory>
                <targetPath>BOOT-INF</targetPath>  
                <includes>  
                    <include>**/**</include>  
                </includes>  
            </resource>  
            <resource>  
                <directory>src/main/resources</directory>  
                <includes>  
                    <include>**/**</include>  
                </includes>  
                <filtering>false</filtering>  
            </resource>  
        </resources>
     
     #1.6、Spring Data JPA jar包   
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        
        <dependency>
		    <groupId>mysql</groupId>
		    <artifactId>mysql-connector-java</artifactId>
		</dependency>
        
