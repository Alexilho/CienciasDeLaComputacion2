package Huffman;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
/**
 *
 * @author Autumn
 */
public class Test {

    public static void testHuffman(String entrada, boolean mostrar, String dot) {
        Huffman h = new Huffman(entrada, mostrar, dot);
        if (mostrar) {
            System.out.println("**** Frecuencia de caracteres ****\n");
            for(Map.Entry<Character, Integer> entry : h.Mwc.entrySet()){
                String key = entry.getKey().toString();
                int val = entry.getValue();
                if (key.equals("\n"))
                    key = "\\n";
                //System.out.println(key + " esta con " + val + "  veces");
            }
            System.out.println("\n El valor en caracter codificado es: \n");
            for (Map.Entry<Character, String> entry : h.Mcod.entrySet()) {
                String key = entry.getKey().toString();
                String val = entry.getValue();
                if (key.equals("\n"))
                    key = "\\n";
                System.out.println(key + ": " + val);
            }
        }

        String e = h.codificar();

        System.out.println("\n**** El texto codificado es ****\n");
        System.out.println(entrada);
        System.out.println(h.codificado());

        double sl = entrada.length() * 8;
        double el = e.length();
        System.out.println("**** Consumo del codificado ****\n");
        System.out.println("\n Sin comprimir: " + entrada.length() + " * 8 = " + (int) sl + " bits");
        System.out.println("\n Comprimido son = " + h.codificar().length() + " bits");
        double r = ((el - sl) / sl) * 100;

        System.out.println("\n El porcentaje comprimido es del: " + (100 - (-r)) + " %");
        System.out.println("\n El porcentaje de ahorro es del: " + (-r) + " %");
    }

    public void EntregarDatos(String campotexto) {
        boolean show = true;
        String dotFile = "C:/Users/Autumn/Desktop/grafica.dot";
        testHuffman(campotexto, show, dotFile);
    }

    public static void main(String[] args) {
        Ventana v = new Ventana();
    }

}
