/*

Simple Project to Test our DataBase
@Date 6/15/2020

 */
package pkg323termproject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Derek
 * @author Kenny
 * @author Dishant
 * @author Min
 * @author Daniel
 */
public class Menu {

    private final Connection conn;
    private final String displayFormat = "%-30s%-30s%-30s%-30s\n";

    private Statement stmt = null;  //initialize the statement that we're using
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private int option;
    private final Scanner cases = new Scanner(System.in);
    private String sql;

    public Menu(Connection conn) {
        this.conn = conn;
    }

    public static String dispNull(String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0) {
            return "N/A";
        } else {
            return input;
        }
    }

    /**
     * This class is a repeating menu that offers all the Options for this
     * Application.
     */
    public void menuThing() {

        try (Scanner in = new Scanner(System.in)) {
            do {
                try {
                    do {
                        String menu = "\n Please select an option to perform."
                                + "\n1. List all Writing Groups."
                                + "\n2. List All the Data for a specific Group."
                                + "\n3. List all Publishers."
                                + "\n4. List all data for a specific publisher."
                                + "\n5. List all Book titles."
                                + "\n6. List all the data for a specific Book."
                                + "\n7. Insert a Book."
                                + "\n8. Insert new Publisher.(And More)"
                                + "\n9. Remove a specific book."
                                + "\n0. Exit.";
                        System.out.println(menu);
                        System.out.print("Enter an option: ");
                        this.option = in.nextInt();
                        if (this.option < 0 || this.option > 9) {
                            System.out.println("That is not a Valid Option. Please Try Again.");
                        }
                    } while (this.option < 0 || this.option > 9); // This will make the menu repeat if option is higher than 6 or lowen than 0.

                    switch (this.option) {
                        case 1:
                            System.out.println("\nYou have entered Option 1. List all Writing Groups.");
                            case1();
                            break;

                        case 2:
                            System.out.println("\nYou have entered Option 2. List All the Data for a specific Group.");
                            case2();
                            break;

                        case 3:
                            System.out.println("\nYou have entered Option 3. List all Publishers.");
                            case3();
                            break;
                        case 4:
                            System.out.println("\nYou have entered Option 4. List all data for a specific publisher.");
                            case4();
                            break;

                        case 5:
                            System.out.println("\nYou have entered Option 5. List all Book titles.");

                            case5();
                            break;
                        case 6:
                            System.out.println("\nYou have entered Option 6. List all the data for a specific Book.");
                            case6();
                            break;
                        case 7:
                            System.out.println("\nYou have entered Option 7. Insert a Book.");
                            case7();
                            break;
                        case 8:
                            System.out.println("\nYou have entered Option 8. Insert new Publisher.(And More)");
                            case8();
                            break;
                        case 9:
                            System.out.println("\nYou have entered Option 9. Remove a specific book.");
                            case9();
                            break;
                        default:
                            System.out.println("Good Bye. Thanks For Playing!");
                            break; //I always use this break, even when not needed.
                    }

                } catch (SQLException se) {
                    System.out.println("We Failed Creating the SQl Statment.\nTry Again.");
                    //se.printStackTrace();
                }

            } while (this.option != 0);

        } finally {//Close All
            this.cases.close();

            try {
                if (this.stmt != null) {
                    this.stmt.close();
                }
            } catch (SQLException se1) {
                System.out.println("There was Trouble Closing Statement Connections.");
            }

            try {
                if (this.pstmt != null) {
                    this.pstmt.close();
                }
            } catch (SQLException se2) {

                System.out.println("There was Trouble Closing Prepared Statement Connections.");
            }

            try {
                if (this.rs != null) {
                    this.rs.close();
                }
            } catch (SQLException se3) {

                System.out.println("There was Trouble Closing ResultSet Connections.");
            }
        }
    }

    /**
     * List All Writing Groups in the WritingGroup Table
     *
     * @throws SQLException if the SQL Statement is not Valid
     */
    public void case1() throws SQLException {

        this.stmt = this.conn.createStatement();
        this.sql = "SELECT groupName, headWriter, yearFormed, subject FROM writingGroups";
        this.rs = this.stmt.executeQuery(this.sql);

        System.out.printf(this.displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
        while (this.rs.next()) {
            //Retrieve by column name
            String groupName = this.rs.getString("groupName");
            String headWriter = this.rs.getString("headWriter");
            String yearFormed = this.rs.getString("yearFormed");
            String subject = this.rs.getString("subject");

            //Display values
            System.out.printf(this.displayFormat,
                    dispNull(groupName), dispNull(headWriter), dispNull(yearFormed), dispNull(subject));
        }
    }

    /**
     * Lists all the Data for a group specified by the User.
     *
     * @throws SQLException if the SQL Statement is not Valid.
     */
    public void case2() throws SQLException {

        boolean valid = false;

        do {
            System.out.print("Please Enter a Group Name: ");
            String userInput = this.cases.nextLine().trim();

            this.sql = "SELECT groupName, headWriter, yearFormed, subject FROM writingGroups WHERE groupName =  ?";

            if (!checkValid(this.sql, userInput)) {
                System.out.println("This Group Name is not in the DataBase, Try Again.");
            } else {
                this.rs = this.pstmt.executeQuery();
                valid = true;
                System.out.printf(this.displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
                while (this.rs.next()) {
                    //Retrieve by column name
                    String groupName = this.rs.getString("groupName");
                    String headWriter = this.rs.getString("headWriter");
                    String yearFormed = this.rs.getString("yearFormed");
                    String subject = this.rs.getString("subject");

                    //Display values
                    System.out.printf(this.displayFormat,
                            dispNull(groupName), dispNull(headWriter), dispNull(yearFormed), dispNull(subject));
                }
            }
        } while (!valid);

    }

    /**
     * Lists All Publisher in the Publisher Table.
     *
     * @throws SQLException if the SQL Statement is not Valid.
     */
    public void case3() throws SQLException {

        this.stmt = this.conn.createStatement();

        this.sql = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM publishers";
        this.rs = this.stmt.executeQuery(this.sql);

        System.out.printf(this.displayFormat, "Publisher Name", "Publisher Address", "Publisher Phone Number", "Publisher Email");
        while (this.rs.next()) {
            //Retrieve by column name
            String publisherName = this.rs.getString("publisherName");
            String publisherAddress = this.rs.getString("publisherAddress");
            String publisherPhone = this.rs.getString("publisherPhone");
            String publisherEmail = this.rs.getString("publisherEmail");

            //Display values
            System.out.printf(this.displayFormat,
                    dispNull(publisherName), dispNull(publisherAddress), dispNull(publisherPhone), dispNull(publisherEmail));
        }

    }

    /**
     * Lists all the data for a publisher specified by the user.
     *
     * @throws SQLException if the SQL Statement is not Valid.
     */
    public void case4() throws SQLException {
        boolean valid = false;

        do {
            System.out.print("Please Enter a Publisher Name: ");
            String userInput = this.cases.nextLine().trim();

            this.sql = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM publishers WHERE publisherName =  ?";

            if (!checkValid(this.sql, userInput)) {
                System.out.println("This Publisher Name is not in the DataBase, Try Again.");
            } else {
                this.rs = this.pstmt.executeQuery();
                valid = true;
                System.out.printf(this.displayFormat, "Publisher Name", "Publisher Address", "Publisher Phone Number", "Publisher Email");
                while (this.rs.next()) {
                    //Retrieve by column name
                    String publisherName = this.rs.getString("publisherName");
                    String publisherAddress = this.rs.getString("publisherAddress");
                    String publisherPhone = this.rs.getString("publisherPhone");
                    String publisherEmail = this.rs.getString("publisherEmail");
                    //Display values
                    System.out.printf(this.displayFormat,
                            dispNull(publisherName), dispNull(publisherAddress), dispNull(publisherPhone), dispNull(publisherEmail));
                }
            }
        } while (!valid);
    }

    /**
     * Lists all book titles.
     *
     * @throws SQLException if the SQL Statement is not Valid.
     */
    public void case5() throws SQLException {
        this.stmt = this.conn.createStatement();

        this.sql = "SELECT groupName, bookTitle, publisherName, numberPages FROM books";
        this.rs = this.stmt.executeQuery(sql);

        System.out.printf(this.displayFormat, "Group Name", "Book Title", "Publisher Name", "Number of Pages");
        while (this.rs.next()) {
            //Retrieve by column name
            String groupName = this.rs.getString("groupName");
            String bookTitle = this.rs.getString("bookTitle");
            String publisherName = this.rs.getString("publisherName");
            String numberPages = this.rs.getString("numberPages");

            //Display values
            System.out.printf(this.displayFormat,
                    dispNull(groupName), dispNull(bookTitle), dispNull(publisherName), dispNull(numberPages));
        }
    }

    /**
     * Lists all the data for a book specified by the user
     *
     * @throws SQLException if the SQL Statement is not Valid.
     */
    public void case6() throws SQLException {
        boolean valid = false;
        do {
            System.out.print("Please Enter a Book Title: ");
            String userInput = this.cases.nextLine().trim();

            this.sql = "SELECT groupName, bookTitle, publisherName, numberPages FROM books WHERE bookTitle =  ?";

            if (!checkValid(this.sql, userInput)) {
                System.out.println("This BookTitle is not in the DataBase, Try Again.");
            } else {
                this.rs = this.pstmt.executeQuery();
                valid = true;
                System.out.printf(this.displayFormat, "Writing Group", "Book Title", "Publisher Name", "Number of Pages");
                while (this.rs.next()) {
                    //Retrieve by column name
                    String groupName = this.rs.getString("groupName");
                    String bookTitle = this.rs.getString("bookTitle");
                    String publisherName = this.rs.getString("publisherName");
                    String numberPages = this.rs.getString("numberPages");
                    //Display values
                    System.out.printf(this.displayFormat,
                            dispNull(groupName), dispNull(bookTitle), dispNull(publisherName), dispNull(numberPages));
                }
            }
        } while (!valid);
    }

    /**
     * Insert a new book specified by the User
     *
     * @throws SQLException if the SQL Statement is not Valid.
     */
    public void case7() throws SQLException {
        boolean valid1;
        boolean valid2;
        PreparedStatement pstmt2 = null;
        ResultSet rs2 = null;
        String groupName;
        String bookTitle;
        String publisherName;

        do {
            do {
                do {
                    System.out.print("Please Enter a Group Name to Insert: ");
                    groupName = this.cases.nextLine().trim();
                    this.sql = "SELECT groupName, headWriter, yearFormed, subject FROM writingGroups WHERE groupName = ?";
                    if (!checkValid(this.sql, groupName)) {
                        System.out.println("The Group Name is not in the DataBase. Please Try Again");
                    }
                } while (!checkValid(this.sql, groupName));

                System.out.print("Please Enter a Book Title to Insert: ");
                bookTitle = this.cases.nextLine().trim();

                this.sql = "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages FROM books WHERE groupName = ? AND bookTitle = ?";
                this.pstmt = this.conn.prepareStatement(this.sql);
                this.pstmt.setString(1, groupName);
                this.pstmt.setString(2, bookTitle);
                this.rs = this.pstmt.executeQuery();

                if (this.rs.next()) {
                    System.out.println("The Group Name and Publisher Name combination already exists in the DataBase. Try Again.");
                    valid1 = false;
                } else {
                    valid1 = true;
                }

            } while (!valid1);

            do {
                System.out.print("Please Enter a Publisher Name to Insert: ");
                publisherName = this.cases.nextLine().trim();

                this.sql = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM publishers WHERE publisherName = ?";
                if (!checkValid(this.sql, publisherName)) {
                    System.out.println("The Publisher Name is not in the DataBase. Please Try Again");
                }
            } while (!checkValid(this.sql, publisherName));

            String sql2 = "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages FROM books WHERE bookTitle = ? AND publisherName = ?";
            pstmt2 = this.conn.prepareStatement(sql2);
            pstmt2.setString(1, bookTitle);
            pstmt2.setString(2, publisherName);
            rs2 = pstmt2.executeQuery();

            if (rs2.next()) {
                System.out.println("The BookTitle and PublisherName combination already exists in the DataBase. Try Again.");
                valid2 = false;
            } else {
                valid2 = true;
            }

        } while (!valid2);

        System.out.print("Please Enter Year of Publication: ");
        int yearPublished = this.cases.nextInt();

        System.out.print("Please Enter Number of Pages of Book: ");
        int numberPages = this.cases.nextInt();


        this.sql = "INSERT INTO  books (groupName, bookTitle, publisherName, yearPublished,numberPages)VALUES  (?,?,?,?,?)";
        this.pstmt = this.conn.prepareStatement(this.sql);
        this.pstmt.setString(1, groupName);
        this.pstmt.setString(2, bookTitle);
        this.pstmt.setString(3, publisherName);
        this.pstmt.setInt(4, yearPublished);
        this.pstmt.setInt(5, numberPages);

        if (this.pstmt.executeUpdate() > 0) {
            System.out.println("We have Inserted a New Book into the DataBase.");
        } else {
            System.out.println("We have Failed to Update Books");
        }

        //Close All extra stuff
        try {
            if (pstmt2 != null) {
                pstmt2.close();
            }
        } catch (SQLException se2) {

            System.out.println("There was Trouble Closing Prepared Statement Connections.");
        }

        try {
            if (rs2 != null) {
                rs2.close();
            }
        } catch (SQLException se3) {

            System.out.println("There was Trouble Closing ResultSet Connections.");
        }
        //Checks for duplicates before INSERT
    }

    /**
     * Insert a new publisher and update all books published by one publisher to
     * be published by the new publisher.
     *
     * @throws SQLException if the SQL Statement is not Valid.
     */
    public void case8() throws SQLException {

        boolean valid = false;
        boolean valid2 = false;
        String publisherUpdate;
        String newPublisherName;

        do {

            System.out.print("Please Enter the new Publisher Name: ");
            publisherUpdate = this.cases.nextLine().trim();
            this.sql = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM publishers WHERE publisherName = ?";

            if (checkValid(this.sql, publisherUpdate)) {
                System.out.println("The publisher is already in the DataBase. Please Try Again.");
            } else {
                valid = true;
            }
        } while (!valid);

        System.out.print("Please Enter the new Publisher Address: ");
        String publisherAddress = this.cases.nextLine().trim();
        System.out.print("Please Enter the new Publisher Phone Number: ");
        String publisherPhone = this.cases.nextLine().trim();
        System.out.print("Please Enter the new Publisher Email: ");
        String publisherEmail = this.cases.nextLine().trim();

        do {
            System.out.print("Please Enter a Publisher Name to Update: ");
            newPublisherName = this.cases.nextLine().trim();
            this.sql = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM publishers WHERE publisherName = ?";

            if (!checkValid(this.sql, newPublisherName)) {
                System.out.println("The publisher isn't in the DataBase. Try Again");
            } else {
                valid2 = true;
            }
        } while (!valid2);

        this.sql = "INSERT INTO publishers (publisherName, publisherAddress, publisherPhone, publisherEmail) VALUES (?, ? , ?, ?)";
        this.pstmt = this.conn.prepareStatement(this.sql);
        this.pstmt.setString(1, publisherUpdate);
        this.pstmt.setString(2, publisherAddress);
        this.pstmt.setString(3, publisherPhone);
        this.pstmt.setString(4, publisherEmail);
        if (this.pstmt.executeUpdate() > 0) {
            System.out.println("We have Inserted a New Publisher into the DataBase");
        } else {
            System.out.println("We have Failed to Insert New Publisher");
        }

        this.sql = "UPDATE books set publisherName = ? where publisherName = ?";
        this.pstmt = this.conn.prepareStatement(this.sql);
        this.pstmt.setString(1, publisherUpdate);
        this.pstmt.setString(2, newPublisherName);
        if (this.pstmt.executeUpdate() > 0) {
            System.out.println("We have Updated Books with New Publisher");
        } else {
            System.out.println("We have Failed to Update Books");
        }
    }

    /**
     * Remove a book specified by the user
     *
     * @throws SQLException if the SQL Statement is not Valid.
     */
    public void case9() throws SQLException {
        boolean valid = false;

        do {
            System.out.print("Please Enter the Group Name of the Book: ");
            String groupName = this.cases.nextLine().trim();
            System.out.print("Please Enter the book you want to remove: ");
            String bookTitle = this.cases.nextLine().trim();

            this.sql = "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages FROM books WHERE groupName = ? AND bookTitle = ?";
            this.pstmt = this.conn.prepareStatement(this.sql);
            this.pstmt.setString(1, groupName);
            this.pstmt.setString(2, bookTitle);
            this.rs = this.pstmt.executeQuery();

            if (!this.rs.next()) {
                System.out.println("The book you are trying to remove is not in the table, or the group name does not affliate with the book you are trying to remove, please try again");

            } else {
                valid = true;
                this.sql = "DELETE FROM books where groupName = ? and bookTitle = ?";
                this.pstmt = this.conn.prepareStatement(this.sql);
                this.pstmt.setString(1, groupName);
                this.pstmt.setString(2, bookTitle);
                if (this.pstmt.executeUpdate() > 0) {
                    System.out.println("You Deleted a Book From the DataBase");
                } else {
                    System.out.println("We have Failed to Delete Book.");
                }
            }
        } while (!valid);
    }

    /**
     * Check to see if SQL Statement is Valid. Basically if it is already in the
     * data base
     *
     * @param sql the Statement to Search the DB
     * @param userInput the user input
     * @return true if Item is already in DataBase
     */
    public boolean checkValid(String sql, String userInput) {

        boolean result = false;

        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, userInput);
            this.rs = this.pstmt.executeQuery();

            result = this.rs.next();
        } catch (SQLException e) {
            System.out.println("This is a Bad SQL Statement, Sorry, Try Again.");
            //e.printStackTrace();
        }

        return result;
    }
}