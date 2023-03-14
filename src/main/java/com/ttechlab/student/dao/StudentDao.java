package com.ttechlab.student.dao;

import java.util.List;

import com.ttechlab.student.entity.Student;
import com.ttechlab.student.exception.BusinessException;
import com.ttechlab.student.exception.DataException;

public interface StudentDao {
    List<Student> getAllStudents() throws DataException;
    Student saveStudent(Student student) throws DataException;
	String getSupportedType();
}

