package com.example.mvcdemo.service;

import com.example.mvcdemo.model.Student;
import com.example.mvcdemo.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentDao studentDao;

    @Override
    public void insert(Student student) {
        studentDao.insert(student);
    }

    @Override
    public void insertList(List<Student> studentList) {
        studentDao.insertList(studentList);
    }

    @Override
    public String getById(Integer studentId) {
        return studentDao.getById(studentId);
    }

    @Override
    public List<Student> getAllData() {
        return studentDao.getAllData();
    }

    @Override
    public Integer count() {
        return studentDao.count();
    }
    @Override
    public void update(Integer studentId, Map<String, String> requestBodyName) {
        studentDao.update(studentId, requestBodyName);
    }
    @Override
    public void updateList(List<Student> studentList) {
        studentDao.updateList(studentList);
    }
    @Override
    public void delete(Integer studentId) {
        studentDao.delete(studentId);
    }
    @Override
    public void deleteList(List<Integer> idList) {
        studentDao.deleteList(idList);
    }

}
