package app;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class JPAProjectMain {
    private EntityManager em;
    private static final Logger logger = Logger.getLogger(app.JPAProjectMain.class.getName());

    public JPAProjectMain(EntityManager manager){this.em = manager;}

    public static void main(String [] args) {
        logger.fine("Creating EntityManagerFactory and EntityManager");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Book");
        EntityManager manager = factory.createEntityManager();

        app.JPAProjectMain bookData = new app.JPAProjectMain(manager);

        logger.fine("Begin of Transaction");
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        List<Publisher> publishers = new ArrayList<Publisher>();
        publishers.add(new Publisher("asdf", "asdf", "1234"));
        List<Book> books = new ArrayList<Book>();
        books.add(new Book("asdf", "asdfg", 1000, publishers.get(0)));
    }
}
