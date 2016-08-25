import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.EventObject;
import java.util.GregorianCalendar;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.plaf.metal.MetalToggleButtonUI;

public class MonthSelectPad extends JPanel
{
  JButton[] weekLabel;
  JToggleButton[] dayBuf;
  JPanel monthPanel;
  JLabel monthLabel;
  int yearNum;
  int monthNum;
  int start = 0;
  int end = 30;
  int locX = 0;
  int locY = 15;
  Color selectColor;
  Color backgroundColor;
  Color satColor;
  Color sunColor;
  private ActionListener actionPerform = new ActionListener(this) { private final MonthSelectPad this$0;

    public void actionPerformed() { MonthSelectPad.access$000(this.this$0, paramActionEvent);
    }

  };
  private ActionListener dayActionPerform = new ActionListener(this) { private final MonthSelectPad this$0;

    public void actionPerformed() { MonthSelectPad.access$100(this.this$0, paramActionEvent);
    }
  };

  public MonthSelectPad()
  {
    this.backgroundColor = new Color(215, 215, 215);
    this.selectColor = new Color(255, 255, 55);
    this.satColor = new Color(0, 0, 255);
    this.sunColor = new Color(255, 0, 0);

    this.monthPanel = new JPanel();
    this.monthPanel.setLayout(null);

    this.monthPanel.setPreferredSize(new Dimension(180, 160));
    this.monthPanel.setMinimumSize(new Dimension(180, 160));
    this.monthPanel.setMaximumSize(new Dimension(180, 160));

    this.monthLabel = new JLabel();
    this.monthLabel.setHorizontalAlignment(0);
    this.monthLabel.setBounds(new Rectangle(5, 0, 170, 15));

    this.weekLabel = new JButton[7];
    this.dayBuf = new JToggleButton[42];

    this.weekLabel[0] = new JButton("S");
    this.weekLabel[1] = new JButton("M");
    this.weekLabel[2] = new JButton("T");
    this.weekLabel[3] = new JButton("W");
    this.weekLabel[4] = new JButton("T");
    this.weekLabel[5] = new JButton("F");
    this.weekLabel[6] = new JButton("S");

    this.weekLabel[0].setForeground(this.sunColor);
    this.weekLabel[6].setForeground(this.satColor);

    for (int i = 0; i < 7; ++i) {
      this.weekLabel[i].setMargin(new Insets(0, 0, 0, 0));
      this.weekLabel[i].setFocusPainted(false);
      this.weekLabel[i].setBorderPainted(false);

      this.weekLabel[i].setVerticalAlignment(0);
      this.weekLabel[i].setHorizontalAlignment(0);

      this.weekLabel[i].setBounds(new Rectangle(this.locX + i * 24, this.locY, 22, 18));

      this.weekLabel[i].addActionListener(this.actionPerform);
      this.monthPanel.add(this.weekLabel[i]);
    }

    MonthSelectPad.DayButtonUI localDayButtonUI = new MonthSelectPad.DayButtonUI(this);

    for (int j = 0; j < 6; ++j)
      for (int k = 0; k < 7; ++k) {
        int l = j * 7 + k;

        this.dayBuf[l] = new JToggleButton("");
        this.dayBuf[l].setMargin(new Insets(0, 0, 0, 0));
        this.dayBuf[l].setFocusPainted(false);

        this.dayBuf[l].addActionListener(this.dayActionPerform);
        this.dayBuf[l].setUI(localDayButtonUI);

        this.dayBuf[l].setVerticalAlignment(0);
        this.dayBuf[l].setHorizontalAlignment(0);

        if (k == 6)
          this.dayBuf[l].setForeground(this.satColor);
        else if (k == 0)
          this.dayBuf[l].setForeground(this.sunColor);

        this.dayBuf[l].setBackground(this.backgroundColor);

        int i1 = this.locX + k * 24;
        int i2 = this.locY + 25 + j * 20;
        this.dayBuf[l].setBounds(new Rectangle(i1, i2, 22, 18));
        this.monthPanel.add(this.dayBuf[l]);
      }


    setLayout(new BorderLayout());
    add(this.monthPanel, "Center");
  }

  public void setSelectColor(Color paramColor)
  {
    this.selectColor = paramColor;
  }

  public void init(int paramInt1, int paramInt2)
  {
    this.yearNum = paramInt1;
    this.monthNum = paramInt2;

    GregorianCalendar localGregorianCalendar = new GregorianCalendar();

    localGregorianCalendar.set(paramInt1, paramInt2 - 1, 1);
    int i = localGregorianCalendar.get(7);
    switch (i)
    {
    case 1:
      this.start = 0;
      break;
    case 2:
      this.start = 1;
      break;
    case 3:
      this.start = 2;
      break;
    case 4:
      this.start = 3;
      break;
    case 5:
      this.start = 4;
      break;
    case 6:
      this.start = 5;
      break;
    case 7:
      this.start = 6;
    }

    int j = localGregorianCalendar.getActualMaximum(5);
    this.end = (this.start + j - 1);

    String str = String.valueOf(paramInt1);

    switch (paramInt2)
    {
    case 1:
      str = str + " " + "January";
      break;
    case 2:
      str = str + " " + "February";
      break;
    case 3:
      str = str + " " + "March";
      break;
    case 4:
      str = str + " " + "April";
      break;
    case 5:
      str = str + " " + "May";
      break;
    case 6:
      str = str + " " + "June";
      break;
    case 7:
      str = str + " " + "July";
      break;
    case 8:
      str = str + " " + "August";
      break;
    case 9:
      str = str + " " + "September";
      break;
    case 10:
      str = str + " " + "October";
      break;
    case 11:
      str = str + " " + "November";
      break;
    case 12:
      str = str + " " + "December";
    }

    this.monthLabel.setText(str);
    this.monthPanel.add(this.monthLabel);

    for (int l = 0; l < 42; ++l) {
      this.dayBuf[l].setSelected(false);
      this.dayBuf[l].setBackground(this.backgroundColor);

      if ((l < this.start) || (l > this.end))
      {
        this.dayBuf[l].setText("");
        this.dayBuf[l].setVisible(false);
      }
      else {
        int k = l - this.start + 1;
        this.dayBuf[l].setText(String.valueOf(k));
        this.dayBuf[l].setVisible(true);
      }
    }
  }

  public int[] getSelectedDay()
  {
    int i = 0;
    for (int j = this.start; j <= this.end; ++j)
      if (this.dayBuf[j].isSelected())
        ++i;


    int[] arrayOfInt = new int[i];

    int k = this.start; for (int l = 0; k <= this.end; ++k) {
      if (this.dayBuf[k].isSelected())
        arrayOfInt[(l++)] = (k - this.start + 1);

    }

    return arrayOfInt;
  }

  public int[] getSelectedDay1()
  {
    int[] arrayOfInt = new int[31];

    int i = this.start; for (int j = 0; i <= this.end; ) {
      if (this.dayBuf[i].isSelected())
        arrayOfInt[j] = 1;
      else
        arrayOfInt[j] = 0;
      ++i; ++j;
    }

    return arrayOfInt;
  }

  public void setSelectedDay(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;

    for (int j = 0; j < i; ++j) {
      int k = paramArrayOfInt[j];

      this.dayBuf[(this.start + k - 1)].setSelected(true);
      this.dayBuf[(this.start + k - 1)].setBackground(this.selectColor);
    }
  }

  public void setSelectedDay1(int[] paramArrayOfInt)
  {
    for (int i = 0; i < 31; ++i)
      if ((paramArrayOfInt[i] == 1) && (this.dayBuf[(this.start + i)].isEnabled())) {
        this.dayBuf[(this.start + i)].setSelected(true);
        this.dayBuf[(this.start + i)].setBackground(this.selectColor);
      }
  }

  public void setFlagDay(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;

    for (int j = 0; j < i; ++j) {
      int k = paramArrayOfInt[j];

      this.dayBuf[(this.start + k - 1)].setSelected(true);
      this.dayBuf[(this.start + k - 1)].setBackground(this.selectColor);
    }
  }

  public void setEnabledDay(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;

    int[] arrayOfInt = new int[31];
    for (int j = 0; j < 31; ++j)
      arrayOfInt[j] = 0;

    for (int k = 0; k < i; ++k) {
      arrayOfInt[(paramArrayOfInt[k] - 1)] = 1;
    }

    for (int l = 0; l < 31; ++l)
      if (arrayOfInt[l] == 0)
        this.dayBuf[(this.start + l)].setEnabled(false);
  }

  public void enableDay()
  {
    for (int i = 0; i < 31; ++i)
      this.dayBuf[(this.start + i)].setEnabled(true);
  }

  public void disableDay(int paramInt)
  {
    for (int i = 0; i < paramInt; ++i)
      this.dayBuf[(this.start + i)].setEnabled(false);
  }

  public void disableAllDay()
  {
    for (int i = 0; i < 31; ++i)
    {
      this.dayBuf[(this.start + i)].setEnabled(false);
    }
  }

  public void setEnabledDay(int[] paramArrayOfInt, Color paramColor)
  {
    int i = paramArrayOfInt.length;

    int[] arrayOfInt = new int[31];
    for (int j = 0; j < 31; ++j)
      arrayOfInt[j] = 0;

    for (int k = 0; k < i; ++k) {
      arrayOfInt[(paramArrayOfInt[k] - 1)] = 1;
    }

    for (int l = 0; l < 31; ++l)
      if (arrayOfInt[l] == 0)
        this.dayBuf[(this.start + l)].setEnabled(false);
      else
        this.dayBuf[(this.start + l)].setBackground(paramColor);
  }

  private void actionFunc(ActionEvent paramActionEvent)
  {
    Object localObject = paramActionEvent.getSource();

    int i = 0;
    if (localObject.equals(this.weekLabel[0])) {
      i = 0;
    }
    else if (localObject.equals(this.weekLabel[1])) {
      i = 1;
    }
    else if (localObject.equals(this.weekLabel[2])) {
      i = 2;
    }
    else if (localObject.equals(this.weekLabel[3])) {
      i = 3;
    }
    else if (localObject.equals(this.weekLabel[4])) {
      i = 4;
    }
    else if (localObject.equals(this.weekLabel[5])) {
      i = 5;
    }
    else if (localObject.equals(this.weekLabel[6])) {
      i = 6;
    }

    for (int j = i; j < 42; j += 7)
      if (j >= this.start) { if (j > this.end)
          continue;

        if (!(this.dayBuf[j].isEnabled()))
          continue;

        if (this.dayBuf[j].isSelected()) {
          this.dayBuf[j].setSelected(false);
          this.dayBuf[j].setBackground(this.backgroundColor);
        }
        else {
          this.dayBuf[j].setSelected(true);
          this.dayBuf[j].setBackground(this.selectColor);
        }
      }
  }

  private void dayActionFunc(ActionEvent paramActionEvent)
  {
    JToggleButton localJToggleButton = (JToggleButton)paramActionEvent.getSource();

    if (localJToggleButton.isSelected())
      localJToggleButton.setBackground(this.selectColor);
    else
      localJToggleButton.setBackground(this.backgroundColor);
  }

  static void access$000(MonthSelectPad paramMonthSelectPad, ActionEvent paramActionEvent)
  {
    paramMonthSelectPad.actionFunc(paramActionEvent); } 
  static void access$100(MonthSelectPad paramMonthSelectPad, ActionEvent paramActionEvent) { paramMonthSelectPad.dayActionFunc(paramActionEvent);
  }

  private class DayButtonUI extends MetalToggleButtonUI
  {
    private final MonthSelectPad this$0;

    public DayButtonUI()
    {
      this.this$0 = paramMonthSelectPad;
    }

    protected void paintButtonPressed(, AbstractButton paramAbstractButton)
    {
    }
  }
}