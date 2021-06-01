package ArbolesB;
/**
 *
 * @author Autumn
 */
interface ArbolTemplate<K extends Comparable<K>, E> {

    boolean Vacio();

    Nodo<ParejaNodo<K, E>> getRaiz();

    int getTamanoArbol();

    int getAltura();

    String toString();
}
