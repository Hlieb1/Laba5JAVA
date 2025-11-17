package util;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

import model.Book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileManager {

    private static final Logger logger = LogManager.getLogger(FileManager.class);

    // ===== Збереження об’єктів =====
    public static void saveBooksToFile(List<Book> books, String filePath) {
        logger.info("Спроба збереження книг у файл: " + filePath);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(books);
            logger.info("Дані успішно збережено у файл: " + filePath);
        } catch (IOException e) {
            logger.error("Помилка запису у файл: " + filePath, e);
        }
    }

    // ===== Читання об’єктів =====
    @SuppressWarnings("unchecked")
    public static List<Book> readBooksFromFile(String filePath) {
        logger.info("Спроба читання книг із файлу: " + filePath);

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Book> books = (List<Book>) in.readObject();
            logger.info("Успішно прочитано книг: " + books.size());
            return books;
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Помилка читання файлу: " + filePath, e);
            return new ArrayList<>();
        }
    }

    // ===== Шифрування тексту =====
    public static void encryptFile(String input, String output, char key) {
        logger.info("Шифрування файлу: " + input + " → " + output);
        logger.debug("Ключ шифрування (char code): " + (int) key);

        try (Reader reader = new BufferedReader(new FileReader(input));
             Writer writer = new BufferedWriter(new FileWriter(output))) {

            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c + key);
            }

            logger.info("Файл успішно зашифровано: " + output);

        } catch (IOException e) {
            logger.error("Помилка шифрування файлу: " + input, e);
        }
    }

    // ===== Дешифрування =====
    public static void decryptFile(String input, String output, char key) {
        logger.info("Дешифрування файлу: " + input + " → " + output);
        logger.debug("Ключ дешифрування (char code): " + (int) key);

        try (Reader reader = new BufferedReader(new FileReader(input));
             Writer writer = new BufferedWriter(new FileWriter(output))) {

            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c - key);
            }

            logger.info("Файл успішно розшифровано: " + output);

        } catch (IOException e) {
            logger.error("Помилка дешифрування файлу: " + input, e);
        }
    }

    // ===== Аналіз тегів з URL =====
    public static void analyzeTagsFromURL(String urlString) {
        logger.info("Аналіз тегів за URL: " + urlString);

        try {
            URL url = new URL(urlString);
            var connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            try (InputStream input = connection.getInputStream();
                 Scanner scanner = new Scanner(input)) {

                Map<String, Integer> tagCount = new HashMap<>();
                Pattern tagPattern = Pattern.compile("<\\s*([a-zA-Z0-9]+)");

                logger.debug("Починаю зчитування HTML контенту");

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Matcher matcher = tagPattern.matcher(line);

                    while (matcher.find()) {
                        String tag = matcher.group(1).toLowerCase();
                        tagCount.put(tag, tagCount.getOrDefault(tag, 0) + 1);
                    }
                }

                logger.info("Завершено аналіз тегів. Знайдено різних тегів: " + tagCount.size());

                System.out.println("\nТеги у лексикографічному порядку:");
                tagCount.keySet().stream().sorted().forEach(tag ->
                        System.out.println(tag + " — " + tagCount.get(tag)));

                System.out.println("\nТеги за зростанням частоти:");
                tagCount.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .forEach(e -> System.out.println(e.getKey() + " — " + e.getValue()));

            }

        } catch (IOException e) {
            logger.error("Помилка зчитування URL: " + urlString, e);
            System.out.println("Помилка зчитування з URL: " + urlString);
        }
    }

    // ===== Пошук рядка з найбільшою кількістю слів =====
    public static void findLineWithMostWords(String filePath) {
        logger.info("Пошук рядка з найбільшою кількістю слів у файлі: " + filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            String maxLine = "";
            int maxWords = 0;

            while ((line = reader.readLine()) != null) {
                int words = line.trim().isEmpty() ? 0 : line.trim().split("\\s+").length;
                if (words > maxWords) {
                    maxWords = words;
                    maxLine = line;
                }
            }

            logger.info("Найбільша кількість слів: " + maxWords);

            System.out.println("\nРядок із найбільшою кількістю слів:");
            System.out.println(maxLine);
            System.out.println("Кількість слів: " + maxWords);

        } catch (IOException e) {
            logger.error("Помилка читання файлу: " + filePath, e);
        }
    }
}
