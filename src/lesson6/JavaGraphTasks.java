package lesson6;

import kotlin.NotImplementedError;
import lesson6.impl.GraphBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     * Ресурсоемкость = о(n+m);
     * Трудоемкость = о(n*m)
     * n - кол-во дуг, m - кол-во вершин
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        List<Graph.Edge> result = new ArrayList<>();
        int count = 0;
        Graph.Vertex startingPoint = null;
        Set<Graph.Edge> setOfEdges = graph.getEdges();
        if (setOfEdges.size() <= 2)
            return result;
        for (Graph.Edge element : setOfEdges) {
            if (graph.getNeighbors(element.getBegin()).size() % 2 == 1) {
                count++;
                if (count > 2)
                    return result;
                startingPoint = element.getBegin();
            }
            if (startingPoint == null)
                startingPoint = element.getBegin();
        }
        Graph.Vertex currentPoint = startingPoint;
        int c = setOfEdges.size();
        for (int i = 0; i < c; i++) {
            Set<Graph.Vertex> points = graph.getNeighbors(currentPoint);
            setOfEdges.remove(currentPoint);
            if (!setOfEdges.isEmpty())
                for (Graph.Vertex element : points) {
                    if (element != startingPoint) {
                        result.add(graph.getConnection(currentPoint, element));
                        currentPoint = element;
                        break;
                    }
                }
            if (setOfEdges.size() == 1)
                result.add(graph.getConnection(currentPoint, startingPoint));
        }
        return result;
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     * Ресурсоемкость = о(n)
     * Трудоемкость = о(~logn)
     */
    public static Graph minimumSpanningTree(Graph graph) {
        Set<Graph.Vertex> noVisit = new HashSet<>(graph.getVertices());
        GraphBuilder builder = new GraphBuilder();

        for (Graph.Vertex vertex : graph.getVertices()){
            if (noVisit.isEmpty())
                break;
            builder.addVertex(vertex.getName());
            noVisit.remove(vertex);
            for (Graph.Vertex neighbor : graph.getNeighbors(vertex)){
                //добавил сравнение на то, чтобы текущая вершина не совпада с соседом, потому как это
                //теоритически может быть
                if ((noVisit.contains(neighbor) || graph.getConnections(vertex).size() == 1)
                && !vertex.equals(neighbor)) {
                    builder.addVertex(neighbor.getName());
                    builder.addConnection(vertex, neighbor);
                    noVisit.remove(neighbor);
                }
            }
        }

        return builder.build();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Если на входе граф с циклами, бросить IllegalArgumentException
     *
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        return null;
    }
    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        throw new NotImplementedError();
    }


    /**
     * Балда
     * Сложная
     *
     * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
     * поэтому задача присутствует в этом разделе
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
