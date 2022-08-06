package Post.util;

import Post.repository.PostComRepos;
import Post.repository.UserRepos;

import java.util.Scanner;

public class AppContext {
    private static User user;
    private static final DatabaseInitializer databaseInitializer = new DatabaseInitializer();
    private static final UserRepos userRepos = new UserRepos();
    private static final PostComRepos postComRepos = new PostComRepos();
    private static final Scanner stringScanner = new Scanner(System.in);
    private static final Scanner numberScanner = new Scanner(System.in);
    private static final Menu menu = new Menu();

    public AppContext() {
        user = new User();
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        AppContext.user = user;
    }

    public static DatabaseInitializer getDatabaseInitializer() {
        return databaseInitializer;
    }

    public static UserRepos getUserRepos() {
        return userRepos;
    }

    public static PostComRepos getPostComRepos() {
        return postComRepos;
    }

    public static Scanner getStringScanner() {
        return stringScanner;
    }

    public static Scanner getNumberScanner() {
        return numberScanner;
    }

    public static Menu getMenu() {
        return menu;
    }


}

