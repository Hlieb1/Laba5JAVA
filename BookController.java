package controller;

import model.Book;
import view.BookView;
import util.FileManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class BookController {

    private static final Logger logger = LogManager.getLogger(BookController.class);

    private final List<Book> library;
    private final BookView view;
    private final Scanner scanner = new Scanner(System.in);

    public BookController(List<Book> books, BookView view) {
        this.library = new ArrayList<>(books);
        this.view = view;
        logger.info("Контролер створено. Книг у бібліотеці: " + library.size());
    }

    public void execute() {

        logger.info("Запуск головного меню");

        view.displayMessage("Початковий перелік книг:");
        view.showBooks(library);

        int action;
        do {
            view.showMenu();

            while (!scanner.hasNextInt()) {
                scanner.next();
                view.displayMessage("Введіть номер пункту з меню!");
                logger.warn("Некоректне введення пункту меню");
            }

            action = scanner.nextInt();
            scanner.nextLine();

            logger.info("Обрано пункт меню: " + action);

            try {
                switch (action) {
                    case 1 -> searchByAuthor();
                    case 2 -> searchByPublisher();
                    case 3 -> searchAfterYear();
                    case 4 -> sortByPublisher();
                    case 5 -> saveBooks();
                    case 6 -> readBooks();
                    case 7 -> encryptFile();
                    case 8 -> decryptFile();
                    case 9 -> analyzeURL();
                    case 10 -> findMaxWordsLine();
                    case 11 -> changeLanguage();
                    case 0 -> logger.info("Вихід з програми");
                    default -> {
                        view.displayMessage("Невірний вибір, спробуйте ще раз.");
                        logger.warn("Некоректний вибір пункту меню: " + action);
                    }
                }
            } catch (Exception e) {
                logger.error("Помилка під час виконання операції меню", e);
            }

        } while (action != 0);
    }

    private void searchByAuthor() {
        logger.info("Пошук книг за автором");
        System.out.print("Введіть автора: ");
        String author = scanner.nextLine();
        List<Book> result = library.stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
        logger.info("Знайдено книг: " + result.size());
        view.showBooks(result);
    }

    private void searchByPublisher() {
        logger.info("Пошук книг за видавництвом");
        System.out.print("Введіть назву видавництва: ");
        String publisher = scanner.nextLine();
        List<Book> result = library.stream()
                .filter(b -> b.getPublisher().equalsIgnoreCase(publisher))
                .collect(Collectors.toList());
        logger.info("Знайдено книг: " + result.size());
        view.showBooks(result);
    }

    private void searchAfterYear() {
        logger.info("Пошук книг після року");
        System.out.print("Введіть рік: ");
        int year = scanner.nextInt();
        List<Book> result = library.stream()
                .filter(b -> b.getPublishYear() > year)
                .collect(Collectors.toList());
        logger.info("Знайдено книг: " + result.size());
        view.showBooks(result);
    }

    private void sortByPublisher() {
        logger.info("Сортування книг за видавництвом");
        List<Book> sorted = new ArrayList<>(library);
        sorted.sort(Comparator.comparing(Book::getPublisher));
        view.displayMessage("📚 Книги, відсортовані за видавництвом:");
        view.showBooks(sorted);
    }

    private void saveBooks() {
        logger.info("Збереження книг у файл");
        System.out.print("Введіть шлях до файлу для збереження: ");
        String path = scanner.nextLine();
        FileManager.saveBooksToFile(library, path);
    }

    private void readBooks() {
        logger.info("Читання книг з файлу");
        System.out.print("Введіть шлях до файлу для читання: ");
        String path = scanner.nextLine();
        List<Book> loaded = FileManager.readBooksFromFile(path);
        view.showBooks(loaded);
    }

    private void encryptFile() {
        logger.info("Шифрування файлу");
        System.out.print("Введіть шлях до вихідного файлу: ");
        String input = scanner.nextLine();
        System.out.print("Введіть шлях до зашифрованого файлу: ");
        String output = scanner.nextLine();
        System.out.print("Введіть символ-ключ: ");
        char key = scanner.nextLine().charAt(0);
        FileManager.encryptFile(input, output, key);
    }

    private void decryptFile() {
        logger.info("Дешифрування файлу");
        System.out.print("Введіть шлях до зашифрованого файлу: ");
        String input = scanner.nextLine();
        System.out.print("Введіть шлях до розшифрованого файлу: ");
        String output = scanner.nextLine();
        System.out.print("Введіть символ-ключ: ");
        char key = scanner.nextLine().charAt(0);
        FileManager.decryptFile(input, output, key);
    }

    private void analyzeURL() {
        logger.info("Аналіз сторінки за URL");
        System.out.print("Введіть URL сторінки: ");
        String url = scanner.nextLine();
        FileManager.analyzeTagsFromURL(url);
    }

    private void findMaxWordsLine() {
        logger.info("Пошук рядка з найбільшою кількістю слів");
        System.out.print("Введіть шлях до файлу: ");
        String path = scanner.nextLine();
        util.FileManager.findLineWithMostWords(path);
    }
    
    private void changeLanguage() {
        System.out.print(view.getBundle().getString("msg.languageChoose") + " ");
        String lang = scanner.nextLine().trim().toLowerCase();

        ResourceBundle newBundle;

        if (lang.equals("en")) {
            newBundle = ResourceBundle.getBundle("location.messages_en");
        } else {
            newBundle = ResourceBundle.getBundle("location.messages_uk");
        }

        view.changeLanguage(newBundle);
        System.out.println(view.getBundle().getString("msg.languageSet") + " " + lang);
    }

}
