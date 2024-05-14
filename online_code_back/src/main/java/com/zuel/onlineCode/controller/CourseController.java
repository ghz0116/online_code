package com.zuel.onlineCode.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zuel.onlineCode.entity.Course;
import com.zuel.onlineCode.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;


    @RequestMapping("/add")
    private ResponseEntity<?> addCourse(@RequestBody Course course) {
        boolean add = courseService.addCourse(course);
        if (add) {
            return new ResponseEntity<>("课程创建成功", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("课程创建成功，封面保存失败", HttpStatus.OK);
        }
    }

    @RequestMapping("/teach")
    private ResponseEntity<?> teach(@AuthenticationPrincipal UserDetails userDetails) {
        List<Course> courses = courseService.getTeachCourse(userDetails.getUsername());
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @RequestMapping("/study")
    private ResponseEntity<?> study(@AuthenticationPrincipal UserDetails userDetails) {
        List<Course> courses = courseService.getStudyCourse(userDetails.getUsername());
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<?> getList(@AuthenticationPrincipal UserDetails userDetails, String search, int page) {
        String username = null;
        if (userDetails != null) {
            username = userDetails.getUsername();
        }
        Page<Course> courses = courseService.getCourseList(search, page, username);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping(value = "/covers/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        byte[] imageBytes = courseService.getCourseCover(filename);
        if (imageBytes != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinCourse(@AuthenticationPrincipal UserDetails userDetails, Integer courseId) {
        boolean join = courseService.joinCourse(courseId, userDetails.getUsername());
        if (join) {
            return new ResponseEntity<>("加入成功", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("加入失败", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/fetchDetail")
    public ResponseEntity<?> fetchDetail(@AuthenticationPrincipal UserDetails userDetails, Integer courseId) {
        String username = null;
        if (userDetails != null) {
            username = userDetails.getUsername();
        }
        Course course = courseService.fetchDetail(courseId, username);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping("/top")
    public ResponseEntity<?> top() {
        List<Course> courses = courseService.top();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping("/chart")
    public ResponseEntity<?> getChart(Integer courseId) {
        HashMap<String, List> data = courseService.getChartData(courseId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping("/score")
    public void exportScore(HttpServletResponse response, Integer courseId) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = courseService.exportScore(courseId);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=\"report.xlsx\"");
        byteArrayOutputStream.writeTo(response.getOutputStream());
        byteArrayOutputStream.close();
    }
}
