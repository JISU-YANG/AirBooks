package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class AlertSet {
	static UIManager alert = new UIManager();
	static String alertText;
	static int alertChoice;

	public AlertSet() {
	}

	@SuppressWarnings("static-access")
	public static void loadAlert(String msg, int type) {
		String title = "알림";
		alert.put("OptionPane.background", Color.WHITE);
		alert.put("Panel.background", Color.WHITE);
		alert.put("Button.background", Color.BLACK);
		alert.put("Button.foreground", Color.WHITE);
		alert.put("Button.focus", Color.BLACK);
		alert.put("Button.border", BorderFactory.createEmptyBorder(4,16,4,16));

		switch (type) {
			case 0:
				JOptionPane.showMessageDialog(null, msg, title, JOptionPane.PLAIN_MESSAGE);
				break;
			case 1:
				alertText = JOptionPane.showInputDialog(null, msg, title, JOptionPane.DEFAULT_OPTION);
				break;
			case 2:
				String [] buttonName = new String [type];
				buttonName[0] = "진행하기";
				buttonName[1] = "돌아가기";

				alertChoice = JOptionPane.showOptionDialog(null, msg, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttonName, buttonName[0]);
				break;

			case 3 :
				JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.DEFAULT_OPTION);
			default:
				break;
		}
	}
}
