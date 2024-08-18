package com.example.mvcdemo.dao;

import com.example.mvcdemo.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentDaoImplTest {

    @Autowired
    private StudentDao studentDao;

    //新增 api
    @Test
    @Transactional
    public void insert() {
        Student student = new Student();
        student.setName("Grace");

        Integer id = studentDao.insert(student);
        //檢查資料庫中是否有該數據(name)
        String newStudentName = studentDao.getById(id);

        assertNotNull(newStudentName);
        assertEquals("Grace", newStudentName);
    }

    //查詢 api
    @Test
    public void getById() {
        String name = studentDao.getById(4);
        assertEquals("Jerry", name);
        String nullName = studentDao.getById(1);
        assertEquals("查無此學生資料", nullName);
    }

    //更新 api
    @Test
    @Transactional
    public void update() {
        //先查詢資料庫是否有該筆數據
        String name = studentDao.getById(4);
        assertEquals("Jerry", name);

        //宣告要更新的name值
        Map<String, String> newName = new HashMap<>();
        newName.put("name", "I'm test");
        //更新id為4的name值
        studentDao.update(4, newName);

        //再次取得資料庫中id=4的name值
        String updatedName = studentDao.getById(4);
        assertEquals("I'm test", updatedName);
    }

    //刪除 api
    @Test
    @Transactional
    public void delete() {
        studentDao.delete(4);
        String NotExistingName = studentDao.getById(4);
        assertEquals("查無此學生資料", NotExistingName);
    }
}