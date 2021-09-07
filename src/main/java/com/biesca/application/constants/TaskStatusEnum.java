package com.biesca.application.constants;

public enum TaskStatusEnum {

	NEW("NEW"),
	DONE("DONE"),
	STOPPED("STOPPED"),
	REMOVED("REMOVED"),
	DOING("DOING");
	
	private String type;

	private TaskStatusEnum(String tipo) {
		this.type = tipo;
	}

	public String getType() {
		return this.type;
	}
	
	

}
