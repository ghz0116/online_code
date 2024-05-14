package com.zuel.onlineCode.util;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisUtil {
    public static void set(String key,String value,int seconds) {
        // 创建RedisClient实例并连接到本地Redis服务器（默认端口6379）
        RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        // 获取同步执行的RedisCommands对象
        RedisCommands<String, String> commands = connection.sync();
        // 执行Redis命令
        commands.set(key, value);
        commands.expire(key,seconds);
        // 关闭连接
        connection.close();
        redisClient.shutdown();
    }
    public static String get(String key) {
        // 创建RedisClient实例并连接到本地Redis服务器（默认端口6379）
        RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        // 获取同步执行的RedisCommands对象
        RedisCommands<String, String> commands = connection.sync();
        // 执行Redis命令
        String value = commands.get(key);
        // 关闭连接
        connection.close();
        redisClient.shutdown();
        return value;
    }

    public static void remove(String key) {
        // 创建RedisClient实例并连接到本地Redis服务器（默认端口6379）
        RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        // 获取同步执行的RedisCommands对象
        RedisCommands<String, String> commands = connection.sync();
        // 执行Redis命令
        commands.del(key);
        // 关闭连接
        connection.close();
        redisClient.shutdown();
    }
    public static void main(String[] args) {

    }
}