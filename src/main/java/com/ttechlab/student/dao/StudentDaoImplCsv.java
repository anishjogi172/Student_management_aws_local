package com.ttechlab.student.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.ttechlab.student.entity.Student;
import com.ttechlab.student.exception.BusinessException;
import com.ttechlab.student.exception.DataException;

@Repository
public class StudentDaoImplCsv implements StudentDao {

	@Value("${csv.file.path}")
	private String csvFilePath;

	@Override
	public List<Student> getAllStudents() throws DataException {
		List<Student> students = new ArrayList<>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csvFilePath));
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				Long id = Long.parseLong(data[0]);
				String name = data[1];
				Student student = new Student(id, name);
				students.add(student);
			}

		} catch (FileNotFoundException e) {

			throw new DataException("803", "File not found" + e.getMessage());
		}

		catch (Exception e) {

			throw new DataException("801", "Something went wrong in Dao" + e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				throw new DataException("802", "Failed to close BufferedReader");
			}
		}
		return students;
	}

	@Override
	public Student saveStudent(Student student) throws DataException {
		File file = new File(csvFilePath);
		if (!file.exists()) {
			throw new DataException("804", "File not found");
		}
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = null;
		try {

			fr = new FileReader(file);
			br = new BufferedReader(fr);

			List<String> lines = new ArrayList<>();
			String line = br.readLine();
			while (line != null) {
				lines.add(line);
				line = br.readLine();
			}

			Long lastStudentId = 0L;
			if (!lines.isEmpty()) {
				String[] lastLineData = lines.get(lines.size() - 1).split(",");
				lastStudentId = Long.parseLong(lastLineData[0]);
			}

			Long newStudentId = lastStudentId + 1;
			student.setId(newStudentId);

			fw = new FileWriter(file, true);
			fw.write(student.getId() + "," + student.getName() + "\n");

		} catch (Exception e) {
			throw new DataException("805", "Something went wrong in Dao" + e.getMessage());
		} finally {

			try {
				br.close();
			} catch (IOException e1) {
				throw new DataException("806", "Failed to close BufferedReader");
			}
			try {
				fr.close();
			} catch (IOException e) {
				throw new DataException("807", "Failed to close FileReader");
			}
			try {
				fw.close();
			} catch (IOException e) {
				throw new DataException("808", "Failed to close FileWriter");
			}

		}
		return student;
	}

	@Override
	public String getSupportedType() {
		return "csv";
	}

}
