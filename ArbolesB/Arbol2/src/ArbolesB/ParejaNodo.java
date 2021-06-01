package ArbolesB;

import java.io.Serializable;
/**
 *
 * @author Autumn
 */
class ParejaNodo<A extends Comparable<A>, B> implements Comparable<ParejaNodo<A, B>>, Serializable {

    A primero;
    B segundo;

    ParejaNodo(A a, B b) {
        primero = a;
        segundo = b;
    }

    public String toString() {
        if (primero == null || segundo == null)
            return "(null, null)";
        return "(" + primero.toString() +" "+ segundo.toString() + ")";
    }

    @Override
    public int compareTo(ParejaNodo<A, B> o) {
        return primero.compareTo(o.primero);
    }
}