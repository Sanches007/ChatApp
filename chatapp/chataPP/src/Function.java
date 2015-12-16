import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class Function implements Observer {
	private Friends f = new Friends();
	private CallListenerThread callListenerThread;
	private Connection connection;
	private ServerConnection server;
	private MainForm form;
	public Function(MainForm form){
		this.form=form;
	}


	public void Server() throws FileNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		server=new ServerConnection();
		server.setServerAddress("jdbc:mysql://files.litvinov.in.ua/chatapp_server?characterEncoding..");
		server.connect();
		f.readFriends();
	}

	public ServerConnection getServer(){
		return server;
	}
	
	public void Send(JTextArea login,JTextArea rlogin, JList list, JFrame frame, JTextArea mess, DefaultListModel dlm) {
				System.out.println("Pop");
				if ((login.getText().equals("")) || (rlogin.getText().equals(""))
						|| (rlogin.getText().equals(""))) {
					JOptionPane.showMessageDialog(frame, "Not enough data for sending the message");
				} else {
					String name = new String();
					if (login.getText().length() > 10) {
						try {
							name = login.getText(0, 10);
						} catch (BadLocationException ignore) {
						}
						name = name + "...";
					} else
						name = login.getText();
					long date = System.currentTimeMillis();
					dlm.addElement("<html>" + name + " " + new Date(date).toLocaleString() + ":<br>"
							+ mess.getText() + " </span></html>");
					list.setModel(dlm);

					try {
						connection.sendMessage(mess.getText());
						System.out.println("Sended");
					} catch (IOException ex) {
						System.out.println("No internet connection");
					}

				}
				mess.setText("");
				mess.requestFocus();
			}

	public void Mess(JButton send) {

					send.doClick();

	}

	public void Disconnect(JButton disconnect) {
				if (connection != null)
					try {
						connection.disconnect();
					} catch (IOException ignored) {
				}

	}

	public void Apply( JTextArea login) {
				if (callListenerThread == null) {
					System.out.println("Added obs");
					callListenerThread = new CallListenerThread(new CallListener(login.getText()));
					callListenerThread.addObserver(form);
				} else {
					callListenerThread.setLocalNick(login.getText());
				}
	}

	public void Add(JTable frends, JTextArea login,JTextArea raddr,JTextArea rlogin) {

				f.addFriends(login, rlogin, raddr);
				try {
					f.writeFriends();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				frends.repaint();
	}

	public void Delete(JTable frends) {
				try {
					f.deleteFriends(frends);
					f.writeFriends();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				frends.repaint();
	}

	public void Connect(JTextArea login,JTextArea raddr,JTextArea rlogin, JFrame frame) {
				Caller caller = new Caller(login.getText(), raddr.getText());
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							connection = caller.call();

							if (caller.getStatus().toString().equals("OK"))
								rlogin.setText(caller.getRemoteNick());
							else if (caller.getStatus().toString().equals("BUSY")) {
								JOptionPane.showMessageDialog(frame,
										"User " + caller.getRemoteNick() + " is busy");
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

	private boolean question(String nick, String remoteAddress) {
		Object[] options = { "Receive", "Reject" };
		int dialogResult = JOptionPane.showOptionDialog(main.getFrame(),
				"User " + nick + " with ip " + remoteAddress + " is trying to connect with you", "Recive connection",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (dialogResult == JOptionPane.YES_OPTION) {
			System.out.println("Receive");
			main.getRLoginArea().setText(nick);
			main.getRAddrArea().setText(remoteAddress);
			return true;
		}
		System.out.println("Rejected");
		return false;

	}

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
				dlm.addElement("<html>" + main.getRLoginArea().getText() + " "
								+ new Date(System.currentTimeMillis()).toLocaleString() + ":<br>" + arg.toString()
								+ " </span></html>");
				main.getList().setModel(dlm);
			}
		}
	}
}
