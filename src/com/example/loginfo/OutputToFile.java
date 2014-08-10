package com.example.loginfo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputToFile {
	public void exportToFile(String string){
		try{
			FileWriter fw = new FileWriter("output.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(string);
			bw.write("\r\n");
			bw.close();
		}catch(IOException e){
			
		}	
	}
}
