import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLSocket;

public class ChatServer {

	public static void main(String[] args) {
		try {
			ServerSocket ss= new ServerSocket(8888);
			
			while(true) {
				Socket s=ss.accept();
System.out.println("a client connected");
				DataInputStream dis=new DataInputStream(s.getInputStream());
				String str=dis.readUTF();
				System.out.println(str);
				dis.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
