package com.faks.elepo;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class ElepoApplication implements CommandLineRunner {

	private final SocketIOServer socketIOServer;

	public ElepoApplication(SocketIOServer socketIOServer) {
		this.socketIOServer = socketIOServer;
	}


	public static void main(String[] args) {
		SpringApplication.run(ElepoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		socketIOServer.start();
	}

	@PreDestroy
	public void destroy() {
		socketIOServer.stop();
	}

}
