package com.ttechlab.student.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ttechlab.student.dao.DaoFactory;
import com.ttechlab.student.dao.StudentDao;

import com.ttechlab.student.entity.Student;
import com.ttechlab.student.exception.BusinessException;
import com.ttechlab.student.exception.DataException;

@Service
public class StudentBoImpl implements StudentBO {

	@Autowired
	private DaoFactory daoFactory;

	@Override
	public List<Student> getAllStudents() throws BusinessException {

		try {
			return daoFactory.getStudentDao().getAllStudents();
		} catch (DataException e) {
			
			throw new BusinessException("817","something went worng in Bo"); 
		}
		
	}

	@Override
	public Student saveStudent(Student student) throws BusinessException {
		try {
			return daoFactory.getStudentDao().saveStudent(student);
		} catch (DataException e) {
			throw new BusinessException("818","something went worng in Bo"); 
			
		}
		

	}

}
