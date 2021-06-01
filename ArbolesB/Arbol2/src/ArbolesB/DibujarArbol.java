package ArbolesB;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
/**
 *
 * @author Autumn
 */
public class DibujarArbol extends JFrame {

    private int llave;
    private Ventana ventana;
    private JTextField texto = new JTextField(10);
    private JTextField elemento = new JTextField(10);
    private int in = 0;
    private LinkedList<ArbolB<Integer, Double>> listaarbol = new LinkedList<ArbolB<Integer, Double>>();
    private ArbolB<Integer, Double> arbolb;

    public DibujarArbol(ArbolB<Integer, Double> arbol) {
        super("A R B O L E S   B");
        arbolb = arbol;
        final int altura = 720;
        final int ancho = 1024;
        ventana = new Ventana(ancho, altura, arbolb);
        listaarbol.add(Listas.List(arbolb));

        JButton insertar = new JButton("Insertar");
        JButton eliminar = new JButton("Eliminar");
        JLabel keyPrompt = new JLabel("Digita un numero: ");
        elemento.setText("0.0");
        Valido();
        JPanel contentPanel = new JPanel();
        JPanel controlPanel = new JPanel();
        JPanel menuPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        controlPanel.setLayout(new BorderLayout());
        menuPanel.setLayout(new FlowLayout());
        menuPanel.add(keyPrompt);
        menuPanel.add(texto);
        menuPanel.add(insertar);
        menuPanel.add(eliminar);
        controlPanel.add(menuPanel, BorderLayout.CENTER);
        contentPanel.add(controlPanel, BorderLayout.NORTH);
        contentPanel.add(ventana);
        setContentPane(contentPanel);
        insertar.addActionListener(e -> IngresarValor());
        eliminar.addActionListener(e -> EliminarValor());

        setSize(ancho, altura);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void Valido() {
        if (in > 0 && in < listaarbol.size() - 1) {
        } else if (in > 0 && in == listaarbol.size() - 1) {
        } else if (in == 0 && in < listaarbol.size() - 1) {
        } else {
        }
    }

    private void EliminarLista() {
        for (int i = listaarbol.size() - 1; i >= in; i--) {
            listaarbol.removeLast();
        }
    }

    private void IngresarValor() {
        try {
            llave = Integer.parseInt(texto.getText());
            double element = Double.parseDouble(elemento.getText());
            texto.setText("");
            elemento.setText("0.0");
            if (in < listaarbol.size() - 1) {
                EliminarLista();
                listaarbol.add(Listas.List(arbolb));
            }
            arbolb.Insertar(llave, element);
            listaarbol.add(Listas.List(arbolb));
            in = listaarbol.size() - 1;
            Valido();
            ventana.updateCanvas(arbolb);
        } catch (NumberFormatException e) {
            System.out.println("No se puede eliminar nada");
        }
    }

    private void EliminarValor() {
        try {
            llave = Integer.parseInt(texto.getText());
            texto.setText("");
            elemento.setText("0.0");
            if (in < listaarbol.size() - 1) {
                EliminarLista();
                listaarbol.add(Listas.List(arbolb));
            }
            arbolb.Eliminar(llave);
            listaarbol.add(Listas.List(arbolb));
            in = listaarbol.size() - 1;
            Valido();
            ventana.updateCanvas(arbolb);
        } catch (NumberFormatException e) {
        }
    }
}
