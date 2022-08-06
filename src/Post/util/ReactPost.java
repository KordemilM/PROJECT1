package Post.util;

import Post.entity.PostCom;
import entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ReactPost {
    private User user;
    private PostCom post;

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(PostCom post) {
        this.post = post;
    }

    public void main(Scanner scanner, Connection connection) throws SQLException {
        boolean repeat = true;
        while (repeat) {
            System.out.println(
                    "Subject: " + post.getSubject() + "\n" +
                            "Content: " + post.getContent() + "\n" +
                            "Likes: " + post.getLikes() + "\n" +
                            "Date: " + post.getDate() + "\n" +
                            "User: " + post.getUserName() + "\n"
            );
            System.out.println(
                    "1-Like\n" +
                            "2-Comments\n" +
                            "3-Add comment\n" +
                            "4-Back\n"
            );
            switch (scanner.nextInt()) {
                case 1 -> {
                    AppContext.getPostComRepos().likePost(post, connection);
                    System.out.println("Liked");
                }
                case 2 -> {
                    Explore explore = new Explore();
                    explore.setUser(user);
                    explore.mainCom(post, scanner, connection);
                }
                case 3 -> {
                    AddPost.setUser(user);
                    AddPost.addComment(post, scanner, connection);
                }
                case 4 -> {
                    repeat = false;
                }
            }
        }
    }
}
