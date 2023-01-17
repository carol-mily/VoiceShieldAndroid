package com.example.util;

/**
 * 这是客服端与服务器端通信的配置信息。
 */

public class ServerConfiguration {
    public static final String IP = "http://localhost:8080/test_war_exploded/";//服务器地址   需要改
    public static final int PORT = 8080;//要根据应用服务器tomcat的端口改变    需要改
    public static final String USERSERVICEURI = "testSSM/test/getInfo.json";
}
