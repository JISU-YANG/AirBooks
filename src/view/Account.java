package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.User;

@SuppressWarnings("serial")
public class Account extends JPanel implements ActionListener{
	protected static final String Alert = null;

	public static JPanel loadView() {
		Layout.preview = "설정";

		JPanel account = new JPanel(new GridLayout(10, 1, 0, 2));
		JButton [] menuBtName = new JButton [5];
		String [] btName = {"　　로그아웃","　　비밀번호 변경","　　EXIT"};

		for (int i = 0; i < btName.length; i++) {
			menuBtName[i] = new JButton(btName[i]);
			menuBtName[i].setName(btName[i]);
			menuBtName[i].setBackground(new Color(0xFFFFFF));
			menuBtName[i].setForeground(new Color(0x000000));
			menuBtName[i].setFocusPainted(false);
			menuBtName[i].setFont(new Font("돋움", Font.BOLD, 12));
			menuBtName[i].setHorizontalAlignment(JLabel.LEFT);
			menuBtName[i].setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(0xD9D9D9)));
			account.add(menuBtName[i]);
		}
		menuBtName[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 오류 - USER DB가 두번 호출됨
				User.logout();
				System.out.println("로그아웃");
				new Login();
			}
		});
		menuBtName[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("비밀번호 변경");
				AlertSet.loadAlert("변경하실 비밀번호를 입력해주세요.", 1);
				User.changePW(AlertSet.alertText);
				System.out.println("입력한 비밀번호" + AlertSet.alertText);
				AlertSet.alertText="";
			}
		});
		menuBtName[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("프로그램 종료");
				System.exit(0);
			}
		});

		account.setName("account");
		account.setBackground(new Color(0xF7F7F7));

		return account;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
