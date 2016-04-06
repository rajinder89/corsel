package dummy.googly.scripts;

import java.io.IOException;

import org.monte.iodemo.ConcatDemoMain;

import dummy.core.webwidgets.VideoRecord;

public class Test2 extends ConcatDemoMain{
	
	
	public static void main(String[] args){
		
//		args=new String[] {
//		         "-o",
//		         "C:\\Users\\rajinder_singh\\workspace\\myDraft\\corsel\\dummy\\googly\\results\\concat.avi",
//		         "-i",
//		         "C:\\Users\\rajinder_singh\\workspace\\myDraft\\corsel\\dummy\\googly\\results\\dummy.googly.scripts.GoogleAbout-0331094340950.avi",
//		         "C:\\Users\\rajinder_singh\\workspace\\myDraft\\corsel\\dummy\\googly\\results\\dummy.googly.scripts.GoogleSearch-0331094503448.avi",};
//		         
//			
//	
//		C:\Users\rajinder_singh\workspace\myDraft\corsel\dummy\googly\results\dummy.googly.scripts.GoogleSearch-0331094503448.avi
//		
//		
//		C:\Users\rajinder_singh\Downloads>java -jar ConcatDemo.jar -o C:\Users\rajinder_singh\workspace\myDr
//		aft\corsel\dummy\googly\results\concat.avi -i C:\Users\rajinder_singh\workspace\myDraft\corsel\dummy
//		\googly\results\GoogleAbout.avi C:\Users\rajinder_singh\workspace\myDraft\corsel\dummy\googly\result
//		s\GoogleSearch.avi
		
		
//		VideoRecord.getRecordedFiles();
		String sDQ = "\"";
		System.out.println(sDQ);
		
		try {
	        Runtime.getRuntime().exec("cmd.exe /c start");
	        System.out.println("ok");
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}

}
