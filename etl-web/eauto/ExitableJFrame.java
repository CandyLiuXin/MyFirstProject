import javax.swing.JFrame;

public class ExitableJFrame extends JFrame
{
  public ExitableJFrame()
  {
  }

  public ExitableJFrame(String paramString)
  {
    super(paramString);
  }

  protected void frameInit()
  {
    super.frameInit();
    super.setDefaultCloseOperation(3);
  }
}