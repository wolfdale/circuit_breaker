package com.tutorial.domain;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentServiceController {
    private static Map<String, List<Student>> schooDB;

    static {
        System.out.println("Testing");
        schooDB = new HashMap<String, List<Student>>();

        List<Student> lst = new ArrayList<Student>();
        Student std = new Student("Alpha", "Class IV");
        lst.add(std);
        std = new Student("Beta", "Class V");
        lst.add(std);

        schooDB.put("abcschool", lst);

        lst = new ArrayList<Student>();
        std = new Student("Gamma", "Class III");
        lst.add(std);
        std = new Student("Delta", "Class VI");
        lst.add(std);

        schooDB.put("xyzschool", lst);

    }

    @RequestMapping(value = "/school/{schoolname}", method = RequestMethod.GET)
    public List<Student> getStudents(@PathVariable String schoolname) {
        System.out.println("Getting Student details for " + schoolname);

        List<Student> studentList = schooDB.get(schoolname);
        if (studentList == null) {
            studentList = new ArrayList<Student>();
            Student std = new Student("Not Found", "N/A");
            studentList.add(std);
        }
        return studentList;
    }
}
