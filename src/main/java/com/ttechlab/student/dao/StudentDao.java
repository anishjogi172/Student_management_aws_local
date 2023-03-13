package com.ttechlab.student.dao;

import java.util.List;

import com.ttechlab.student.entity.Student;
import com.ttechlab.student.exception.BusinessException;

public interface StudentDao {
    List<Student> getAllStudents() throws BusinessException;
    Student saveStudent(Student student) throws BusinessException;
}

