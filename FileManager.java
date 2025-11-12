package util;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.*;
import model.Book;

public class FileManager {

    // ===== Збереження об’єктів =====
    public static void saveBooksToFile(List<Book> books, String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(books);
            System.out.println("Дані успішно збережено у файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }
    }

    // ===== Читання об’єктів =====
    @SuppressWarnings("unchecked")
    public static List<Book> readBooksFromFile(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Book>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Помилка читання файлу: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ===== Шифрування тексту =====
    public static void encryptFile(String input, String output, char key) {
        try (Reader reader = new BufferedReader(new FileReader(input));
             Writer writer = new BufferedWriter(new FileWriter(output))) {
            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c + key);
            }
            System.out.println("Файл зашифровано і збережено в: " + output);
        } catch (IOException e) {
            System.out.println("Помилка шифрування: " + e.getMessage());
        }
    }

    // ===== Дешифрування =====
    public static void decryptFile(String input, String output, char key) {
        try (Reader reader = new BufferedReader(new FileReader(input));
             Writer writer = new BufferedWriter(new FileWriter(output))) {
            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c - key);
            }
            System.out.println("Файл розшифровано і збережено в: " + output);
        } catch (IOException e) {
            System.out.println("Помилка дешифрування: " + e.getMessage());
        }
    }

 // ===== Аналіз тегів з URL 
    public static void analyzeTagsFromURL(String urlString) {
        try {
            URL url = new URL(urlString);
            
            var connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");

            try (InputStream input = connection.getInputStream();
                 Scanner scanner = new Scanner(input)) {

                Map<String, Integer> tagCount = new HashMap<>();
                Pattern tagPattern = Pattern.compile("<\\s*([a-zA-Z0-9]+)");

                while (scanner.hasNextLine()) {
                    Matcher matcher = tagPattern.matcher(scanner.nextLine());
                    while (matcher.find()) {
                        String tag = matcher.group(1).toLowerCase();
                        tagCount.put(tag, tagCount.getOrDefault(tag, 0) + 1);
                    }
                }

                System.out.println("\nТеги у лексикографічному порядку:");
                tagCount.keySet().stream().sorted().forEach(tag ->
                        System.out.println(tag + " — " + tagCount.get(tag)));

                System.out.println("\nТеги за зростанням частоти:");
                tagCount.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .forEach(e -> System.out.println(e.getKey() + " — " + e.getValue()));
            }

        } catch (IOException e) {
            System.out.println("Помилка зчитування з URL: " + e.getMessage());
        }
    }
    
    public static void findLineWithMostWords(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String maxLine = "";
            int maxWords = 0;

            while ((line = reader.readLine()) != null) {
                int words = line.trim().split("\\s+").length;
                if (words > maxWords) {
                    maxWords = words;
                    maxLine = line;
                }
            }

            System.out.println("\nРядок із найбільшою кількістю слів:");
            System.out.println(maxLine);
            System.out.println("Кількість слів: " + maxWords);
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }

}
