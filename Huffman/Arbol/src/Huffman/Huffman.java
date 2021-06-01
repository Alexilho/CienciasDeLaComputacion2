package Huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;

public class Huffman {

    private String entrada, codificado;
    public HashMap<Character, Integer> Mwc;  // recuento ocurrencia
    public HashMap<Character, String> Mcod; // caracter
    public HashMap<String, Character> Mcor; // codigo caracter
    private PriorityQueue<nodo> pq;  // MinHeap
    private int contador;  // Unique id assigned to each node
    private int tamañoarbol;  // # of total nodes in the tree
    private nodo raiz;

    // Inner class
    private class nodo {

        int a, peso;
        char ch;
        nodo izquierda, derecha;

        private nodo(Character ch, Integer peso, nodo izquierda, nodo derecha) {
            a = ++contador;
            this.peso = peso;
            this.ch = ch;
            this.izquierda = izquierda;
            this.derecha = derecha;
        }
    }

    private boolean Hoja(nodo n) {
        return (n.izquierda == null) && (n.derecha == null);
    }

    private void Contar() {
        Character ch;
        Integer valor;
        for (int i = 0; i < entrada.length(); i++) {
            ch = new Character(entrada.charAt(i));
            if (Mwc.containsKey(ch) == false) {
                valor = new Integer(1);
            } else {
                valor = Mwc.get(ch) + 1;
            }
            Mwc.put(ch, valor);
        }
    }

    private void ConstruirArbol() {
        ConstruirHashMap();  // Set all leaf nodes into MinHeap
        nodo left, right;
        while (!pq.isEmpty()) {
            left = pq.poll();
            tamañoarbol++;
            if (pq.peek() != null) {
                right = pq.poll();
                tamañoarbol++;
                raiz = new nodo('\0', left.peso + right.peso, left, right);
            } else {  // only left child. right=null
                raiz = new nodo('\0', left.peso, left, null);
            }

            if (pq.peek() != null) {
                pq.offer(raiz);
            } else {  // = Top root. Finished building the tree.
                tamañoarbol++;
                break;
            }
        }
    }

    private void Escribirdot(String fname) {
        if (tamañoarbol > 1) {
            nodo n = raiz;
            try (PrintWriter o = new PrintWriter(new BufferedWriter(new FileWriter(fname)))) {
                o.println("## Command to generate pdf:  dot -Tpdf test.dot -o test.pdf");
                o.println("digraph g {");
                dotRecursivo(n, o);  // Recursion
                o.println("}");
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private void ConstruirTabla() {
        String cod = "";
        nodo n = raiz;
        ConstruirRecursion(n, cod);  // Recursivo
    }

    public Huffman(String orgStr, boolean show, String dot) {
        this.contador = 0;
        this.tamañoarbol = 0;
        this.entrada = orgStr;
        Mwc = new HashMap<Character, Integer>();
        Mcod = new HashMap<Character, String>();
        Mcor = new HashMap<String, Character>();
        pq = new PriorityQueue<nodo>(1, new Comparator<nodo>() {
            @Override
            public int compare(nodo n1, nodo n2) {
                if (n1.peso < n2.peso) {
                    return -1;
                } else if (n1.peso > n2.peso) {
                    return 1;
                }
                return 1;
            }
        });
        Contar();  // STEP 1: Count frequency of word
        ConstruirArbol();  // STEP 2: Build Huffman Tree
        Escribirdot(dot);  // STEP 3: Write .dot file to visualize the tree with Graphviz software
        ConstruirTabla();  // STEP 4: Build Huffman Code Table
    }

    private void ConstruirRecursion(nodo n, String cod) { //organiza recursivamente
        if (n != null) {
            if (!Hoja(n)) {  // n = nodo interno
                ConstruirRecursion(n.izquierda, cod + '0');
                ConstruirRecursion(n.derecha, cod + '1');
            } else {  // n = nodo Hoja
                Mcod.put(n.ch, cod);
                Mcor.put(cod, n.ch);
            }
        }
    }

    private void dotRecursivo(nodo n, PrintWriter o) {
        if (!Hoja(n)) {
            if (n.izquierda != null) {  // Hijo de la izquierda
                String t = "";
                char c = n.izquierda.ch;
                if (c != '\0' && c != ' ' && c != '"' && c != '\n') {
                    t = "\\n " + c;
                } else if (c == ' ') {
                    t = "\\n blank";
                } else if (c == '"') //escape 
                {
                    t = "\\n \\\"";
                } else if (c == '\n') {
                    t = "\\n /n";
                }
                o.println(" \"" + n.a + "\\n" + n.peso + "\" -> \"" + n.izquierda.a + "\\n" + n.izquierda.peso + t + "\" [color=red, label=0]");
                dotRecursivo(n.izquierda, o);
            }
            if (n.derecha != null) { // hijo de la derecha
                String t = "";
                char c = n.derecha.ch;
                if (c != '\0' && c != ' ' && c != '"' && c != '\n') {
                    t = "\\n " + c;
                } else if (c == ' ') {
                    t = "\\n blank";
                } else if (c == '"') //escape
                {
                    t = "\\n \\\"";
                } else if (c == '\n') {
                    t = "\\n /n";
                }
                o.println(" \"" + n.a + "\\" + "n" + n.peso + "\" -> \"" + n.derecha.a + "\\n" + n.derecha.peso + t + "\" [color=blue, label=1]");
                dotRecursivo(n.derecha, o);
            }
        }
    }

    private void ConstruirHashMap() {
        for (Map.Entry<Character, Integer> entry : Mwc.entrySet()) {
            Character ch = entry.getKey();
            Integer peso = entry.getValue();
            nodo n = new nodo(ch, peso, null, null);
            pq.offer(n);
        }
    }

    public String codificar() {
        StringBuilder sb = new StringBuilder();
        Character ch;
        for (int i = 0; i < entrada.length(); i++) {
            ch = entrada.charAt(i);
            sb.append(Mcod.get(ch));
        }
        codificado = sb.toString();
        return codificado;
    }

    public String codificado() {
        StringBuilder sb = new StringBuilder();
        Character ch;
        for (int i = 0; i < entrada.length(); i++) {
            ch = entrada.charAt(i);
            sb.append(Mcod.get(ch)+" ");
        }
        codificado = sb.toString();
        return codificado;
    }

}
