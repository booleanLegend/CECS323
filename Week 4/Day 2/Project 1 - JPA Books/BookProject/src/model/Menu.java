package model;

import java.sql.SQLException;
import java.util.Scanner;

import javax.persistence.*;
import javax.util.Logging.Logger;

/**
 * @author Brayan
 * @author Hunter
 * @author Prince
 * @author Matthew
 */
public class Menu {

    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(Menu.class.getName());

    private int option;
    private String sql;
    private final Scanner enquiry = new Scanner(System.in);

    private Book books = new Book();


    public Menu(EntityManager manager) {
        this.entityManager = manager;
    }

    /*
     * What I'm supposed to do is:
     * 1) Add Item
     * 2) List Info
     * 3) Delete Book
     * 4) Update Book
     * 5) List primary keys of a class
     * */
    public void completeMenu() {
        Scanner in = new Scanner(System.in);
        do {
            try {
                do {
                    String menu = "\nPlease select an option to do."
                            + "\n1) Add Item"
                            + "\n2) List Info"
                            + "\n3) Delete Book"
                            + "\n4) Update Book"
                            + "\n5) List Primary Keys of a Class"
                            + "\n0) Exit";
                    System.out.println(menu);
                    System.out.print("Enter an option: ");
                    this.option = in.nextInt();
                    if (this.option < 0 || this.option > 5) {
                        System.out.println("Invalid option. Please try again.");
                    }
                } while (this.option < 0 || this.option > 5);
                switch (this.option) {
                    case 1:
                        System.out.println("\nChosen... Option 1 - Add Item");
                        addItem();
                        break;
                    case 2:
                        System.out.println("\nChosen...Option 2 - List Info");
                        listInfo();
                        break;
                    case 3:
                        System.out.println("\nChosen...Option 3 - Delete Book");
                        deleteBook();
                        break;
                    case 4:
                        System.out.println("\nChosen...Option 4 - Update Book");
                        updateBook();
                        break;
                    case 5:
                        System.out.println("\nChosen...Option 5 - List Primary Keys of a Class");
                        listPKOfClass();
                        break;
                    default:
                        System.out.println("\nExiting...\nExited");
                        break;
                }
            } catch (SQLException sqlE) {
                System.out.println("Failed to create SQL Statement.\nTry again.");
            }
        } while (this.option != 0);
    }

    /**
     * Adds Item to Database
     * @throws SQLException if the SQL Statement is invalid
     */
    public void addItem() throws SQLException {
        int addOption;
        do {
            System.out.println("Please select the option for the item you would like to add: " +
                    "\n1) Authoring Entity" +
                    "\n2) A new Publisher" +
                    "\n3) A new Book" +
                    "\n0) Exit");
            addOption = this.enquiry.nextInt();
        } while (addOption < 0 || addOption > 3);
        switch (addOption) {
            case 1:
                int authEntOption;
                do {
                    System.out.println("Please select the item within Authoring Entities you would like to add: " +
                            "\n1) A Writing Group" +
                            "\n2) An Individual Author" +
                            "\n3) An Ad Hoc Team" +
                            "\n4) An author to an existing Writing Group" +
                            "\n0) Exit");
                    authEntOption = this.enquiry.nextInt();
                } while (authEntOption < 0 || authEntOption > 4);
                switch (authEntOption) {
                    case 1:
                        System.out.println("You have chosen to add a Writing Group.");
                        System.out.print("Please enter the name of the authoring entity: ");
                        String aeName_WG = this.enquiry.nextLine().trim();
                        System.out.print("Please enter the email of the writing group: ");
                        String email_WG = this.enquiry.nextLine().trim();
                        System.out.print("Please enter the name of the head writer: ");
                        String headWriter_WG = this.enquiry.nextLine().trim();
                        System.out.print("Please enter the year when the group was formed: ");
                        int year_WG = this.enquiry.nextInt();

                        // needs call to insert sql annotation statement
                        Query addWG = entityManager.createNamedQuery("addWG", Writing_Groups.class);
                        // catch exception if could not add it here

                        System.out.println("Successfully added a new Writing Group to the table.");
                        break;
                    case 2:
                        System.out.println("You have chosen to add an Individual Author.");
                        System.out.print("Please enter the name of the Authoring Entity: ");
                        String aeName_IA = this.enquiry.nextLine().trim();
                        System.out.print("Please enter the email of the authoring entity: ");
                        String aeEmail_IA = this.enquiry.nextLine().trim();
                        System.out.print("Please enter the author's name: ");
                        String aeWriter_IA = this.enquiry.nextLine().trim();
                        System.out.print("Please enter the year this author was established: ");
                        int aeYear_IA = this.enquiry.nextInt();

                        // needs call to insert sql annotation statement
                        Query addIA = entityManager.createNamedQuery("addIA", Individual_Author.class);

                        // try catch statement if could not add
                        System.out.println("Successfully added an individual author to the table.");
                        break;
                    case 3:
                        System.out.println("You have chosen to add an Ad Hoc Team.");
                        System.out.print("Please enter the email of the Ad Hoc Team: ");
                        String aeEmail_AHT = this.enquiry.nextLine().trim();
                        boolean hasAHTMember = true;
                        do {
                            int addAHTMemOpt;
                            System.out.println("Would you like to enter an Ad Hoc Team Member?" +
                                    "\nY - Enter 1" +
                                    "\nN - Enter 2");
                            addAHTMemOpt = this.enquiry.nextInt();
                            if (addAHTMemOpt == 1) {
                                // check if member is in the database, if they are, add them as a member, else, create
                                System.out.print("Please enter the name of the member to add to the team: ");
                                String aeName_AHTMem = this.enquiry.nextLine().trim();
                                Query aeCheckName_AHTMem = entityManager.createNamedQuery("findAuthor",
                                        Individual_Author.class);
                                if (entityManager.find(Individual_Author.class, aeName_AHTMem).getName().length() > 0) {
                                    System.out.println("That name already exists in the table." +
                                            "\nAdding Author to Ad Hoc Team as Member.");
                                    // add member name and email to table here
                                } else {
                                    System.out.println("Could not find name in table." +
                                            "\nAdding Author to Ad Hoc Team");
                                    System.out.print("Please enter the email of the author: ");
                                    String aeEmailAHTMem = this.enquiry.nextLine().trim();
                                    // add member name and email to table here
                                }
                            } else if (addAHTMemOpt == 2) {
                                hasAHTMember = false;
                                System.out.println("Exiting Ad Hoc Team Option...\nExited!");
                            }
                            else {
                                System.out.println("Invalid option. Please try again.");
                            }
                        } while (hasAHTMember);
                        break;
                    case 4:
                        boolean hasAEEmailAuthWG = false;
                        do {
                            System.out.println("You have chosen to add an author to an existing writing group.");
                            System.out.print("Please enter the email of the writing group: ");
                            String aeEmailAddAuthWG = this.enquiry.nextLine().trim();
                            Query aeCheckEmail_AuthWG = entityManager.createNamedQuery("findEmailWG",
                                    Writing_Groups.class);
                            if (entityManager.find(Writing_Groups.class, aeCheckEmail_AuthWG).getEmail().length() > 0) {
                                System.out.println("We found the Writing Group.");
                                System.out.print("Please enter the name of the author you would like to add: ");
                                String aeNameAddAuthWG = this.enquiry.nextLine().trim();
                                // add author to WG to database here

                                hasAEEmailAuthWG = true;
                            } else {
                                System.out.println("We could not find a writing group associated with that email." +
                                        "Please try again.");
                            }
                        } while (!hasAEEmailAuthWG);

                        break;
                    default:
                        System.out.println("Exiting...\nExited!");
                        break;
                }
                break;
            case 2:
                System.out.println("You have chosen to add a new Publisher.");
                System.out.print("Please enter the name of the Publisher: ");
                String addPublisherName = this.enquiry.nextLine().trim();
                System.out.print("Please enter the email of the Publisher: ");
                String addPublisherEmail = this.enquiry.nextLine().trim();
                System.out.println("Plesae enter the phone number of the Publisher: ");
                String addPublisherNumber = this.enquiry.nextLine().trim();

                Query addPublisher = entityManager.createNamedQuery("addPublisher", Publisher.class);
                // check to see if publisher already exists
                // add publisher to database here
                System.out.println("Successfully added a Publisher to the database.");
                break;
            case 3:
                System.out.println("You have chosen to add a Book.");
                System.out.print("Please enter the ISBN of the book ");
                String addBookISBN = this.enquiry.nextLine().trim();
                Query addBookCheckISBN = entityManager.createNamedQuery("ReturnBookInfo", Book.class);
                entityManager.find(Book.class, addBookISBN);
                if (addBookCheckISBN.equals(addBookISBN)) {
                    System.out.println("A book with that ISBN already exists in the database.");
                } else {
                    System.out.print("Please enter the title of the book: ");
                    String addBookTitle = this.enquiry.nextLine().trim();
                    System.out.print("Please enter the year of publication: ");
                    int addBookYear = this.enquiry.nextInt();
                    System.out.println("Please enter the name of the publisher: ");
                    String addBookPublisher = this.enquiry.nextLine().trim();
                    // add book to database here
                    System.out.println("Successfully added the book to the database.");
                }
                break;
            default:
                System.out.println("Exiting...\nExited!");
                break;
        }
    }

    /**
     * List all the info about a specific Object, such as a publisher
     * @throws SQLException if the SQL Statement is invalid
     */
    public void listInfo() throws SQLException {
        System.out.println("Please select an option you would like to see info for: " +
                "\n1) A Book" +
                "\n2) A Publisher" +
                "\n3) A Writing Group" +
                "\n0) Exit" +
                "\nPlease enter an option: ");
        int listOption = this.enquiry.nextInt();
        switch (listOption) {
            case 1:
                boolean isValidBookInfo = false;
                do {
                    System.out.println("You have chosen to see info for a Book.");
                    System.out.print("Please enter the ISBN for the book: ");
                    String ISBN = this.enquiry.nextLine().trim();
                    Query bookInfo = entityManager.createNamedQuery("findBookByISBN", Book.class)
                            .setParameter(ISBN);
                    if (bookInfo.getMaxResults() != 0) {
                        isValidBookInfo = true;
                        entityManager.getTransaction().begin();
                        entityManager.getTransaction().commit();
                        // Here i have to show the info of the book, how do I do that?
                        Query showBookInfo = entityManager.createNamedQuery("ReturnBookInfo", Book.class);
                    } else {
                        System.out.println("That book is not in the table. Please try again.");
                    }
                } while (!isValidBookInfo);
                break;
            case 2:
                boolean isValidPublisherInfo = false;
                do {
                    System.out.println("You have chosen to see info for a Publisher.");
                    System.out.print("Please enter the name of the Publisher: ");
                    String name = this.enquiry.nextLine().trim();
                    Query publisherInfo = entityManager.createNamedQuery("getPublisherInfo", Publisher.class)
                            .setParameter(name);
                    if (publisherInfo.getMaxResults() != 0) {
                        isValidPublisherInfo = true;
                        // entityManager.getTransaction().begin();
                        // entityManager.getTransaction().commit();
                        entityManager.find(Publisher.class, name);
                        // is this right? have i been doing this wrong?
                        // i still have to show all the info for a publisher
                    } else {
                        System.out.println("That Publisher name is not in the table. Please try again.");
                    }
                } while (!isValidPublisherInfo);
                break;
            case 3:
                boolean isValidWritingGroup = false;
                do {
                    System.out.println("Please enter the email: ");
                    String email = this.enquiry.nextLine().trim();
                    Query wgInfo = entityManager.createNamedQuery("getWGInfo", Writing_Groups.class)
                            .setParameter(email);
                    if (wgInfo.getMaxResults() != 0) {
                        isValidWritingGroup = false;
                        entityManager.find(Writing_Groups.class, email);
                        // i still have to show all the info for a writing group
                    }
                } while (!isValidWritingGroup);
                break;
            default:
                System.out.println("Exiting...\nExited!");
                break;
        }
    }

    /**
     * Delete book user-specified
     * @throws SQLException if the SQL Statement is invalid
     */
    public void deleteBook() throws SQLException {
        boolean isValid = false;
        do {
            System.out.print("Please enter the ISBN of the book: ");
            String ISBN = this.enquiry.nextLine().trim();
            System.out.print("Please enter the title of the book: ");
            String title = this.enquiry.nextLine().trim();
            System.out.print("Please enter the year published of the book: ");
            int yearPublished = this.enquiry.nextInt();
            Query bookToRemove = entityManager.createNamedQuery("findBookByISBN", Book.class)
                    .setParameter(ISBN);
            if (bookToRemove.getMaxResults() != 0) {
                isValid = true;
                entityManager.getTransaction().begin();
                entityManager.remove(bookToRemove);
                entityManager.getTransaction().commit();
                System.out.println("You removed a book from the database.");
            } else {
                System.out.println("That book is not in the table. Please try again.");
            }
        } while (!isValid);
    }

    /**
     * Updates a book user-specified
     * @throws SQLException if the SQL Statement is invalid
     */
    public void updateBook() throws SQLException {
        boolean isValid = false;
        Book books = new Book();
        do {
            System.out.print("Please enter the ISBN of the book to update: ");
            String ISBN = this.enquiry.nextLine().trim();
            System.out.print("Please enter the title of the book: ");
            String title = this.enquiry.nextLine().trim();
            System.out.print("Please enter the book's publishing year: ");
            int yearPublished = this.enquiry.nextInt();
            Query bookToUpdate = entityManager.createNamedQuery("findBookByISBN", Book.class)
                    .setParameter(ISBN);
            if (bookToUpdate.getMaxResults() != 0) {
                isValid = true;
                System.out.print("Please enter the name of the authoring entity to update.");
                String authEntity = this.enquiry.nextLine().trim();
                entityManager.getTransaction().begin();
                books.setAuthoringEntity(authEntity);
                entityManager.getTransaction().commit();
                System.out.println("The book has been updated.");
            } else {
                System.out.println("Sorry, that book does not exist in the database. Please try again.");
            }
        } while (!isValid);
    }

    /**
     * Lists the Primary Keys of the Classes
     * @throws SQLException if the SQL Statement is invalid
     */
    public void listPKOfClass() throws SQLException {
        int pkOption;
        do {
            System.out.println("Please select an option you would like to see primary keys for: " +
                    "\n1) Books" +
                    "\n2) Publishers" +
                    "\n3) Authoring Entries" +
                    "\n0) Exit" +
                    "\nPlease enter an option: ");
            pkOption = this.enquiry.nextInt();
        } while (pkOption < 0 || pkOption > 3);
        switch (pkOption) {
            case 1:
                System.out.println("You have chosen to see primary keys for Books.");
                Query bookPKs = entityManager.createNamedQuery("getPK", Book.class);
                // here i have to show bookpks, including title, ISBN
                break;
            case 2:
                System.out.println("You have chosen to see primary keys for Publisher.");
                Query publisherPKs = entityManager.createNamedQuery("getPK", Publisher.class);
                // here i have to show publisher pks
                break;
            case 3:
                System.out.println("You have chosen to see primary keys for Authoring Entries.");
                Query authEntitiesPKs = entityManager.createNamedQuery("getPK", Authoring_Entities.class);
                // here i have to show authoring entities pks
                break;
            default:
                System.out.println("Exiting...\nExited!");
                break;
        }
    }

}
