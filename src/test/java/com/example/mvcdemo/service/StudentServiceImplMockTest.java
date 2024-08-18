package com.example.mvcdemo.service;

import com.example.mvcdemo.dao.StudentDao;
import com.example.mvcdemo.model.Student;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentServiceImplMockTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentDao studentDao;

    //每個 test case 在運行前創建 Mock，並執行 getById 方法
    @BeforeEach
    public void before() {
        Student mockStudent = new Student();
        mockStudent.setId(100);
        mockStudent.setName("Mock");
        Mockito.when(studentDao.getById(Mockito.any())).thenReturn(mockStudent.getName());
    }

    //新增
    @Test
    public void insert() {
        //模擬新增學生的id為1
        Mockito.when(studentDao.insert(Mockito.any())).thenReturn(1);

        //設定新增學生的數據
        Student newStudent = new Student();
        newStudent.setName("I'm new");

        //執行insert方法 並驗證id值
        Integer newStudentId = studentService.insert(newStudent);
        assertEquals(1, newStudentId);

        //重新模擬新增學生的數據
        Mockito.when(studentDao.getById(1)).thenReturn(newStudent.getName());
        String newStudentName = studentService.getById(1);
        assertEquals("I'm new", newStudentName);
    }

    //查詢
    @Test
    public void getById() {
        String name = studentService.getById(100);
        assertEquals("Mock", name);
    }

    //修改
    @Test
    public void update() {
        //建立要更新的name
        Map<String, String> updateName = new HashMap<>();
        updateName.put("name", "Mock2");

        //模擬update方法
        Mockito.doNothing().when(studentDao).update(100, updateName);

        //更新id=100的name
        studentService.update(100, updateName);

        //重新模擬 getById 方法
        Mockito.when(studentDao.getById(100)).thenReturn("Mock2");

        //查詢更改的name
        String name = studentService.getById(100);
        assertEquals("Mock2", name);

        //驗證 update 方法是否被調用
        Mockito.verify(studentDao).update(100, updateName);
    }

    //刪除
    @Test
    public void delete() {
        //模擬delete方法
        Mockito.doNothing().when(studentDao).delete(4);

        studentService.delete(4);

        //重新模擬查詢id=4的數據
        Mockito.when(studentDao.getById(4)).thenReturn("查無該筆數據");

        String nullName = studentService.getById(4);
        assertEquals("查無該筆數據", nullName);

        //驗證 delete 方法是否被調用
        Mockito.verify(studentDao).delete(4);
    }
}