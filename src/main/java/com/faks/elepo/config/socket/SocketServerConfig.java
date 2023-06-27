package com.faks.elepo.config.socket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class SocketServerConfig {
    @Value("${socket.hostname}")
    private String hostname;

    @Value("${socket.port}")
    private Integer port;

    @Bean(name = "socketIoServer")
    public SocketIOServer socketIOServer() {
        Configuration configuration = new Configuration();
        configuration.setHostname(hostname);
        configuration.setPort(port);

        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);

        configuration.setSocketConfig(socketConfig);

        return new SocketIOServer(configuration);
    }
}
