package Huffman;

import java.io.PrintStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class Ventana extends JFrame {

    public Ventana() {
        /**
         * Crea los elementos de la ventana
         */
        JTextArea areatexto = new JTextArea();
        JLabel label = new JLabel("Ingresa el texto a codificar: ");
        JTextField texto = new JTextField();
        JButton boton = new JButton();
        JScrollPane scroll = new JScrollPane(areatexto);

        /**
         * Creando la ventana
         */
        this.setSize(550, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Huffman");
        label.setBounds(10, 10, 180, 30);
        texto = new JTextField();
        texto.setBounds(200, 10, 250, 30);
        boton = new JButton("Codificar");
        boton.setBounds(10, 50, 100, 30);
        areatexto.setBounds(10, 100, 500, 1200);
        //areatexto.setBounds(10, 100, 400, 250);
        areatexto.setEditable(false);
        this.setResizable(true);
        this.setLayout(null);
        /**
         * Añaden el controlador
         */

        Controlador controlador = new Controlador(texto, areatexto);
        boton.addActionListener(controlador);
        boton.setActionCommand("ejecutar");
        this.add(texto);
        this.add(label);
        this.add(boton);
        this.add(areatexto);
        //this.add(scroll);
        PrintStream print = new PrintStream(new CustomOutputStream(areatexto));
        System.setOut(print);
        System.setErr(print);
        this.setVisible(true);
    }
}
