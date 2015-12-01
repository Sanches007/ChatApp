import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.sql.Date;
import java.awt.GridBagConstraints;
import java.awt.Color;
import javax.swing.text.BadLocationException;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
/*import org.eclipse.wb.swing.FocusTraversalOnArray;*/
import java.util.Observable;
import java.util.Observer;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MainForm implements Observer {

	private JFrame frame;
	private JTextArea textLogin;
	private JTextArea textRLogin;
	private JTextArea textRAddr;
	private JTextArea textMess;
	private CallListenerThread callListenerThread;
	public static MainForm window;
	private DefaultListModel dlm;
	private JList list;
	private Connection connection;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainForm();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainForm() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		int w = screen.width;
		int h = screen.height;
		frame = new JFrame("ChatApp");
		frame.setSize(w / 2, h / 2);
		frame.setLocation(w / 4, h / 4);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JPanel panel_1 = new JPanel(new GridLayout(1, 2, 50, 0));
		JPanel ltop = new JPanel(new GridLayout(2, 2));
		JPanel rtop = new JPanel(new GridLayout(2, 3));

		JLabel login = new JLabel();
		login.setText("Local Login");
		login.setHorizontalAlignment(JLabel.CENTER);
		login.setOpaque(true);
		login.setPreferredSize(new Dimension(70, 25));

		textLogin = new JTextArea(2, 0);
		textLogin.setEditable(true);
		JScrollPane scrollBar1 = new JScrollPane(textLogin);
		scrollBar1.setViewportView(textLogin);

		JButton apply = new JButton("Apply");

		JLabel time = new JLabel();
		time.setText("Time: 00:00:00");
		time.setOpaque(true);
		time.setPreferredSize(new Dimension(70, 25));

		ltop.add(login);
		ltop.add(scrollBar1);
		ltop.add(apply);
		ltop.add(time);

		JLabel remoteLogin = new JLabel();
		remoteLogin.setText("Remote Login");
		remoteLogin.setHorizontalAlignment(JLabel.CENTER);
		remoteLogin.setOpaque(true);
		remoteLogin.setPreferredSize(new Dimension(70, 25));

		textRLogin = new JTextArea(2, 0);
		textRLogin.setEditable(true);
		JScrollPane scrollBar2 = new JScrollPane(textRLogin);
		scrollBar2.setViewportView(textRLogin);

		JButton connect = new JButton("Connect");

		JLabel remoteAddr = new JLabel();
		remoteAddr.setText("Remote Addr");
		remoteAddr.setHorizontalAlignment(JLabel.CENTER);
		remoteAddr.setOpaque(true);
		remoteAddr.setPreferredSize(new Dimension(70, 25));

	    textRAddr = new JTextArea(2, 0);
		textRAddr.setEditable(true);
		JScrollPane scrollBar3 = new JScrollPane(textRAddr);
		scrollBar3.setViewportView(textRAddr);

		JButton disconnect = new JButton("Disconnect");

		rtop.add(remoteLogin);
		rtop.add(scrollBar2);
		rtop.add(connect);
		rtop.add(remoteAddr);
		rtop.add(scrollBar3);
		rtop.add(disconnect);

		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.add(ltop);
		panel_1.add(rtop);

		frame.add(panel_1, BorderLayout.NORTH);

		JPanel panel_2 = new JPanel(new GridLayout(1, 2, 30, 0));
		JButton send = new JButton("Send");
		send.setPreferredSize(new Dimension(30, 20));

		textMess = new JTextArea(3, 5);
		textMess.setEditable(true);
		textMess.setLineWrap(true);
		JScrollPane scrollBar4 = new JScrollPane(textMess);
		scrollBar4.setViewportView(textMess);

		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.add(scrollBar4);
		panel_2.add(send);

		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		frame.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane, BorderLayout.CENTER);

		list = new JList();
		scrollPane.setViewportView(list);

		JPanel frend = new JPanel(new BorderLayout());
		JPanel but = new JPanel(new GridLayout(1, 2, 25, 0));

		JLabel friends = new JLabel();
		friends.setText("Friends");
		friends.setBorder(new LineBorder(Color.BLACK, 1));
		friends.setHorizontalAlignment(JLabel.CENTER);
		friends.setPreferredSize(new Dimension(30, 40));

		String[] elements = { "FRIENDSFRIENDSFRIENDSFRIENDS", "FRIENDS", "FRIENDS", "FRIENDS", "FRIENDS", "FRIENDS",
				"FRIENDS", "FRIENDS", "FRIENDS", "FRIENDS", "FRIENDS", "FRIENDS", "FRIENDS", "FRIENDS" };
		JList northList = new JList(elements);
		northList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollBar6 = new JScrollPane(northList);
		scrollBar6.setViewportView(northList);

		JButton add = new JButton("Add");
		JButton delete = new JButton("Delete");
		add.setPreferredSize(new Dimension(30, 25));

		but.add(add);
		but.add(delete);

		frend.add(friends, BorderLayout.NORTH);
		frend.add(scrollBar6, BorderLayout.CENTER);
		frend.add(but, BorderLayout.SOUTH);

		frame.add(frend, BorderLayout.EAST);
		frame.add(panel_2, BorderLayout.SOUTH);

		dlm = new DefaultListModel();
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((textLogin.getText().equals("")) || (textRLogin.getText().equals(""))
						|| (textRAddr.getText().equals(""))) {
					JOptionPane.showMessageDialog(frame, "Not enough data for sending the message");
				} else {
					String name = new String();
					if (textLogin.getText().length() > 10) {
						try {
							name = textLogin.getText(0, 10);
						} catch (BadLocationException ignore) {
						}
						name = name + "...";
					} else
						name = textLogin.getText();
					long date = System.currentTimeMillis();
					dlm.addElement("<html>" + name + " " + new Date(date).toLocaleString() + ":<br>"
							+ textMess.getText() + " </span></html>");
					list.setModel(dlm);

					try {
						connection.sendMessage(textMess.getText());
						System.out.println("Sended");
					} catch (IOException ex) {
						System.out.println("No internet connection");
					}

				}
				textMess.setText("");
				textMess.requestFocus();
			}
		});

		textMess.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
					send.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (callListenerThread == null) {
					System.out.println("Added obs");
					callListenerThread = new CallListenerThread(new CallListener(textLogin.getText()));
					callListenerThread.addObserver(window);
				} else {
					callListenerThread.setLocalNick(textLogin.getText());
				}
			}
		});

		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Caller caller = new Caller(textLogin.getText(), textRAddr.getText());
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							connection = caller.call();

							if (caller.getStatus().toString().equals("OK"))
								textRLogin.setText(caller.getRemoteNick());
							else if (caller.getStatus().toString().equals("BUSY")) {
								JOptionPane.showMessageDialog(frame, "User " + caller.getRemoteNick() + " is busy");
							} else {
								JOptionPane.showMessageDialog(frame,
										"User " + caller.getRemoteNick() + " has declined your call.");
								connection = null;
							}

						} catch (IOException ex) {
							JOptionPane.showMessageDialog(frame,
									"Connection error. User with ip does not exist or there is no Internet connection");
							connection = null;
						}
					}
				}).start();
			}

		});
	}

	public boolean question(String nick, String remoteAddress) {
		Object[] options = { "Receive", "Reject" };
		int dialogResult = JOptionPane.showOptionDialog(frame,
				"User " + nick + " with ip " + remoteAddress + " is trying to connect with you", "Recive connection",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (dialogResult == JOptionPane.YES_OPTION) {
			System.out.println("Receive");
			textRLogin.setText(nick);
			textRAddr.setText(remoteAddress);
			return true;
		}
		System.out.println("Rejected");
		return false;

	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof CallListener) {
			CallListener c = (CallListener) arg;
			callListenerThread.suspend();
			callListenerThread.setReceive(question(c.getRemoteNick(), c.getRemoteAddress()));
			callListenerThread.resume();
		} else if (arg instanceof Connection) {
			connection = (Connection) arg;
			System.out.println("Output connection created");
		} else {
			System.out.println("Receive message");
			System.out.println(arg.toString());
			Command command = (Command) arg;

			if (command instanceof MessageCommand) {
				dlm.addElement(
						"<html>" + textRLogin.getText() + " " + new Date(System.currentTimeMillis()).toLocaleString()
								+ ":<br>" + arg.toString() + " </span></html>");
				list.setModel(dlm);
			}
		}
	}
}
