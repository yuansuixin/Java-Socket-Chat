import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyChatClient extends Frame{
	TextField textField=new TextField();
	TextArea textArea=new TextArea();
	Socket socket;
	DataOutputStream dataOutputStream=null;

	public static void main(String[] args) {
		new MyChatClient().launchFrame();
	}
	
	private void launchFrame() {
		Frame frame=new Frame();
		this.setLocation(300,300);
		this.setSize(300, 400);
		
		add(textField, BorderLayout.SOUTH);//南方
		add(textArea,BorderLayout.NORTH);//北方
		pack();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		textField.addActionListener(new TFListener());
		connected();
		setVisible(true);	
	}
	
	public void connected() {
		try {
			socket= new Socket("127.0.0.1", 8888);
			dataOutputStream=new DataOutputStream(socket.getOutputStream());
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
			// TODO Auto-generated method stub
			String string=textField.getText().trim();
			textArea.setText(string);
			textField.setText("");
		}	
	}
}
