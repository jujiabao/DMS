package com.jujiabao;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * DMS服务端
 * 负责接收所有客户端发送过来的配对日志，并存入一个文件中保存
 * 负责接收所有客户端发送过来的配对日志，并存入一个文件中保存
 * 负责接收所有客户端发送过来的配对日志，并存入一个文件中保存
 * 负责接收所有客户端发送过来的配对日志，并存入一个文件中保存
 * 负责接收所有客户端发送过来的配对日志，并存入一个文件中保存
 * @author Hello.Ju
 *
 */
public class DMSServer {
	/**
	 * 属性的定义
	 */
	private ServerSocket server;
	
	//线程池，用来管理与客户端交互的线程
	private ExecutorService threadPool;
	//用于保存所有客户端发送过来的配对文件
	private File serverLogFile;
	//用于保存客户端的接收的带保存的日志消息队列
	private BlockingQueue<String> messageQueue;
	/**
	 * 构造方法，用来初始化服务端
	 * @throws Exception
	 */
	public DMSServer() throws Exception {
		try {
			/**
			 * 这里初始化还是应当使用一个配置文件
			 * 我们可以定义一个server——config.xml，然后将服务端需要的内容进行配置，在使用这些配置项初始化服务端的属性,
			 * 参考客户端的方法
			 */
			server = new ServerSocket(8088);
			threadPool = Executors.newFixedThreadPool(30);
			serverLogFile = new File("server-logs.txt");
			messageQueue = new LinkedBlockingQueue<String>();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("服务端初始化失败！");
			throw e;
		}
	}
	/**
	 * 服务端开始工作的方法
	 * @throws Exception
	 */
	public void start() throws Exception{
		try {
			/**
			 * 将保存日志的线程启动起来
			 */
			SaveLogHandler saveLogHandler = new SaveLogHandler();
			Thread thread = new Thread(saveLogHandler);
			thread.start();
			
			while (true) {
				Socket socket = server.accept();
				ClientHandler handler = new ClientHandler(socket);
				threadPool.execute(handler);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("服务端运行失败！");
			throw e;
		}
	}
	
	public static void main(String[] args) {
		try {
			DMSServer mDmsServer = new DMSServer();
			mDmsServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 该线程任务用于周期性的从消息队列中取出每一条配对日志并保存到serverLogFile中
	 * @author Hello.Ju
	 *
	 */
	private class SaveLogHandler implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(serverLogFile);
				/**
				 * 从消息队列中获取一条日志，并写入文件，当队列中暂时没有元素时，休息
				 */
				while (true) {
					if (messageQueue.size() > 0) {
						String log = messageQueue.poll();
						pw.println(log);
					}else {
						pw.flush();
						Thread.sleep(500);//让线程休息500ms
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				if (pw != null) {
					pw.close();
				}
			}
		}
	}
	/**
	 * 该线程主要任务是接收一个客户端发送过来的所有配对日志并保存，然后回应客户端的处理结果
	 * @author Hello.Ju
	 *
	 */
	private class ClientHandler implements Runnable{
		//与客户端通讯的Socket
		private Socket socket;
		
		public ClientHandler(Socket socket){
			this.socket = socket;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//用于回复客户端的输出流
			PrintWriter pw = null;
			try {
				//3 这个是步骤3的内容
				pw = new PrintWriter(new OutputStreamWriter(
						socket.getOutputStream(), "UTF-8"));
				/**
				 * 步骤：
				 * 1：通过socket获取输入流，并包装为BufferedReader，用于读取客户端发送过来的配对日志
				 * 2:循环读取每一条日志并将日志写入文件中保存；
				 * 3：当全部所接受完毕后，通过Socket获取输出流，并转换为PrintWriter用于给客户端发送响应；
				 * 4：回复客户端“OK”
				 */
				//1
				BufferedReader br = new BufferedReader(new InputStreamReader(
						socket.getInputStream(), "UTF-8"));				
				//2
				String line = null;
				while ((line = br.readLine()) != null) {
					if ("OVER".equals(line)) {
						break;
					}
					messageQueue.offer(line);
				}
				//4
				pw.println("OK");
				pw.flush();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				pw.println("ERROR");
				pw.flush();
			}finally{
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
