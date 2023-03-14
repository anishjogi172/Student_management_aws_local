package com.ttechlab.student.dao;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class DaoFactory {
	
	@Value("${storage.type}")
	private String storageType;
	
	
	
	
	@Autowired
	private Set<StudentDao> studentDaoSet;
	

	public StudentDao getStudentDao() {
		
		Optional<StudentDao>  optionalDao = studentDaoSet.stream().filter(studentDao -> studentDao.getSupportedType().equalsIgnoreCase(storageType))
				   .findFirst();
		
		return optionalDao.isPresent() ? optionalDao.get(): null;
	}

}
