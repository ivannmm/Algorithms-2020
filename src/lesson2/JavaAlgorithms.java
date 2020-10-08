package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.util.stream.IntStream;

import static java.util.stream.IntStream.*;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     * Ресурсоемкость = O(~n^2)
     * Трудоемкость = O(~n^2)
     */
    static public String longestCommonSubstring(String first, String second) {
        int[][] mat = new int[first.length() + 1][second.length() + 1];
        int maxLength = 0;
        int endPointInFirstWord = 0;
        String result;
        for (int i = 1; i < first.length() + 1; i++)
            for (int j = 1; j < second.length() + 1; j++)
                if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    mat[i][j] = mat[i - 1][j - 1] + 1;
                    if (mat[i][j] > maxLength) {
                        endPointInFirstWord = i;
                        maxLength = mat[i][j];
                    }
                }
        if (maxLength == 0) {
            return "";
        } else return first.substring(endPointInFirstWord - maxLength, endPointInFirstWord);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     * Ресурсоемкость = O(n)
     * Трудоемкость = O(n*log(log(n)))
     */
    static public int calcPrimesNumber(int limit) {
        if (limit <= 1) return 0;
        boolean[] numbers = new boolean[limit + 1];
        rangeClosed(2, limit).forEach(i -> numbers[i] = true);
        int count = 0;
        for (int i = 2; i <= limit; i++) {
            if (numbers[i]) {
                for (int j = 2; i * j <= limit; j++) {
                    numbers[i * j] = false;
                }
                count++;
            }
        }
        return count;
    }/**
        if (limit <= 1 ) return 0;
        if (limit == 2) return 1;
        if (limit < 5) return 2;
        int forFind = 5;
        int count = 2;

        /**Учитываем то, что все простые числы находятся около 6n +/- 1
        while (forFind <= limit){
            if (isPrime(forFind))
                count++;
            forFind = forFind + 2;
            if (forFind <= limit && isPrime(forFind))
                count++;
            forFind = forFind + 4;
        }
        return count;
    }

    static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int m = 3; m <= (int) Math.sqrt(n); m++) {
            if (n % m == 0) return false;
        }
        return true;
    }*/


}
