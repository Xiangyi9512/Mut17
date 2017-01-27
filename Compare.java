// package handleInvariantFiles;

import java.io.*;
import java.util.*;

public class Compare {	
	
	static ArrayList<String> originArrayList = new ArrayList<String>();	
	static ArrayList<String> differentExitPoint = new ArrayList<String>();
	static ArrayList<File> files = new ArrayList<File>();
	static ArrayList<String> filePaths = new ArrayList<String>();
	static ArrayList<ArrayList<String>> mutantArrayLists = new ArrayList<ArrayList<String>>();
	int countDifferentExit = 0;
    static StringBuilder sb = new StringBuilder();
   
	
	public void setArrayListExit(ArrayList<String> blockFromOrigin, ArrayList<String> mutantFile){
		int size = mutantFile.size();
		String firstString = blockFromOrigin.get(0);
		int i = 0;
		while(i <= (size - 2)){
			String currentString = mutantFile.get(i);
			Boolean compare = true;
			int j = 0;
			while(firstString.charAt(j) != ':' && compare != false){
				Character a = firstString.charAt(j);
				Character b = currentString.charAt(j);
				int compareResult = a.compareTo(b);
				if( compareResult != 0){
					compare = false;
				}
				j++;
			}
			
			if(compare == true ){
				if(currentString.charAt(j) == ':' && currentString.charAt(j+1) == ':' && currentString.charAt(j+2) == ':' && currentString.charAt(j+3) == 'E' && currentString.charAt(j+4) == 'X' && currentString.charAt(j+5) == 'I' && currentString.charAt(j+6) == 'T' && currentString.length() > (j+7)){	
					differentExitPoint.add(currentString);
//					System.out.println("setup arraylist" + currentString);
				}
			}
			i++;
		}
	}
	
	public void checkMutantFile(ArrayList<String> blockFromOrigin, ArrayList<String> mutantFile, int[] array){
		int size = mutantFile.size();
		int i = 0;
		boolean findEqual = false;
		ArrayList<String> sameBlock = new ArrayList<String>();
		while(i <= (size - 2)){
			String currentString = mutantFile.get(i);
			if(currentString.equals(blockFromOrigin.get(0))){
				findEqual = true;
				i++;
				while(mutantFile.get(i).charAt(0) != '=' && i <= (size - 2)){
					sameBlock.add(mutantFile.get(i));
					i++;
				}
				break;
			}
			
			i++;
		}
		i = 0;
		if(findEqual == false  && differentExitPoint.isEmpty()){
			setArrayListExit(blockFromOrigin, mutantFile);
		}
		if(findEqual == false){
			String firstString = blockFromOrigin.get(0);
			i = 0;
			while(i <= (size - 2)){
				String currentString = mutantFile.get(i);
				Boolean compare = true;
				int j = 0;
				while(firstString.charAt(j) != ':' && compare != false){
					Character a = firstString.charAt(j);
					Character b = currentString.charAt(j);
					int compareResult = a.compareTo(b);
					if( compareResult != 0){
						compare = false;
					}
					j++;
				}		
				if(compare == true){
					if(currentString.charAt(j) == ':' && currentString.charAt(j+1) == ':' && currentString.charAt(j+2) == ':' && currentString.charAt(j+3) == 'E' && currentString.charAt(j+4) == 'X' && currentString.charAt(j+5) == 'I' && currentString.charAt(j+6) == 'T' && currentString.length() > (j+7)){
						String currentFoundString = differentExitPoint.get(countDifferentExit);
						i = 0;
						while(i <= (size - 2)){
							currentString = mutantFile.get(i);
							if(currentString.equals(currentFoundString)){
								findEqual = true;
								i++;
								while(mutantFile.get(i).charAt(0) != '=' && i <= (size - 2)){
									sameBlock.add(mutantFile.get(i));
									i++;
								}
								break;
							}
							i++;
						}
						if(countDifferentExit == (differentExitPoint.size()- 1)){
							countDifferentExit = 0;
							differentExitPoint.clear();
						}
						else{
							countDifferentExit++;
						}
						break;
					}
				}
				i++;
			}
		}
		int blockSize = blockFromOrigin.size();
		for(int m = 1; m < blockSize; m++){
			for(int n = 0; n < sameBlock.size(); n++){
				if(blockFromOrigin.get(m).equals(sameBlock.get(n))){
					++array[m-1];
				}
			}
		}
	}
	
	public void checkIfSameString(){
		int size = originArrayList.size();
		//System.out.println(size);
		int i = 0;
		while(i <= (size - 2)){
			String currentString = originArrayList.get(i);
			if(currentString.charAt(0) == '='){
//				String nextString = originArrayList.get(i+1);
//				System.out.println(nextString);
				i++;
				ArrayList<String> needCompareRegion = new ArrayList<String>();
				while(originArrayList.get(i).charAt(0) != '=' && i <= (size - 2)){
					needCompareRegion.add(originArrayList.get(i));
//					System.out.println(originArrayList.get(i));
					i++;
				}
//				int regionSize = needCompareRegion.size();
//				System.out.println("The region size is : " + regionSize);
//				for(int j = 0; j < regionSize; j++){
//					System.out.println("compare region " + j + " is : " + needCompareRegion.get(j));
//				}
				int compareRegionSize = needCompareRegion.size();
				//System.out.println(compareRegionSize);
				int[] array = new int[compareRegionSize-1];
				Arrays.fill(array, 0);
				for(int j = 0; j < mutantArrayLists.size(); j++){
					checkMutantFile(needCompareRegion, mutantArrayLists.get(j), array);
				}
				System.out.println(needCompareRegion.get(0).toString());
				sb.append(needCompareRegion.get(0).toString());
				sb.append('\n');
				for(int m = 1; m < compareRegionSize; m++){
					System.out.println(needCompareRegion.get(m) + " appears " + array[m-1] + " times");	
			        sb.append(needCompareRegion.get(m));
			        sb.append(',');
			        sb.append(array[m-1]);
			        sb.append('\n');
				}
			}
		}
	}
	
	public void readStringMut(String path) throws IOException{
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		ArrayList<String> mutantArrayList = new ArrayList<String>();
		String strLine;	
		int i = 0;
		
		while ((strLine = br.readLine())!= null){	
			if(!strLine.equals("")){
				if(strLine.charAt(0) == '='){
					i = 1;
				}
				if(i == 1){	
					mutantArrayList.add(strLine);
				}
			}
		}
		mutantArrayLists.add(new ArrayList<String>(mutantArrayList));
		mutantArrayList.clear();
		in.close();
	}
	
	public void readStringOrigin(String originalTxtPath) throws IOException{
		FileInputStream fstream = new FileInputStream(originalTxtPath);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;	
		int i = 0;
		while ((strLine = br.readLine())!= null){	
			if(!strLine.equals("")){
				if(strLine.charAt(0) == '=')
					i = 1;
				if(i == 1){					
						originArrayList.add(strLine);
						//int size = ar.size();
						//for(int j = 0; j < size; j++){
							//System.out.println(ar.get(j));
						//}
				}
			}
		}
		in.close();
	}
	
	public void handleFilePath(){
		for(File f: files){
			String filePath = f.getAbsolutePath();
			filePaths.add(filePath);
//			int filePathSize = filePath.length();
//			System.out.println("The file in files is " + f + " and its path has size " + filePathSize);
//			for(int i = 0; i < filePathSize; i++){
//				if(filePath.charAt(i) == '.'){
//					String relativePath = filePath.substring(i);
//					System.out.println("RelativePath is " + relativePath);
//					filePaths.add(relativePath);
//					break;
//				}
//			}
		}
	}
		
	public void listf(String directoryName) {
	    File directory = new File(directoryName);
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            String filePath = file.getAbsolutePath();
	            String subfilePath = filePath.substring(filePath.length()-3);
	            String checkOrigin = filePath.substring(filePath.length()-12);
//	            System.out.println("check origin" + checkOrigin);
	            if(subfilePath.equals("txt")){
	            	if(!checkOrigin.equals("original.txt")){
//		            	System.out.println("if origin" + filePath);
		            	files.add(file);
	            	}
	            }
	        } 
	        else if (file.isDirectory()) {
	            listf(file.getAbsolutePath());
	        }
	    }
	}
	
	public String findProgramName(String directoryPath){
		File directory = new File(directoryPath);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.getName().equals("result")) {
//				System.out.println("Find result folder");
				String resultPath = file.getAbsolutePath();
//				System.out.println("Result path : " + resultPath);
				File result = new File(resultPath);
				File[] resultFiles = result.listFiles();
				String programName = "";
				int count = 0;
				for (File resultFile : resultFiles) {
					count++;
					programName = resultFile.getName();
				}
				if (count == 1) {
//					System.out.println("The program name is " + programName);
					return programName;
				}
			}
		}
		return "";
	}
	
	public static void main(String[] args) throws IOException {		
		compare string = new compare();
		File directory = new File("C:/Users/yxy/Documents/daikonparent/daikon/examples/program");
	    File[] fList = directory.listFiles();
	    PrintWriter pw = new PrintWriter(new File("test.csv"));
	    sb.append("Invariant");
        sb.append(',');
        sb.append("Appear times");
        sb.append('\n');
	    for (File file : fList) {
	        if (file.isDirectory()) {
	            String directoryPath = file.getAbsolutePath();
//	            System.out.println("The directory path is : " + directoryPath);
	            String checkSession = directoryPath.substring(directoryPath.length()-8,directoryPath.length()-1);
//	            System.out.println("The check session is : " + checkSession);
	            String session = new String("session");
	            if(checkSession.equals(session)){
//	            	System.out.println("Enter if statement.");
	            	String programName = string.findProgramName(directoryPath);
	            	String originalTxt = directoryPath.concat("\\result\\").concat(programName).concat("\\original\\original.txt");
//	            	System.out.println("The original text file path is : " + originalTxt);
		            string.readStringOrigin(originalTxt);
		            String mutantPath = directoryPath.concat("\\result\\").concat(programName);
//		            System.out.println("The mutant path is " + mutantPath);
					string.listf(mutantPath);
					string.handleFilePath();
					for(int i = 0; i < filePaths.size(); i++){
//						System.out.println("reading mutant file : " + i + " with path " + filePaths.get(i));
//						System.out.println("print file path : " + filePaths.get(i));
						string.readStringMut(filePaths.get(i));
					}
			//		for(int i = 0; i < mutantArrayLists.size(); i++){
			//			System.out.println("In mutant array lists" + mutantArrayLists.get(i));
			//		}
					string.checkIfSameString();
					originArrayList.clear();
					differentExitPoint.clear();
					files.clear();
					filePaths.clear();
					mutantArrayLists.clear();
					sb.append('\n');
	            }
	        }
	    }
	    pw.write(sb.toString());
	    pw.close();
	    System.out.println("done!");	
	}
}
