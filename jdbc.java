package cecs.pkg323.java.term.project;

import java.sql.*;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 */
public class JDBC {
    static final String displayFormat="%-35s%-20s%-25s%-25s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/CECS_323_JDBC";
    static Scanner in = new Scanner(System.in);
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
            String Menu = ("Functions for this database\n-------------------------\n"
                + "1) List all writing groups\n"
                + "2) List all the data for a group specified by the user\n"
                + "3) List all publishers\n"
                + "4) List all the data for a publisher specified by the user\n"
                + "5) List all book titles\n"
                + "6) List all the data for a book specified by the user\n"
                + "7) Insert a new book\n"
                + "8) Insert a new publisher\n"
                + "9) Delete a book\n"
                + "10) Quit\n"
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
            else if (input == 10) {
                quit = false;
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
        System.out.printf("\n%-25s\n", "Group Name\n-------------------------");
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
            System.out.println("");
    }
    public static void listPublishers (Statement stmt) throws SQLException{
        String sql; 
        sql = "SELECT PublisherName FROM Publishers"; 
        ResultSet rs = stmt.executeQuery(sql);
        System.out.printf("\n%-25s\n", "Publisher Name\n-------------------------"); 
        while (rs.next()) { 
//Retrieve by column name 
            String publisherName = rs.getString("PublisherName"); 
            //String publisherAddress = rs.getString("PublisherAddress"); 
            //String publisherPhone = rs.getString("PublisherPhone"); 
            //String publisherEmail = rs.getString("PublisherEmail"); 
        System.out.printf("%-25s\n", 
                dispNull(publisherName));
        }
        System.out.println("");
    }
    public static void listBooks (Statement stmt) throws SQLException{
        String sql; 
        sql =  "SELECT BookTitle FROM Books";; 
        ResultSet rs = stmt.executeQuery(sql);
        System.out.printf("\n%-25s\n",  "Book Title\n-------------------------"); 
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
        System.out.println("");
    }
   public static void listSpecifiedWritingGroups () throws SQLException{
       Connection conn = DriverManager.getConnection(DB_URL);
       Statement stmt = conn.createStatement();
       String sql;
       
       System.out.println("Which group would you like to look up: \n"); 
       listWritingGroups(stmt);
       
       String Group = in.nextLine();
       sql = "SELECT * FROM WritingGroups WHERE GroupName =?";
       PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, Group);
       ResultSet rs = pstmt.executeQuery();
       boolean exist = rs.next();
       
       System.out.println("");
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
       System.out.println("\nPress Enter to continue");
       in.nextLine();
       }
    }

    public static void listSpecifiedBooks () throws SQLException{
      
       Connection conn = DriverManager.getConnection(DB_URL);
       Statement stmt = conn.createStatement();
       System.out.println("Which book would you like to look up: \n"); 
       listBooks(stmt);
       
       String BookName = in.nextLine();
       String sql;
       
       sql = "SELECT * FROM Books WHERE BookTitle = ?";
       PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, BookName);
       
       ResultSet rs = pstmt.executeQuery();
       boolean exist = rs.next();
       
       System.out.println("");
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
       System.out.println("\nPress Enter to continue");
       in.nextLine();
        }
    }
        public static void listSpecifiedPublishers () throws SQLException{
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        System.out.println("Which publisher would you like to look up: \n"); 
        listPublishers(stmt);
        String PublisherName = in.nextLine();
        String sql; 
        
        sql = "SELECT * FROM Publishers WHERE PublisherName =?"; 
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, PublisherName);
        
        ResultSet rs = pstmt.executeQuery();
        boolean exist = rs.next();
        
        System.out.println("");
        if (exist== false) {
                System.out.println("Specified publisher does not exist!");
       }
       else{
        System.out.printf(pdisplayFormat, "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email"); 
        while (exist) { 
//Retrieve by column name 
            String publisherName = rs.getString("PublisherName"); 
            String publisherAddress = rs.getString("PublisherAddress"); 
            String publisherPhone = rs.getString("PublisherPhone"); 
            String publisherEmail = rs.getString("PublisherEmail"); 
        System.out.printf(pdisplayFormat, 
                dispNull(publisherName), dispNull(publisherAddress), dispNull(publisherPhone), dispNull(publisherEmail));
        exist = rs.next();
            }
        System.out.println("\nPress Enter to continue");
        in.nextLine();
        }
    }
    public static void deleteSpecifiedBooks () throws SQLException{
        
       Connection conn = DriverManager.getConnection(DB_URL);
       Statement stmt = conn.createStatement();
       System.out.println("Which book would you like to delete: "); 
       listBooks(stmt);
       String BookName = in.nextLine();
       String sql;
       sql = "DELETE FROM Books WHERE BookTitle = ?";
       PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, BookName);
       int exist = pstmt.executeUpdate();
       if (exist <= 0){
           System.out.println("Specified book does not exist!\n");
       }
       else {
           System.out.println("\nBook has been deleted");
           System.out.println("\nPress Enter to continue");
           in.nextLine();
       }
    }
        public static void insertBook() throws SQLException
    {
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        
        try
        {
        listWritingGroups(stmt);
        
        System.out.println("Enter the group name:");
        String group = in.nextLine();
        System.out.println("\nEnter the book title:");
        String title = in.nextLine();
        listPublishers(stmt);
        System.out.println("Enter the publisher name:");
        String publisher = in.nextLine();
        System.out.println("\nEnter the year published:");
        int year = in.nextInt();
        System.out.println("\nEnter the number of pages:");
        int page = in.nextInt();

       String sql;
       if(!(groupCheck(group)&&publisherCheck(publisher)))
       {
           System.out.println(!groupCheck(group)
                   ?"The writing group does not exist":"");
           System.out.println(!publisherCheck(publisher)
                   ?"The publisher does not exist":"");
           System.out.println("Please try again\nPress Enter to continue");
           in.nextLine();
       }
       else if(conflictCheck(group,title) || conflictCheck2(title, publisher))
       {
           System.out.println(conflictCheck(group,title) 
                   ?"Duplicated book title in the writing group":"");
           System.out.println(conflictCheck2(title,publisher) 
                   ?"Duplicated book title in the publisher group":"");
           System.out.println("Please try again\nPress Enter to continue");
           in.nextLine();
       }
       else
       {
       sql = "INSERT INTO Books ( GroupName, BookTitle, PublisherName ,YearPublished, NumberPages) VALUES" + 
               "(?,?,?,?,?)";
       PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, group);
       pstmt.setString(2, title);
       pstmt.setString(3, publisher);
       pstmt.setInt(4, year);
       pstmt.setInt(5, page);
       
       pstmt.executeUpdate();
       System.out.println("Book has been inserted");
       System.out.println("\nPress Enter to continue");
       in.nextLine();
       }
        }
        catch(InputMismatchException exception)
        {
            System.out.println("Invalid integer input\nPlease try again\n");
            
        }   
    }
    public static void insertPublisher() throws SQLException
    {
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        
        Scanner in = new Scanner(System.in);
        System.out.println("\nEnter the publisher name: ");
        String pName = in.nextLine();
        System.out.println("\nEnter the publisher address: ");
        String pAddress = in.nextLine();
        System.out.println("\nEnter the publisher phone: ");
        String pPhone = in.nextLine();
        System.out.println("\nEnter the publisher email: ");
        String pEmail = in.nextLine();
        listPublishers(stmt);
        System.out.println("\nWhich publisher would you like to replace: ");
        String pReplace = in.nextLine();
     

       String sql;
        if(conflictCheck3(pName))
       {
           System.out.println(conflictCheck3(pName) 
                   ?"Publisher Name already exist! ":null);
       }
        else
        {      
            if(!conflictCheck3(pReplace)){
                System.out.println(!conflictCheck3(pReplace)
                   ?"The publisher does not exist":"");
            }
            else{
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
       System.out.println("Publisher has been replaced");
       System.out.println("\nPress Enter to continue");
       in.nextLine();
       
       }}
    }
    public static boolean conflictCheck(String g, String b) throws SQLException
    {
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement(); 
        String sql; 
        
        sql = "SELECT * FROM Books WHERE GroupName = ? AND BookTitle = ?"; 
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, g);
        pstmt.setString(2, b);
        
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }
    public static boolean conflictCheck2(String b, String p) throws SQLException
    {
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement(); 
        String sql; 

        sql = "SELECT * FROM Books WHERE BookTitle = ? AND PublisherName = ?"; 
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, b);
        pstmt.setString(2, p);

        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }
    public static boolean conflictCheck3(String p) throws SQLException
    {
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement(); 
        String sql; 

        sql = "SELECT PublisherName FROM Publishers WHERE PublisherName = ?"; 
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, p);


        ResultSet rs = pstmt.executeQuery();
        
        return rs.next();
    }
    public static boolean groupCheck(String g) throws SQLException
    {
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        String sql; 
        
        sql = "SELECT * FROM WritingGroups  WHERE GroupName =?"; 
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, g);
        
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }
    public static boolean publisherCheck(String p) throws SQLException
    {
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        String sql; 
        
        sql = "SELECT * FROM Publishers  WHERE PublisherName =?"; 
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, p);
        
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }
}//end FirstExample}
