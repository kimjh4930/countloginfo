package com.example.loginfo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoFromFile {
	
	private FileInputStream fstream = null;
	private String line = null;
	
	private String codeState;
	private String serviceID;
	private String brower;
	private String apikey;
	private int sum;
	
	private int numOfCodeState[] = new int[]{0,0,0};
	private int numOfServiceID[] = new int[]{0,0,0,0,0,0};
	private int numOfBrower[]	 = new int[]{0,0,0,0,0};
	private int numOfapikey[];
	
	private HashMap<String, Integer> apiHashmap = new HashMap();
	
	int equals = 0;
	int notequals = 0;
	//private String apiList[]	 = new String[]{};
	private ArrayList<String> apiList = new ArrayList<String>();
	
	private AnalysisInfo analysisInfo = null;

	public String loadFileInfo() throws IOException {

		fstream = null;
		analysisInfo = new AnalysisInfo();
		//한줄씩 내용을 불러옴
		try {
			File inFile = new File("input.log");
			fstream = new FileInputStream(inFile);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			// Do Something with the stream
			
			
			while ((line = br.readLine()) != null && line != "") {
				//System.out.println(line);
				codeState = analysisInfo.analyzeStateCode(line);
				serviceID = analysisInfo.analyzeService(line);
				brower = analysisInfo.analyzeBrower(line);
				apikey = analysisInfo.analyzeApikey(line);			
				
				if(codeState.equals("[10]"))	 {numOfCodeState[0]++;}
				if(codeState.equals("[200]"))	 {numOfCodeState[1]++;}
				if(codeState.equals("[404]"))	 {numOfCodeState[2]++;}
				//"blog","book","image","knowledge","news","vclip"
				if(serviceID.equals("blog"))	 {numOfServiceID[0]++;}
				if(serviceID.equals("book"))	 {numOfServiceID[1]++;}
				if(serviceID.equals("image"))	 {numOfServiceID[2]++;}
				if(serviceID.equals("knowledge")){numOfServiceID[3]++;}
				if(serviceID.equals("news"))	 {numOfServiceID[4]++;}
				if(serviceID.equals("vclip"))	 {numOfServiceID[5]++;}
				//"\\[IE\\]","\\[Firefox\\]","\\[Safari\\]","\\[Chrome\\]","\\[Opera\\]"
				if(brower.equals("[IE]"))		 {numOfBrower[0]++;}
				if(brower.equals("[Firefox]"))	 {numOfBrower[1]++;}
				if(brower.equals("[Safari]")) 	 {numOfBrower[2]++;}
				if(brower.equals("[Chrome]")) 	 {numOfBrower[3]++;}
				if(brower.equals("[Opera]"))	 {numOfBrower[4]++;}
				
				/*//해시맵에 있는경우
				if(apiHashmap.containsKey(apikey)){
					int value = apiHashmap.get(apikey);
					
				}
				//해시맵에 없는경우
				if(!apiHashmap.containsKey(apikey)){
					apiHashmap.put(apikey, 0);
				}
								
				//apikey의 처음값은 무조건 넣고 시작
				//해시맵을 사용하여 숫자를 저장해도될까
				
				/*if(apiList.size() == 0){
					apiList.add(apikey);
					notequals++;
				}else{
					//while(whileIndex!=apiList.size()){
					for(int index=0; index<apiList.size(); index++){
						//System.out.println("apikey = " + apikey);
						if(apikey.equals(apiList.get(index))){
							//배열값이 같을경우, 해당 Hash맵의 value 값을 1 올린다.
							//해당배열의 값을 더한다.
							//numOfapikey[index]++;
							
							equals++;
							//System.out.println("apikey : " + apikey);
							break;
						}
						if(!apikey.equals(apiList.get(index))){
							//HashMap 에 추가한다.
							notequals++;
							//index++;
							//apiList.add(apikey);
						}						
						//System.out.println("size : " + apiList.size());
					}
					
					//System.out.println("equals : " + equals);
					//System.out.println("not equals : " + notequals);
				}*/
				
				//System.out.println("stateLogTypeArray["+index+"] : " + stateLogTypeArray[index]);
			}
			
			System.out.println("10  : "+numOfCodeState[0]);
			System.out.println("200 : "+numOfCodeState[1]);
			System.out.println("404 : "+numOfCodeState[2]);
			System.out.println("");
			System.out.println("blog       : "+numOfServiceID[0]);
			System.out.println("book       : "+numOfServiceID[1]);
			System.out.println("image      : "+numOfServiceID[2]);
			System.out.println("knowledge  : "+numOfServiceID[3]);
			System.out.println("news       : "+numOfServiceID[4]);
			System.out.println("vclip      : "+numOfServiceID[5]);
			System.out.println("");
			
			
			for(int index=0 ; index<5; index++){
				sum += numOfBrower[index];
			}
			System.out.println("IE         : "+(double)numOfBrower[0]/sum*100+"%");
			System.out.println("Firefox    : "+(double)numOfBrower[1]/sum*100+"%");
			System.out.println("Safari     : "+(double)numOfBrower[2]/sum*100+"%");
			System.out.println("Chrome     : "+(double)numOfBrower[3]/sum*100+"%");
			System.out.println("Opera      : "+(double)numOfBrower[4]/sum*100+"%");
			System.out.println("");
			
			
				
		} catch (FileNotFoundException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null,ex);
		}
		String result;
		
		result = "10  : "+numOfCodeState[0] + "\r\n" +
				"200 : "+numOfCodeState[1] + "\r\n" +
				"404 : "+numOfCodeState[2] +"\r\n" +
				 "\r\n" +
				"blog       : "+numOfServiceID[0] + "\r\n" +
				"book       : "+numOfServiceID[1] + "\r\n" +
				"image      : "+numOfServiceID[2] + "\r\n" +
				"knowledge  : "+numOfServiceID[3] + "\r\n" +
				"news       : "+numOfServiceID[4] + "\r\n" +
				"vclip      : "+numOfServiceID[5] + "\r\n" +
				 "\r\n" +
				"IE         : "+(double)numOfBrower[0]/sum*100+"%" + "\r\n" +
				"Firefox    : "+(double)numOfBrower[1]/sum*100+"%" + "\r\n" +
				"Safari     : "+(double)numOfBrower[2]/sum*100+"%" + "\r\n" +
				"Chrome     : "+(double)numOfBrower[3]/sum*100+"%" + "\r\n" +
				"Opera      : "+(double)numOfBrower[4]/sum*100+"%";
		return result;
	}
}
