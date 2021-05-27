package com.vvvtimes.com;

import java.util.Properties;
import java.util.UUID;

import org.eclipse.jetty.server.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vvvtimes.com.server.MainServer;

@SpringBootApplication
@RestController
public class Application {

	private static String url;

	public static void main(String[] args)throws Exception{
		SpringApplication.run(Application.class, args);
		Properties props = new Properties();
		props.load(Application.class.getClassLoader().getResourceAsStream("application.properties"));
		Integer port = Integer.parseInt(props.getProperty("server.port"))+1;
		String ip = props.getProperty("ipconfig");
		Server server = new Server(port);
		server.setHandler(new MainServer());
		server.start();
		System.out.println("License Server started at http://localhost:" + port);
		System.out.println("JetBrains Activation address was: http://localhost:" + port + "/");
		System.out.println("JRebel 7.1 and earlier version Activation address was: http://localhost:" + port + "/{tokenname}, with any email.");
		System.out.println("JRebel 2018.1 and later version Activation address was: http://localhost:" + port + "/{guid}(eg:http://localhost:" + port + "/"+ UUID.randomUUID().toString()+"), with any email.");
		url="http://"+ip+":"+port+"/"+ UUID.randomUUID().toString();
		server.join();
	}
	
	@RequestMapping
	public String license(){
		return url;
	}
}
