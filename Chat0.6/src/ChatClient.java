import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Frame{
	
	TextField  tfText=new TextField();
	TextArea  taContent=new TextArea();

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
				System.exit(0);
			}
		});
		tfText.addActionListener(new TFListener());
		setVisible(true);
		connect();
	}
	
	public void connect() {
		try {
			Socket socket= new Socket("127.0.0.1", 8888);
System.out.println("connected");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private class TFListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String s= tfText.getText().trim();//trim方法去掉两边的空格
			taContent.setText(s);
			tfText.setText("");
		}

		
	}
	
}
