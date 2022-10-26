package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import db.BookDB;
import db.PathDB;
import db.UserDB;

public class User {
	public static boolean login(String id, String pw) {
		boolean isCheck = false; //로그인상태 체크
		for (int i = 0; i < UserDB.getLoadUserDB().size(); i++) {
			if (UserDB.getLoadUserDB().get(i).get(0).equals(id)) {
				if (pw.equals(UserDB.getLoadUserDB().get(i).get(1))) { //복호화된 pw와 같은지 확인
					isCheck = true; //로그인 성공
					UserDB.setNowUser(i);
				}
			}
		}
		return isCheck;
	}

	public static void logout() {
		UserDB.setNowUser(-1);
	}

	public static boolean sign(String Id, String Pw) {
		boolean isCheck = false;
		int idCheckDup = 0;

		if (UserDB.getLoadUserDB().size() != 0) {
			for (int i = 0; i < UserDB.getLoadUserDB().size(); i++) {
				if (UserDB.getLoadUserDB().get(i).get(0).equals(Id)) {
					idCheckDup++;
				}
			}
		}
		if (idCheckDup == 0) {
			Book.addUserFile("/"+Id+"/"+Pw+"/", PathDB.userDB);
			Book.addUserFile("/"+Id+"/", PathDB.borrowDB);
			UserDB.getLoadUserDB().clear();
			Book.takeUserDB(PathDB.userDB);
			BookDB.getLoadBorBook().put(Id, null);
			isCheck = true;
		}else {
			System.out.println("이미 사용중인 아이디입니다.");
		}
		return isCheck;
	}

	public static void changePW(String pw) {
		//로그인중인 id가 가진 비밀번호랑 입력한 비밀번호가 같으면
		//변경할 비밀번호 입력 후 id가 가진 비밀번호를 변경할 비밀번호로 업데이트
		String change = null;
		String now = null;

		System.out.println("변경할 암호를 입력하세요");
		change = pw;
		UserDB.getLoadUserDB().get(UserDB.getNowUser()).set(1, change);
		System.out.println(UserDB.getLoadUserDB().get(UserDB.getNowUser()));

		try {
			File f = new File(PathDB.userDB);
			String fileText = "";
			BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));

			for (int i = 0; i < UserDB.getLoadUserDB().size(); i++) {
				fileText += "\r\n"+"/"+UserDB.getLoadUserDB().get(i).get(0)+"/"+UserDB.getLoadUserDB().get(i).get(1)+"/";
			}
			buffWrite.write(fileText,0,fileText.length());
			buffWrite.flush();
			buffWrite.close();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}