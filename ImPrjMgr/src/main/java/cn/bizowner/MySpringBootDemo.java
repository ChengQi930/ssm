package cn.bizowner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//使用内嵌tomcat启动类
/*@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
public class MySpringBootDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SpringApplication.run(MySpringBootDemo.class,args);

	}

}*/





//部署到tomcat启动类
@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
public class MySpringBootDemo extends SpringBootServletInitializer{

	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MySpringBootDemo.class);
    }
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(MySpringBootDemo.class,args);
	}

}
