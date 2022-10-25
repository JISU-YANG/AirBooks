package model;

public final class PathDB {
    private static String defaultPath = System.getProperty("user.home") + "/airbooks";
    public static String userDB = defaultPath + "/txt/userDB.txt";
    public static String bookDB = defaultPath + "/txt/bookDB.txt";
    public static String borrowDB = defaultPath + "/txt/borrowBookList.txt";

    public static String getImagePath(String imageName){
        System.out.println(defaultPath + "/img/" + imageName + ".png");
        return defaultPath + "/img/" + imageName + ".png";
    }
    public static String getBookImagePath(int bookIndex){
        return defaultPath + "/img/thumb/bookImage_" + bookIndex + ".png";
    }
}
