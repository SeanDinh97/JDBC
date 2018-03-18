package cecs.pkg323.java.term.project;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 */
public class JDBC {
    //  Database credentials
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are 
    //strings, but that won't always be the case.
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
            Boolean quit = true;
            String Menu = ("Functions for this database\n"
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
            while(quit)
            {
            System.out.println(Menu);
            int input = in.nextInt();
            if (input == 1) {
                listWritingGroups(stmt);
            }
            else if (input == 2) {
                listSpecifiedWritingGroups();
            }
            else if (input == 3) {
                listPublishers(stmt);
            }
            else if (input == 4) {
                listSpecifiedPublishers();
            }
            else if (input == 5) {
                listBooks(stmt);
            }
            else if (input == 6) {
                listSpecifiedBooks();
            }
            else if (input == 7) {
                insertBook();
            }
            else if (input == 8) {
                insertPublisher();
            }
            else if (input == 9) {
                deleteSpecifiedBooks();
            }
            else {
                System.out.println("Invalid input");
            }}
            
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
            sql = "SELECT GroupName FROM WritingGroups";
            ResultSet rs = stmt.executeQuery(sql);
        System.out.printf("%-25s\n", "Group Name");
            while (rs.next()) {
                //Retrieve by column name
                String groupName = rs.getString("GroupName");
                //String headWriter = rs.getString("HeadWriter");
                //String yearFormed = rs.getString("YearFormed");
                //String subject = rs.getString("Subject");

                //Display values
                System.out.printf("%-25s\n", 
                        dispNull(groupName));
        }
    }
    public static void listPublishers (Statement stmt) throws SQLException{
        String sql; 
        sql = "SELECT PublisherName FROM Publishers"; 
        ResultSet rs = stmt.executeQuery(sql);
        System.out.printf("%-25s\n", "Publisher Name"); 
        while (rs.next()) { 
//Retrieve by column name 
            String publisherName = rs.getString("PublisherName"); 
            //String publisherAddress = rs.getString("PublisherAddress"); 
            //String publisherPhone = rs.getString("PublisherPhone"); 
            //String publisherEmail = rs.getString("PublisherEmail"); 
        System.out.printf("%-25s\n", 
                dispNull(publisherName));
        }
    }
    public static void listBooks (Statement stmt) throws SQLException{
        String sql; 
        sql =  "SELECT BookTitle FROM Books";; 
        ResultSet rs = stmt.executeQuery(sql);
        System.out.printf("%-25s\n",  "Book Title"); 
        while (rs.next()) { 
//Retrieve by column name 
            //String groupName = rs.getString("GroupName"); 
            String bookTitle = rs.getString("BookTitle"); 
            //String publisherName = rs.getString("PublisherName"); 
            //String yearPublished = rs.getString("YearPublished");
            //String numberOfPages = rs.getString("NumberPages");
        System.out.printf("%-25s\n", 
                 dispNull(bookTitle));
        }
    }
   public static void listSpecifiedWritingGroups () throws SQLException{
      
       System.out.println("Which group would you like to look up: "); 
       String Group = in.nextLine();
       String sql;
       Connection conn = DriverManager.getConnection(DB_URL);
       
       sql = "SELECT * FROM WritingGroups WHERE GroupName =?";
       PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, Group);
       
       ResultSet rs = pstmt.executeQuery();
       boolean exist = rs.next();
       if (exist == false) {
                System.out.println("Specified group does not exist!");
       }
       else{
       
       System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
       //System.out.println(rs.next());
       while (exist) {
                //Retrieve by column name
                String groupName = rs.getString("GroupName");
                String headWriter = rs.getString("HeadWriter");
                String yearFormed = rs.getString("YearFormed");
                String subject = rs.getString("Subject");

                //Display values
                System.out.printf(displayFormat, 
                        dispNull(groupName), dispNull(headWriter), dispNull(yearFormed), dispNull(subject));
                exist = rs.next();
                        }
       }
    }

    public static void listSpecifiedBooks () throws SQLException{
      
       System.out.println("Which book would you like to look up: "); 
       String BookName = in.nextLine();
       String sql;
       Connection conn = DriverManager.getConnection(DB_URL);
       
       sql = "SELECT * FROM Books WHERE BookTitle = ?";
       PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, BookName);
       
       ResultSet rs = pstmt.executeQuery();
       boolean exist = rs.next();
       if (exist == false) {
                System.out.println("Specified book does not exist!");
       }
       else{
       System.out.printf(displayFormat, "Group Name", "Book Title", "Publisher Name" ,"Year Published", "Number of Pages"); 
       while (exist) { 
//Retrieve by column name 
            String groupName = rs.getString("GroupName"); 
            String bookTitle = rs.getString("BookTitle"); 
            String publisherName = rs.getString("PublisherName"); 
            String yearPublished = rs.getString("YearPublished");
            String numberOfPages = rs.getString("NumberPages");
        System.out.printf(displayFormat, 
                dispNull(groupName), dispNull(bookTitle), dispNull(publisherName), dispNull(yearPublished), dispNull(numberOfPages));
        exist = rs.next();
            }
        }
    }
        public static void listSpecifiedPublishers () throws SQLException{
        System.out.println("Which publisher would you like to look up: "); 
        String PublisherName = in.nextLine();
        String sql; 
        Connection conn = DriverManager.getConnection(DB_URL);
        
        sql = "SELECT * FROM Publishers WHERE PublisherName =?"; 
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, PublisherName);
        
        ResultSet rs = pstmt.executeQuery();
        boolean exist = rs.next();
        if (exist== false) {
                System.out.println("Specified publisher does not exist!");
       }
       else{
        System.out.printf(displayFormat, "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email"); 
        while (exist) { 
//Retrieve by column name 
            String publisherName = rs.getString("PublisherName"); 
            String publisherAddress = rs.getString("PublisherAddress"); 
            String publisherPhone = rs.getString("PublisherPhone"); 
            String publisherEmail = rs.getString("PublisherEmail"); 
        System.out.printf(displayFormat, 
                dispNull(publisherName), dispNull(publisherAddress), dispNull(publisherPhone), dispNull(publisherEmail));
        exist = rs.next();
            }
        }
    }
    public static void deleteSpecifiedBooks () throws SQLException{
        
       Connection conn = DriverManager.getConnection(DB_URL); 
       System.out.println("Which book would you like to delete: "); 
       String BookName = in.nextLine();
       String sql;
       sql = "DELETE FROM Books WHERE BookTitle = ?";
       PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, BookName);
       pstmt.executeUpdate();
    }
    public static void insertBook() throws SQLException
    {
        Connection conn = DriverManager.getConnection(DB_URL);
        
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the group name:");
        String group = in.nextLine();
        System.out.println("Enter the book title:");
        String title = in.nextLine();
        System.out.println("Enter the publisher name:");
        String publisher = in.nextLine();
        System.out.println("Enter the year published:");
        String year = in.nextLine();
        System.out.println("Enter the number of pages:");
        String page = in.nextLine();

       String sql;
       
       sql = "INSERT INTO Books ( GroupName, BookTitle, PublisherName ,YearPublished, NumberPages) VALUES" + 
               "(?,?,?,?,?)";
       PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, group);
       pstmt.setString(2, title);
       pstmt.setString(3, publisher);
       pstmt.setString(4, year);
       pstmt.setString(5, page);
       
       int check = pstmt.executeUpdate();
    }
    public static void insertPublisher() throws SQLException
    {
        Connection conn = DriverManager.getConnection(DB_URL);
        
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the publisher name: ");
        String pName = in.nextLine();
        System.out.println("Enter the publisher address: ");
        String pAddress = in.nextLine();
        System.out.println("Enter the publisher phone: ");
        String pPhone = in.nextLine();
        System.out.println("Enter the publisher email: ");
        String pEmail = in.nextLine();
        System.out.println("Which publisher would you like to replace: ");
        String pReplace = in.nextLine();
     

       String sql;
       
       sql = "INSERT INTO Publishers ( PublisherName ,PublisherAddress, PublisherPhone, PublisherEmail) VALUES" + 
               "(?,?,?,?)";
       PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, pName);
       pstmt.setString(2, pAddress);
       pstmt.setString(3, pPhone);
       pstmt.setString(4, pEmail);
       pstmt.executeUpdate();
       
       String sql2;
       sql2 = "UPDATE Books SET PublisherName = ? WHERE PublisherName = ?";
       PreparedStatement pstmt2 = conn.prepareStatement(sql2);
       pstmt2.setString(1, pName);
       pstmt2.setString(2, pReplace);
       pstmt2.executeUpdate();
    }
}//end FirstExample}
