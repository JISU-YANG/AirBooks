package dao;

import model.PathDB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BookInfo {
	static Scanner scan = new Scanner(System.in);

	static int bookCnt = 1;
	static ArrayList<ArrayList<String>> bookList = new ArrayList<>();

	public static void createFile(String FilePath) {//파일 생성
		try {
			System.out.println(FilePath);

			int nLast = FilePath.lastIndexOf("\\");
			String strDir = FilePath.substring(0, nLast);
			String strFile = FilePath.substring(nLast+1, FilePath.length());

			File dirFolder = new File(strDir);
			dirFolder.mkdir();
			File f = new File(dirFolder, strFile);
			f.createNewFile();
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private static String readFileText(File file) {//파일 읽기
		String strText="";
		int nBuffer;
		try {
			BufferedReader buffRead = new BufferedReader(new FileReader(file));
			while((nBuffer = buffRead.read()) != -1){
				strText += (char)nBuffer;
			}
			buffRead.close();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return strText;
	}

	@SuppressWarnings("unused")
	private void addFile(String Text) {//파일 수정
		try {
			File f = new File(PathDB.bookDB);
			if(f.exists() == false) {
				createFile(PathDB.bookDB);
			}

			String fileText = readFileText(f);
			BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));
			Text = fileText+"\r\n"+Text;
			buffWrite.write(Text,0,Text.length());
			buffWrite.flush();
			buffWrite.close();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}

	}


	public static ArrayList<ArrayList<String>> takeBookDB(String fpath) {
		bookList.removeAll(bookList);
		try {
			File f = new File(fpath);
			String result = readFileText(f);
			String[] copy = result.split("/");
			// 파일에서 가져와서 arrayList에 닮기
			for(int i = 0; i<copy.length/7; i++) {
				ArrayList<String> temp = new ArrayList<>();
				for(int j = 0; j < 6; j++) {
					temp.add(copy[j + i*7]);
				}
				bookList.add(temp);
			}
		} catch (Exception e) {}
		return bookList;
	}

	public static int bestSeller() {
		takeBookDB(PathDB.bookDB);
		int max = Integer.parseInt(bookList.get(0).get(5));
		int bestIndex =0;
		for (int i = 0; i <bookList.size(); i++) {
			if(Integer.parseInt(bookList.get(i).get(5)) >max) {
				max = Integer.parseInt(bookList.get(i).get(5));
				bestIndex = i;
			}
		}
		return bestIndex;
	}
	public static ArrayList<Integer> searchBook(String search) {
		takeBookDB(PathDB.bookDB);
		ArrayList<Integer> bookListNum = new ArrayList<>();
		for(int i = 0; i < bookList.size(); i++) {
			for(int j = 0; j < bookList.get(i).size(); j++) {
				if(bookList.get(i).get(j).contains(search)) {
					bookListNum.add(i);
					break;
				}
			}
		}
		return bookListNum;
	}

	public static String takeInfo(int index, int info) {
		takeBookDB(PathDB.bookDB);
		String result = bookList.get(index-1).get(info+1);

		return result;
	}
}
