import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class YearSelectPad extends JPanel
{
  JPanel yearPanel;
  int yearNum;
  MonthSelectPad[] months;

  public YearSelectPad()
  {
    this.months = new MonthSelectPad[12];

    Color localColor = new Color(255, 0, 0);
    for (int i = 0; i < 3; ++i)
      for (int j = 0; j < 4; ++j) {
        int k = i * 4 + j;
        this.months[k] = new MonthSelectPad();
        this.months[k].setSelectColor(localColor);
        this.months[k].setBounds(new Rectangle(15 + j * 180, 10 + i * 165, 180, 160));
      }


    setLayout(null);
    for (i = 0; i < 12; ++i)
      add(this.months[i]);
  }

  public YearSelectPad(Color paramColor)
  {
    this.months = new MonthSelectPad[12];

    for (int i = 0; i < 3; ++i)
      for (int j = 0; j < 4; ++j) {
        int k = i * 4 + j;
        this.months[k] = new MonthSelectPad();
        this.months[k].setSelectColor(paramColor);
        this.months[k].setBounds(new Rectangle(15 + j * 180, 10 + i * 165, 180, 160));
      }


    setLayout(null);
    for (i = 0; i < 12; ++i)
      add(this.months[i]);
  }

  public YearSelectPad(int paramInt)
  {
    this.yearNum = paramInt;

    this.months = new MonthSelectPad[12];

    Color localColor = new Color(255, 0, 0);
    for (int i = 0; i < 3; ++i)
      for (int j = 0; j < 4; ++j) {
        int k = i * 4 + j;
        this.months[k] = new MonthSelectPad();
        this.months[k].setSelectColor(localColor);
        this.months[k].init(paramInt, k + 1);
        this.months[k].setBounds(new Rectangle(15 + j * 180, 10 + i * 165, 180, 160));
      }


    setLayout(null);
    for (i = 0; i < 12; ++i)
      add(this.months[i]);
  }

  public YearSelectPad(int paramInt, Color paramColor)
  {
    this.yearNum = paramInt;

    this.months = new MonthSelectPad[12];

    for (int i = 0; i < 3; ++i)
      for (int j = 0; j < 4; ++j) {
        int k = i * 4 + j;
        this.months[k] = new MonthSelectPad();
        this.months[k].setSelectColor(paramColor);
        this.months[k].init(paramInt, k + 1);
        this.months[k].setBounds(new Rectangle(15 + j * 180, 10 + i * 165, 180, 160));
      }


    setLayout(null);
    for (i = 0; i < 12; ++i)
      add(this.months[i]);
  }

  public void init(int paramInt)
  {
    for (int i = 0; i < 12; ++i)
      this.months[i].init(paramInt, i + 1);
  }

  public void setMonthSelectedDay(int paramInt, int[] paramArrayOfInt)
  {
    this.months[(paramInt - 1)].setSelectedDay(paramArrayOfInt);
  }

  public void setMonthSetFlagDay(int paramInt, int[] paramArrayOfInt)
  {
    this.months[(paramInt - 1)].setFlagDay(paramArrayOfInt);
  }

  public void setMonthEnabledDay(int paramInt, int[] paramArrayOfInt)
  {
    this.months[(paramInt - 1)].setEnabledDay(paramArrayOfInt);
  }

  public void setMonthEnabledDay(int paramInt, int[] paramArrayOfInt, Color paramColor)
  {
    this.months[(paramInt - 1)].setEnabledDay(paramArrayOfInt, paramColor);
  }

  public int[] getMonthSelectedDay(int paramInt)
  {
    int[] arrayOfInt = this.months[(paramInt - 1)].getSelectedDay();

    return arrayOfInt;
  }
}