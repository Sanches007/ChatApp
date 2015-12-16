import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;

public class MainForm2 implements Observer  {

	private JFrame frame;
	private JTextArea textLogin;
	private JTextArea textRLogin;
	private JTextArea textRAddr;
	private JTextArea textMess;
	private JButton connect,delete,add,apply,disconnect,send;
	public static MainForm2 window;
	private JList list;
	public static Function f;
	public DefaultListModel dlm;
	private String[][] frendmass = new String[1000][3];
	private String[] headers = { "Name", "IP","Online" };
	private JTable frends;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainForm2();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	public MainForm2() throws FileNotFoundException {
		initialize();
		f = new Function(window);
	}

	
	private void initialize() {
		frame = new JFrame();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		int w = screen.width;
		int h = screen.height;
		frame = new JFrame("ChatApp");
		frame.setSize(800 ,  500);
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

		apply = new JButton("Apply");

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

		connect = new JButton("Connect");

		JLabel remoteAddr = new JLabel();
		remoteAddr.setText("Remote Addr");
		remoteAddr.setHorizontalAlignment(JLabel.CENTER);
		remoteAddr.setOpaque(true);
		remoteAddr.setPreferredSize(new Dimension(70, 25));

		textRAddr = new JTextArea(2, 0);
		textRAddr.setEditable(true);
		JScrollPane scrollBar3 = new JScrollPane(textRAddr);
		scrollBar3.setViewportView(textRAddr);

		disconnect = new JButton("Disconnect");

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

		JPanel panel_2 = new JPanel(new BorderLayout());
		send = new JButton("Send");
		send.setPreferredSize(new Dimension(100, 30));

		textMess = new JTextArea(3,5);
		textMess.setEditable(true);
		textMess.setLineWrap(true);
		JScrollPane scrollBar4 = new JScrollPane(textMess);
		scrollBar4.setViewportView(textMess);

		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.add(scrollBar4,BorderLayout.CENTER);
		panel_2.add(send,BorderLayout.EAST);

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

		frends = new JTable(frendmass, headers);
		frends.setColumnSelectionAllowed(false);
		frends.setRowSelectionAllowed(false);
		frends.setCellSelectionEnabled(true);
		frends.setPreferredScrollableViewportSize(new Dimension(200, 100));
		JScrollPane scrollBar6 = new JScrollPane(frends);
		scrollBar6.setViewportView(frends);


		add = new JButton("Add");
		delete = new JButton("Delete");
		add.setPreferredSize(new Dimension(30, 25));

		but.add(add);
		but.add(delete);

		
		frend.add(friends, BorderLayout.NORTH);
		frend.add(scrollBar6, BorderLayout.CENTER);
		frend.add(but, BorderLayout.SOUTH);

		frame.add(frend, BorderLayout.EAST);
		frame.add(panel_2, BorderLayout.SOUTH);

	
	

		connect.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				f.Connect(textLogin, textRAddr, textRLogin, frame);
			} 
		});
	
		disconnect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				f.Disconnect(disconnect);
			} 
		});
		
		textMess.addKeyListener(new KeyListener(){
				public void keyPressed(KeyEvent e) {
					f.Mess(send);
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
	
		send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				f.Send(textLogin, textRLogin, list, frame, textMess, dlm);
			} 
		});
	
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				f.Delete(frends);
			} 
		});
	
		apply.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				f.Apply(textLogin);
			} 
		});
		
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				f.Add(frends,textLogin, textRAddr,textRLogin);
			} 
		});
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
