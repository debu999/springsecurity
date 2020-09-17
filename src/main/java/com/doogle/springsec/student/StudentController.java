package com.doogle.springsec.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Debabrata"),
            new Student(2, "Priyabrata"),
            new Student(3, "Sanjay"),
            new Student(4, "Saroj")
    );

    @GetMapping(path = "/{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return STUDENTS.stream().filter(s -> studentId.equals(s.getStudentId()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                String.format("Student %s does not Exist.", studentId)));

    }
}
