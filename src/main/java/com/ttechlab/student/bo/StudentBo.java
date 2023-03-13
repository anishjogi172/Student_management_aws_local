package com.ttechlab.student.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ttechlab.student.entity.Student;
import com.ttechlab.student.exception.BusinessException;
@Service
public interface StudentBo {
	List<Student> getAllStudents() throws BusinessException;
    Student saveStudent(Student student) throws BusinessException;
}
