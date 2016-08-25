import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

public class ETLViewJobLogFileDlg extends JDialog
{
  private JTextArea textLog;
  private JPanel jPanelLog;
  private JPanel jPanelButton;
  private JButton jBnClose;
  private JScrollPane jScrollPane;
  private ETLMainFrame mainFrame;
  private ETLViewJobLogFileDlg dlg;

  public ETLViewJobLogFileDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "View Job Log File", false);
    this.mainFrame = ((ETLMainFrame)paramJFrame);
    this.dlg = this;
  }

  public void setLogFileName(String paramString)
  {
    String str = "View Job Log File - " + paramString;
    setTitle(str);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLViewJobLogFileDlg.1 local1 = new WindowAdapter(this) { private final ETLViewJobLogFileDlg this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    this.jPanelButton = new JPanel(new FlowLayout());
    this.jPanelLog = new JPanel(new BorderLayout());

    this.jBnClose = new JButton("Close");
    this.jBnClose.setMnemonic(67);

    this.jBnClose.addActionListener(new ActionListener(this) { private final ETLViewJobLogFileDlg this$0;

      public void actionPerformed() { ETLViewJobLogFileDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnClose);

    this.textLog = new JTextArea();

    this.textLog.setFont(new Font("Serif", 0, 14));

    this.textLog.setEditable(false);

    this.jScrollPane = new JScrollPane();
    this.jScrollPane.setViewportView(this.textLog);

    this.jPanelLog.add(this.jScrollPane, "Center");
    this.jPanelLog.setPreferredSize(new Dimension(760, 560));

    localContainer.add(this.jPanelLog, "Center");
    localContainer.add(this.jPanelButton, "South");

    pack();

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  public JTextArea getTextArea()
  {
    return this.textLog;
  }

  static ETLViewJobLogFileDlg access$000(ETLViewJobLogFileDlg paramETLViewJobLogFileDlg)
  {
    return paramETLViewJobLogFileDlg.dlg;
  }
}