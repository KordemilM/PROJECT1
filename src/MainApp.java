import utils.ConnectToTheDatabase;
import utils.Menu;
import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Menu.run(ConnectToTheDatabase.ConnectDatabase());
    }
}
