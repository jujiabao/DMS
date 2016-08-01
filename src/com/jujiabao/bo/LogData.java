package com.jujiabao.bo;
/**
 * 该类用于表示wtmpx文件上的一条日志
 * 由于我们的业务逻辑只需要一条日志中的五项信息
 * 所以这里仅提供五个属性即可
 * @author Hello.Ju
 *
 */
public class LogData {
	/**
	 * 常量定义
	 * 
	 * 多个实例都应当具有，但是值不一样的就应该定义为属性，若每个实力都一样的就应当定义为常量
	 */
	
	/**
	 * 日志类型，登入日志
	 * 日志类型，登出日志
	 */
	public static final short TYPE_LOGIN = 7;
	public static final short TYPE_LOGOUT = 8;
	
	/**
	 * 日志在wtmpx文件中的长度（字节量）
	 */
	public static final int LOG_LENGTH = 372;
	/**
	 * user在一条日志中的起始位置
	 */
	public static final int USER_OFFSET = 0;
	/**
	 * user在一条日志中占用的直接量
	 */
	public static final int USER_LENGTH = 32;
	/**
	 * pid在一条日志中的起始位置
	 */
	public static final int PID_OFFSET = 68;
	/**
	 * type在一条日志中的起始位置
	 */
	public static final int TYPE_OFFSET = 72;
	/**
	 * time在一条日志中的起始位置
	 */
	public static final int TIME_OFFSET = 80;
	/**
	 * host在一条日志中的起始位置
	 */
	public static final int HOST_OFFSET = 114;
	/**
	 * host在一条日志中占用的字节量
	 */
	public static final int HOST_LENGTH = 258;
	
	//用户名
	private String user;
	//进程id
	private int pid;
	//日志类型
	private short type;
	//日志生成时间
	private int time;
	//用户地址
	private String host;
	
	public LogData(){
		
	}

	public LogData(String user, int pid, short type, int time, String host) {
		super();
		this.user = user;
		this.pid = pid;
		this.type = type;
		this.time = time;
		this.host = host;
	}
	
	/**
	 * 将给定的字符串进行解析后以LogData的形式保存，该字符串的格式必须由当前类的toString方法决定格式
	 * uesr,pid,type,time,host
	 * @param str
	 */
	public LogData(String str){
		String[] data = str.split(",");
		user = data[0];
		pid = Integer.parseInt(data[1]);
		type = Short.parseShort(data[2]);
		time = Integer.parseInt(data[3]);
		host = data[4];
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return user + "," + pid + "," + type + "," + time + "," + host;
	}
	
	
}
