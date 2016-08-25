import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ETLAboutDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelImage;
  private ImageIcon aboutImage;
  private JButton okButton;
  private JDialog dlg;

  public ETLAboutDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "About", true);
    setResizable(false);
    this.dlg = this;
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLAboutDlg.1 local1 = new WindowAdapter(this) { private final ETLAboutDlg this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    this.aboutImage = new ImageIcon(getClass().getResource("/images/Program Info.jpg"));

    this.jPanelButton = new JPanel(new FlowLayout());
    this.jPanelImage = new JPanel(new BorderLayout());

    this.okButton = new JButton("OK");

    this.okButton.addActionListener(new ActionListener(this) { private final ETLAboutDlg this$0;

      public void actionPerformed() { ETLAboutDlg.access$000(this.this$0).dispose();
      }

    });
    JLabel localJLabel1 = new JLabel(this.aboutImage);
    localJLabel1.setPreferredSize(new Dimension(this.aboutImage.getIconWidth(), this.aboutImage.getIconHeight()));

    this.jPanelButton.add(this.okButton);
    this.jPanelImage.add(localJLabel1, "Center");

    String str1 = ETLAdmin.versionNumber + " " + ETLAdmin.buildNumber;
    String str2 = "";
    JLabel localJLabel2 = new JLabel(str1);
    JLabel localJLabel3 = new JLabel(str2);

    JPanel localJPanel1 = new JPanel(new FlowLayout());
    localJPanel1.add(localJLabel2);
    JPanel localJPanel2 = new JPanel(new FlowLayout());
    localJPanel2.add(localJLabel3);

    JPanel localJPanel3 = new JPanel(new BorderLayout());

    localJPanel3.add(localJPanel1, "Center");
    localJPanel3.add(localJPanel2, "South");

    this.jPanelImage.add(localJPanel3, "South");

    localContainer.add(this.jPanelImage, "Center");
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

  static JDialog access$000(ETLAboutDlg paramETLAboutDlg)
  {
    return paramETLAboutDlg.dlg;
  }
}