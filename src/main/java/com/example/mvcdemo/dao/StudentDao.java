package com.example.mvcdemo.dao;

import com.example.mvcdemo.model.Student;

import java.util.List;
import java.util.Map;


public interface StudentDao {
    Integer insert(Student student);
    void insertList(List<Student> studentList);
    String getById(Integer studentId);

    List<Student> getAllData();

    Integer count();

    void update(Integer studentId, Map<String, String> requestBodyName);

    void updateList(List<Student> studentList);

    void delete(Integer studentId);

    void deleteList(List<Integer> idList);
}
