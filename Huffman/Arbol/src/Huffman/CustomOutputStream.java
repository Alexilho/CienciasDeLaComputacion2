
package Huffman;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;   
/**
 *
 * @author Autumn
 */
public class CustomOutputStream extends OutputStream {
    
    private JTextArea textarea;
    
    public CustomOutputStream(JTextArea areatexto){
        this.textarea = areatexto;
    }

    @Override
    public void write(int b) throws IOException {
        textarea.append(String.valueOf((char)b));
        textarea.setCaretPosition(textarea.getDocument().getLength());
        textarea.update(textarea.getGraphics());
    }
    
}
