import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.BookController;
import model.Book;
import view.BookView;

import java.util.*;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Старт програми BookMVCApp");

        List<Book> books = Arrays.asList(
                new Book("Тіні забутих предків", "М.Коцюбинський", "Веселка", 1911, 250, 250.0),
                new Book("Кобзар", "Т.Шевченко", "Основи", 1840, 350, 300.0),
                new Book("Місто", "В.Підмогильний", "Ранок", 1928, 280, 270.0),
                new Book("Захар Беркут", "І.Франко", "Світ", 1883, 240, 200.0),
                new Book("Кайдашева сім'я", "І.Нечуй-Левицький", "Веселка", 1879, 180, 180.0),
                new Book("Хіба ревуть воли", "П.Мирний", "Основи", 1880, 320, 260.0),
                new Book("Маруся Чурай", "Л.Костенко", "А-БА-БА-ГА-ЛА-МА-ГА", 1979, 210, 230.0),
                new Book("Сонячна машина", "В.Винниченко", "Ранок", 1928, 410, 280.0),
                new Book("Лісова пісня", "Леся Українка", "Світ", 1911, 190, 190.0),
                new Book("Тигролови", "І.Багряний", "А-БА-БА-ГА-ЛА-МА-ГА", 1944, 300, 290.0)
        );

        BookView view = new BookView();
        BookController controller = new BookController(books, view);

        logger.info("Запуск контролера");
        controller.execute();

        logger.info("Завершення програми BookMVCApp");
    }
}
