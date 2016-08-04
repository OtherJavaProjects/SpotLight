package com.rahul.spotlight;

public class Files {

	private String name;
	private String path;
	private String ext;

	public Files(String fileName, String filePath, String extension) {
		name = fileName;
		path = filePath;
		ext = extension;
	}

	public String getFileName() {
		return name;
	}

	public String getFilePath() {
		return path;
	}

	public String getExtension() {
		return ext;
	}
}
