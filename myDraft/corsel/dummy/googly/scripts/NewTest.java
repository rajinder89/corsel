package dummy.googly.scripts;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;


import org.monte.media.Format;
import org.monte.media.math.Rational;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

import org.monte.screenrecorder.ScreenRecorder;


public class NewTest {
  
	 ScreenRecorder screenRecorder;
	
	@Test
	public void f() throws InterruptedException, IOException {
		
		
		WebDriver driver = new FirefoxDriver();
		
		//Call the start method of ScreenRecorder to begin recording
	    screenRecorder.start();
	
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
	  driver.manage().window().maximize();
	  
//	  System.out.println(driver.getClass());
//	  System.out.println(driver.getClass().getName());
	  
	  System.out.println(driver.manage().window().getSize());
	  System.out.println(driver.manage().window().getSize().getWidth());
	  System.out.println(driver.manage().window().getSize().getHeight());
	  
	  driver.manage().window().setSize(new Dimension(300,500));
	  
	  Thread.sleep(20000);
	  
	  System.out.println(driver.manage().window().getPosition());
	  System.out.println(driver.manage().window().getPosition().getX());
	  System.out.println(driver.manage().window().getPosition().getY());
	  
	  driver.manage().window().setPosition(new Point(50,200)); 
	  
	  Thread.sleep(20000);
	  
	  driver.close();
	  
	  //Call the stop method of ScreenRecorder to end the recording
	  screenRecorder.stop();
  }
	
	public void startRecording() throws Exception
    {
                          
         GraphicsConfiguration gc = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();

        this.screenRecorder = new ScreenRecorder
        	(gc,new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
        		new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                 CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                 QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)), null);
        this.screenRecorder.start();
      
    }

    public void stopRecording() throws Exception
    {
      this.screenRecorder.stop();
    }


}
