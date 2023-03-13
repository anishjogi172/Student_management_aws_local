package com.ttechlab.student.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ttechlab.student.entity.Student;

@Component
public class DaoFactory {
	
	@Value("${storage.type}")
	private String storageType;
	
	
	@Autowired
	private StudentDaoImplCsv studentDaoImpl;
	
	@Autowired
	private StudentDaoImplS3 studentDaoImplS3;

	public StudentDao getStudent() {
		switch (storageType) {
		case "csv":
			return studentDaoImpl;
		case "s3":
			return studentDaoImplS3;
		default:
			throw new IllegalArgumentException("Invalid" + storageType);

		}

	}
}
