package server; //서버 만들기

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaServer {
	public static void main(String[] args) {
		MyServer ms = new MyServer();
		ms.start();
	}
}

class MyServer extends Thread{
	private InputStream is;
	private OutputStream os;
	private ServerSocket serverSocket; // 접속용
	ObjectInputStream ois;
	ObjectOutputStream oos; 
	Socket socket; // 채팅용
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(5000);
			while(true) {
				System.out.println("요청 대기");
				socket = serverSocket.accept(); //클라이언트가 들어올때까지 대기
				System.out.println("접속한 클라이언트 : "+socket.getInetAddress());
				is = socket.getInputStream();
				os = socket.getOutputStream();           //객체화
				ois = new ObjectInputStream(is);
				oos = new ObjectOutputStream(os);
				String msg = (String)ois.readObject();
				System.out.println("클라이언트의 메세지 : "+msg);
				String retMsg = "서버로부터 되돌아온 메세지 : " +msg; //클라이언트에게 보내줄 문자열(메세지)
				oos.writeObject(retMsg); //보내기
				socket.close();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 192.168.3.6
		}
	}
}

/*  
 (컴퓨터의 네트워크 카드 이용)
<통신의 종류> - 3대 통신
- TCP/IP 통신    ==> 게임, 채팅(Socket), 강제로 끊기 전까지는 연결되어 있음 <-(이걸 사용할 것임)
-   HTTP 통신    ==> 웹(URL), 일방적 접근 후 리턴 값을 받고 끊음
- Serial 통신    ==> 기기간의 연결(마우스, 키보드, 전자기기, USB포트)
*/          