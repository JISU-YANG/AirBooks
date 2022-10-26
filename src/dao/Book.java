package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import db.BookDB;
import db.PathDB;
import db.UserDB;
public class Book {
	public static void borrowBook(int wantBook) {
		int flag = 0;
		int index = 0;
		ArrayList<String> bookInfo = new ArrayList<>();

		//HashMap에 저장 후, txt파일에 담아야함
		Calendar calender = Calendar.getInstance();
		int year = calender.get(Calendar.YEAR) - 1900;
		int month = calender.get(Calendar.MONTH);
		int day = calender.get(Calendar.DATE) + 7;

		Set<java.util.Map.Entry<String, ArrayList<Integer>>> set = BookDB.getLoadBorBook().entrySet();
		Iterator<java.util.Map.Entry<String, ArrayList<Integer>>> iter = set.iterator();
		while(iter.hasNext()) {
			Map.Entry<String, ArrayList<Integer>> e  = iter.next();
			if(e.getValue()!=null) {
				for (int i = 0; i < e.getValue().size(); i+=4) {
					if(e.getValue().get(i)==wantBook) {
						flag++;
					}
				}
			}
		}

		if(flag!=0) {
			System.out.println("이미 대여중인 책입니다.(대여불가능)");
		}else {
			//iter쓰면 안됨!!
			if (BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)) != null) {
				if (BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).size() > 12) {
					System.out.println("최대 대여가능 권수 초과");
				} else {
					BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).add(wantBook);
					BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).add(year);
					BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).add(month);
					BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).add(day);
					BookDB.getLoadBorBook().put(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0),
							BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)));
					addBorBookFile();
				}
			} else {
				System.out.println("NO");
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(wantBook);
				temp.add(year);
				temp.add(month);
				temp.add(day);
				BookDB.getLoadBorBook().put(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0), temp);
				addBorBookFile();
			}
		}
	}

	public static void returnBook(int wantBook) {
		//HashMap에서 해당 책 정보 지운 후, txt 파일에도 적용시켜줌
		int flag = 0;
		int index = 0;

		if(BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0))!=null) {
			for (int i = 0; i < BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).size(); i+=4) {
				if(BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).get(i)==wantBook) {
					flag++;
					index = i;
				}
			}
			if(flag!=0) {
				for (int i = 0; i < 4; i++) {
					BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).remove(index);
				}
				addBorBookFile();
			}
			else {
				System.out.println("접근 권한이 없습니다.");
			}
		}else {
			System.out.println("접근 권한이 없습니다.");
		}
	}

	public static void delayDate(int wantBook) {	//반납 기한을 연장하고 싶은 책의 인덱스를 받아옴
		int flag = 0;
		int index = 0;
		int dayValue = 0;

		if(BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0))!=null) {
			for (int i = 0; i < BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).size(); i+=4) {
				if(BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).get(i)==wantBook) {
					flag++;
					index = i;
				}
			}
			if(flag!=0) {
				index+=3;
				dayValue = BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).get(index) + 7;
				BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(0)).set(index, dayValue);
				addBorBookFile();
			}
			else {
				System.out.println("접근 권한이 없습니다.");
			}
		}else {
			System.out.println("접근 권한이 없습니다.");
		}
	}

	public static void createFile(String FilePath) {//파일 생성
		try {
			File f = new File(FilePath);
			f.createNewFile();
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static String readFileText(File file) {//파일 읽기
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

	public static void addUserFile(String Text, String fpath) {//파일 수정
		try {
			File f = new File(fpath);
			if(f.exists() == false) {
				createFile(fpath);
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

	public static void addBorBookFile() {
		try {
			File f = new File(PathDB.borrowDB);
			if(f.exists() == false) {
				createFile(PathDB.borrowDB);
			}

			String fileText = "";
			BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));
			Set<java.util.Map.Entry<String, ArrayList<Integer>>> set = null;
			set = BookDB.getLoadBorBook().entrySet();
			Iterator<java.util.Map.Entry<String, ArrayList<Integer>>> iter = set.iterator();
			while(iter.hasNext()) {
				Map.Entry<String, ArrayList<Integer>> e  = iter.next();
				fileText += "\r\n"+"/"+e.getKey()+"/";
				if(e.getValue()!=null) {
					for (int i = 0; i < e.getValue().size(); i++) {
						if(i%4==0) {
							fileText += "\r\n"+"/";
						}
						fileText +=  e.getValue().get(i)+"/";
					}
				}
			}
			buffWrite.write(fileText,0,fileText.length());
			buffWrite.flush();
			buffWrite.close();
			takeBorrowList(PathDB.borrowDB);
		}catch(Exception ex) {System.out.println(ex.getMessage());}
	}

	public static void takeUserDB(String fpath) {
		try {
			File f = new File(fpath);
			String result = readFileText(f);

			String[] copy = result.split("/");

			// 파일에서 가져와서 arrayList에 담기
			for (int i = 0; i < copy.length / 3; i++) {
				ArrayList<String> temp = new ArrayList<>();
				for (int j = 0; j < 3; j++) {
					if ((j + i * 3) % 3 != 0) {
						temp.add(copy[j + i * 3]);
					}
				}
				UserDB.getLoadUserDB().add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void takeBorrowList(String fpath) {
		try {
			File f = new File(fpath);
			String result = readFileText(f);

			String[] copy = result.split("/");
			int index = 0;
			int saveIdx = 0;
			int flag = 0;

			// 파일에서 가져와서 arrayList에 담기
			if(copy.length>2) {
				for(int i = 0; i<copy.length; i++) {
					ArrayList<Integer> temp = new ArrayList<Integer>();
					if(copy[i].equals(UserDB.getLoadUserDB().get(index).get(0))) {
						BookDB.getLoadBorBook().put(copy[i], null);
						saveIdx = i;
						if(i+1<copy.length) {
							if(copy[i+2].charAt(0)<'0' || copy[i+2].charAt(0)>'9') {
								index++;
							}
						}
					}else {
						if(copy[i].charAt(0)>='0' && copy[i].charAt(0)<='9') {
							for (int j = i; j < i+4; j++) {
								temp.add(Integer.parseInt(copy[j]));
								flag++;
							}
							i+=4;
							if(i+2<copy.length) {
								if((flag==4) && (copy[i+2].charAt(0)>='0' && copy[i+2].charAt(0)<='9')) {
									i++;
									for (int j = i; j < i+4; j++) {
										temp.add(Integer.parseInt(copy[j]));
										flag++;
									}
									i+=4;
									if(i+2<copy.length) {
										if((flag==8) && (copy[i+2].charAt(0)>='0' && copy[i+2].charAt(0)<='9')) {
											i++;
											for (int j = i; j < i+4; j++) {
												temp.add(Integer.parseInt(copy[j]));
												flag++;
											}
											i+=4;
										}
									}
								}
							}
						}
						if(flag==4||flag==8||flag==12) {
							BookDB.getLoadBorBook().put(copy[saveIdx], temp);
							index++;
							flag = 0;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
