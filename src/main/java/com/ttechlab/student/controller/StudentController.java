
package com.ttechlab.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttechlab.student.bo.StudentBO;
import com.ttechlab.student.entity.Student;
import com.ttechlab.student.exception.BusinessException;
import com.ttechlab.student.exception.ControllerException;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {
	
	@Value("${storage.type}")
	private String storageType;
	
	@Autowired
	private StudentBO studentBO;

	@GetMapping
	public ResponseEntity<?> getAllStudents() {

		try {
			List<Student> student = studentBO.getAllStudents();
			return new ResponseEntity<List<Student>>(student, HttpStatus.OK);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getCode(), e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (Exception e) {
			ControllerException ce = new ControllerException("816", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping
	public ResponseEntity<?> createStudent(@RequestBody Student student) {
		try {
			Student studentDetail = studentBO.saveStudent(student);
			return new ResponseEntity<Student>(studentDetail, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getCode(), e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("815", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
