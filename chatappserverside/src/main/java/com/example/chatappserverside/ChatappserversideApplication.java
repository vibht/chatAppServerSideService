package com.example.chatappserverside;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.chatappserverside.Background.ConfiqRead;

@SpringBootApplication
public class ChatappserversideApplication {

	@Autowired
	public static ConfiqRead configurationServerFile;

	public static void main(String[] args) {
		SpringApplication.run(ChatappserversideApplication.class, args);
	}

}
