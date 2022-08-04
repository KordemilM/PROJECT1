package utils;

import repository.SignUpIn;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    public static final Scanner scanner = new Scanner(System.in);

    public static void run(Connection connection) throws SQLException {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        databaseInitializer.createTables(connection);

        System.out.println("Hi " + "\n" +"WELCOME");

        while (true){
            System.out.println("""
                    1-Sign Up
                    2-Log in
                    3-Exit""");
            switch (scanner.next()){
                case "1":
                    SignUpIn.signUp(connection);
                    break;
                case "2":
                    if(SignUpIn.login(connection)==0)
                        FirstMenu.run(connection);
                    break;
                case "3":
                    System.exit(0);
                default:
                    System.out.println("invalid command");
            }
        }
    }

}
