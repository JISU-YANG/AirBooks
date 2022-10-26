package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import dao.BookInfo;
import db.PathDB;

@SuppressWarnings("serial")
public class Reco extends JPanel {
	public static int indexCount = -1;
	public static ArrayList<Integer> indexList = new ArrayList<>();
	static JPanel reco = new JPanel(new BorderLayout());

	public static JPanel loadView() {

		if (Header.searchCheck) {
			indexCount = indexList.size();
			System.out.println(indexCount);
		} else {
			indexCount = -1;
		}

		Layout.preview = "탐색하기";
		int rows = indexCount;
		if (indexCount >= 0 && indexCount < 3) {
			rows = 3;
		}
		JPanel result = new JPanel(new GridLayout(rows,1,10,10));
		JPanel noneBook = new JPanel(new GridLayout(rows,1,10,10));
		JPanel bestBook = new JPanel(new BorderLayout());
		JPanel newBook = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane(result);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		ImageIcon bestBookImage = new ImageIcon(PathDB.getImagePath("recoTitle02"));
		ImageIcon newBookImage = new ImageIcon(PathDB.getImagePath("recoTitle01"));
		JLabel bestBookTitle = new JLabel(bestBookImage);
		JLabel newBookTitle = new JLabel(newBookImage);

		reco.setName("reco");
		reco.setBackground(new Color(0xF7F7F7));
		reco.setOpaque(true);

		reco.removeAll();
		if (!Header.searchCheck) {
			bestBookTitle.setHorizontalAlignment(JLabel.LEFT);
			bestBook.setBorder(new EmptyBorder(10, 20, 10, 20));
			bestBook.setBackground(new Color(0xF7F7F7));
			bestBook.add(bestBookTitle,BorderLayout.NORTH);
			bestBook.add(BookInfoPanel.loadBookInfo(BookInfo.bestSeller()+1,1),BorderLayout.CENTER);
			bestBook.setOpaque(true);

			newBookTitle.setHorizontalAlignment(JLabel.LEFT);
			newBook.setBorder(new EmptyBorder(10, 20, 10, 20));
			newBook.setBackground(new Color(0xF7F7F7));
			newBook.add(newBookTitle,BorderLayout.NORTH);
			newBook.add(BookInfoPanel.loadBookInfo(11,1),BorderLayout.CENTER);
			newBook.setOpaque(true);

			noneBook.setBorder(new EmptyBorder(10, 0, 20, 0));
			noneBook.setBackground(new Color(0xF7F7F7));
			noneBook.setOpaque(true);
			noneBook.add(bestBook);
			noneBook.add(newBook);

			reco.add(noneBook,BorderLayout.CENTER);
		} else {
			result.setBorder(new EmptyBorder(10, 20, 10, 20));
			result.setBackground(new Color(0xF7F7F7));
			result.setOpaque(true);
			for (int i = 0; i < indexList.size(); i++) {
				result.add(BookInfoPanel.loadBookInfo(1+indexList.get(i),1));
			}
			reco.add(scroll,BorderLayout.CENTER);
		}
		return reco;
	}

	static void refresh() {
		Reco.loadView();
		Reco.reco.revalidate();
		Reco.reco.repaint();
		Header.searchCheck = false;
	}

}
