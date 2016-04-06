package dummy.core.webwidgets;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import dummy.core.libs.CustomScreenRecorder;
import dummy.core.libs.FileIO;
import dummy.core.libs.Log;
 
public class VideoRecord {
   
	public static ScreenRecorder screenRecorder;
	public static File videoFilepath;
  
	/**
     * This method will start the recording of the execution. recorded file will
     * get generated in the Results folder. Recording can
     * be started from any particular time.
     */
    public static void startRecording() 
       { 
    	
    	try {

   // 	File videoFilepath = new File("D://Videos//AVI//");
    	 videoFilepath = new File(Log.gsAutomationAutoResultPath);
    	
                           
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int width = screenSize.width;
      int height = screenSize.height;
                     
      Rectangle captureSize = new Rectangle(0,0, width, height);
                     
      GraphicsConfiguration gc = GraphicsEnvironment
    		  					.getLocalGraphicsEnvironment()
    		  					.getDefaultScreenDevice()
    		  					.getDefaultConfiguration();
		 
               screenRecorder = new CustomScreenRecorder(gc, captureSize,
               new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
               new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                    CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                    QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
	           new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "yellow", FrameRateKey, Rational.valueOf(30)),
	           null, 
	           videoFilepath, 
	           Log.gsScriptName);  // "My Automation Video"   
	   
		       screenRecorder.start();   
		       
		       screenRecorder.getStartTime();
    	
    		} catch (IOException e) {
    			e.printStackTrace();
    		} catch (AWTException e) {
    			e.printStackTrace();
    		}
       }
 
    /**
     * This method will stop the recording.
     */
       public static void stopRecording() 
       {
    	    try {
            	 	screenRecorder.stop();
    	    	} 
               catch (IOException e) {
                   e.printStackTrace();
             }
       }
       
       public static void deleteRecordedFile() 
       {
    	   if (videoFilepath != null) 
           {
        	   //  File resultDir[] = videoFilepath.listFiles();
        	     
        	     File resultDir[] = FileIO.getDirContents(Log.gsAutomationAutoResultPath);
                 
        	     for (int i = 0; i < resultDir.length; i++) 
                 {                	                 	 
                	// File aviFile = resultDir[i];**************************************
                	 
        	    	 	    	 
                	 if(resultDir[i].getName().endsWith(".avi") == true)
                	 {             		 
                		 
                		 FileIO.deleteFile(getRecordedFiles());
                	//	 resultDir[i].delete();
                         System.out.println("Deleted the AVI File " + resultDir[i] + "// from the result location: " + videoFilepath);
                	 }
                	 
                	 else {
						System.out.println("No AVI files to be deleted....");
					}
                	 
                 
                 }
        	     
           		}
          
           }
           

       
       
//       public static void deleteRecordedFile1() 
//       { 
//       
//       
//       File folder = new File(dir);
//       File fList[] = folder.listFiles();
//       // Searchs .lck
//       for (int i = 0; i < fList.length; i++) {
//           String pes = fList[i];
//           if (pes.endsWith(".lck")) {
//               // and deletes
//               boolean success = (new File(fList[i]).delete());
//           }
//       }
//       }
//       
//       
//       
//       
//       public void deleteRecordedFile() {
//           if (RecordedFile != null) {
//                 String[] children = RecordedFile.list();
//                 for (int i = 0; i &lt; children.length; i++) {
//                       new File(RecordedFile, children[i]).delete();
//                       System.out.println("Deleted this " + RecordedFile + "//"
//                                   + children[i]);
//                 }
//
//                 // The directory is now empty so delete it
//                 RecordedFile.delete();
//           }
//     }
       
           
       public static String getRecordedFiles(){
         	
    	   String MovieName = null;
    	   
    	   List<File> createdMovieFiles = screenRecorder.getCreatedMovieFiles();
           
    	   for(File movie : createdMovieFiles){
               System.out.println("New movie created: " + movie.getAbsolutePath());
               
                MovieName =  movie.getAbsolutePath();
           }
	
    	   return MovieName;
	   
    	   
       }

       
}
