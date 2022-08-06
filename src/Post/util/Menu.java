package Post.util;

import Post.entity.PostCom;

import java.util.ArrayList;

public class Menu {

        public static void showLogMenu() {
            System.out.println(
                    "1-Add New user\n" +
                    "2-Login\n"+
                    "3-Exit");
        }

        public static void showMenu() {
            System.out.println(
                    "1-Add Post\n" +
                    "2-Posts\n"+
                    "3-MyPosts\n"+
                    "4-Follower Post\n"+
                    "5-Back");
        }

        public static void showPostsMenu(ArrayList<PostCom> posts) {
            System.out.println("0-Back");
            for (int i = 1; i <= posts.size(); i++) {
                System.out.println(i + "-" + posts.get(i-1));
            }
        }

        public static void showPostMenu() {
            System.out.println(
                    "1-Add Comment\n" +
                    "2-Comments\n"+
                    "3-Back");
        }

        public static void showCommentMenu() {
            System.out.println(
                    "1-Like\n" +
                    "2-Comment\n"+
                    "3-Back");
        }

        public static void addUserMenu() {
            System.out.println(
                    "Enter username\n" +
                    "Is this a Business account?(y/n)\n"+
                    "<-Back");
        }

        public static void login() {
            System.out.println(
                    "Enter username\n" +
                    "<-Back");
        }

        public static void addPostMenu() {
            System.out.println(
                "Enter post(subject,content)\n" +
                "<-Back");
        }

        public static void addCommentMenu() {
            System.out.println(
                "Enter comment(content)\n" +
                "<-Back");
        }
}
