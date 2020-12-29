package com.alian;


import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alian.entity.StudentModel;
import com.alian.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class MongoDemoApplicationTests {

    @Resource
    private StudentRepository studentRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void insert(){
        StudentModel student = new StudentModel();
        student.setName("张无忌");
        student.setPhone("66666666");
        student.setBirthday(DateUtil.parse("2016-05-01","yyyy-MM-dd"));
        student.setMoney(BigDecimal.valueOf(80000000));
        studentRepository.insert(student);
    }

    @Test
    public void updateTest(){
        Optional<StudentModel> byId = studentRepository.findById("5feabe6a74f1f6144ffcd815");
        StudentModel studentModel = byId.get();
        studentModel.setName("张无忌");
        studentModel.setPhone("6666666666666666");
        studentRepository.save(studentModel);
    }

    @Test
    public void removeTest(){
        studentRepository.deleteById("5feabe6a74f1f6144ffcd815");
    }

    @Test
    public void findTest(){
        List<StudentModel> all = studentRepository.findAll();
        System.out.println(JSONUtil.parse(all));
    }

    @Test
    public void findSort(){
        studentRepository.findAll();
    }

}
