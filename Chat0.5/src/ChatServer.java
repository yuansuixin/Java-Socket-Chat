import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLSocket;

public class ChatServer {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket= new ServerSocket(8888);
			
			while(true) {
				Socket socket=serverSocket.accept();
System.out.println("a client connected");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
