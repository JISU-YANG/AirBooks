package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.BookDB;
import model.UserDB;

@SuppressWarnings("serial")
public class Home extends JPanel {
	@SuppressWarnings("unlikely-arg-type")
	public static JPanel loadView() {
		Layout.preview = "읽고 있는 도서";
		JPanel home = new JPanel(new GridLayout(3,1,10,10));

		home.setBorder(new EmptyBorder(10, 20, 30, 20));
		home.setBackground(new Color(0xF7F7F7));

		// 사용자 책 정보 출력하기
		for (int i = 0; i < 3; i++) {
			try {
				home.add(BookInfoPanel.loadBookInfo(BookDB.getLoadBorBook().get(UserDB.getLoadUserDB().get(UserDB.getNowUser()).get(i)).get(0), 2));
			} catch (Exception e) {}
		}

		return home;
	}
}
