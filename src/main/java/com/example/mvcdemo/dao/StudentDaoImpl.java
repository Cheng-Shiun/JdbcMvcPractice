package com.example.mvcdemo.dao;

import com.example.mvcdemo.mapper.StudentRowMapper;
import com.example.mvcdemo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentDaoImpl implements StudentDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insert(Student student) {
        String sql = "INSERT INTO student(name) VALUE (:studentName)";

        Map<String, Object> map = new HashMap<>();
        map.put("studentName", student.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();  //KeyHolder interface implements GeneratedKeyHolder接住資料庫自動生成的Key -> 存放在keyHolder變數中

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);  //執行.update()更新資料庫資料，取得sql要更新key的值，並用keyHolder對應起來
    }

    @Override
    public void insertList(List<Student> studentList) {
        String sql = "INSERT INTO student(name) VALUE (:studentName)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[studentList.size()];

        for (int i = 0 ; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("studentName", student.getName());
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    @Override
    public String getById(Integer studentId) {
        String sql = "SELECT name FROM student WHERE id = :studentId";

        Map<String, Object> map = new HashMap<>();
        map.put("studentId", studentId);

        //資料庫查無資料會返回空值給spring boot 噴出EmptyResultDataAccessException
        try {
            String name = namedParameterJdbcTemplate.queryForObject(sql, map, String.class);
            return (name != null && !name.isBlank()) ? name : "查無此學生資料";
        }catch (EmptyResultDataAccessException e) {
            return "查無此學生資料";
        }
    }

    @Override
    public List<Student> getAllData() {
        String sql = "SELECT id, name FROM student";

        List<Student> list = namedParameterJdbcTemplate.query(sql, new HashMap<>(), new StudentRowMapper());
        return list;
    }

    @Override
    public Integer count() {
        String sql = "SELECT count(*) FROM student";
        return namedParameterJdbcTemplate.queryForObject(sql, new HashMap<>(), Integer.class);
    }

    @Override
    public void update(Integer studentId,
                         Map<String, String> requestBodyName) {
        String sql = "UPDATE student SET name = :studentName WHERE id = :studentId";

        Map<String, Object> map = new HashMap<>();
        map.put("studentId", studentId);
        map.put("studentName", requestBodyName.get("name"));  //取得request body中的"name"對應的值，並放到map
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void updateList(List<Student> studentList) {
        String sql = "UPDATE student SET name = :studentName WHERE id = :studentId";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[studentList.size()];

        for(int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);  //取得第i個參數集合
            parameterSources[i] = new MapSqlParameterSource();   //該筆資料初始化
            parameterSources[i].addValue("studentId", student.getId());
            parameterSources[i].addValue("studentName", student.getName());
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);  //執行一批數據
    }

    @Override
    public void delete(Integer studentId) {
        String sql = "DELETE FROM student WHERE id = :studentId";
        Map<String, Object> map = new HashMap<>();
        map.put("studentId", studentId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteList(List<Integer> idList) {
        String sql = "DELETE FROM student WHERE id = :studentId";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[idList.size()];

        for (int i = 0; i < idList.size(); i++) {
            Integer id = idList.get(i);     //取得陣列中第i個值，並放到id變數中
            parameterSources[i] = new MapSqlParameterSource();   //初始化parameterSources參數
            parameterSources[i].addValue("studentId", id);  //將id值新增到parameterSources參數中對應的studentId值
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }
}
