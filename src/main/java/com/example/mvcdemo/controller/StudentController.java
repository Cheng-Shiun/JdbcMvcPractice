package com.example.mvcdemo.controller;

import com.example.mvcdemo.model.Student;
import com.example.mvcdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    //create POST api (新增一筆學生資料)
    @PostMapping("/student")
    public String insert(@RequestBody Student student) {
        studentService.insert(student);
        return "執行 INSERT sql";
    }
    //create POST api (新增一批數據)
    @PostMapping("/students/batch")
    public String insertList(@RequestBody List<Student> studentList) {
        studentService.insertList(studentList);
        return "執行一批 INSERT sql";
    }

    //query GET api (查詢一筆學生資料)
    @GetMapping("/students/{studentId}")
    public String select(@PathVariable Integer studentId) {
        return studentService.getById(studentId);
    }

    //query GET api (查詢所有數據)
    @GetMapping("/students")
    public List<Student> selectList() {
        return studentService.getAllData();
    }

    //query GET api (查詢所有數據數量)
    @GetMapping("/students/count")
    public Integer select() {
        return studentService.count();
    }

    //update POST api (更新一筆學生資料)
    //Map<String, String>: 適用於簡單的情況，當只需要更新少數幾個屬性時
    //POJO (Student): 適用於複雜的情況，當需要更新多個屬性並且希望有更好的結構和可讀性時
    @PutMapping("/students/{studentId}")
    public String update(@PathVariable Integer studentId,
                         @RequestBody Map<String, String> requestBodyName) {
        studentService.update(studentId, requestBodyName);
        return "執行 UPDATE sql";
    }

    //update POST api (更新一批數據)
    @PutMapping("/students/batch")
    public String updateList(@RequestBody List<Student> studentList){
        studentService.updateList(studentList);
        return "執行一批 UPDATE sql";
    }

    //delete DELETE api (刪除一筆學生資料)
    @DeleteMapping("/students/{studentId}")
    public String delete(@PathVariable Integer studentId) {
        studentService.delete(studentId);
        return "執行 DELETE sql";
    }

    //delete DELETE api (刪除一批數據)
    @DeleteMapping("/students/batch")
    public String deleteList(@RequestParam (name = "ids") List<Integer> idList) {
        studentService.deleteList(idList);
        return "執行一批 DELETE sql";
    }
}
