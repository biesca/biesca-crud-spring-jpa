package com.biesca.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class CommonTestUtils {
	
	private static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	
	protected ObjectMapper objectMapper = new ObjectMapper();
	
	protected CommonTestUtils() {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.writerWithDefaultPrettyPrinter();		
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}
	
	@SuppressWarnings("unchecked")
	protected  <T> T parseJsonDto(String filename, Class<T> classOnWhichArrayIsDefined) {

		try {

			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			objectMapper.setDateFormat(df);

			objectMapper.registerModule(new JavaTimeModule());
			
			BufferedReader br;
			Resource resource = new ClassPathResource(filename);
			br = new BufferedReader(new FileReader(resource.getFile()));
			Class<T> arrayClass = (Class<T>) Class.forName(classOnWhichArrayIsDefined.getName());
			T objects = objectMapper.readValue(br, arrayClass);
			return objects;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}
}
