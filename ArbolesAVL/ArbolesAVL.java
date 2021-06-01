import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

class ArbolAVL {

    class Nodo {

        int N;
        int altura = 0;
        Nodo padre = null, izquierda = null, derecha = null;

        Nodo(final int n, final int h, final Nodo P) {
            N = n;
            altura = h;
            padre = P;
        }
    }

    Nodo raiz = null;

    void insertar(final int N, final Nodo R) {
        if (raiz == null) {
            raiz = new Nodo(N, 1, null);
        } else {
            if (N <= R.N) {
                if (R.izquierda == null) {
                    R.izquierda = new Nodo(N, 1, R);
                    balancear(R.izquierda);
                } else {
                    insertar(N, R.izquierda);
                }
            } else {
                if (R.derecha == null) {
                    R.derecha = new Nodo(N, 1, R);
                    balancear(R.derecha);
                } else {
                    insertar(N, R.derecha);
                }
            }
        }
    }

    void remover(final Nodo x) {
        Nodo y, z = null;
        if (x.izquierda == null || x.derecha == null) {
            y = x;
        } else { // encontrar sucesor
            Nodo temp = x;
            for (y = temp.padre; y != null && temp == y.derecha; y = y.padre) {
                temp = y;
            }
        } // ambas ramas o ninguna

        // y almenos un hijo
        if (y.izquierda != null) {
            z = y.izquierda;
        } else {
            z = y.derecha;
        }

        if (z != null) {
            z.padre = y.padre;
        }

        if (y.padre == null) // z era raiz
        {
            raiz = z;
        } else { // remover y
            if (y == y.padre.izquierda) // y izquierda
            {
                y.padre.izquierda = z;
            } else {
                y.padre.derecha = z; // y derecha
            }
        }
        x.N = y.N; // moviendo y a x
        y.altura = 0;

        do {
            y = balancear(y);
        } while (y != null); // balancear hasta raiz
    }

    /**
     * reorganizar arbol
     */
    Nodo balancear(Nodo x) {
        int hijo = 0, hija = 0; // alturas de nodos hermanos
        Nodo n1 = null, rent = null;
        Nodo y = x, z;
        while (y.padre != null) {
            if (y.padre.izquierda == y) {
                n1 = y.padre.derecha;
            } else if (y.padre.derecha == y) {
                n1 = y.padre.izquierda;
            }
            hijo = y.altura;
            if (n1 == null) {
                hija = 0;
            } else {
                hija = n1.altura;
            }
            if (Math.abs(hija - hijo) > 1) {
                break;
            }
            y.padre.altura = 1 + Math.max(hijo, hija);
            y = y.padre;
        }

        if (y.padre == null) {
            return null;
        }

        z = y.padre;
        rent = z;
        /*
         * enlaza z a y e y con x
         */
        hijo = (z.izquierda == null) ? 0 : z.izquierda.altura;
        hija = (z.derecha == null) ? 0 : z.derecha.altura;
        if (hijo < hija) {
            y = z.derecha;
        } else {
            y = z.izquierda;
        }

        hijo = (y.izquierda == null) ? 0 : y.izquierda.altura;
        hija = (y.derecha == null) ? 0 : y.derecha.altura;
        if (hijo < hija) {
            x = y.derecha;
        } else {
            x = y.izquierda;
        }

        y.padre = z;
        x.padre = y;

        // rotaciones a realizar
        if (z.izquierda == y) {
            if (y.izquierda == x) {
                Derrotacion(y, z); // rotar izquierda
            } else {
                Izrotacion(x, y); // doble rotar izquierda
                x.altura++;
                Derrotacion(x, z);
            }
        } else {
            if (y.derecha == x) {
                Izrotacion(y, z); // rotar derecha
            } else {
                Derrotacion(x, y); // doble rotar derecha
                x.altura++;
                Izrotacion(x, z);
            }
        }

        return rent;
    }

    /**
     * rotacion derecha
     */
    void Derrotacion(final Nodo y, final Nodo z) {
        // enlace de y con el padre de z
        y.padre = z.padre;
        if (y.padre == null) {
            raiz = y;
        } else if (y.padre.izquierda == z) {
            y.padre.izquierda = y;
        } else {
            y.padre.derecha = y;
        }

        // rotar y i z
        z.izquierda = y.derecha;
        if (z.izquierda != null) {
            z.izquierda.padre = z;
        }
        y.derecha = z;
        z.padre = y;
        z.altura--;
    }

    /**
     * rotacion izquierda
     */
    void Izrotacion(final Nodo y, final Nodo z) {
        // enlace de y con el padre de z
        y.padre = z.padre;
        if (z.padre == null) {
            raiz = y;
        } else if (z.padre.izquierda == z) {
            y.padre.izquierda = y;
        } else {
            y.padre.derecha = y;
        }

        // rotar x y y
        z.derecha = y.izquierda;
        if (z.derecha != null) {
            z.derecha.padre = z;
        }
        y.izquierda = z;
        z.padre = y;
        z.altura--;
    }

    /**
     * buscar un nodo
     */
    Nodo buscar(final int N, final Nodo r) {
        if (r == null) {
            return null;
        }
        if (N == r.N) {
            return r;
        } else if (N < r.N) {
            return buscar(N, r.izquierda);
        } else {
            return buscar(N, r.derecha);
        }
    }

    static ArbolAVL Arbol;

    public static void main(final String[] args) {
        Arbol = new ArbolAVL();
        new Main();
    }

    /**
     * parte grafica
     */
    static class Main extends JFrame implements ActionListener {

        private static final long serialVersionUID = -2829448395694197965L;

        public Main() {
            this.setSize(300, 200);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.setTitle("A R B O L  A V L");
            panel1 = new JPanel();
            button0 = new JButton("Insertar nodo");
            button1 = new JButton("Eliminar nodo");
            button2 = new JButton("Mostrar arbol");

            button0.addActionListener(this);
            button1.addActionListener(this);
            button2.addActionListener(this);

            panel1.add(button0);
            panel1.add(button1);
            panel1.add(button2);

            final Border b = BorderFactory.createEmptyBorder(35, 40, 0, 40);
            panel1.setBorder(b);

            this.add(panel1);
            this.setVisible(true);
        }

        JPanel panel1;
        private final JButton button0, button1, button2;

        public void actionPerformed(final ActionEvent e) {
            if (e.getSource() == button0) {
                final String s = JOptionPane.showInputDialog("Ingresar el valor");
                final int i = Integer.parseInt(s);
                Arbol.insertar(i, Arbol.raiz);
            } else if (e.getSource() == button1) {
                final String s = JOptionPane.showInputDialog("Ingresar el valor");
                final int i = Integer.parseInt(s);
                final Nodo temp = Arbol.buscar(i, Arbol.raiz);
                if (temp == null) {
                    JOptionPane.showMessageDialog(null, "No se encuentra");
                } else {
                    Arbol.remover(temp);
                    JOptionPane.showMessageDialog(null, "Eliminado");
                }
            } else if (e.getSource() == button2) {
                final JFrame f = new JFrame("Arbol AVL");
                f.addWindowListener(new WindowAdapter() {
                    public void windowClosing(final WindowEvent e) {
                    }
                });
                final Drawtree applet = new Drawtree();
                f.getContentPane().add("Center", applet);
                final Toolkit tk = Toolkit.getDefaultToolkit();
                final int xSize = ((int) tk.getScreenSize().getWidth());
                final int ySize = ((int) tk.getScreenSize().getHeight());
                applet.init(Arbol.raiz, xSize - 0);
                f.pack();
                f.setSize(new Dimension(xSize, ySize));
                f.setVisible(true);
            }
        }
    }

    /**
     * Generar dibujo
     */
    static class Drawtree extends JApplet {

        private static final long serialVersionUID = -7654352523443329890L;
        final Color bg = Color.white;
        final BasicStroke stroke = new BasicStroke(2.0f);
        final BasicStroke wideStroke = new BasicStroke(8.0f);

        Dimension totalSize;
        int height, width;
        Nodo r = null;

        public void init(final Nodo N, final int x) {
            setBackground(bg);
            r = N;
            width = x;
        }

        Graphics2D g2;

        public void paint(final Graphics g) {
            g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            getSize();
            inorden(r, 10, width, 40);
        }

        public void draw(final int x1, final int x2, final int y, final String num, final int d) {
            g2.setStroke(stroke);

            g2.setPaint(Color.black);
            final int x = (x1 + x2) / 2;
            if (d == 1) {
                g2.draw(new Line2D.Double(x2, y - 30, x + 15, y));
            } else if (d == 2) {
                g2.draw(new Line2D.Double(x + 15, y, x1 + 30, y - 30));
            }
            g2.setPaint(Color.gray);
            final Shape circle = new Ellipse2D.Double((x1 + x2) / 2, y, 30, 30);
            g2.draw(circle);
            g2.setPaint(Color.black);
            g2.drawString(num, x + 12, y + 20);
        }

        int x1 = 50, y1 = 50;

        void inorden(final Nodo r, final int x1, final int x2, final int y) {
            if (r == null) {
                return;
            }

            inorden(r.izquierda, x1, (x1 + x2) / 2, y + 45);
            if (r.padre == null) {
                draw(x1, x2, y, r.N + "", 0);
            } else {
                if (r.padre.N < r.N) {
                    draw(x1, x2, y, r.N + "", 2);
                } else {
                    draw(x1, x2, y, r.N + "", 1);
                }
            }
            inorden(r.derecha, (x1 + x2) / 2, x2, y + 45);
        }
    }

}
