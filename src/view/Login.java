package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.Book;
import dao.User;
import model.PathDB;
import model.UserDB;

@SuppressWarnings("serial")
public class Login extends JFrame{
	ImageIcon [] loginImage = {new ImageIcon(PathDB.getImagePath("loginImage01")),
			new ImageIcon(PathDB.getImagePath("loginImage02")),
			new ImageIcon(PathDB.getImagePath("loginImage03")),
			new ImageIcon(PathDB.getImagePath("loginImage04")),
			new ImageIcon(PathDB.getImagePath("loginImage05"))
	};

	String Id = null;
	String Pw = null;
	boolean ok1 = false;

	JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();

	public Login() {
		// 전체 레이아웃
		setTitle("AirBooks");
		setSize(375, 667);

		JPanel background = new JPanel(new BorderLayout());
		JLabel name = new JLabel(loginImage[0]);
		JLabel hello = new JLabel(loginImage[2]);
		JPanel input = new JPanel(new GridLayout(3, 1, 0, 0));
		JPanel button = new JPanel(new GridLayout(2, 1, 20,5));
		JButton signIn = new JButton(loginImage[4]);
		JButton login = new JButton(loginImage[3]);

		background.setBorder(new EmptyBorder(130, 30, 130, 30));
		background.setBackground(new Color(0xffffff));

		login.setName("로그인");
		login.setForeground(new Color(0x000000));
		login.setBackground(new Color(0x222222));
		login.setFocusPainted(false);
		login.setBorder(new EmptyBorder(15, 0, 15, 0));
		login.setOpaque(true);

		signIn.setName("회원가입");
		signIn.setForeground(new Color(0x000000));
		signIn.setBackground(new Color(0x444444));
		signIn.setFocusPainted(false);
		signIn.setBorder(new EmptyBorder(15, 0, 15, 0));
		signIn.setOpaque(true);

		hello.setBorder(null);
		hello.setHorizontalAlignment(JLabel.LEFT);

		input.setBorder(new EmptyBorder(40, 0, 40, 0));
		input.setBackground(new Color(0xFFFFFF));
		input.add(hello);
		input.add(id);
		input.add(pw);

		id.setBackground(new Color(0xffffff));
		id.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xD9D9D9)));
		id.setText("아이디");
		id.setForeground(Color.LIGHT_GRAY);
		id.addKeyListener(new KeyListener() {
			// 키워드 입력이 없을 시 버튼 비활성화
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (id.getText().length() == 0) {
					id.setText("아이디");
					id.setForeground(Color.LIGHT_GRAY);
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (id.getText().contains("아이디")) {
					id.setText("");
					id.setForeground(Color.BLACK);
				}
			}
		});

		pw.setBackground(new Color(0xffffff));
		pw.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xD9D9D9)));
		pw.setText("비밀번호입력");
		pw.setForeground(Color.LIGHT_GRAY);
		pw.addKeyListener(new KeyListener() {
			// 키워드 입력이 없을 시 버튼 비활성화
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@SuppressWarnings("deprecation")
			@Override
			public void keyReleased(KeyEvent e) {
				if (pw.getText().length() == 0) {
					pw.setText("비밀번호입력");
					pw.setForeground(Color.LIGHT_GRAY);
				}
			}
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				if (pw.getText().contains("비밀번호입력")) {
					pw.setText("");
					pw.setForeground(Color.BLACK);
				}
			}
		});
		// 유저디비 출력
		System.out.println(UserDB.getLoadUserDB());

		button.setBackground(Color.WHITE);
		login.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				Book.takeUserDB(PathDB.userDB);

				if(id.getText().equals("아이디") && pw.getText().equals("비밀번호입력")) {
					System.out.println("아이디랑 비밀번호를 먼저 입력해주세요!");
				} else {
					// 메서드로 넘기기
					// 로그인 정보 입력
					Id = id.getText();
					Pw = pw.getText();
					ok1 = User.login(Id, Pw);
					if (ok1) {
						System.out.println("로그인 완료");
						dispose();
						new Layout();

					} else {
						System.out.println("로그인 실패");
					}
				}

			}
		});
		signIn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				Book.takeUserDB(PathDB.userDB);
				if(id.getText().equals("아이디") && pw.getText().equals("비밀번호입력")) {
					System.out.println("아이디랑 비밀번호를 먼저 입력해주세요!");
				} else {
					System.out.println(UserDB.getLoadUserDB());
					Id = id.getText();
					Pw = pw.getText();
					ok1 = User.sign(Id, Pw);
					// X 알림 메시지
					if (ok1) {
						System.out.println("회원가입 완료");
					} else {
						System.out.println("중복된 아이디 입니다.");
					}
					System.out.println(UserDB.getLoadUserDB());
				}
			}
		});
		button.add(login);
		button.add(signIn);


		name.setOpaque(true);
		name.setBackground(Color.WHITE);
		name.setHorizontalAlignment(JLabel.LEFT);
		name.setFont(new Font("돋움", Font.BOLD, 18));

		background.add(name, BorderLayout.NORTH);
		background.add(input, BorderLayout.CENTER);
		background.add(button, BorderLayout.SOUTH);

		add(background);

		setLocationRelativeTo(null);
		setUndecorated(true);
		setVisible(true);
	}

}
