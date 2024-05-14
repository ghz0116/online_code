package com.zuel.onlineCode.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuel.onlineCode.dto.CodeCompileResult;
import com.zuel.onlineCode.dto.CodeJavaDetail;
import com.zuel.onlineCode.dto.CodeRunResult;
import com.zuel.onlineCode.entity.Question;
import com.zuel.onlineCode.entity.QuestionAnswer;
import com.zuel.onlineCode.entity.QuestionTestCase;
import com.zuel.onlineCode.mapper.QuestionAnswerMapper;
import com.zuel.onlineCode.service.QuestionAnswerService;
import com.zuel.onlineCode.util.CodeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionAnswerServiceImpl extends ServiceImpl<QuestionAnswerMapper, QuestionAnswer> implements QuestionAnswerService {
    @Value("${userSubmitCodeDir}")
    private String USER_SUBMIT_CODE_DIR;
    @Value("${temporaryCodeDir}")
    private String TEMPORARY_CODE_DIR;


    @Override
    public String saveCodeToLocal(String code) {
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + ".txt";
        try {
            File file = new File(USER_SUBMIT_CODE_DIR + fileName);
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    return null;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(code);
            writer.close();
        } catch (IOException e) {
            return null;
        }
        return fileName;
    }

    @Override
    public ArrayList<CodeRunResult> runCode(Question question, QuestionAnswer answer, List<QuestionTestCase> testCases) {
        String language = answer.getLanguage();
        String code = answer.getCode();
        String parameterStr = question.getParameterStr();
        ArrayList<CodeRunResult> runResults = new ArrayList<>();
        if (language.equals("JavaScript")) {
            for (QuestionTestCase testCase : testCases) {
                runResults.add(runJs(parameterStr, testCase, code));
            }
        }
        if (language.equals("Java")) {
            for (QuestionTestCase testCase : testCases) {
                runResults.add(runJava(parameterStr, testCase, code));
            }
        }
        if (language.equals("Python")) {
            for (QuestionTestCase testCase : testCases) {
                runResults.add(runPython(parameterStr, testCase, code));
            }
        }
        if (language.equals("C++")) {
            for (QuestionTestCase testCase : testCases) {
                runResults.add(runCPlusPlus(parameterStr, testCase, code));
            }
        }
        boolean answerRight = true;
        long averageTime = 0;
        for (CodeRunResult codeRunResult : runResults) {
            boolean right = codeRunResult.isRight();
            if (right) {
                averageTime += codeRunResult.getDurationInMillis();
            } else {
                answerRight = false;
                break;
            }
        }
        if (answerRight) {
            averageTime = averageTime / runResults.size();
            answer.setCostTime(averageTime);
        }
        answer.setCorrect(answerRight);
        save(answer);
        return runResults;
    }

    @Override
    public List<QuestionAnswer> getSubmitRecords(Integer id, int questionId) {
        QueryWrapper<QuestionAnswer> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        wrapper.eq("question_id", questionId);
        return list(wrapper);
    }

    @Override
    public String getAnswerCode(Integer answerId) {
        QuestionAnswer answer = getById(answerId);
        String codeFile = answer.getCodeFile();
        String filePath = USER_SUBMIT_CODE_DIR + codeFile;
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace(); // 处理文件读取异常
        }
        return contentBuilder.toString();
    }

    private CodeRunResult runCPlusPlus(String parameterStr, QuestionTestCase testCase, String code) {
        String caseName = testCase.getName();
        boolean b = CodeUtil.complementCPlusPlus(parameterStr, testCase.getParameterMap(), code, TEMPORARY_CODE_DIR);
        if (!b) {
            return new CodeRunResult(500, caseName, null, "代码参数获取失败", 0, false);
        }
        CodeRunResult codeRunResult = CodeUtil.runJs(TEMPORARY_CODE_DIR, caseName);
        String result = testCase.getResult();
        codeRunResult.setRight(judgeResult(codeRunResult.getRunResult(), result));
        return codeRunResult;
    }

    private CodeRunResult runPython(String parameterStr, QuestionTestCase testCase, String code) {
        String caseName = testCase.getName();
        boolean b = CodeUtil.complementPython(parameterStr, testCase.getParameterMap(), code, TEMPORARY_CODE_DIR);
        if (!b) {
            return new CodeRunResult(500, caseName, null, "代码参数获取失败", 0, false);
        }
        CodeRunResult codeRunResult = CodeUtil.runPython(TEMPORARY_CODE_DIR, caseName);
        String result = testCase.getResult();
        codeRunResult.setRight(judgeResult(codeRunResult.getRunResult(), result));
        return codeRunResult;
    }

    private CodeRunResult runJava(String parameterStr, QuestionTestCase testCase, String code) {
        //补全代码获取代码参数信息
        CodeUtil.complementJavaMethodDetail(code, TEMPORARY_CODE_DIR);
        //第一编译运行获取代码参数信息
        CodeCompileResult codeCompileResult1 = CodeUtil.compileJavaFile(TEMPORARY_CODE_DIR);
        String caseName = testCase.getName();
        if (codeCompileResult1.getCode() != 0) {
            //如果编译失败
            return new CodeRunResult(codeCompileResult1.getCode(), caseName, null, codeCompileResult1.getInfo(), 0, false);
        }
        //运行获取代码参数信息
        CodeRunResult codeDetailResult = CodeUtil.runJavaFile(TEMPORARY_CODE_DIR);
        //将参数信息解析
        CodeJavaDetail codeJavaDetail = parseJavaDetail(codeDetailResult.getRunResult());
        //补全最终可运行的代码
        CodeUtil.complementJava(parameterStr, codeJavaDetail, testCase, code, TEMPORARY_CODE_DIR);
        //编译最终可运行的代码
        CodeCompileResult codeCompileResult = CodeUtil.compileJavaFile(TEMPORARY_CODE_DIR);
        if (codeCompileResult.getCode() != 0) {
            return new CodeRunResult(codeCompileResult.getCode(), caseName, null, codeCompileResult.getInfo(), 0, false);
        }
        //最终代码的运行结果
        CodeRunResult codeRunResult = CodeUtil.runJavaFile(TEMPORARY_CODE_DIR);
        String caseResult = testCase.getResult();
        codeRunResult.setRight(judgeResult(codeRunResult.getRunResult(), caseResult));
        codeRunResult.setCaseName(caseName);
        return codeRunResult;
    }

    private CodeJavaDetail parseJavaDetail(String runResult) {
        runResult = runResult.replace("\n", "");
        String[] details = runResult.split(";");
        String functionName = details[0];
        String returnType = details[1];
        ArrayList<HashMap<String, String>> parameters = new ArrayList<>();
        for (String parameter : details[2].split("\\+")) {
            String[] split1 = parameter.split("-");
            HashMap<String, String> map = new HashMap<>();
            map.put("name", split1[0]);
            map.put("type", split1[1]);
            parameters.add(map);
        }
        return new CodeJavaDetail(functionName, parameters, returnType);
    }

    public CodeRunResult runJs(String parameterStr, QuestionTestCase testCase, String code) {
        String caseName = testCase.getName();
        boolean b = CodeUtil.complementJs(parameterStr, testCase.getParameterMap(), code, TEMPORARY_CODE_DIR);
        if (!b) {
            return new CodeRunResult(500, caseName, null, "代码参数获取失败", 0, false);
        }
        CodeRunResult codeRunResult = CodeUtil.runJs(TEMPORARY_CODE_DIR, caseName);
        String result = testCase.getResult();
        codeRunResult.setRight(judgeResult(codeRunResult.getRunResult(), result));
        return codeRunResult;
    }

    public boolean judgeResult(String runResult, String rightResult) {
        runResult = runResult.replaceAll(" ", "").replace("\n", "");
        rightResult = rightResult.replaceAll(" ", "").replace("\n", "");
        if (runResult.equals(rightResult)) {
            return true;
        }
        try {
            // 尝试将字符串解析为整数
            int int1 = Integer.parseInt(runResult);
            int int2 = Integer.parseInt(rightResult);
            // 如果解析成功，比较整数值
            return int1 == int2;
        } catch (NumberFormatException e1) {
            try {
                // 如果解析为整数失败，尝试将字符串解析为浮点数
                double double1 = Double.parseDouble(runResult);
                double double2 = Double.parseDouble(rightResult);
                // 比较浮点数值
                double epsilon = 0.000001; // 定义一个极小的差值
                return Math.abs(double1 - double2) < epsilon;
            } catch (NumberFormatException e2) {
                // 如果解析为浮点数也失败，说明字符串格式不正确，返回 false
                return false;
            }
        }
    }
}
