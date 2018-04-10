import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingDeque;

public class ChatClient extends Frame {

	TextField tfText = new TextField();
	TextArea taContent = new TextArea();
	Socket s = null;
	DataOutputStream dos;
	DataInputStream dis;
	private boolean bConnected = false;

	Thread tRecv = new Thread(new RecvThread());

	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}

	public void launchFrame() {
		setLocation(400, 300);
		this.setSize(300, 300);
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

		tRecv.start();
	}

	public void connect() {
		try {
			s = new Socket("127.0.0.1", 8888);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
System.out.println("connected");
			bConnected = true;
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
			dis.close();
			dos.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		try {
//			bConnected = false;
//			// 合并
//			tRecv.join();		
//		} catch (InterruptedException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//
//		} finally {
//			try {
//				dis.close();
//				dos.close();
//				s.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
		
		
	}

	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = tfText.getText().trim();// trim方法去掉两边的空格
			// taContent.setText(str);
			tfText.setText("");

			try {
				// DataOutputStream dos= new DataOutputStream(s.getOutputStream());
				dos.writeUTF(str);
				dos.flush();
				// dos.close();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class RecvThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while (bConnected) {
					String string = dis.readUTF();
					// System.out.println(string);
					taContent.setText(taContent.getText() + string + "\n");
				}
			} catch (SocketException e) {
				System.out.println("退出了，bye");
			}catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
