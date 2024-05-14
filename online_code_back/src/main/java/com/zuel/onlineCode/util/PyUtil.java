package com.zuel.onlineCode.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PyUtil {
    public static String start(String workDir, String scriptName, List<String> parameters) {
        // 使用包装类
        class ResultWrapper {
            String value;
        }
        ResultWrapper resultWrapper = new ResultWrapper();
        try {
            // 创建ProcessBuilder对象
            ProcessBuilder processBuilder = new ProcessBuilder("python", workDir + "/" + scriptName);
            // 添加命令行参数
            for (String parameter : parameters) {
                processBuilder.command().add(parameter);  // 添加参数名
            }
            processBuilder.directory(new File(workDir));
            // 启动进程
            Process process = processBuilder.start();
            // 异步读取进程输出
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String contentLine;
                    String lastLine = null;
                    while ((contentLine = reader.readLine()) != null) {
                        lastLine = contentLine;
                    }
                    return lastLine;
                } catch (IOException e) {
                    return null;
                }
            });

            future.thenAccept(result -> {
                resultWrapper.value = result;
            });
            // Ensure the main thread waits for the CompletableFuture to complete
            future.join();


            // 异步读取进程错误
            CompletableFuture.runAsync(() -> {
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        System.err.println(errorLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // 等待进程执行结束
            int exitCode = process.waitFor();
            return resultWrapper.value;
        } catch (IOException | InterruptedException e) {
            System.out.println("python脚本执行异常！！");
            return null;
        }
    }
}
