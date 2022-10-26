package view;

import db.PathDB;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Layout extends JFrame implements ActionListener{
	// FOOTER BUTTON 이미지
	ImageIcon [] icon = {new ImageIcon(PathDB.getImagePath("home_off")),
			new ImageIcon(PathDB.getImagePath("home_on")),
			new ImageIcon(PathDB.getImagePath("reco_off")),
			new ImageIcon(PathDB.getImagePath("reco_on")),
			new ImageIcon(PathDB.getImagePath("account_off")),
			new ImageIcon(PathDB.getImagePath("account_on"))};

	static ImageIcon thum = new ImageIcon(PathDB.getImagePath("thum00"));

	// FOOTER 전역변수
	JButton [] arFtBtn = new JButton [6];
	JPanel footer = new JPanel(new GridLayout(1, 3));

	// VIEW Memory
	static String preview = "TEST";

	// VIEW 패널 선언
	BorderLayout border = new BorderLayout();

	// UI 생성자
	public Layout() {
		// LAYOUT 기본값
		setTitle("AirBooks");
		setSize(375, 667);
		setLayout(border);

		// FOOTER BUTTON 기본값 반복문
		for (int i = 0; i < arFtBtn.length; i++) {
			String j = "" + i;
			arFtBtn[i] = new JButton(icon[i]);
			arFtBtn[i].setName(j);
			arFtBtn[i].setBackground(new Color(0xffffff));
			arFtBtn[i].setBorderPainted(false);
			arFtBtn[i].setFocusPainted(false);
			arFtBtn[i].setHorizontalAlignment(JLabel.CENTER);
			arFtBtn[i].setPreferredSize(new Dimension(94, 60));
			arFtBtn[i].addActionListener(this);
		}
		footer.add(arFtBtn[1]);
		footer.add(arFtBtn[2]);
		footer.add(arFtBtn[4]);
		footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, new Color(0xdcdcdc)));

		// VIEW PANEL 기본값
		add(Header.loadView(), BorderLayout.NORTH);
		add(footer, BorderLayout.SOUTH);
		add(Home.loadView(), BorderLayout.CENTER);

		setLocationRelativeTo(null);
		setUndecorated(true);
		setVisible(true);
	}

	// FOOTER BUTTON으로 PANEL 전환
	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		JButton button = (JButton) object;

		getContentPane().removeAll();
		this.footer.removeAll();

		switch (button.getName()) {
			case "0": case "1":
				this.add(Home.loadView(), BorderLayout.CENTER);
				this.footer.add(arFtBtn[1]);
				this.footer.add(arFtBtn[2]);
				this.footer.add(arFtBtn[4]);
				break;
			case "2": case "3":
				this.add(Reco.loadView(), BorderLayout.CENTER);
				this.footer.add(arFtBtn[0]);
				this.footer.add(arFtBtn[3]);
				this.footer.add(arFtBtn[4]);
				break;
			case "4": case "5":
				this.add(Account.loadView(), BorderLayout.CENTER);
				this.footer.add(arFtBtn[0]);
				this.footer.add(arFtBtn[2]);
				this.footer.add(arFtBtn[5]);
				break;
		}
		this.add(Header.loadView(), BorderLayout.NORTH);
		this.add(footer, BorderLayout.SOUTH);

		revalidate();
		repaint();
	}
}