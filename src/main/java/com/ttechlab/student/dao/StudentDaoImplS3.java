package com.ttechlab.student.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.ttechlab.student.entity.Student;
import com.ttechlab.student.exception.BusinessException;

@Repository
public class StudentDaoImplS3 implements StudentDao {

	private final AmazonS3 s3Client;

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Value("${aws.s3.fileName}")
	private String fileName;

	public StudentDaoImplS3(AmazonS3 s3Client) {
		this.s3Client = s3Client;
	}

	@Override
	public List<Student> getAllStudents() throws BusinessException {
		S3Object s3Object = s3Client.getObject(bucketName, fileName);
		InputStream inputStream = s3Object.getObjectContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		List<Student> students = new ArrayList<>();
		String line = null;
		try {
			line = reader.readLine();
			while (line != null) {
				String[] data = line.split(",");
				Long id = Long.parseLong(data[0].replaceAll("\"", ""));
				String name = data[1].replaceAll("\"", "");
				Student student = new Student(id, name);
				students.add(student);

				line = reader.readLine();
			}
		} catch (IOException e) {
			throw new BusinessException("809", "Failed to read Csv file");
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new BusinessException("810", "Failed to close reader");
			}
		}

		return students;
	}

	@Override
	public Student saveStudent(Student student) throws BusinessException {
		S3Object s3Object = s3Client.getObject(bucketName, fileName);
		if (!s3Client.doesBucketExistV2(bucketName)) {
			throw new BusinessException("811", "Failed to found" + bucketName);
		}
		if (!s3Client.doesObjectExist(bucketName, fileName)) {
			throw new BusinessException("812", "Failed to found" + fileName);
		}
		InputStream inputStream = s3Object.getObjectContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		List<String> lines = new ArrayList<>();
		String line = null;
		try {
			line = reader.readLine();

			while (line != null) {
				lines.add(line);

				line = reader.readLine();

			}

		} catch (IOException e) {
			throw new BusinessException("813", "Failed to read Csv file");

		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new BusinessException("814", "Failed to close BufferedReader");
			}
		}
		Long nextId = (long) (lines.size() + 1);
		String newLine = nextId + "," + student.getName();
		lines.add(newLine);
		s3Client.putObject(bucketName, fileName, String.join("\n", lines));
		return student;
	}
}
