package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     * Ресурсоемкость = O(n)
     * Трудоемкость = O(n*log(n))
     */
    static public void sortTimes(String inputName, String outputName) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
            BufferedReader reader = new BufferedReader(new FileReader(inputName))){
            List<String> time = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                time.add(line);
            }

            time.sort(new Comparator<>() {
                final DateFormat date = new SimpleDateFormat("hh:mm:ss a");

                @Override
                public int compare(String o1, String o2) {
                    try {
                        return date.parse(o1).compareTo(date.parse(o2));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException();
                    }
                }
            });



            for (String date : time) {
                writer.write(date + '\n');
            }
        }
    }

    /**ортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны
     * С. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     * Ресурсоемкость = O(n)
     * Трудоемкость = O(n*log(n))
     */
    static public void sortAddresses(String inputName, String outputName) throws Exception {
        try (
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8));
        BufferedWriter writer =
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputName), StandardCharsets.UTF_8))) {
            Map<String, ArrayList<String>> address = new TreeMap<>((o1, o2) -> {
                String streetName1 = o1.split(" ")[0];
                String streetName2 = o2.split(" ")[0];
                Integer houseNumber1 = Integer.parseInt(o1.split(" ")[1]);
                Integer houseNumber2 = Integer.parseInt(o2.split(" ")[1]);
                if (!streetName1.equals(streetName2))
                    return streetName1.compareTo(streetName2);
                else return houseNumber1.compareTo(houseNumber2);
            });

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches("^([А-Яа-яЁёA-Za-z]+ ){2}- ([А-Яа-яЁёA-Za-z\\-]+ \\d+)$")) {
                    String[] data = line.split(" ");
                    String street = data[3] + " " + data[4];
                    String name = data[0] + " " + data[1];

                    if (!address.containsKey(street)) {
                        ArrayList<String> names = new ArrayList<>();
                        names.add(name);
                        address.put(street, names);
                    } else {
                        address.get(street).add(name);
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }

            for (String key : address.keySet()) {
                Collections.sort(address.get(key));
                StringBuilder sb = new StringBuilder();
                sb.append(key);
                sb.append(" - ");
                List<String> list = address.get(key);
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i));
                    if (i != list.size() - 1)
                        sb.append(", ");
                }
                sb.append('\n');
                writer.write(String.valueOf(sb));
            }
        }
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     * Трудоемкость = O(n*logn)
     * Ресурсоемкость = O(n)
     */
    static public void sortTemperatures(String inputName, String outputName) throws Exception {
        //пока не приходят мысли в голову, как решить эту задачу оптимальнее, ибо кол-во показателей температур может
        //достигать ста миллионов
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8));
             BufferedWriter writer =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputName), StandardCharsets.UTF_8))) {
            String line;
            List<Double> temp = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                temp.add(Double.parseDouble(line));
            }

            Collections.sort(temp);

            for (Double l : temp) {
                StringBuilder sb = new StringBuilder();
                sb.append(l);
                sb.append('\n');
                writer.write(String.valueOf(sb));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
