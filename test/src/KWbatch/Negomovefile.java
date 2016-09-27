package KWbatch;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;


public class Negomovefile {
	static String stProperties = "batchSetting";
	static String logsPath = PropertyUtil.getPropertyString(stProperties,"LogsPath");
	static String filePath = PropertyUtil.getPropertyString(stProperties,"filePath");
	static String new_filePath = PropertyUtil.getPropertyString(stProperties,"new_filePath");
	
	
	Set<String> Folder_tree = new TreeSet<String>();
	
	
	static StringBuffer sb = new StringBuffer();

	public static void main(String[] args) throws Exception {
		try{
			System.out.println("--ALL Start---");
			Negomovefile fss = new Negomovefile();	

			
			fss.searchFile(filePath); 
			 
			fss.createNewFolder(new_filePath);
			
			fss.copy(filePath, new_filePath);
			
			System.out.println("--ALL End ---");
			
		}catch (Exception e)
		{
			throw e;
		}finally {
			//LogsPrinter lprinter = new LogsPrinter(stSuccessLogsPath);
			doWriting(logsPath,sb.toString());
			
		}

		
		
		//total files;
	}

	//search files;
	public void searchFile(String filePath){
		
		sb.append("----------search Folder start------------");
		sb.append("\n");
		
		File fObjectFolder = new File(filePath);
		
		
		File[] stFilesObject = fObjectFolder.listFiles();
		
		
		
		
		for(int j=0;j<stFilesObject.length;j++){
			
			boolean isFolder = stFilesObject[j].isDirectory();
			boolean isFile = stFilesObject[j].isFile();
			
			sb.append(stFilesObject[j].getPath());
			sb.append("\n");
			
			if(isFolder){
				///i1home1/uploadfiles/nego
				
				//searchFile(stFilesObject[j].getPath());
//				System.out.println(stFilesObject[j].getPath());
//				System.out.println(stFilesObject[j].getPath().lastIndexOf("\\"));
//				System.out.println(stFilesObject[j].getPath().substring(stFilesObject[j].getPath().lastIndexOf("\\")+1, stFilesObject[j].getPath().lastIndexOf("\\")+5));
				
				String new_folder_name=stFilesObject[j].getPath().substring(stFilesObject[j].getPath().lastIndexOf("/")+1, stFilesObject[j].getPath().lastIndexOf("/")+5);
				
				Folder_tree.add(new_folder_name);
				sb.append(new_folder_name);
				sb.append("\n");
				
				//Folder_tree();
				
			}else if(isFile){
				
				sb.append(stFilesObject[j].getPath());
				sb.append("\n");
				
			}
		}
		
		sb.append("----------search Folder end------------");
		sb.append("\n");
	}
	
	public void createNewFolder(String filePath){
		sb.append("----------createNewFolder start------------");
		sb.append("\n");
		
		for(String str_temp:Folder_tree){
			File new_Folder = new File(filePath+"/"+str_temp);
			if(!new_Folder.exists() && !new_Folder.isDirectory()){
				new_Folder.mkdirs();
			}
		}
		
		sb.append("----------createNewFolder end------------");
		sb.append("\n");
		
	}
	
	
	
	public void copy(String old_filePath,String new_filePath) throws IOException{
		
		sb.append("----------copy start------------");
		sb.append("\n");
		
		File fObjectFolder = new File(filePath);
		
		File[] stFilesObject = fObjectFolder.listFiles();
		
		int cnt=1;
		
		for(int j=0;j<stFilesObject.length;j++){
			System.out.println("THE "+cnt+" line");
			cnt++;
			boolean isFolder = stFilesObject[j].isDirectory();
			boolean isFile = stFilesObject[j].isFile();
			
			if(isFolder){
				
				String new_folder_name_before_4=stFilesObject[j].getPath().substring(stFilesObject[j].getPath().lastIndexOf("/")+1, stFilesObject[j].getPath().lastIndexOf("/")+5);
				String new_folder_name_all=stFilesObject[j].getPath().substring(stFilesObject[j].getPath().lastIndexOf("/")+1);
				
				//Folder_tree.add(new_folder_name);
				
				
				File old_file = new File(stFilesObject[j].getPath());
				File new_file = new File(new_filePath+"/"+new_folder_name_before_4+"/"+new_folder_name_all);
				
				copyFolder(old_file,new_file);

				
			}else if(isFile){
				
				File old_file = new File(old_filePath+"/"+stFilesObject[j].getName());
				File new_file = new File(new_filePath+"/"+stFilesObject[j].getName());
				
				copyFolder(old_file,new_file);
				
			}
		}
		
		
		
		
		
		
		sb.append("----------copy end------------");
		sb.append("\n");
		
	}

	
	
	/** 
	 * copy file and folder to new folder
	 * @param src 
	 * @param dest 
	 * @throws IOException 
	 */  
	private void copyFolder(File src, File dest) throws IOException { 
		
	    if (src.isDirectory()) { 
	    	
	        if (!dest.exists()) {  
	            dest.mkdir();  
	        }  
	        
	        
	        String files[] = src.list(); 
	        
	        for (String file : files) {  
	        	
            File srcFile = new File(src, file);  
	        File destFile = new File(dest, file);  
	            // 
	            copyFolder(srcFile, destFile);  
	            
	            
	        }  
	    } else {  
	    	
	    	fileChannelCopy(src,dest);
	    	
	    }  
	    
	}  

	public static void doWriting(String sFilePath,String stContent) throws IOException{
		File objFile = new File(sFilePath);
		File parentFile = new File(objFile.getParent());
		if(!objFile.exists()){
			//System.out.println("create new one!");
		}

		if(!parentFile.mkdirs()){
			//System.out.println("make dirs failed! dirs already exist in disc!");
		}

		if(!objFile.createNewFile()){
			//System.out.println("create new file failed!");
		}

		//write string into log ;
		if(objFile.canWrite()){
			FileWriter fw = new FileWriter(sFilePath,true);
			fw.write(stContent);
			fw.write("\n");
			fw.flush();
			//end
		}
	}
	
	/**

	    * 

	    * 

	    * @param s

	    *           

	    * @param t

	    *           

	    */

	    public void fileChannelCopy(File s, File t) {

	        FileInputStream fi = null;

	        FileOutputStream fo = null;

	        FileChannel in = null;

	        FileChannel out = null;

	        try {

	            fi = new FileInputStream(s);

	            fo = new FileOutputStream(t);

	            in = fi.getChannel();//

	            out = fo.getChannel();//

	            in.transferTo(0, in.size(), out);//

	        } catch (IOException e) {

	            e.printStackTrace();

	        } finally {

	            try {

	                fi.close();

	                in.close();

	                fo.close();

	                out.close();

	            } catch (IOException e) {

	                e.printStackTrace();

	            }

	        }

	    }



	//judge if this file is end with the type in array;
	public boolean isFileTypeInBox(String fileName,String[] stArray){
		boolean blReturn = false;
		int size = stArray.length;
		for(int i=0;i<size;i++){
			String stType = stArray[i];
			if(fileName.endsWith(stType)){
				blReturn = true;
				break;
			}
		}
		return blReturn;
	}

	//check if this string has alphabet.
	//
	public static boolean judgeStringPassed(String st0,String st1){
		boolean blResult = true;
		if("".equals(st0)){
			blResult = false;
			return blResult;
		}
		for(int k=0;k<st0.length();k++){
			char c = st0.charAt(k);
			if(st1.indexOf(c) == -1){
				blResult = false;
				break;
			}
		}
		return blResult;
	}

	//get date of your own format;
	public static String changeDateFormat(String dateString,String dateFormat){
		String stResult = "";
		try{
			Date stDate = new Date(dateString);
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			stResult = sdf.format(stDate);
			//System.out.println(stResult);			
		}
		catch(RuntimeException e)
		{
			System.out.println("*"+dateString+"*");
			System.out.println(e);
			e.printStackTrace();
			throw e;
		}

		return stResult;
	}

	//remove the sign "" or '' at the beginning and the end;
	public static String removeQuotationMarks(String st){
		String stResult = "";
		if(!"".equals(st) && st != null){
			String firstChar = new String(new Character(st.charAt(0)).toString());
			String lastChar = new String(new Character(st.charAt(st.length()-1)).toString());
			while("\"".equalsIgnoreCase(firstChar) || "'".equalsIgnoreCase(firstChar)){
				st = st.substring(1,st.length());
				firstChar = new String(new Character(st.charAt(0)).toString());
			}
			while("\"".equalsIgnoreCase(lastChar) || "'".equalsIgnoreCase(lastChar)){
				st = st.substring(0,st.length()-1);
				lastChar = new String(new Character(st.charAt(st.length()-1)).toString());
			}
			stResult = st;
		}
		//System.out.println(stResult);
		return stResult;
	}



	//get date;
	public static String getObjectDate(ArrayList al,String strContained1,String strContained2){
		String stResult = "";
		boolean blFlag1 = false;
		boolean blFlag2 = false;
		int size = al.size();
		if(!"".equalsIgnoreCase(strContained1) && strContained1 != null){
			blFlag1 = true;
		}
		if(!"".equalsIgnoreCase(strContained2) && strContained2 != null){
			blFlag2 = true;
		}
		//---- add by siyi 20090128 start ----
		//int tempcout = 0;
		//---- add by siyi 20090128 end ----
		if(blFlag1 && blFlag2){
			int datePosition = -1;
			for(int i=0;i<size;i++){
				String stTemp = (String)al.get(i);
				//System.out.println(stTemp);
				int sPosition1 = stTemp.indexOf(strContained1);
				int sPosition2 = stTemp.indexOf(strContained2);
				if(sPosition1 != -1 && sPosition2 != -1){
					datePosition = i;
					//tempcout++;
					break;//
				}
			}
			//System.out.println(tempcout);
			//if(tempcout>=2)
			//{
			//	System.out.println(al);
			//}
			if(datePosition >=1){
				stResult = (String)al.get(datePosition-1);
			}
		}
		return stResult;
	}

	//get date ; two parameters;
	public static String getObjectDate(ArrayList al,String strContained1){
		String stResult = "";
		boolean blFlag1 = false;
		int size = al.size();
		if(!"".equalsIgnoreCase(strContained1) && strContained1 != null){
			blFlag1 = true;
		}
		if(blFlag1){
			int datePosition = -1;
			for(int i=0;i<size;i++){
				String stTemp = (String)al.get(i);
				int stTempSize = stTemp.length();
				int stContainedSize = strContained1.length();
				int sPosition1 = stTemp.indexOf(strContained1);
				if(sPosition1 != -1 && stTempSize > stContainedSize){
					datePosition = i;
					break;
				}
			}
			if(datePosition >=1){
				stResult = (String)al.get(datePosition-1);
			}
		}
		//System.out.println("resultDate="+stResult);
		return stResult;
	}

	//get leader's name;
	public static String getObjectName(ArrayList al,String strContained1,String strContained2){
		String stResult = "";
		boolean blFlag1 = false;
		boolean blFlag2 = false;
		int size = al.size();
		if(!"".equalsIgnoreCase(strContained1) && strContained1 != null){
			blFlag1 = true;
		}
		if(!"".equalsIgnoreCase(strContained2) && strContained2 != null){
			blFlag2 = true;
		}
		if(blFlag1 && blFlag2){
			for(int i=0;i<size;i++){
				String stTemp = (String)al.get(i);
				int sPosition1 = stTemp.indexOf(strContained1);
				int sPosition2 = stTemp.indexOf(strContained2);
				if(sPosition1 != -1 && sPosition2 != -1){
					int stParentHeses = stTemp.indexOf("(");
					stResult = stTemp.substring(stParentHeses+1,sPosition2-1);
					//break;
				}
			}
		}
		return stResult;
	}

	//get subString of the string passed
	public static String getSubString(String st,String stSign,String stDirection){
		String stResult = "";
		if(!"".equals(st) && st != null){
			int position = st.indexOf(stSign);
			if(position != -1){
				if("left".equalsIgnoreCase(stDirection)){
					stResult = st.substring(0,position).trim();
				}
				if("right".equalsIgnoreCase(stDirection)){
					stResult = st.substring(position+1,st.length()).trim();
				}
			}
		}
		return stResult;
	}

	//remove several zeros at first;
	public static String removeHeadZERO(String st){
		String stResult = "";
		if(!"".equals(st) && st != null){
			String firstChar = new String(new Character(st.charAt(0)).toString());
			while("0".equalsIgnoreCase(firstChar)){
				st = st.substring(1,st.length());
				firstChar = new String(new Character(st.charAt(0)).toString());
			}
			if(".".equals(new Character(st.charAt(0)).toString())){
				stResult = "0" + st;
			}else{
				stResult = st;
			}
		}
		//System.out.println(stResult);
		return stResult;
	}


	


}
