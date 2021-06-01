package ArbolesB;
import ArbolesB.ArbolB;
import ArbolesB.DibujarArbol;
/**
 *
 * @author Autumn
 */
public class Main {
    public static void main(String[] args) {
        ArbolB<Integer, Double> arbol = new ArbolB<Integer, Double>(3);
        new DibujarArbol(arbol);
    }
}
