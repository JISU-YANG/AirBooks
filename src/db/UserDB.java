package db;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDB {
	private static int nowUser = -1; //ID index 기억하고 있을 변수
	private static HashMap<String, ArrayList<Integer>> m = new HashMap<>();
	private static ArrayList<ArrayList<String>> loadUserDB = new ArrayList<>();

	public static ArrayList<ArrayList<String>> getLoadUserDB() {
		return loadUserDB;
	}

	public static void setLoadUserDB(ArrayList<ArrayList<String>> loadUserDB) {
		UserDB.loadUserDB = loadUserDB;
	}

	public static HashMap<String, ArrayList<Integer>> getM() {
		return m;
	}

	public static void setM(HashMap<String, ArrayList<Integer>> m) {
		UserDB.m = m;
	}

	public static int getNowUser() {
		return nowUser;
	}

	public static void setNowUser(int nowUser) {
		UserDB.nowUser = nowUser;
	}
}