package view;

import java.util.List;
import java.util.ResourceBundle;

import model.Book;

public class BookView {

    private ResourceBundle bundle;

    public BookView(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public void changeLanguage(ResourceBundle newBundle) {
        this.bundle = newBundle;
    }

    public void showMenu() {
        System.out.println("\n" + bundle.getString("menu.title"));
        System.out.println(bundle.getString("menu.author"));
        System.out.println(bundle.getString("menu.publisher"));
        System.out.println(bundle.getString("menu.year"));
        System.out.println(bundle.getString("menu.sort"));
        System.out.println(bundle.getString("menu.save"));
        System.out.println(bundle.getString("menu.read"));
        System.out.println(bundle.getString("menu.encrypt"));
        System.out.println(bundle.getString("menu.decrypt"));
        System.out.println(bundle.getString("menu.url"));
        System.out.println(bundle.getString("menu.words"));
        System.out.println(bundle.getString("menu.language"));
        System.out.println(bundle.getString("menu.exit"));
        System.out.print(bundle.getString("menu.choice") + " ");
    }

    public void showBooks(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println(bundle.getString("msg.noBooks"));
        } else {
            books.forEach(System.out::println);
        }
    }

    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
}
