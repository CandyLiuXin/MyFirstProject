import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class InputJTextField extends JTextField
{
  public static int NOCASE = 1;
  public static int UPPERCASE = 256;
  public static int LOWERCASE = 272;
  public static int NUMBERONLY = 4096;
  private int inputCase = 0;
  private int inputLimited = -1;

  public InputJTextField()
  {
  }

  public InputJTextField(int paramInt)
  {
    setInputCase(paramInt);
  }

  public void setInputCase(int paramInt)
  {
    this.inputCase = paramInt;
  }

  public void setInputLimited(int paramInt)
  {
    this.inputLimited = paramInt;
  }

  protected Document createDefaultModel()
  {
    return new InputJTextField.InputFieldDocument(this);
  }

  static int access$000(InputJTextField paramInputJTextField)
  {
    return paramInputJTextField.inputLimited; } 
  static int access$100(InputJTextField paramInputJTextField) { return paramInputJTextField.inputCase;
  }

  protected class InputFieldDocument extends PlainDocument
  {
    private final InputJTextField this$0;

    protected InputFieldDocument()
    {
      this.this$0 = paramInputJTextField;
    }

    public void insertString(, String paramString, AttributeSet paramAttributeSet) throws BadLocationException
    {
      char[] arrayOfChar1 = paramString.toCharArray();
      char[] arrayOfChar2 = new char[arrayOfChar1.length];

      int j = 0;

      for (int k = 0; k < arrayOfChar2.length; ++k)
      {
        int i;
        if ((InputJTextField.access$000(this.this$0) != -1) && 
          (getLength() + j == InputJTextField.access$000(this.this$0)))
          break;

        if ((InputJTextField.access$100(this.this$0) & InputJTextField.UPPERCASE) == InputJTextField.UPPERCASE)
          i = Character.toUpperCase(arrayOfChar1[k]);
        else if ((InputJTextField.access$100(this.this$0) & InputJTextField.LOWERCASE) == InputJTextField.LOWERCASE)
          i = Character.toLowerCase(arrayOfChar1[k]);
        else
          i = arrayOfChar1[k];

        if ((InputJTextField.access$100(this.this$0) & InputJTextField.NUMBERONLY) == InputJTextField.NUMBERONLY) {
          if ((arrayOfChar1[k] < '0') || (arrayOfChar1[k] > '9')) continue;
          i = arrayOfChar1[k];
        }

        arrayOfChar2[(j++)] = i;
      }

      insertString(paramInt, new String(arrayOfChar2, 0, j), paramAttributeSet);
    }
  }
}