package ArbolesB;

import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * @author Autumn
 */
class Nodo<E extends Comparable<E>> implements Serializable {

    private int numero;
    private Nodo<E> padre;
    private ArrayList<Nodo<E>> hijo = new ArrayList<Nodo<E>>();
    private ArrayList<E> llaves = new ArrayList<>();

    Nodo() {
    }
    Nodo(int orden) {
        numero = orden - 1;
    }
    boolean UltimoNodo() {
        if (llaves.size() == 0) {
            return false;
        }
        for (Nodo<E> node : hijo) {
            if (node.llaves.size() != 0) {
                return false;
            }
        }
        return true;
    }

    Nodo<E> getPadre() {
        return padre;
    }

    void setPadre(Nodo<E> father) {
        this.padre = father;
    }

    Nodo<E> getHijo(int index) {
        return hijo.get(index);
    }

    void InsertarHijo(int index, Nodo<E> node) {
        hijo.add(index, node);
    }

    void EliminarHijo(int index) {
        hijo.remove(index);
    }

    E getLlave(int index) {
        return llaves.get(index);
    }

    void InsertarLlave(int index, E element) {
        llaves.add(index, element);
    }

    void EliminarLlave(int index) {
        llaves.remove(index);
    }

    boolean LLeno() {
        return numero == llaves.size();
    }

    boolean Completo() {
        return numero < llaves.size();
    }

    boolean Vacio() {
        return llaves.isEmpty();
    }

    int getTamaño() {
        return llaves.size();
    }

    public String toString() {
        if (llaves.size() == 0) {
            return "No hay nodos";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[Numero: ").append(llaves.size()).append("] [valor: ");
        for (E e : llaves) {
            sb.append(e).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("] [Padre: ");
        if (padre.llaves.size() == 0) {
            sb.append("No hay nodo padre");
        } else {
            for (E e : padre.llaves) {
                sb.append(e).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("] [Hijo: ");
        for (Nodo<E> node : hijo) {
            if (node.getTamaño() == 0) {
                sb.append(node).append(", ");
            } else {
                sb.append("No hay nodos hijos" + ", ");
            }
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("] [TamañoHijo: ").append(hijo.size()).append("]");
        return sb.toString();
    }
}
