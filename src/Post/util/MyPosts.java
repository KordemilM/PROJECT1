package Post.util;

import Post.entity.PostCom;
import entity.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class MyPosts {
    private static User user;

    public static void setUser(User user) {
        MyPosts.user = user;
    }

    public static void main(Scanner scanner, Connection connection) throws SQLException {
        ArrayList<PostCom> posts = AppContext.getPostComRepos().getAllPostsByUser(user.getUserName(), connection);
        while (true) {
            Menu.showPostsMenu(posts);
            String input = scanner.next();
            if (input.equals("Back"))
                break;
            int postNum = Integer.parseInt(input)-1;
            PostCom post = posts.get(postNum);
            System.out.println(
                    "Subject: " + post.getSubject() + "\n" +
                            "Content: " + post.getContent() + "\n" +
                            "Likes: " + post.getLikes() + "\n" +
                            "Views: " + post.getViews() + "\n" +
                            "Date: " + post.getDate() + "\n" +
                            "Parent: " + post.getParent() + "\n" +
                            "User: " + post.getUserName() + "\n" +
                            "Id: " + post.getId() + "\n"
            );
            if(user.getAccount() == 0){
                System.out.println("Show Stats: ");
                LinkedHashMap<Date,Integer> likeStats = AppContext.getPostComRepos().getLikes(post, connection);
                LinkedHashMap<Date,Integer> viewStats = AppContext.getPostComRepos().getViews(post, connection);
                for(Date date : likeStats.keySet()){
                    System.out.println("Date: " + date + " Likes: " + likeStats.get(date));
                }

                for(Date date : viewStats.keySet()){
                    System.out.println("Date: " + date + " Views: " + viewStats.get(date));
                }
            }
        }
    }
}
