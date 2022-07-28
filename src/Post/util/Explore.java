package Post.util;

import Post.entity.PostCom;
import Post.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Explore {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void main(Scanner scanner, Connection connection) throws SQLException {
        ArrayList<PostCom> posts =  AppContext.getPostComRepos().getAllPostOtherThanUser(user.getName(), connection);
        while(true){
            Menu.showPostsMenu(posts);
            String input = scanner.next();
            if(input.equals("Back"))
                break;
            int postNum = Integer.parseInt(input)-1;
            PostCom post = posts.get(postNum);
            AppContext.getPostComRepos().addView(post, connection);
            ReactPost reactPost = new ReactPost();
            reactPost.setPost(post);
            reactPost.setUser(user);
            reactPost.main(scanner, connection);
        }
    }

    public void mainCom(PostCom post, Scanner scanner, Connection connection) throws SQLException {
        ArrayList<PostCom> posts =  AppContext.getPostComRepos().getChildren(post.getId(), connection);
        while(true){
            Menu.showPostsMenu(posts);
            String input = scanner.next();
            if(input.equals("Back"))
                break;
            int postNum = Integer.parseInt(input)-1;
            PostCom temp = posts.get(postNum);
            ReactPost reactPost = new ReactPost();
            reactPost.setPost(temp);
            reactPost.setUser(user);
            reactPost.main(scanner, connection);
        }
    }
}
