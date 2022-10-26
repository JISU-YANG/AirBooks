package db;

import java.util.ArrayList;
import java.util.HashMap;



public class BookDB {
	private static HashMap<String, ArrayList<Integer>> loadBorBook = new HashMap<String, ArrayList<Integer>>();

	public static HashMap<String, ArrayList<Integer>> getLoadBorBook() {
		return loadBorBook;
	}
	public static void setLoadBorBook(HashMap<String, ArrayList<Integer>> loadBorBook) {
		BookDB.loadBorBook = loadBorBook;
	}
}

