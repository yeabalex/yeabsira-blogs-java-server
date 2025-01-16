package com.blog.blog_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogServerApplication {

	public static void main(String[] args) {
		System.out.println("Hello World!");

		SpringApplication.run(BlogServerApplication.class, args);
	}

}
