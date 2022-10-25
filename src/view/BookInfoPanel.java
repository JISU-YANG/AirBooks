package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.Book;
import dao.BookInfo;
import model.PathDB;

@SuppressWarnings("serial")
public class BookInfoPanel extends JPanel{
	public static JPanel loadBookInfo(int bookIndex,int buttonType){

		ImageIcon image = new ImageIcon(PathDB.getBookImagePath(bookIndex));
		JPanel widget = new JPanel(new BorderLayout());
		JPanel widgetWest = new JPanel();
		JPanel widgetCenter = new JPanel(new GridLayout(4, 1));
		JPanel button = new JPanel(new GridLayout(1, buttonType, 5, 0));
		JButton [] widgetButton = {new JButton("대여"),new JButton("반납"),new JButton("연장")};

		// 컴포넌트 만들기
		JLabel [] widgetLabel = new JLabel [3];
		JLabel widgetThumb = new JLabel();
		String [] arText = {"책 이름", "저자, 출판사", "2019.06.12"};
		String bookTitle;
		arText[0] = BookInfo.takeInfo(bookIndex, 1);
		arText[1] = BookInfo.takeInfo(bookIndex, 2)+ ", " + BookInfo.takeInfo(bookIndex, 3);

		// 예정
		bookTitle = arText[0];
		if (arText[0].length() >= 8 ) bookTitle = arText[0].substring(0, 8)+"...";
		System.out.println(arText[0].length());

		widgetLabel[0] = new JLabel(bookTitle);
		widgetLabel[0].setName(arText[0]);
		widgetLabel[0].setFont(new Font("돋움", Font.BOLD, 18));
		widgetLabel[0].setForeground(new Color(0x000000));
		widgetCenter.add(widgetLabel[0]);

		widgetLabel[1] = new JLabel(arText[1]);
		widgetLabel[1].setName(arText[1]);
		widgetLabel[1].setFont(new Font("돋움", Font.BOLD, 12));
		widgetLabel[1].setForeground(new Color(0x979797));
		widgetCenter.add(widgetLabel[1]);


		widgetThumb = new JLabel(image);

		if (buttonType==1) arText[2] = "";
		widgetLabel[2] = new JLabel(arText[2]);
		widgetLabel[2].setName(arText[2]);
		widgetLabel[2].setFont(new Font("돋움", Font.BOLD, 12));
		widgetLabel[2].setForeground(new Color(0x9b9b9b));
		widgetLabel[2].setBackground(Color.WHITE);
		widgetLabel[2].setOpaque(true);
		widgetCenter.add(widgetLabel[2]);

		button.setBackground(new Color(0xF7F7F7));
		// 예정 이프문으로 버튼 붙이기
		for (int i = 0; i < widgetButton.length; i++) {
			widgetButton[i].setBackground(new Color(0x222222));
			widgetButton[i].setForeground(Color.WHITE);
			widgetButton[i].setBorderPainted(false);
			widgetButton[i].setFocusPainted(false);
			widgetButton[i].setOpaque(true);
		}
		widgetButton[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 대여버튼 액션
				System.out.println("대여버튼 - 대여완료");
				Book.borrowBook(bookIndex);
//				Book.borrowBook(index);
			}
		});
		widgetButton[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 반납버튼 액션
				System.out.println("반납버튼 - 반납완료");
				Book.returnBook(bookIndex);
//				Book.returnBook(index);
			}
		});
		widgetButton[2].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 연장버튼 액션
				System.out.println("연장버튼 - 연장완료");
				Book.delayDate(bookIndex);
//				Book.delayDate(index);				
			}
		});

		switch (buttonType) {
			case 1:
				button.add(widgetButton[0]);
				break;
			default:
				button.add(widgetButton[1]);
				button.add(widgetButton[2]);
				break;
		}
		widgetCenter.add(button);
		widgetWest.add(widgetThumb);
		widgetWest.setBackground(new Color(0xF7F7F7));
		widgetWest.setOpaque(true);
		widgetCenter.setBorder(new EmptyBorder(10, 30, 10, 30));
		widgetCenter.setBackground(new Color(0xF7F7F7));
		widgetCenter.setOpaque(true);
		widget.add(widgetWest, BorderLayout.WEST);
		widget.add(widgetCenter, BorderLayout.CENTER);
		widget.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xD9D9D9)));

		return widget;
	}
}
