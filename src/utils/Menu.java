package utils;

import repository.UserRepository;
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
            System.out.println("1-Sign Up" + "\n" +"2-Log in" + "\n" + "3-Exit");
            switch (Integer.parseInt(scanner.next())){
                case 1:
                    UserRepository.signUp(connection);
                    break;
                case 2:
                    if(UserRepository.login(connection)==0)
                        FirstMenu.run(connection);
                    break;
                default:
                    System.exit(0);
            }
        }

    }


}
