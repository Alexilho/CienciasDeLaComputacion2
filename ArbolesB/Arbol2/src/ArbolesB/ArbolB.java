package ArbolesB;

import java.io.Serializable;
import java.util.LinkedList;
/**
 *
 * @author Autumn
 */
public class ArbolB<K extends Comparable<K>, E> implements ArbolTemplate, Serializable {
    private Nodo<ParejaNodo<K, E>> raiz = null;
    private int orden, in, tamanoarbol;
    private final int num;
    private final Nodo<ParejaNodo<K, E>> nodoarbol = new Nodo<ParejaNodo<K, E>>();
    
    public ArbolB(int orden) {
        if (orden < 3) {
            try {
                throw new Exception("");
            } catch (Exception e) {
                e.printStackTrace();
            }
            orden = 3;
        }
        this.orden = orden;
        num = (orden - 1) / 2;
    }
    public boolean Vacio() {
        return raiz == null;
    }
    public Nodo<ParejaNodo<K, E>> getRaiz() {
        return raiz;
    }
    public int getTamanoArbol() {
        return tamanoarbol;
    }
    public int getAltura() {
        if (Vacio()) {
            return 0;
        } else {
            return getAncho(raiz);
        }
    }
    private int getAncho(Nodo<ParejaNodo<K, E>> nodo) {
        int a = 0;
        Nodo<ParejaNodo<K, E>> currentNode = nodo;
        while (!currentNode.equals(nodoarbol)) {
            currentNode = currentNode.getHijo(0);
            a++;
        }
        return a;
    }
    public ParejaNodo<K, E> get(K llave) {
        Nodo<ParejaNodo<K, E>> nodo = getNodo(llave);
        if (nodo != nodoarbol) {
            return nodo.getLlave(in);
        } else {
            return null;
        }
    }
    private Nodo<ParejaNodo<K, E>> getNodo(K llave) {
        if (Vacio()) {
            return nodoarbol;
        }
        Nodo<ParejaNodo<K, E>> cnodo = raiz;
        while (!cnodo.equals(nodoarbol)) {
            int i = 0;
            while (i < cnodo.getTamaño()) {
                if (cnodo.getLlave(i).primero.equals(llave)) {
                    in = i;
                    return cnodo;
                } else if (cnodo.getLlave(i).primero.compareTo(llave) > 0) {
                    cnodo = cnodo.getHijo(i);
                    i = 0;
                } else {
                    i++;
                }
            }
            if (!cnodo.Vacio()) {
                cnodo = cnodo.getHijo(cnodo.getTamaño());
            }
        }
        return nodoarbol;
    }
    public void Reemplazar(K llave, E elemento) {
        ParejaNodo<K, E> pair = new ParejaNodo<K, E>(llave, elemento);
        Nodo<ParejaNodo<K, E>> cNodo = raiz;

        if (get(pair.primero) == null) {
            return;
        }
        while (!cNodo.equals(nodoarbol)) {
            int i = 0;
            while (i < cNodo.getTamaño()) {
                if (cNodo.getLlave(i).primero.equals(pair.primero)) {
                    cNodo.getLlave(i).segundo = pair.segundo;
                    return;
                } else if (cNodo.getLlave(i).primero.compareTo(pair.primero) > 0) {
                    cNodo = cNodo.getHijo(i);
                    i = 0;
                } else {
                    i++;
                }
            }
            if (!cNodo.Vacio())
                cNodo = cNodo.getHijo(cNodo.getTamaño());
        }
    }
    private Nodo<ParejaNodo<K, E>> getLlaveMedio(ParejaNodo<K, E> p, Nodo<ParejaNodo<K, E>> fNodo) {
        int fnodos = fNodo.getTamaño();

        for (int i = 0; i < fnodos; i++) {
            if (fNodo.getLlave(i).primero.compareTo(p.primero) > 0) {
                fNodo.InsertarLlave(i, p);
                break;
            }
        }
        if (fnodos == fNodo.getTamaño())
            fNodo.InsertarLlave(fnodos, p);

        return getLlaveMitad(fNodo);
    }

    private Nodo<ParejaNodo<K, E>> getLlaveMitad(Nodo<ParejaNodo<K, E>> fNodo) {
        Nodo<ParejaNodo<K, E>> nNodo = new Nodo<ParejaNodo<K, E>>(orden);
        for (int i = 0; i < num; i++) {
            nNodo.InsertarLlave(i, fNodo.getLlave(0));
            fNodo.EliminarLlave(0);
        }
        return nNodo;
    }


    private Nodo<ParejaNodo<K, E>> getOtrasLlaves(Nodo<ParejaNodo<K, E>> hNodo) {
        Nodo<ParejaNodo<K, E>> nNodo = new Nodo<ParejaNodo<K, E>>(orden);
        int hNodos = hNodo.getTamaño();
        for (int i = 0; i < hNodos; i++) {
            if (i != 0) {
                nNodo.InsertarLlave(i - 1, hNodo.getLlave(1));
                hNodo.EliminarLlave(1);
            }
            nNodo.InsertarHijo(i, hNodo.getHijo(0));
            hNodo.EliminarHijo(0);
        }
        return nNodo;
    }


    private void MergePadre(Nodo<ParejaNodo<K, E>> childNode, int in) {
        childNode.getPadre().InsertarLlave(in, childNode.getLlave(0));
        childNode.getPadre().EliminarHijo(in);
        childNode.getPadre().InsertarHijo(in, childNode.getHijo(0));
        childNode.getPadre().InsertarHijo(in + 1, childNode.getHijo(1));
    }


    private void MergeNodoPadre(Nodo<ParejaNodo<K, E>> cnodo) {
        int fnodo = cnodo.getPadre().getTamaño();
        for (int i = 0; i < fnodo; i++) {
            if (cnodo.getPadre().getLlave(i).compareTo(cnodo.getLlave(0)) > 0) {
                MergePadre(cnodo, i);
                break;
            }
        }
        if (fnodo == cnodo.getPadre().getTamaño()) {
            MergePadre(cnodo, fnodo);
        }
        for (int i = 0; i <= cnodo.getPadre().getTamaño(); i++)
            cnodo.getPadre().getHijo(i).setPadre(cnodo.getPadre());
    }


    private void setNodoPadre(Nodo<ParejaNodo<K, E>> nodo) {
        for (int i = 0; i <= nodo.getTamaño(); i++)
            nodo.getHijo(i).setPadre(nodo);
    }


    private void OverFlow(Nodo<ParejaNodo<K, E>> cNodo) {
        Nodo<ParejaNodo<K, E>> nNodo = getLlaveMitad(cNodo);
        for (int i = 0; i <= nNodo.getTamaño(); i++) {
            nNodo.InsertarHijo(i, cNodo.getHijo(0));
            cNodo.EliminarHijo(0);
        }
        Nodo<ParejaNodo<K, E>> oNodo = getOtrasLlaves(cNodo);
        cNodo.InsertarHijo(0, nNodo);
        cNodo.InsertarHijo(1, oNodo);
        oNodo.setPadre(cNodo);
        nNodo.setPadre(cNodo);
        setNodoPadre(oNodo);
        setNodoPadre(nNodo);
    }


    public void Insertar(K llave, E elemento) {
        ParejaNodo<K, E> p = new ParejaNodo<K, E>(llave, elemento);
        if (Vacio()) {
            raiz = new Nodo<ParejaNodo<K, E>>(orden);
            raiz.InsertarLlave(0, p);
            tamanoarbol++;
            raiz.setPadre(nodoarbol);
            raiz.InsertarHijo(0, nodoarbol);
            raiz.InsertarHijo(1, nodoarbol);
            return;
        }

        Nodo<ParejaNodo<K, E>> cNodo = raiz;

        if (get(p.primero) != null) {
            Reemplazar(llave, elemento);
            return;
        }

        while (!cNodo.UltimoNodo()) {
            int i = 0;
            while (i < cNodo.getTamaño()) {
                if (cNodo.UltimoNodo()) {
                    i = cNodo.getTamaño();
                } else if (cNodo.getLlave(i).primero.compareTo(p.primero) > 0) {
                    cNodo = cNodo.getHijo(i);
                    i = 0;
                } else {
                    i++;
                }
            }
            if (!cNodo.UltimoNodo())
                cNodo = cNodo.getHijo(cNodo.getTamaño());
        }

        if (!cNodo.LLeno()) {
            int i = 0;
            while (i < cNodo.getTamaño()) {
                if (cNodo.getLlave(i).primero.compareTo(p.primero) > 0) {
                    cNodo.InsertarLlave(i, p);
                    cNodo.InsertarHijo(cNodo.getTamaño(), nodoarbol);
                    tamanoarbol++;
                    return;
                } else {
                    i++;
                }
            }
            cNodo.InsertarLlave(cNodo.getTamaño(), p);
            cNodo.InsertarHijo(cNodo.getTamaño(), nodoarbol);
            tamanoarbol++;
        } else {
            Nodo<ParejaNodo<K, E>> nNodohijo = getLlaveMedio(p, cNodo);
            for (int i = 0; i < num; i++) {
                nNodohijo.InsertarHijo(i, cNodo.getHijo(0));
                cNodo.EliminarHijo(0);
            }
            nNodohijo.InsertarHijo(num, nodoarbol);
            Nodo<ParejaNodo<K, E>> oNodopadre = getOtrasLlaves(cNodo);
            cNodo.InsertarHijo(0, nNodohijo);
            cNodo.InsertarHijo(1, oNodopadre);
            oNodopadre.setPadre(cNodo);
            nNodohijo.setPadre(cNodo);
            tamanoarbol++;

            if (!cNodo.getPadre().equals(nodoarbol)) {
                while (!cNodo.getPadre().Completo() && !cNodo.getPadre().equals(nodoarbol)) {
                    boolean flag = cNodo.getTamaño() == 1 && !cNodo.getPadre().Completo();
                    if (cNodo.Completo() || flag) {
                        MergeNodoPadre(cNodo);
                        cNodo = cNodo.getPadre();
                        if (cNodo.Completo()) {
                            OverFlow(cNodo);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }


    private int BuscarHijo(Nodo<ParejaNodo<K, E>> nodo) {
        if (!nodo.equals(raiz)) {
            Nodo<ParejaNodo<K, E>> nodopadre = nodo.getPadre();

            for (int i = 0; i <= nodopadre.getTamaño(); i++) {
                if (nodopadre.getHijo(i).equals(nodo))
                    return i;
            }
        }
        return -1;
    }

 
    private Nodo<ParejaNodo<K, E>> Balancear(Nodo<ParejaNodo<K, E>> nodo) {
        boolean flag;
        int nodeIndex = BuscarHijo(nodo);
        ParejaNodo<K, E> pair;
        Nodo<ParejaNodo<K, E>> pNodo = nodo.getPadre();
        Nodo<ParejaNodo<K, E>> nNodo;
        if (nodeIndex == 0) {
            nNodo = pNodo.getHijo(1);
            flag = true;
        } else {
            nNodo = pNodo.getHijo(nodeIndex - 1);
            flag = false;
        }

        int currentSize = nNodo.getTamaño();
        if (currentSize > num) {
            if (flag) {
                pair = pNodo.getLlave(0);
                nodo.InsertarLlave(nodo.getTamaño(), pair);
                pNodo.EliminarLlave(0);
                pair = nNodo.getLlave(0);
                nNodo.EliminarLlave(0);
                nodo.InsertarHijo(nodo.getTamaño(), nNodo.getHijo(0));
                nNodo.EliminarHijo(0);
                pNodo.InsertarLlave(0, pair);
                if (nodo.UltimoNodo()) {
                    nodo.EliminarHijo(0);
                }
            } else {
                pair = pNodo.getLlave(nodeIndex - 1);
                nodo.InsertarLlave(0, pair);
                pNodo.EliminarLlave(nodeIndex - 1);
                pair = nNodo.getLlave(currentSize - 1);
                nNodo.EliminarLlave(currentSize - 1);
                nodo.InsertarHijo(0, nNodo.getHijo(currentSize));
                nNodo.EliminarHijo(currentSize);
                pNodo.InsertarLlave(nodeIndex - 1, pair);
                if (nodo.UltimoNodo()) {
                    nodo.EliminarHijo(0);
                }
            }
            return nodo;
        } else {
            if (flag) {
                nNodo.InsertarLlave(0, pNodo.getLlave(0));
                pNodo.EliminarLlave(0);
                pNodo.EliminarHijo(0);
                if (raiz.getTamaño() == 0) {
                    raiz = nNodo;
                    nNodo.setPadre(nodoarbol);
                }
                if (nodo.getTamaño() == 0) {
                    nNodo.InsertarHijo(0, nodo.getHijo(0));
                    nNodo.getHijo(0).setPadre(nNodo);
                }
                for (int i = 0; i < nodo.getTamaño(); i++) {
                    nNodo.InsertarLlave(i, nodo.getLlave(i));
                    nNodo.InsertarHijo(i, nodo.getHijo(i));
                    nNodo.getHijo(i).setPadre(nNodo);
                }
            } else {
                nNodo.InsertarLlave(nNodo.getTamaño(), pNodo.getLlave(nodeIndex - 1));
                pNodo.EliminarLlave(nodeIndex - 1);
                pNodo.EliminarHijo(nodeIndex);
                if (raiz.getTamaño() == 0) {
                    raiz = nNodo;
                    nNodo.setPadre(nodoarbol);
                }
                int currentNodeSize = nNodo.getTamaño();
                if (nodo.getTamaño() == 0) {
                    nNodo.InsertarHijo(currentNodeSize, nodo.getHijo(0));
                    nNodo.getHijo(currentNodeSize).setPadre(nNodo);
                }
                for (int i = 0; i < nodo.getTamaño(); i++) {
                    nNodo.InsertarLlave(currentNodeSize + i, nodo.getLlave(i));
                    nNodo.InsertarHijo(currentNodeSize + i, nodo.getHijo(i));
                    nNodo.getHijo(currentNodeSize + i).setPadre(nNodo);
                }
            }
            return pNodo;
        }
    }


    private Nodo<ParejaNodo<K, E>> ReemplazarNodo(Nodo<ParejaNodo<K, E>> nodo) {
        Nodo<ParejaNodo<K, E>> cNodo = nodo.getHijo(in + 1);
        while (!cNodo.UltimoNodo()) {
            cNodo = cNodo.getHijo(0);
        }
        if (cNodo.getTamaño() - 1 < num) {
            cNodo = nodo.getHijo(in);
            int currentNodeSize = cNodo.getTamaño();
            while (!cNodo.UltimoNodo()) {
                cNodo = cNodo.getHijo(currentNodeSize);
            }
            nodo.InsertarLlave(in, cNodo.getLlave(currentNodeSize - 1));
            cNodo.EliminarLlave(currentNodeSize - 1);
            cNodo.InsertarLlave(currentNodeSize - 1, nodo.getLlave(in + 1));
            nodo.EliminarLlave(in + 1);
            in = cNodo.getTamaño() - 1;
        } else {
            nodo.InsertarLlave(in + 1, cNodo.getLlave(0));
            cNodo.EliminarLlave(0);
            cNodo.InsertarLlave(0, nodo.getLlave(in));
            nodo.EliminarLlave(in);
            in = 0;
        }
        return cNodo;
    }


    public void Eliminar(K llave) {
        Nodo<ParejaNodo<K, E>> nodo = getNodo(llave);
        Nodo<ParejaNodo<K, E>> eliminarnodo = null;
        if (nodo.equals(nodoarbol))
            return;
        if (nodo.equals(raiz) && nodo.getTamaño() == 1 && nodo.UltimoNodo()) {
            raiz = null;
            tamanoarbol--;
        } else {
            boolean flag = true;
            boolean isReplaced = false;
            if (!nodo.UltimoNodo()) {
                nodo = ReemplazarNodo(nodo);
                eliminarnodo = nodo;
                isReplaced = true;
            }

            if (nodo.getTamaño() - 1 < num) {
                nodo = Balancear(nodo);
                if (isReplaced) {
                    for (int i = 0; i <= nodo.getTamaño(); i++) {
                        for (int j = 0; i < nodo.getHijo(i).getTamaño(); j++) {
                            if (nodo.getHijo(i).getLlave(j).primero.equals(llave)) {
                                eliminarnodo = nodo.getHijo(i);
                                break;
                            }
                        }
                    }
                }
            } else if (nodo.UltimoNodo()) {
                nodo.EliminarHijo(0);
            }
            while (!nodo.getHijo(0).equals(raiz) && nodo.getTamaño() < num && flag) {
                if (nodo.equals(raiz)) {
                    for (int i = 0; i <= raiz.getTamaño(); i++) {
                        if (raiz.getHijo(i).getTamaño() == 0) {
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    nodo = Balancear(nodo);
                }
            }
            if (eliminarnodo == null) {
                nodo = getNodo(llave);
            } else {
                nodo = eliminarnodo;
            }
            if (!nodo.equals(nodoarbol)) {
                for (int i = 0; i < nodo.getTamaño(); i++) {
                    if (nodo.getLlave(i).primero == llave) {
                        nodo.EliminarLlave(i);
                    }
                }
                tamanoarbol--;
            }
        }
    }


    public String toString() {
        if (Vacio())
            return "nodo nulo";
        StringBuilder sb = new StringBuilder();
        int h = getAltura();

        LinkedList<Nodo<ParejaNodo<K, E>>> q = new LinkedList<Nodo<ParejaNodo<K, E>>>();
        q.push(raiz);

        Nodo<ParejaNodo<K, E>> temp;
        while ((temp = q.poll()) != null) {
            for (int i = 0; i <= temp.getTamaño(); i++) {
                if (!temp.getHijo(i).Vacio())
                    q.offer(temp.getHijo(i));
            }
            sb.append("[Nivel: ").append(h - getAncho(temp)).append("] ");
            sb.append(temp.toString()).append("\n");
        }
        return sb.toString();
    }
}
