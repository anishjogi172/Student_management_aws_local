package com.ttechlab.student.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ttechlab.student.dao.DaoFactory;
import com.ttechlab.student.dao.StudentDao;

import com.ttechlab.student.entity.Student;
import com.ttechlab.student.exception.BusinessException;

@Service
public class StudentBoImpl implements StudentBo {

	@Autowired
	private DaoFactory daoFactory;

	@Override
	public List<Student> getAllStudents() throws BusinessException {

		return daoFactory.getStudent().getAllStudents();
	}

	@Override
	public Student saveStudent(Student student) throws BusinessException {
		return daoFactory.getStudent().saveStudent(student);

	}

}
