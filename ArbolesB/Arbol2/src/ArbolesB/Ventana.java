package ArbolesB;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;


class Ventana extends Canvas {
    private ArbolB<Integer, Double> arbolB;
    private int alto, ancho;
    private int fuente = 12;
    private int rectangulo = 35;

    Ventana(int alto, int ancho, ArbolB<Integer, Double> arbolB) {
        setBackground(Color.white);
        this.alto = alto;
        this.ancho = ancho;
        setSize(alto, ancho);
        this.arbolB = arbolB;
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("SimHei", Font.BOLD, 16));
        DibujarArbol(g);
    }

    void updateCanvas(ArbolB<Integer, Double> bTree) {
        this.arbolB = bTree;
        this.repaint();
    }

    private void DibujarNodo(Graphics g, String s, int x, int y) {
        String firstString = s.substring(1, s.length() / 2);
        g.setFont(new Font("SimHei", Font.BOLD, fuente));
        g.drawString(firstString, x + 15, y + 22);
        g.drawRect(x, y, rectangulo, 3 *fuente);
        g.drawOval(x, y, rectangulo,3* fuente);
    }

    private void DibujarArbol(Graphics g) {
        Nodo<ParejaNodo<Integer, Double>> root = arbolB.getRaiz();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (root != null) {
            int lastSize = 0, keySize = 0;

            LinkedList<Nodo<ParejaNodo<Integer, Double>>> queue = new LinkedList<Nodo<ParejaNodo<Integer, Double>>>();
            LinkedList<Nodo<ParejaNodo<Integer, Double>>> treeNodes = new LinkedList<Nodo<ParejaNodo<Integer, Double>>>();
            LinkedList<Integer> nodeSize = new LinkedList<>();
            LinkedList<Integer> lastNodeSize = new LinkedList<>();
            LinkedList<Integer> tempLastSize = new LinkedList<>();
            LinkedList<Integer> lastX = new LinkedList<>();
            LinkedList<Integer> tempLastX = new LinkedList<>();

            queue.push(root);
            Nodo<ParejaNodo<Integer, Double>> currentNode;
            while ((currentNode = queue.poll()) != null) {
                treeNodes.add(currentNode);
                if (currentNode.UltimoNodo()) {
                    lastSize++;
                    keySize += currentNode.getTamaño();
                }
                nodeSize.push(currentNode.getTamaño() + 1);
                for (int i = 0; i <= currentNode.getTamaño(); i++) {
                    if (!currentNode.getHijo(i).Vacio()) {
                        queue.offer(currentNode.getHijo(i));
                    }
                }
            }

            int blockSpace = 90;
            int treeNodeSize = treeNodes.size();
            int x = (alto - (keySize * rectangulo + (lastSize - 1) * 20)) / 2;
            int y = (ancho + ((arbolB.getAltura() - 3) * blockSpace)) / 2;

            for (int i = 0; i < lastSize; i++) {
                int temp = nodeSize.poll();
                lastNodeSize.offer(temp);
                tempLastSize.offer(temp);
            }

            for (int i = treeNodeSize - lastSize; i < treeNodes.size(); i++) {
                Nodo<ParejaNodo<Integer, Double>> node = treeNodes.get(i);
                if (node.UltimoNodo()) {
                    for (int j = 0; j < node.getTamaño(); j++) {
                        String string = node.getLlave(j).toString();
                        DibujarNodo(g, string, x, y);
                        lastX.push(x);
                        x += rectangulo;
                    }
                    x += 20;
                }
            }

            int m = 1;
            while (nodeSize.size() != 0) {
                int l = 0, n = 0;
                y -= blockSpace;
                tempLastX.clear();
                LinkedList<Integer> nodeX = new LinkedList<>();

                if (nodeSize.size() == 1) {
                    n = 1;
                } else {
                    for (int i = 0; i < nodeSize.size(); i++) {
                        if (n != lastNodeSize.size())
                            n += nodeSize.get(i);
                        else {
                            n = i;
                            break;
                        }
                    }
                }

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < nodeSize.get(i); j++) {
                        for (int k = 0; k < lastNodeSize.get(l) - 1; k++) {
                            nodeX.push(lastX.pollFirst());
                        }
                        l++;
                    }
                    for (int j = nodeX.size() - 1; j > 0; j--) {
                        if (nodeX.get(j) - nodeX.get(j - 1) == rectangulo)
                            nodeX.remove(j);
                    }
                    Nodo<ParejaNodo<Integer, Double>> node = treeNodes.get(treeNodeSize - lastSize - m);
                    int size = node.getTamaño();
                    int halfSize = (size + 1) / 2;
                    if ((size + 1) % 2 == 0) {
                        int tempX1 = (tempLastSize.get(node.getTamaño() - halfSize) - 1) * rectangulo;
                        int tempX2 = (tempLastSize.get(node.getTamaño() - halfSize + 1) - 1) * rectangulo;
                        tempX1 = (tempX1 / 2) + nodeX.get(halfSize);
                        tempX2 = (tempX2 / 2) + nodeX.get(halfSize - 1);
                        x = ((tempX1 - tempX2) / 2 + tempX2) - ((nodeSize.get(i) - 1) * rectangulo / 2);
                    } else {
                        int tempX1 = (tempLastSize.get(node.getTamaño() - halfSize) - 1) * rectangulo;
                        tempX1 = (tempX1 / 2) + nodeX.get(halfSize);
                        x = tempX1 - ((nodeSize.get(i) - 1) * rectangulo / 2);
                    }
                    for (int j = 0; j <= node.getTamaño(); j++) {
                        int tempXi = (tempLastSize.get(node.getTamaño() - j) - 1) * rectangulo;
                        tempLastSize.remove((node.getTamaño() - j));
                        g.drawLine(x, y + 3 * fuente, nodeX.get(j) + (tempXi / 2), y + blockSpace);
                        if (j != node.getTamaño()) {
                            String string = node.getLlave(j).toString();
                            DibujarNodo(g, string, x, y);
                            tempLastX.push(x);
                            x += rectangulo;
                        }
                    }
                    m++;
                }
                lastNodeSize.clear();
                for (int i = 0; i < n; i++) {
                    int temp = nodeSize.pollFirst();
                    lastNodeSize.add(temp);
                    tempLastSize.add(temp);
                }
                Collections.sort(tempLastX);
                for (int i = 0; i < tempLastX.size(); i++) {
                    int temp = tempLastX.pollLast();
                    tempLastX.add(i, temp);
                    lastX.add(i, temp);
                }
            }
        }
    }
}
