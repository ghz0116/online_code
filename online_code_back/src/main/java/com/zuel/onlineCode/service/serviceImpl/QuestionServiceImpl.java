package com.zuel.onlineCode.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuel.onlineCode.dto.Pagination;
import com.zuel.onlineCode.dto.QuestionPage;
import com.zuel.onlineCode.dto.QuestionQuery;
import com.zuel.onlineCode.entity.*;
import com.zuel.onlineCode.mapper.*;
import com.zuel.onlineCode.service.QuestionFavoriteService;
import com.zuel.onlineCode.service.QuestionService;
import com.zuel.onlineCode.util.PyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Value("${questionImageDir}")
    private String QUESTION_IMAGE_DIR;
    @Value("${pyScriptDir}")
    private String PY_SCRIPT_DIR;

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionPreCodeMapper preCodeMapper;
    @Autowired
    private QuestionTestCaseMapper testCaseMapper;
    @Autowired
    private QuestionAnswerMapper answerMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionFavoriteMapper favoriteMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Page<Question> getPage(QuestionPage questionPage, Integer userId) {
        QuestionQuery questionQuery = questionPage.getQuestionQuery();
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        String searchContent = questionQuery.getSearchContent();
        wrapper.like("question_name", searchContent);
        Pagination pagination = questionPage.getPagination();
        Integer currentPage = pagination.getCurrentPage();
        Integer pageSize = pagination.getPageSize();
        Page<Question> page = new Page<>(currentPage, pageSize);
        Page<Question> selectPage = questionMapper.selectPage(page, wrapper);
        for (Question record : selectPage.getRecords()) {
            record.setSolutionNum(answerMapper.getSolutionNum(record.getQuestionId()));
            Double questionRate = answerMapper.getQuestionRate(record.getQuestionId());
            record.setPassRate(questionRate);
            if (questionRate != null) {
                double floor = Math.floor(questionRate * 100);
                double v = floor / 20;
                record.setDegree((int) Math.ceil(5 - v));
            }
            updateById(record);
            if (userId != 0) {
                setFavorite(record, userId);
            }
        }
        return selectPage;
    }

    private void setFavorite(Question record, Integer userId) {
        QueryWrapper<QuestionFavorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id", record.getQuestionId()).eq("user_id", userId);
        QuestionFavorite favorite = favoriteMapper.selectOne(queryWrapper);
        record.setFavorite(favorite != null);

    }


    @Override
    public void addQuestion(Question question) {
        ArrayList<String> tags = question.getTags();
        for (String tag : tags) {
            QueryWrapper<Tags> wrapper = new QueryWrapper<>();
            wrapper.eq("name", tag);
            if (tagMapper.selectOne(wrapper) == null) {
                Tags tagDto = new Tags(0, tag);
                tagMapper.insert(tagDto);
            }
        }
        ArrayList<String> parameters = question.getParameters();
        question.setParameterStr(JSON.toJSONString(parameters));
        question.setTagsJson(JSON.toJSONString(tags));
        Integer questionId = question.getQuestionId();
        if (questionId != 0) {
            updateById(question);
            QueryWrapper<QuestionPreCode> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("question_id", questionId);
            preCodeMapper.delete(wrapper1);
            QueryWrapper<QuestionTestCase> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("question_id", questionId);
            testCaseMapper.delete(wrapper2);
        } else {
            save(question);
            questionId = question.getQuestionId();
        }
        ArrayList<QuestionPreCode> preCodes = question.getPreCodes();
        for (QuestionPreCode preCode : preCodes) {
            preCode.setQuestionId(questionId);
            preCodeMapper.insert(preCode);
        }
        ArrayList<QuestionTestCase> testCases = question.getTestCases();
        for (QuestionTestCase testCase : testCases) {
            testCase.setQuestionId(questionId);
            HashMap<String, String> parameter = testCase.getParameter();
            testCase.setParameterMap(JSON.toJSONString(parameter));
            testCaseMapper.insert(testCase);
        }
    }

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                return null;
            }
            String originalFilename = file.getOriginalFilename();
            String[] split = new String[0];
            if (originalFilename != null) {
                split = originalFilename.split("\\.");
            }
            String fileType = split[split.length - 1];
            // 获取文件名
            String fileName = UUID.randomUUID() + "." + fileType;
            String filePath = Paths.get(QUESTION_IMAGE_DIR, fileName).toString();
            // 将文件保存到服务器
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            // 处理异常，例如文件保存失败等
            return null;
        }
    }

    @Override
    public ResponseEntity<byte[]> getImage(String filename) {
        try {
            // 这里我们假设图片都放在resources/static/images目录下
            Path imagePath = Paths.get(QUESTION_IMAGE_DIR, filename);
            File imageFile = imagePath.toFile();
            // 检查文件是否存在且可读
            if (imageFile.exists() && imageFile.isFile() && imageFile.canRead()) {
                // 读取文件内容到字节数组
                byte[] imageBytes = Files.readAllBytes(imagePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageBytes);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ArrayList<String> fetchPreCodeTypes(int questionId) {
        // 使用 LambdaQueryWrapper 构造查询条件
        LambdaQueryWrapper<QuestionPreCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QuestionPreCode::getQuestionId, questionId).select(QuestionPreCode::getType);
        // 执行查询并获取结果
        List<Object> types = preCodeMapper.selectObjs(queryWrapper);
        // 将 List<Object> 转换为 ArrayList<String>
        ArrayList<String> typeList = new ArrayList<>();
        for (Object obj : types) {
            typeList.add((String) obj);
        }
        return typeList;
    }

    @Override
    public List<QuestionTestCase> fetchTestCases(int questionId) {
        QueryWrapper<QuestionTestCase> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionId);
        return testCaseMapper.selectList(wrapper);
    }

    @Override
    public QuestionPreCode fetchPreCode(int questionId, String type) {
        QueryWrapper<QuestionPreCode> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionId);
        wrapper.eq("type", type);
        return preCodeMapper.selectOne(wrapper);
    }

    @Override
    public List<Question> getQuestionsByCourseId(Integer courseId) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        return list(wrapper);
    }

    @Override
    public String[] predictTags(String questionName) {
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(questionName);
        String result = PyUtil.start(PY_SCRIPT_DIR, "model_predict.py", parameters);
        String tagsStr = JSON.parseObject(result, String.class);
        if (tagsStr != null) {
            return tagsStr.split("-");
        } else {
            return null;
        }
    }

    @Override
    public void deleteQuestion(int questionId) {
        removeById(questionId);
        QueryWrapper<QuestionPreCode> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("question_id", questionId);
        preCodeMapper.delete(wrapper1);
        QueryWrapper<QuestionTestCase> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("question_id", questionId);
        testCaseMapper.delete(wrapper2);
    }
}
