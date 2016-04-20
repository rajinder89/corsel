package core.libs;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.monte.media.Format;
import org.monte.screenrecorder.ScreenRecorder;
 
public class CustomScreenRecorder extends ScreenRecorder {
 
    private String VideoName;
 
    public CustomScreenRecorder(GraphicsConfiguration cfg,
    							Rectangle captureArea, 
    							Format fileFormat, 
    							Format screenFormat,
    							Format mouseFormat, 
    							Format audioFormat, 
    							File movieFolder,
    							String name) throws IOException, AWTException {
			super(	cfg, 
					captureArea, 
					fileFormat, 
					screenFormat, 
					mouseFormat,
					audioFormat,
					movieFolder);
					VideoName = name; }
 
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
          if (!movieFolder.exists()) {
                movieFolder.mkdirs();
          } else if (!movieFolder.isDirectory()) {
                throw new IOException("\"" + movieFolder + "\" is not a directory.");
          }                            
//        return new File(movieFolder, VideoName + "-" + DateTime.getFormattedDateTime(new Date().getTime(), "MMddHHmmssSSS") + "." + Registry.getInstance().getExtension(fileFormat));  
          return new File(movieFolder, VideoName + "-" + DateTime.getFormattedDateTime(new Date().getTime(), "MMddHHmmssSSS") + Log.gsAutomationAutoResultVideoSuffix);
    }   
    
 }