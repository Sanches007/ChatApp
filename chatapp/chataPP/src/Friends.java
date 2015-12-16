import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JTable;
import javax.swing.JTextArea;

public class Friends {
	private File file = new File("Friends.txt");
	private String[][] frendmass = new String[1000][2];
	private Integer size = 0;
	private MainForm main;
	private Function f;
	
	public Friends() {
	}
	public void addFriends(JTextArea login,JTextArea rlogin, JTextArea raddr) {
		if (login.getText() != "") {
			frendmass[size][0] = rlogin.getText();
			frendmass[size][1] = raddr.getText();
			online(size);
			size++;
		}
	}
	
	public void online(int i){
			boolean bol;
			bol=f.getServer().isNickOnline(frendmass[i][0]);
			if(bol){
				frendmass[size][2] = "Online";
			}
	}
	
	void deleteFriends(JTable frends) throws FileNotFoundException {
		int row = frends.getSelectedRow();
		for (int i = row; i < size; i++) {
			frendmass[i][0]=frendmass[i+1][0];
			frendmass[i][1]=frendmass[i+1][1];
			frendmass[i][2]=frendmass[i+1][2];
		}
		size--;
	}

	public void readFriends() throws FileNotFoundException {
		String[] fr = new String[1000];
		String s;
		fr=f.getServer().getAllNicks();
		for(int i=0;i<fr.length;i++){
			frendmass[i][0] = fr[i];
			s=f.getServer().getIpForNick(fr[i]);
			frendmass[i][1]=s;
			online(i);
			size++;
		}
	}

	public void writeFriends() throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(file);
		
		for (int i = 0; i < size; i++) {
			writer.print(frendmass[i][0] + " ");
			writer.print(frendmass[i][1] + " ");
			
			writer.flush();
		}
		writer.close();
	}

}
