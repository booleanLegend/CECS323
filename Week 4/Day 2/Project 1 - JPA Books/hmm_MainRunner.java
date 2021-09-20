/*
Simple Project to Test our DataBase
@Date 6/15/2020
*/
package pkg323termproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Maine Runner to Make connection and run the Program
 * @author Derek
 * @author Kenny
 * @author Dishant
 * @author Min
 * @author Daniel
 */
public class MainRunner {
    static String USER = " ";
    static String PASS = " ";
    static String DBNAME = "323Project";
    static String DB_URL = "jdbc:derby://localhost:1527/";
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
        /**
         * Make connection and Run the Menu
         * @param args Nothing to see here
         */
        public static void main(String args[]) {
        DB_URL = DB_URL + DBNAME;
        Connection conn = null; //initialize the connection
         try{  
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            //Make Menu With proper connection
            Menu menu = new Menu(conn);
            //Start Menu
            menu.menuThing();
        } catch (SQLException se) { //Handle errors for JDBC
            System.out.println("Failed connection to DataBase"); //se.printStackTrace();
        } catch (ClassNotFoundException CNFE) { //Handle errors for Class.forName
             System.out.println("Can't find JDBC Driver Class"); //CNFE.printStackTrace();
        } finally { //finally block used to close resources
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                System.out.println("Trouble Closing Connection."); //se.printStackTrace();
            }//end finally try
        }//end try} while (option != 0); 
     }
}