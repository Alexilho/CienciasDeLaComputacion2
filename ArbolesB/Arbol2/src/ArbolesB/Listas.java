package ArbolesB;
import java.io.*;
/**
 *
 * @author Autumn
 */
class Listas {
    @SuppressWarnings("")
    static <T extends Serializable> T List(T object) {
        T cloneObject = null;
        try {
            ByteArrayOutputStream Listasalida = new ByteArrayOutputStream();
            ObjectOutputStream Objetosalida = new ObjectOutputStream(Listasalida);
            Objetosalida.writeObject(object);
            Objetosalida.close();

            ByteArrayInputStream ListaEntrada = new ByteArrayInputStream(Listasalida.toByteArray());
            ObjectInputStream Objetoentrada = new ObjectInputStream(ListaEntrada);
            cloneObject = (T) Objetoentrada.readObject();
            Objetoentrada.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObject;
    }
}
