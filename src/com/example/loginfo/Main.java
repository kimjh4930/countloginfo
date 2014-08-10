package com.example.loginfo;

import java.io.IOException;

public class Main {
	public static void main(String args[]){
		InfoFromFile infoFromFile = new InfoFromFile();
		OutputToFile outputToFile = new OutputToFile();
		
		try {
			outputToFile.exportToFile(infoFromFile.loadFileInfo());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
