package dummy.core.libs;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @Class: Images
 * @Description: The Bitmap class contains general image capture functions
 */
public class Images
{

	private static String sImageType = "jpg";  //"png"

	/**
	 * Captures entire screen (desktop) image and writes it to the specified file as a jpeg
	 * @Parameter: filename - path and filename of file to write image out to
	 * @param filename 
	 */

	public static void captureScreen(String filename) {
		//this method captures the whole screen when only one argument is given
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		doScreenCapture(filename, 0, 0, width, height);
	}

	public static void captureScreen(String gsScriptName, String description, String fileName, boolean error) {
		Dimension myScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle myBounds = new Rectangle(0, 0, myScreen.width, myScreen.height);
		BufferedImage myImage = null;
		Robot robot;
		try {
			robot = new Robot();
			myImage =  robot.createScreenCapture(myBounds);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		File myScreenshot = new File(fileName);
		try {
			ImageIO.write(myImage, sImageType, myScreenshot);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

	/**
	 * Captures individual test object image and writes it to the specified file as a jpeg <p>
	 * @Parameter: filename 	path and filename of file to write image out to
	 * @Parameter: x 		x coordinate of screen location to capture
	 * @Parameter: y 		y coordinate of screen location to capture
	 * @Parameter: width 	width coordinate of screen location to capture
	 * @Parameter: height 	height coordinate of screen location to capture
	 * @author 
	 */
	protected static void captureScreen(String filename, int x, int y, int width, int height) {
		doScreenCapture (filename, x, y, width, height);
	}

	/**
	 * Helper function to capture screen image <p>
	 * @Parameter: filename 	path and filename of file to write image out to
	 * @Parameter: x 		x coordinate of screen location to capture
	 * @Parameter: y 		y coordinate of screen location to capture
	 * @Parameter: width 	width coordinate of screen location to capture
	 * @Parameter: height 	height coordinate of screen location to capture
	 */
	protected static void doScreenCapture (String filename, int x, int y, int width, int height) {
		//screen capture
		try {
			RenderedImage capture = null;
			Rectangle area = new Rectangle(x, y, width, height);
			Robot robot = new Robot();
			capture = robot.createScreenCapture(area);
			File f = new File(filename);
			ImageIO.write(capture, sImageType, f);
		}
		catch (Exception e) {
			Log.errorHandler( "Error in Image#doScreenCapture: error capturing image: " + e);
		}
	}

	/**
	 * Compares two images a pixel at a time
	 * @Parameter: expectedImage 	path and filename of file to compare to
	 * @Parameter: actualImage 		path and filename of the captured image
	 * @return true if the images are identical, false if not
	 * @author
	 * @param expectedImage 
	 * @param actualImage 
	 */
	public static boolean compareImages(String expectedImage, String actualImage) {
		BufferedImage expected = null, actual = null;
		try
		{
			//read in expected image
			File fExpected = new File(expectedImage);
			expected = ImageIO.read(fExpected);

			//read in actual image 
			File fActual = new File(actualImage);
			actual = ImageIO.read(fActual);

		}
		catch (Exception e) {
			Log.errorHandler( "Error in compareImages: error reading images: " + e);
			return false;
		}

		if (expected == null || actual == null)
			return false;
		if (expected.getHeight() != actual.getHeight() || expected.getWidth() != actual.getWidth())
			return false;

		for (int y = 0; y < expected.getHeight(); ++y)
		{
			for (int x = 0; x < expected.getWidth(); ++x)
			{
				if (expected.getRGB(x, y) != actual.getRGB(x, y))
					return false;
			}
		}

		return true;
	}

}