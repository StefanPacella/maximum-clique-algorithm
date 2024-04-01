package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class FileManager {

	private String fileText;

	public FileManager(String path) {
		// TODO Auto-generated constructor stub
		this.fileText = path;
	}
	
	public LinkedList<String> readTheFile() throws IOException {
		LinkedList<String> righe = new LinkedList<>();
		try (BufferedReader in = new BufferedReader(new FileReader(fileText))) {
			String linea = null;
			while ((linea = in.readLine()) != null) {
				righe.add(linea);
			}
		}
		return righe;
	}

	public void renderError(Exception e) {
		e.printStackTrace();
		System.exit(1);
	}

	
}
