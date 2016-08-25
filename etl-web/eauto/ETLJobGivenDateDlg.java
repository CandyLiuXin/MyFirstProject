import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

public class ETLJobGivenDateDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JLabel jLabel1;
  private InputJTextField jTextField1;
  private Connection dbcon;
  private ETLMainFrame mainFrame;
  private int mode;
  public String givenDate;

  public ETLJobGivenDateDlg(JFrame paramJFrame, int paramInt)
  {
    super(paramJFrame, "", true);

    if (paramInt == 0)
      setTitle("Invoke Job");
    else if (paramInt == 1)
      setTitle("Force Start Job Now");
    else if (paramInt == 2)
      setTitle("Invoke Group Head Job");
    else if (paramInt == 3)
      setTitle("Force Start Group Head Job");

    setResizable(false);
    this.mainFrame = ((ETLMainFrame)paramJFrame);
    this.mode = paramInt;
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLJobGivenDateDlg.1 local1 = new WindowAdapter(this) { private final ETLJobGivenDateDlg this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    this.jPanelButton = new JPanel();
    this.jPanelData = new JPanel();
    this.jPanelButton.setLayout(new FlowLayout());
    this.jPanelData.setLayout(null);

    this.jBnUpdate = new JButton("OK");
    this.jBnUpdate.setMnemonic(79);
    this.jBnCancel = new JButton("Cancel");
    this.jBnCancel.setMnemonic(67);

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLJobGivenDateDlg this$0;

      public void actionPerformed() { ETLJobGivenDateDlg.access$000(this.this$0);
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLJobGivenDateDlg this$0;

      public void actionPerformed() { this.this$0.givenDate = "";
        this.this$0.dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("Given Data Date(YYYYMMDD):");
    this.jLabel1.setBounds(new Rectangle(20, 20, 170, 25));

    this.jTextField1 = new InputJTextField(InputJTextField.NUMBERONLY);
    this.jTextField1.setBounds(new Rectangle(200, 20, 80, 25));
    this.jTextField1.setInputLimited(8);

    this.jPanelData.add(this.jLabel1);

    this.jPanelData.add(this.jTextField1);

    setSize(new Dimension(320, 130));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  private void update()
  {
    int i = 0;
    this.givenDate = this.jTextField1.getText().trim();

    if (this.givenDate.length() < 8) {
      String str = "The format of given date is not correct, please type in 'YYYYMMDD' format.";

      JOptionPane.showMessageDialog(this, str, "Error", 0);

      this.jTextField1.requestFocus();
      return;
    }

    if (this.mode == 0) {
      i = JOptionPane.showConfirmDialog(this, "Do you want to invoke this job?", "Invoke Job", 0, 3);
    }
    else if (this.mode == 1) {
      i = JOptionPane.showConfirmDialog(this, "Do you want to start this job now?", "Force Start Job", 0, 3);
    }
    else if (this.mode == 2) {
      i = JOptionPane.showConfirmDialog(this, "Do you want to invoke this group head job?", "Invoke Group Head Job", 0, 3);
    }
    else if (this.mode == 1) {
      i = JOptionPane.showConfirmDialog(this, "Do you want to start this group head job now?", "Force Start Group Head Job", 0, 3);
    }

    if (i != 0)
      return;

    dispose();
  }

  static void access$000(ETLJobGivenDateDlg paramETLJobGivenDateDlg)
  {
    paramETLJobGivenDateDlg.update();
  }
}