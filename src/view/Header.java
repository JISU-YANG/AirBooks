package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.BookInfo;
import model.PathDB;

@SuppressWarnings("serial")
public class Header extends JPanel {
	static ImageIcon [] arHdTitle = {new ImageIcon(PathDB.getImagePath("headTitle01")),
			new ImageIcon(PathDB.getImagePath("headTitle02")),
			new ImageIcon(PathDB.getImagePath("headTitle03"))};
	static ImageIcon hdSearch = new ImageIcon(PathDB.getImagePath("search_on"));

	public static boolean searchCheck = false;

	public static JPanel loadView() {
		// 설명 - 탐색하기 화면에서만 그리드 1열 더 추가
		int x = 1;
		if (Layout.preview == "탐색하기") x = 2;

		int y = 0;

		JPanel headerFull = new JPanel(new GridLayout(x,1));
		JPanel header = new JPanel(new BorderLayout());
		JLabel hdTitle = null;
		JTextField hdKeyword = new JTextField();
		JButton hdConfirm = new JButton(hdSearch);

		// 설명 - 현재 위치에 맞는 이미지 인덱스 설정
		switch (Layout.preview) {
			case "에어북스": y=0; break;
			case "탐색하기": y=1; break;
			case "설정": y=2; break;
		}

		// J라벨 - 헤더 타이틀
		hdTitle = new JLabel(arHdTitle[y]);

		// J패널 - 헤더 전체 (라벨 + 패널)
		headerFull.setName("header");
		headerFull.setBackground(Color.WHITE);
		headerFull.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(0xD9D9D9)));

		// J패널 - 검색헤더 (버튼 + 텍스트필드)
		header.setBackground(Color.WHITE);
		header.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, new Color(0xD9D9D9)));

		// J버튼 - 검색
		hdConfirm.setBorderPainted(false);
		hdConfirm.setFocusPainted(false);
		hdConfirm.setBackground(new Color(0xf7f7f7));
		hdConfirm.setForeground(Color.WHITE);
		hdConfirm.setEnabled(false);
		hdConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Reco.indexList.clear();
				Reco.indexCount = BookInfo.searchBook(hdKeyword.getText()).size();
				for (int data : BookInfo.searchBook(hdKeyword.getText())) {
					Reco.indexList.add(data);
					System.out.println(data);
				}
				searchCheck = true;
				Reco.refresh();

			}
		});

		// J텍스트 필드 - 키워드
		hdKeyword.setBackground(new Color(0xf7f7f7));
		hdKeyword.setForeground(Color.LIGHT_GRAY);
		hdKeyword.setBorder(new EmptyBorder(0, 20, 0, 20));
		hdKeyword.setFont(new Font("돋움", Font.BOLD, 20));
		hdKeyword.setText("키워드를 입력해주세요.");
		hdKeyword.addKeyListener(new KeyListener() {

			// 참고 - 한글은 두글자 이상 입력시 작동
			@Override
			public void keyTyped(KeyEvent e) {
				if (hdKeyword.getText().contains("키워드를 입력해주세요.")) {
					hdKeyword.setText("");
					hdKeyword.setForeground(Color.BLACK);
					hdConfirm.setEnabled(false);
				}
				// Beta - 키 입력시 실시간 검색 기능
//				Reco.indexList.clear();
//				Reco.indexCount = BookInfo.searchBook(hdKeyword.getText()).size();
//				for (int data : BookInfo.searchBook(hdKeyword.getText())) {
//					Reco.indexList.add(data);
//					System.out.println(data);
//				}
//				searchCheck = true;
//				Reco.refresh();
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (hdKeyword.getText().length() == 0) {
					hdKeyword.setText("키워드를 입력해주세요.");
					hdKeyword.setForeground(Color.LIGHT_GRAY);
					hdConfirm.setEnabled(false);
				} else {
					hdConfirm.setEnabled(true);
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		// 헤더전체에 헤더타이틀 추가
		headerFull.add(hdTitle);

		// 탐색하기 화면일 경우 검색영역추가
		if (Layout.preview == "탐색하기") {
			header.add(hdConfirm,BorderLayout.EAST);
			header.add(hdKeyword,BorderLayout.CENTER);
			headerFull.add(header);
		}

		return headerFull;
	}

}