package Chat;

import repository.SignUpIn;

import java.sql.*;
import java.util.*;

import static Chat.messenger.Conn;

public class messenger {
    public static Connection Conn;
    public static void run() throws SQLException {

        Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?autoReconnect=true&useSSL=false","root","123456890");
        Statement s = Conn.createStatement();
        s.executeUpdate("CREATE DATABASE IF NOT EXISTS `chats` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;\n");
        Database.insert("chats", "CREATE TABLE IF NOT EXISTS `chat_info` ("+
                  "`chat_id` int NOT NULL AUTO_INCREMENT,"+
                  "`chat_name` varchar(45) NOT NULL DEFAULT 'new_chat',"+
                  "`members` varchar(225) NOT NULL,"+
                  "PRIMARY KEY (`chat_id`)"+
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;"
                );

        while (true) {
            if(!chat.menu(SignUpIn.onlineUser_username))
                break;
        }
    }

}

class chat{
    public static List<Integer> printChats(String username) throws SQLException {
        ResultSet rs = Database.select("chats","SELECT * FROM chat_info");
        List<Integer> validChats = new ArrayList<>();
        while(rs.next()){

            if(rs.getString("members").matches("^"+username+",.*|.*,"+username+",.*|.*,"+username+"$")){
                System.out.println(rs.getInt("chat_id")+" : "+rs.getString("chat_name"));
                validChats.add(rs.getInt("chat_id"));
            }
        }
        return validChats;
    }
    public static void chatroom (String username , int chat_id) throws SQLException {
        String chatName = "chat_" + chat_id;
        ResultSet rs = Database.select("chats","SELECT * FROM project.user RIGHT JOIN "+chatName+" ON user.user_name = "+chatName+".sender_username");

        while (rs.next()) {
            Timestamp timestamp = rs.getTimestamp("datetime");
            if (username.equals(rs.getString("sender_username"))) {
                System.out.println(rs.getInt("message_id")+". "+"You: " + rs.getString("content") + " " + timestamp);
            } else {
                System.out.println(rs.getInt("message_id")+". "+rs.getString("name")+" : " + rs.getString("content") + " " + timestamp);
            }
        }
        boolean bool = true;
        while (bool){
            System.out.println("You : ");
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();

            switch (message){
                case "exit" -> bool = false;
                case "->forward" ->{

                    System.out.println("choose a message_id you want to forward :");

                    int forward_id;
                    int sender_username;
                    String content;
                    while (true){

                    forward_id = new Scanner(System.in).nextInt();

                    //check the id
                    ResultSet rsf = Database.select("chats",
                            "SELECT * FROM "+chatName+
                                    " LEFT JOIN project.user ON project.user.user_name = "+chatName+".sender_username" +
                                    " WHERE message_id = "+forward_id);

                    if(rsf.next()){
                        sender_username = rsf.getInt("message_id");
                        String forward_label = "[forwarded from " +rsf.getString("name") +" ] ";
                        content = forward_label + rsf.getString("content");
                        break;
                    }
                    else
                        System.out.println("Wrong message ID . Please Try again");
                    }

                    System.out.println("choose a chatroom you want to send this massage");

                    List<Integer> validChats = printChats(username);

                    int forward_chat_id;
                    String destination_chat_table_name;
                    while (true) {
                        forward_chat_id = new Scanner(System.in).nextInt();
                        if (validChats.contains(forward_chat_id)){
                            destination_chat_table_name = "chat_"+forward_chat_id;
                            break;
                        }
                        else {
                            System.out.println("invalid chat ID");
                        }
                    }
                    Database.insert("chats",
                            "INSERT INTO "+destination_chat_table_name+
                                    " (sender_username,content,user_username_forwarded_from) " +
                                    "VALUES ('"+ username  + "', '" +content+"', '"+sender_username+ "' )");

                    System.out.println("forwarded");
                }
                case "->reply" ->{
                    System.out.println("choose a message_id you want to reply on :");
                    int reply_message_id;
                    String reply_label;
                    while (true) {
                        reply_message_id = new Scanner(System.in).nextInt();
                        //check the id
                        ResultSet rsf = Database.select("chats",
                                "SELECT * FROM "+chatName+
                                        " JOIN project.user u ON u.user_name = "+chatName+".sender_username" +
                                        " WHERE message_id = "+reply_message_id);

                        if(rsf.next()){
                            reply_message_id = rsf.getInt("message_id");
                            reply_label = "[reply to { " +rsf.getString("content") +" } , FROM " + rsf.getString("name") +" ]";
                            System.out.println("massage selected");
                            break;
                        }
                        else
                            System.out.println("Wrong message ID . Please Try again");
                    }

                    System.out.println("You :");
                    String content = new Scanner(System.in).nextLine();
                    content = reply_label + content;
                    Database.insert("chats",
                            "INSERT INTO "+chatName+
                                    " (sender_username,content,message_id_reply_to) " +
                                    "VALUES ( '"+ username  + "' , '" +content+"',"+reply_message_id+ ")");
                }
                default -> Database.insert("chats","INSERT INTO "+chatName+" (sender_username,content) VALUES ('" + username + "', '" + message + "')");

            }
        }


    }

    public static boolean menu (String username) throws SQLException {

        System.out.println("1. Create a chatroom");

        System.out.println("2. show recent chats");

        System.out.println("3. personal");

        System.out.println("4. Exit");

        String choice = new Scanner(System.in).nextLine();

        switch (choice) {

            case "1" -> {
                System.out.println("Add members to chatroom like this: username,username,username,...");

                String members = new Scanner(System.in).nextLine();

                if (!members.matches("^"+username+",.*|.*,"+username+",.*|.*,"+username+"$"))
                    members = members + "," + username ;

                System.out.println("Enter chatroom name :");

                String chatName = new Scanner(System.in).nextLine();

                Database.insert("chats", "INSERT INTO chat_info (chat_name,members) VALUES ('" + chatName + "','" + members + "')");

                try {
                    ResultSet nrs = Database.select("chats", "SELECT chat_id FROM chat_info ORDER BY chat_id desc limit 1");
                    int chat_id;
                    if (nrs.next()) {
                        chat_id = nrs.getInt("chat_id");
                    }else {
                        chat_id = 0;
                    }
                    System.out.println("Chatroom created");
                    Database.insert("chats", "CREATE TABLE `chat_"+chat_id+"` (\n" +
                            "  `message_id` int NOT NULL AUTO_INCREMENT,\n" +
                            "  `content` varchar(200) DEFAULT NULL,\n" +
                            "  `sender_username` varchar(45) NOT NULL,\n" +
                            "  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                            "  `message_id_reply_to` int DEFAULT NULL,\n" +
                            "  `user_username_forwarded_from` varchar(45) DEFAULT NULL,\n" +
                            "  PRIMARY KEY (`message_id`)\n" +
                            ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n");

                    return true;

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }

            case "2" -> {

                boolean bool = true;

                while(bool) {

                    List<Integer> validChats = printChats(username);

                    System.out.println("Enter chatroom id (0 for go back) :");

                    while (true) {

                        int chat_id = new Scanner(System.in).nextInt();

                        if (chat_id == 0) {
                            bool = false;
                            break;
                        } else if (!validChats.contains(chat_id)) {
                            System.out.println("chatroom not found");
                        } else {
                            chatroom(username, chat_id);
                            break;
                        }
                    }
                }
                return true;
            }

            case "3" -> {
                System.out.println("Choose one of your previous chats or enter new username to start chat : ( 'cancel' for going back )");
                privateChat(username);
                return true;
            }

            case "4" -> {return false;}

            default -> {
                System.out.println("Invalid choice");
                return true;
            }

        }
    }

    private static void privateChat(String username) throws SQLException {
        PreparedStatement state = Conn.prepareStatement("SELECT `chat_id`,`members`" +
                "FROM `chats`.`chat_info`" +
                "WHERE chat_name = '[PRIVATE]' AND" +
                "( members Like ? OR members Like ?)");
        state.setString(1, username + "%");
        state.setString(2, "%" +username);
        ResultSet rs = state.executeQuery();
        Map<String, Integer> existedChats = new HashMap<>();
        boolean hasPrevious = false;
        while (rs.next()){
            String person = rs.getString("members").replace(username,"").replace(",","");
            System.out.println(person);
            existedChats.put(person,rs.getInt("chat_id"));
            hasPrevious = true;
        }
        if (!hasPrevious)
            System.out.println("you don't have any private chats recently );");
        String input;
        while(true){
            input = new Scanner(System.in).next();
            ResultSet resultSet = Conn.createStatement().executeQuery("SELECT * FROM `project`.`user` " +
                    "WHERE user_name = '"
                    +input+"'");
            if(input.equals("cancel")){
                return;
            }
            if(!resultSet.next()){
                System.out.println("username not found! Please try again");
            }
            else if(input.equals(username)){
                System.out.println("Please chat with yourself some where else XD.try again");
            }else break;
        }
        if(existedChats.containsKey(input)){
            chatroom(username,existedChats.get(input));
        }else{
            int lastID = 1;
            ResultSet lastIdSet = Conn.createStatement().executeQuery("SELECT chat_id FROM `chats`.`chat_info` ORDER BY chat_id DESC LIMIT 1");
            if (lastIdSet.next()) {
                lastID+= lastIdSet.getInt("chat_id");
            }
            String chatName = "chat_"+lastID;
            PreparedStatement st = Conn.prepareStatement("CREATE TABLE `chats`.`"+chatName+"` ("
                      +"`message_id` int NOT NULL AUTO_INCREMENT, "
                      +"`content` varchar(200) DEFAULT NULL, "
                      +"`sender_username` varchar(45) NOT NULL, "
                      +"`datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, "
                      +"`message_id_reply_to` int DEFAULT NULL, "
                      +"`user_username_forwarded_from` varchar(45) DEFAULT NULL, "
                      +"PRIMARY KEY (`message_id`) "
                      +") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
            st.executeUpdate();
            PreparedStatement result = Conn.prepareStatement("INSERT INTO `chats`.`chat_info`\n" +
                    "(`chat_name`,\n" +
                    "`members`)\n" +
                    "VALUES\n" +
                    "('[PRIVATE]',\n" +
                    "?);\n");
            result.setString(1,username+","+input);
            result.executeUpdate();
            chatroom(username, lastID);
        }
    }
}

class Database{
    public static ResultSet select(String schema , String command){
        try {

            Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+schema+"?autoReconnect=true&useSSL=false","root","123456890");

            Statement myStatement = myConnection.createStatement();

            return myStatement.executeQuery(command);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void insert(String schema , String command){
        try {


            Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+schema+"?autoReconnect=true&useSSL=false","root","123456890");

            Statement myStatement = myConnection.createStatement();

            myStatement.executeUpdate(command);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}