
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * 0.1、写一个客户端界面ChatClient，设置好位置、大小
0.2、在界面中添加显示文字的区域和输入文字的区域，并添加到界面中，设置好位置
0.3、为界面添加关闭事件
0.4*、输入框添加事件监听，获取到输入的文字，并且显示到上面的区域，并设置输入框为空
0.5、写一个服务端ChatServer出来，使用serversocket连接。等待接收客户端的连接accept	（）。

0.6、客户端利用套接字socket进行连接，new出来
0.7、需要将输入的数据发送到服务器端，利用DataOutputStream流的方式读取客户端的数据
	相对应的在服务器端需要使用DataInputStream将数据写入到服务器端，可以接受到
	客户端发来的一条数据。
0.8、服务端响应客户端的请求连接，利用的是死循环，可以接受客户端发来的多条数据
0.9、解决异常问题，做到好的用户体验，对于Eof、绑定异常做出处理
1.0、可以实现多客户端的连接，并且服务器端可以对各个客户端做出响应，利用的多线程，
	每一个客户端用一个线程，对于静态的main方法使用内部类的问题，可以写一个方	法将其封装起来，再使用静态的方法调用。
 * 
 * 
 * @author yuan
 *
 */

public class ChatServer {
	boolean started = false;
	ServerSocket ss = null;
	//将客户端连接保存下来
	List<Client> clients= new ArrayList<Client>();
	
	
	public static void main(String[] args) {
		new ChatServer().start();
	}

	
	public void start() {

		try {
			ss = new ServerSocket(8888);
			started = true;
		} catch (BindException e) {
			System.out.println("端口使用中");
			System.out.println("请关闭相关程序，并重新运行服务器");
			System.exit(0);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			
			while (started) {
				Socket s = ss.accept();//阻塞性的方法
				Client c= new Client(s);
System.out.println("a client connected");
				new Thread(c).start();
				clients.add(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	class Client implements Runnable{

		private Socket s;
		private DataInputStream dis=null;
		private boolean bConnected=false;
		private DataOutputStream dos=null;
		
		
		public Client(Socket s) {
			// TODO Auto-generated constructor stub
			this.s=s;
			try {
				dis=new DataInputStream(s.getInputStream());
				dos= new DataOutputStream(s.getOutputStream());
				bConnected=true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void send(String string){
			
				try {
					dos.writeUTF(string);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					clients.remove(this);
System.out.println("对方退出了，我从list里面去除了");
					//e.printStackTrace();
				}
		}
		
		@Override
		public void run() {
			
			try {
				while (bConnected) {
					String str = dis.readUTF();//阻塞性的函数
System.out.println(str);
					for(int i=0;i<clients.size();i++) {
						Client c= clients.get(i);
						c.send(str);
System.out.println(" a string send");
					}
				//内部锁定，效率不高
//				for (Iterator<Client> iterator = clients.iterator(); iterator.hasNext();) {
//					Client client = (Client) iterator.next();
//					client.send(str);
//				}
//								
				}
				
			}catch (SocketException e) {
				// TODO: handle exception
				clients.remove(this);
				System.out.println("a client quit");
			}catch (EOFException e) {
				// TODO: handle exception
				System.out.println("client closed");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (dis != null)
						dis.close();
					if(dos!=null)
						dos.close();
					if(s!=null) {
						s.close();
						s=null;
					}
						
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
 