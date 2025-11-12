package view;

import model.Book;
import java.util.List;

public class BookView {

    public void showMenu() {
        System.out.println("""
                \n========= МЕНЮ =========
                1 — Знайти книги за автором
                2 — Знайти книги за видавництвом
                3 — Книги після певного року
                4 — Відсортувати за видавництвом
                5 — Зберегти книги у файл
                6 — Прочитати книги з файлу
                7 — Шифрувати файл
                8 — Дешифрувати файл
                9 — Аналіз тегів із URL
                10 — Знайти рядок із найбільшою кількістю слів у файлі
                0 — Вихід
                """);
        System.out.print("Ваш вибір: ");
    }

    public void showBooks(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("Книг не знайдено.");
        } else {
            books.forEach(System.out::println);
        }
    }

    public void displayMessage(String msg) {
        System.out.println(msg);
    }
}
