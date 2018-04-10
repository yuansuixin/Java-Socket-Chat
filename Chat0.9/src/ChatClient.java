import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingDeque;

public class ChatClient extends Frame{
	
	TextField  tfText=new TextField();
	TextArea  taContent=new TextArea();
	Socket s=null;
	DataOutputStream dos;

	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}
	
	public void launchFrame() {
		setLocation(400,300);
		this.setSize(300,300);
		add(tfText, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			}
		});
		tfText.addActionListener(new TFListener());
		setVisible(true);
		connect();
	}
	
	public void connect() {
		try {
			s= new Socket("127.0.0.1", 8888);
			dos=new DataOutputStream(s.getOutputStream());
System.out.println("connected");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			dos.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private class TFListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String str= tfText.getText().trim();//trim方法去掉两边的空格
			taContent.setText(str);
			tfText.setText("");
			
			try {
				//DataOutputStream dos= new DataOutputStream(s.getOutputStream());
				dos.writeUTF(str);
				dos.flush();
				//dos.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		
	}
	
}
