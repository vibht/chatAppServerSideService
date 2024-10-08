package com.example.chatappserverside.helper;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Constanat {

    public String SERVER_CONF;
    public String SERVER_NETWORK;
    public String SERVER_HOST;
    public String CHAT_APP_HOST;
    public Integer CHAT_APP_PORT;
    public String SCALA_CLIENT_HOST;
    public String SCALA_SERVER_HOST;
    public Integer SCALA_CLIENT_PORT;
    public Integer SCALA_SERVER_PORT;
    public String SCALA_SERVER_API_BASE_URL;
    public String SCALA_CLIENT_APi_BASE_URL;
    public String SCALA_CHAT_API_BASE_URL;
    public boolean MULTI_CLIENT_CONFIGURATION;
    public boolean SERVER_STOP_CONF;


}
