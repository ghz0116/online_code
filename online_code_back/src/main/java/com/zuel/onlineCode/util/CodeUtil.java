package com.zuel.onlineCode.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zuel.onlineCode.dto.CodeCompileResult;
import com.zuel.onlineCode.dto.CodeJavaDetail;
import com.zuel.onlineCode.dto.CodeRunResult;
import com.zuel.onlineCode.entity.QuestionTestCase;
import okhttp3.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
public class CodeUtil {

    public static boolean complementJs(String parameters, String testCase, String code, String codeDir) {
        JSONArray parameterList = JSON.parseArray(parameters);
        JSONObject jsonObject = JSON.parseObject(testCase);
        String trim = code.replace(" ", "");
        String[] split = trim.split("=function");
        String left = split[0];
        String[] split1 = left.split("var");
        String functionName = split1[1];
        StringBuilder builder = new StringBuilder("console.log(JSON.stringify(");
        builder.append(functionName).append("(");
        for (int i = 0; i < parameterList.size(); i++) {
            String p = (String) parameterList.get(i);
            builder.append(jsonObject.get(p));
            if (i == parameterList.size() - 1) {
                builder.append(")))");
            } else {
                builder.append(" ,");
            }
        }
        code = code + "\n" + new String(builder);
        try {
            File file = new File(codeDir + "Solution.js");
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    return false;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(code);
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static CodeRunResult runJs(String dirPath, String caseName) {
        StringBuilder resultBuilder = new StringBuilder();
        StringBuilder errorInfoBuilder = new StringBuilder();
        String parameter = dirPath + "Solution.js";
        try {
            // 创建一个ProcessBuilder对象
            ProcessBuilder pb = new ProcessBuilder("node", parameter);
            // 启动进程
            // 记录开始时间
            long startTime = System.nanoTime();
            Process process = pb.start();
            // 读取进程的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                resultBuilder.append(line);
            }
            // 读取进程的错误输出
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                errorInfoBuilder.append(line); // 打印错误信息到标准错误流
            }
            // 等待进程结束
            int exitCode = process.waitFor();
            // 记录结束时间
            long endTime = System.nanoTime();
            // 计算运行时间（以毫秒为单位）
            long durationInMillis = (endTime - startTime) / 1000000;
            return new CodeRunResult(exitCode, caseName, new String(resultBuilder), new String(errorInfoBuilder), durationInMillis, false);
        } catch (IOException | InterruptedException e) {
            return new CodeRunResult(500, caseName, null, e.getMessage(), 0, false);
        }
    }

    public static boolean complementJavaMethodDetail(String code, String codeDir) {
        // Find the last occurrence of "}" to extract the main method
        int lastBraceIndex = code.lastIndexOf("}");
        String mainMethodContent = code.substring(0, lastBraceIndex);

        // Build the code to add main method and get method details
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("import java.lang.reflect.Method;\n")
                .append("import java.lang.reflect.Parameter;\n")
                .append("import java.util.*;\n")
                .append(mainMethodContent)
                .append("    public static void main(String[] args) {\n")
                .append("        Solution solution = new Solution();\n")
                .append("        Method[] declaredMethods = solution.getClass().getDeclaredMethods();\n")
                .append("        Method function = null;\n")
                .append("        for (Method declaredMethod : declaredMethods) {\n")
                .append("            String name = declaredMethod.getName();\n")
                .append("            if (!name.equals(\"main\")) {\n")
                .append("                function = declaredMethod;\n")
                .append("                break;\n")
                .append("            }\n")
                .append("        }\n")
                .append("        StringBuilder builder = new StringBuilder();\n")
                .append("        builder.append(function.getName()).append(\";\");\n")
                .append("        builder.append(function.getReturnType().getTypeName()).append(\";\");\n")
                .append("        for (Parameter parameter : function.getParameters()) {\n")
                .append("            String name = parameter.getName();\n")
                .append("            String typeName = parameter.getType().getTypeName();\n")
                .append("            builder.append(name).append(\"-\").append(typeName).append(\"+\");\n")
                .append("        }\n")
                .append("        String s = builder.toString();\n")
                .append("        String substring = s.substring(0, s.length() - 1);\n")
                .append("        System.out.println(substring);\n")
                .append("    }\n")
                .append("}");
        return saveCode(codeDir, codeBuilder);
    }

    public static CodeCompileResult compileJavaFile(String javaFilePath) {
        try {
            // 构建编译命令
            String compileCommand = "javac -encoding UTF-8 " + javaFilePath + "Solution.java";
            // 执行编译命令
            Process process = Runtime.getRuntime().exec(compileCommand);

            // 读取错误流
            try (InputStream errorStream = process.getErrorStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
                StringBuilder errorMessageBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorMessageBuilder.append(line).append("\n");
                }
                String errorMessage = errorMessageBuilder.toString();

                // 等待编译完成
                int exitCode = process.waitFor();

                CodeCompileResult codeCompileResult = new CodeCompileResult();
                codeCompileResult.setCode(exitCode);
                if (exitCode != 0) {
                    String cleanedErrorMessage = errorMessage.replaceAll("�", ""); // 移除非法字符
                    codeCompileResult.setInfo("编译失败：" + cleanedErrorMessage);
                }
                return codeCompileResult;
            }
        } catch (IOException | InterruptedException e) {
            return new CodeCompileResult(500, e.getMessage());
        }
    }

    public static CodeRunResult runJavaFile(String javaFilePath) {
        try {
            // 构建编译命令
            String compileCommand = "java -cp \"" + javaFilePath + "\" Solution";
            long startTime = System.nanoTime();
            // 执行编译命令
            Process process = Runtime.getRuntime().exec(compileCommand);

            // 读取进程的输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            // 读取进程的错误输出流
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorOutput.append(errorLine).append("\n");
            }
            // 等待进程执行结束
            int exitValue = process.waitFor();
            long endTime = System.nanoTime();
            // 计算运行时间（以毫秒为单位）
            long durationInMillis = (endTime - startTime) / 1000000;
            CodeRunResult runResult = new CodeRunResult();
            // 如果有错误信息，打印出来
            if (errorOutput.length() > 0) {
                runResult.setErrorInfo(errorOutput.toString());
            }
            runResult.setDurationInMillis(durationInMillis);
            runResult.setExitCode(exitValue);
            runResult.setRunResult(output.toString());
            // 返回运行结果
            return runResult;
        } catch (IOException | InterruptedException e) {
            return new CodeRunResult(500, "", "", "代码运行出错", 0, false);
        }
    }

    public static boolean complementJava(String parameterStr, CodeJavaDetail codeJavaDetail, QuestionTestCase testCase, String code, String codeDir) {
        List<String> strings = JSON.parseArray(parameterStr, String.class);
        String parameterMap = testCase.getParameterMap();
        HashMap<?, ?> hashMap = JSON.parseObject(parameterMap, HashMap.class);

        StringBuilder fileCodeBuilder = new StringBuilder();
        fileCodeBuilder.append("import java.util.*;\n");
        int lasted = code.lastIndexOf("}");
        String substring = code.substring(0, lasted);
        fileCodeBuilder.append(substring);
        fileCodeBuilder.append("\tpublic static void main(String[] args) {\n");
        List<HashMap<String, String>> parameters = codeJavaDetail.getParameters();
        for (int i = 0; i < parameters.size(); i++) {
            HashMap<String, String> map = parameters.get(i);
            String type = map.get("type");
            String name = strings.get(i);
            String value = hashMap.get(name).toString();
            if (type.contains("[]")) {
                value = value.replace("[", "{").replace("]", "}");
            }
            fileCodeBuilder.append(String.format("\t\t%s %s = %s;\n", type, name, value));
        }

        String params = String.join(" ,", strings);
        String returnType = codeJavaDetail.getReturnType();
        String functionName = codeJavaDetail.getFunctionName();
        String printStatement = returnType.contains("[]") ?
                String.format("\t\tSystem.out.println(Arrays.toString(new Solution().%s(%s)));\n", functionName, params) :
                String.format("\t\tSystem.out.println(new Solution().%s(%s));\n", functionName, params);

        fileCodeBuilder.append(printStatement);
        fileCodeBuilder.append("\t}\n}");
        return saveCode(codeDir, fileCodeBuilder);
    }

    public static boolean complementPython(String parameters, String testCase, String code, String codeDir) {
        JSONArray parameterList = JSON.parseArray(parameters);
        JSONObject jsonObject = JSON.parseObject(testCase);
        String[] split = code.split("def ");
        String right = split[1];
        String[] split1 = right.split("\\(");
        String functionName = split1[0];
        StringBuilder builder = new StringBuilder("if __name__ == '__main__':\n");
        builder.append("\tprint(")
                .append(functionName)
                .append("(");
        for (int i = 0; i < parameterList.size(); i++) {
            String p = (String) parameterList.get(i);
            builder.append(jsonObject.get(p));
            if (i == parameterList.size() - 1) {
                builder.append("))");
            } else {
                builder.append(" ,");
            }
        }
        code = code + "\n" + new String(builder);
        try {
            File file = new File(codeDir + "Solution.py");
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    return false;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(code);
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static CodeRunResult runPython(String dirPath, String caseName) {
        StringBuilder resultBuilder = new StringBuilder();
        StringBuilder errorInfoBuilder = new StringBuilder();
        String scriptPath = dirPath + "Solution.py"; // 修改脚本路径
        try {
            // 创建一个ProcessBuilder对象
            ProcessBuilder pb = new ProcessBuilder("python", scriptPath); // 修改命令为"python"
            // 启动进程
            // 记录开始时间
            long startTime = System.nanoTime();
            Process process = pb.start();
            // 读取进程的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                resultBuilder.append(line);
            }
            // 读取进程的错误输出
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                errorInfoBuilder.append(line); // 打印错误信息到标准错误流
            }
            // 等待进程结束
            int exitCode = process.waitFor();
            // 记录结束时间
            long endTime = System.nanoTime();
            // 计算运行时间（以毫秒为单位）
            long durationInMillis = (endTime - startTime) / 1000000;
            return new CodeRunResult(exitCode, caseName, resultBuilder.toString(), errorInfoBuilder.toString(), durationInMillis, false);
        } catch (IOException | InterruptedException e) {
            return new CodeRunResult(500, caseName, null, e.getMessage(), 0, false);
        }
    }

    public static boolean saveCode(String codeDir, StringBuilder codeBuilder) {
        try {
            File file = new File(codeDir + "Solution.java");
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    return false;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(new String(codeBuilder));
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean complementCPlusPlus(String parameters, String testCase, String code, String codeDir) {
        JSONArray parameterList = JSON.parseArray(parameters);
        JSONObject jsonObject = JSON.parseObject(testCase);
        String[] split = code.split("\\(");
        String left = split[0];
        String right = split[1];
        String[] split2 = left.split(" ");
        String functionName = split2[split2.length - 1];
        String[] ps = right.split(",");
        StringBuilder builder = new StringBuilder("int main() {\n" +
                "    Solution solution;");
        builder.append(functionName).append("(");
        for (int i = 0; i < parameterList.size(); i++) {
            String p = (String) parameterList.get(i);
            builder.append(jsonObject.get(p));
            if (i == parameterList.size() - 1) {
                builder.append(")))");
            } else {
                builder.append(" ,");
            }
        }
        code = code + "\n" + new String(builder);
        try {
            File file = new File(codeDir + "Solution.js");
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    return false;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(code);
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

