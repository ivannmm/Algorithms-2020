package lesson7;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     * @return
     * Трудоемкость = O(n^2)
     * Ресурсоемкость = O(n)
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() == 0)
            return list;
        Integer[] ln = new Integer[list.size()];
        List<Integer> length = new ArrayList<>(Arrays.asList(ln));
        List<Integer> ancestors = new ArrayList<>(Arrays.asList(ln));
        Collections.fill(length, 1);
        int max;
        int ancestor;
        int needNumber = 0;
        for (int i = 0; i < list.size(); i++) {
            max = -1;
            for (int j = 0; j < i; j++) {
                if (length.get(j) > max && list.get(j) < list.get(i)) {
                    max = length.get(j);
                    needNumber = j;
                }
            }
            if (max != -1)
                length.set(i, length.get(needNumber) + 1);
        }
        List<Integer> result = new ArrayList<>();
        max = -1;
        int posOfMax = -1;
        for (int i = 0; i < length.size(); i++) {
            if (length.get(i) > max) {
                max = length.get(i);
                posOfMax = i;
            }
        }
        result.add(list.get(posOfMax));
        for (int i = max; i >= 0; i--) {
            for (int j = 0; j < posOfMax; j++)
                if (length.get(j) == max - 1 && list.get(j) < list.get(posOfMax)) {
                    result.add(0, list.get(j));
                    posOfMax = j;
                    max = length.get(j);
                }
        }
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     * Ресурсоемкость = O(n^2)
     * Трудоемкость = O(n^2)
     */
    public static int shortestPathOnField(String inputName) {
        List<ArrayList<Integer>> field = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                ArrayList<Integer> arr = new ArrayList<>();
                field.add(lineNumber,arr);
                for (int i = 0; i < line.length(); i = i + 2) {
                    field.get(lineNumber).add(Character.getNumericValue(line.charAt(i)));
                }
                lineNumber++;
            }
        } catch (IOException ignored) {};
        int[][] newField = new int[field.size()][field.get(0).size()];
        int z = 0;
        for (int i = 0; i < field.size(); i++)
            for (int j = 0; j < field.get(0).size(); j++) {
                newField[i][j] = minimalNeighbor(newField, i, j) + field.get(i).get(j);
            }
        return newField[field.size() - 1][field.get(0).size() - 1];
    }

    static int minimalNeighbor (int[][] field, int i, int j){
        if (i == 0 && j == 0) return 0;
        if (i == 0) {
            return field[i][j - 1];
        }
        if (j == 0) {
            return field[i-1][j];
        }
        return minOf(field[i][j - 1], field[i-1][j], field[i-1][j-1]);
    }

    static int minOf(int a, int b, int c) {
        if (a < b && a < c)
            return a;
        if (c < b && c < a)
            return c;
        return b;
    }
    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
