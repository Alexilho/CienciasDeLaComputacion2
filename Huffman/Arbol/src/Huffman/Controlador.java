package Huffman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 *
 * @author Autumn
 */
public class Controlador implements ActionListener {
    JTextField campotexto;
    JTextArea area;
    public Controlador(JTextField texto, JTextArea areatexto) {
        this.campotexto = texto;
        this.area = areatexto;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Test t = new Test();
        switch (e.getActionCommand()) {
            case "ejecutar":
                area.setText("");
                t.EntregarDatos(campotexto.getText());
        }
    }
}
