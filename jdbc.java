package cecs.pkg323.java.term.project;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 */
public class JDBC {
    static Scanner in = new Scanner(System.in);
    static final String displayFormat="%-35s%-50s%-25s%-25s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/CECS_323_JDBC";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    public static void main(String[] args) throws SQLException {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to 
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            System.out.println("Functions for this database\n"
                + "1) List all writing groups\n"
                + "2) List all the data for a group specified by the user\n"
                + "3) List all publishers\n"
                + "4) List all the data for a publisher specified by the user\n"
                + "5) List all book titles\n"
                + "6) List all the data for a book specified by the user\n"
                + "7) Insert a new book\n"
                + "8) Insert a new publisher\n"
                + "9) Delete a book\n"
                + "Enter your choice");
            listWritingGroups(stmt);
            listPublishers(stmt);
            listBooks(stmt);
            listSpecifiedPublishers(stmt);
            listSpecifiedWritingGroups(stmt);
            listSpecifiedBooks(stmt);
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
    public static void listWritingGroups (Statement stmt) throws SQLException{
       String sql;
            sql = "SELECT GroupName, HeadWriter, YearFormed, Subject FROM WritingGroups";
            ResultSet rs = stmt.executeQuery(sql);
        System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
            while (rs.next()) {
                //Retrieve by column name
                String groupName = rs.getString("GroupName");
                String headWriter = rs.getString("HeadWriter");
                String yearFormed = rs.getString("YearFormed");
                String subject = rs.getString("Subject");

                //Display values
                System.out.printf(displayFormat, 
                        dispNull(groupName), dispNull(headWriter), dispNull(yearFormed), dispNull(subject));
        }
    }
    public static void listPublishers (Statement stmt) throws SQLException{
        String sql; 
        sql = "SELECT PublisherName, PublisherAddress, PublisherPhone, PublisherEmail FROM Publishers"; 
        ResultSet rs = stmt.executeQuery(sql);
        System.out.printf(displayFormat, "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email"); 
        while (rs.next()) { 
//Retrieve by column name 
            String publisherName = rs.getString("PublisherName"); 
            String publisherAddress = rs.getString("PublisherAddress"); 
            String publisherPhone = rs.getString("PublisherPhone"); 
            String publisherEmail = rs.getString("PublisherEmail"); 
        System.out.printf(displayFormat, 
                dispNull(publisherName), dispNull(publisherAddress), dispNull(publisherPhone), dispNull(publisherEmail));
        }
    }
    public static void listBooks (Statement stmt) throws SQLException{
        String sql; 
        sql =  "SELECT GroupName, BookTitle, PublisherName, YearPublished, NumberPages FROM Books";; 
        ResultSet rs = stmt.executeQuery(sql);
        System.out.printf(displayFormat, "Group Name", "Book Title", "Publisher Name" ,"Year Published", "Number of Pages"); 
        while (rs.next()) { 
//Retrieve by column name 
            String groupName = rs.getString("GroupName"); 
            String bookTitle = rs.getString("BookTitle"); 
            String publisherName = rs.getString("PublisherName"); 
            String yearPublished = rs.getString("YearPublished");
            String numberOfPages = rs.getString("NumberPages");
        System.out.printf(displayFormat, 
                dispNull(groupName), dispNull(bookTitle), dispNull(publisherName), dispNull(yearPublished), dispNull(numberOfPages));
        }
    }
    public static void listSpecifiedWritingGroups (Statement stmt) throws SQLException{
      
       System.out.println("Which group would you like to look up: "); 
       String Group = in.nextLine();
       String sql;
       sql = "SELECT * FROM WritingGroups WHERE GroupName ='"+Group+"'";
       ResultSet rs = stmt.executeQuery(sql);
       System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
       while (rs.next()) {
                //Retrieve by column name
                String groupName = rs.getString("GroupName");
                String headWriter = rs.getString("HeadWriter");
                String yearFormed = rs.getString("YearFormed");
                String subject = rs.getString("Subject");

                //Display values
                System.out.printf(displayFormat, 
                        dispNull(groupName), dispNull(headWriter), dispNull(yearFormed), dispNull(subject));
        }
    }
    public static void listSpecifiedPublishers (Statement stmt) throws SQLException{
        System.out.println("Which publisher would you like to look up: "); 
        String PublisherName = in.nextLine();
        String sql; 
        sql = "SELECT * FROM Publishers WHERE PublisherName ='"+PublisherName+"'"; 
        ResultSet rs = stmt.executeQuery(sql);
        System.out.printf(displayFormat, "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email"); 
        while (rs.next()) { 
//Retrieve by column name 
            String publisherName = rs.getString("PublisherName"); 
            String publisherAddress = rs.getString("PublisherAddress"); 
            String publisherPhone = rs.getString("PublisherPhone"); 
            String publisherEmail = rs.getString("PublisherEmail"); 
        System.out.printf(displayFormat, 
                dispNull(publisherName), dispNull(publisherAddress), dispNull(publisherPhone), dispNull(publisherEmail));
        }
    }
    public static void listSpecifiedBooks (Statement stmt) throws SQLException{
      
       System.out.println("Which book would you like to look up: "); 
       String BookName = in.nextLine();
       String sql;
       sql = "SELECT * FROM Books WHERE BookTitle ='"+BookName+"'";
       ResultSet rs = stmt.executeQuery(sql);
       System.out.printf(displayFormat, "Group Name", "Book Title", "Publisher Name" ,"Year Published", "Number of Pages"); 
       while (rs.next()) { 
//Retrieve by column name 
            String groupName = rs.getString("GroupName"); 
            String bookTitle = rs.getString("BookTitle"); 
            String publisherName = rs.getString("PublisherName"); 
            String yearPublished = rs.getString("YearPublished");
            String numberOfPages = rs.getString("NumberPages");
        System.out.printf(displayFormat, 
                dispNull(groupName), dispNull(bookTitle), dispNull(publisherName), dispNull(yearPublished), dispNull(numberOfPages));
        }
    }
}//end FirstExample}
