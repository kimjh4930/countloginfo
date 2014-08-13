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
import java.util.Iterator;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoFromFile {
	
	private FileInputStream fstream = null;
	private String line = null;
	
	private String codeState;
	private String serviceID;
	private String brower;
	private String apikey;
	private String time;
	private String rankService[] = new String[]{null, null, null};
	private String muchApikey;
	private String peakTime;
	private int sum;
	private int test;
	
	private int numOfCodeState[] = new int[]{0,0,0};
	private int numOfBrower[]	 = new int[]{0,0,0,0,0};
	
	private HashMap<String, Integer> serviceHashmap = new HashMap<String, Integer>();
	private HashMap<String, Integer> apiHashmap = new HashMap<String, Integer>();
	private HashMap<String, Integer> timeHashmap = new HashMap<String, Integer>();

	private TreeMap<String, Integer> serviceSortedmap = null;
	private TreeMap<String, Integer> apiSortedmap = null;
	private TreeMap<String, Integer> timeSortedmap = null;
	
	int equals = 0;
	int notequals = 0;
	//private String apiList[]	 = new String[]{};
	private ArrayList<String> apiList = new ArrayList<String>();
	
	private AnalysisInfo analysisInfo = null;
	private ValueComparator hashComparator = null;

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
				time = analysisInfo.analyzeTime(line);
				
				//숫자를 다른곳에서 세는게 어떨까.......
				
				if(codeState.equals("[10]"))	 {numOfCodeState[0]++;}
				if(codeState.equals("[200]"))	 {numOfCodeState[1]++;}
				if(codeState.equals("[404]"))	 {numOfCodeState[2]++;}
				//"\\[IE\\]","\\[Firefox\\]","\\[Safari\\]","\\[Chrome\\]","\\[Opera\\]"
				if(brower.equals("[IE]"))		 {numOfBrower[0]++;}
				if(brower.equals("[Firefox]"))	 {numOfBrower[1]++;}
				if(brower.equals("[Safari]")) 	 {numOfBrower[2]++;}
				if(brower.equals("[Chrome]")) 	 {numOfBrower[3]++;}
				if(brower.equals("[Opera]"))	 {numOfBrower[4]++;}
				
				if(serviceHashmap.containsKey(serviceID)){
					//해당키가 있을경우
					int value=0;
					value = serviceHashmap.get(serviceID);
					serviceHashmap.put(serviceID, ++value);
				}
				if(!serviceHashmap.containsKey(serviceID)){
					//해당키가 없을경우
					serviceHashmap.put(serviceID, 1);
				}
				//우선 해쉬맵에 넣고 시작
				if(apiHashmap.containsKey(apikey)){
					//해당키가 있을경우
					int value=0;
					value = apiHashmap.get(apikey);
					apiHashmap.put(apikey, ++value);
				}
				if(!apiHashmap.containsKey(apikey)){
					//해당키가 없을경우
					apiHashmap.put(apikey, 1);
				}
				if(timeHashmap.containsKey(time)){
					int value=0;
					value = timeHashmap.get(time);
					timeHashmap.put(time, ++value);
				}else{
					timeHashmap.put(time, 1);
				}
			}
			
			hashComparator = new ValueComparator(serviceHashmap);
			serviceSortedmap = new TreeMap<String, Integer>(hashComparator);
			serviceSortedmap.putAll(serviceHashmap);
			
			Iterator<String> serviceIterator = serviceSortedmap.keySet().iterator();
			/*while(iterator.hasNext()){
				String key = (String) iterator.next();
		        System.out.print("key="+key);
		        System.out.println(" value="+apiSortedmap.get(key));
			}*/
			for(int index=0; index<3 ; index++){
				rankService[index] = (String) serviceIterator.next();
			}
			
			hashComparator = new ValueComparator(apiHashmap);
			apiSortedmap = new TreeMap<String, Integer>(hashComparator);
			apiSortedmap.putAll(apiHashmap);
			
			Iterator<String> iterator = apiSortedmap.keySet().iterator();
			/*while(iterator.hasNext()){
				String key = (String) iterator.next();
		        System.out.print("key="+key);
		        System.out.println(" value="+apiSortedmap.get(key));
			}*/
			muchApikey = (String) iterator.next();
			
			hashComparator = new ValueComparator(timeHashmap);
			timeSortedmap = new TreeMap<String, Integer>(hashComparator);
			timeSortedmap.putAll(timeHashmap);
			Iterator<String> timeIterator = timeSortedmap.keySet().iterator();
			
			/*while(timeIterator.hasNext()){
				String key = (String) timeIterator.next();
		        System.out.print("key="+key);
		        System.out.println(" value="+timeSortedmap.get(key));
			}*/
			
			peakTime = (String) timeIterator.next();
			
			System.out.println("10  : "+numOfCodeState[0]);
			System.out.println("200 : "+numOfCodeState[1]);
			System.out.println("404 : "+numOfCodeState[2]);
			System.out.println("");
			//이거 상위3개로 바꿔야함 얘도 HashMap 으로 바꿔야할듯
			System.out.println("1st ServiceID        : "+rankService[0]);
			System.out.println("2nd ServiceID        : "+rankService[1]);
			System.out.println("3rd ServiceID        : "+rankService[2]);
			
			System.out.println("");
			System.out.println("최다 호출 apikey : " + muchApikey);
			System.out.println("");
			System.out.println("peak time : " + peakTime);
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
				"1st Service : " + rankService[0] + "\r\n" +
				"2nd Service : " + rankService[1] + "\r\n" +
				"3rd Service : " + rankService[2] + "\r\n" +
				 "\r\n" +
				"IE         : "+(double)numOfBrower[0]/sum*100+"%" + "\r\n" +
				"Firefox    : "+(double)numOfBrower[1]/sum*100+"%" + "\r\n" +
				"Safari     : "+(double)numOfBrower[2]/sum*100+"%" + "\r\n" +
				"Chrome     : "+(double)numOfBrower[3]/sum*100+"%" + "\r\n" +
				"Opera      : "+(double)numOfBrower[4]/sum*100+"%" + "\r\n" +
				"\r\n" +
				"apikey     : " + muchApikey +"\r\n" +
				"\r\n" +
				"peak time  : " + peakTime;
		return result;
	}
	
}
