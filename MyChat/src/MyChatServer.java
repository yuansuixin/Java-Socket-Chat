import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyChatServer {

	public static void main(String[] args) {
		
		try {
			ServerSocket serverSocket=new ServerSocket(8888);
			while (true) {
				Socket socket= serverSocket.accept();
				DataInputStream dataInputStream= new DataInputStream(socket.getInputStream());
System.out.println("a client connected");
			}
		}catch (BindException e) {
			// TODO: handle exception
			e.printStackTrace();
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		}
		
	}

