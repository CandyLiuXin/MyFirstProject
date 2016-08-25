import java.io.PrintStream;
import java.util.Locale;
import javax.swing.UIManager;

public class ETLAdmin
{
  static ETLMainFrame mainFrame;
  static String metalClassName = "javax.swing.plaf.metal.MetalLookAndFeel";
  static String motifClassName = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
  static String windowsClassName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
  static String versionNumber = "Version 2.5.3";
  static String buildNumber = "Build Number 071 (2004-3-29)";

  public ETLAdmin()
  {
    mainFrame = new ETLMainFrame();
  }

  public static void main(String[] paramArrayOfString)
  {
    Locale.setDefault(new Locale("en", "US"));
    try
    {
      UIManager.setLookAndFeel(metalClassName);
    }
    catch (Exception localException) {
      System.err.println("Could not load LookAndFeel");
    }

    new ETLAdmin();
  }
}