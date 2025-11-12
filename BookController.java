package controller;

import model.Book;
import view.BookView;
import util.FileManager;
import java.util.*;
import java.util.stream.Collectors;

public class BookController {
    private final List<Book> library;
    private final BookView view;
    private final Scanner scanner = new Scanner(System.in);

    public BookController(List<Book> books, BookView view) {
        this.library = new ArrayList<>(books);
        this.view = view;
    }

    public void execute() {
        view.displayMessage("Початковий перелік книг:");
        view.showBooks(library);

        int action;
        do {
            view.showMenu();

            while (!scanner.hasNextInt()) {
                scanner.next();
                view.displayMessage("Введіть номер пункту з меню!");
            }

            action = scanner.nextInt();
            scanner.nextLine();

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
                case 0 -> view.displayMessage("Програму завершено.");
                default -> view.displayMessage("Невірний вибір, спробуйте ще раз.");
            }

        } while (action != 0);
    }

    private void searchByAuthor() {
        System.out.print("Введіть автора: ");
        String author = scanner.nextLine();
        List<Book> result = library.stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
        view.showBooks(result);
    }

    private void searchByPublisher() {
        System.out.print("Введіть назву видавництва: ");
        String publisher = scanner.nextLine();
        List<Book> result = library.stream()
                .filter(b -> b.getPublisher().equalsIgnoreCase(publisher))
                .collect(Collectors.toList());
        view.showBooks(result);
    }

    private void searchAfterYear() {
        System.out.print("Введіть рік: ");
        int year = scanner.nextInt();
        List<Book> result = library.stream()
                .filter(b -> b.getPublishYear() > year)
                .collect(Collectors.toList());
        view.showBooks(result);
    }

    private void sortByPublisher() {
        List<Book> sorted = new ArrayList<>(library);
        sorted.sort(Comparator.comparing(Book::getPublisher));
        view.displayMessage("📚 Книги, відсортовані за видавництвом:");
        view.showBooks(sorted);
    }

    private void saveBooks() {
        System.out.print("Введіть шлях до файлу для збереження: ");
        String path = scanner.nextLine();
        FileManager.saveBooksToFile(library, path);
    }

    private void readBooks() {
        System.out.print("Введіть шлях до файлу для читання: ");
        String path = scanner.nextLine();
        List<Book> loaded = FileManager.readBooksFromFile(path);
        view.showBooks(loaded);
    }

    private void encryptFile() {
        System.out.print("Введіть шлях до вихідного файлу: ");
        String input = scanner.nextLine();
        System.out.print("Введіть шлях до зашифрованого файлу: ");
        String output = scanner.nextLine();
        System.out.print("Введіть символ-ключ: ");
        char key = scanner.nextLine().charAt(0);
        FileManager.encryptFile(input, output, key);
    }

    private void decryptFile() {
        System.out.print("Введіть шлях до зашифрованого файлу: ");
        String input = scanner.nextLine();
        System.out.print("Введіть шлях до розшифрованого файлу: ");
        String output = scanner.nextLine();
        System.out.print("Введіть символ-ключ: ");
        char key = scanner.nextLine().charAt(0);
        FileManager.decryptFile(input, output, key);
    }

    private void analyzeURL() {
        System.out.print("Введіть URL сторінки: ");
        String url = scanner.nextLine();
        FileManager.analyzeTagsFromURL(url);
    }
    private void findMaxWordsLine() {
        System.out.print("Введіть шлях до файлу: ");
        String path = scanner.nextLine();
        util.FileManager.findLineWithMostWords(path);
    }

}
