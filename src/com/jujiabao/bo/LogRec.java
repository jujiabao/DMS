package com.jujiabao.bo;
/**
 * 该日志用于表示一组配对的日志
 * 其中包含两条日志信息，一个登入，一个登出
 * @author Hello.Ju
 *
 */
public class LogRec {
	//登入日志
	private LogData login;
	//登出日志
	private LogData logout;
	
	public LogRec() {
		// TODO Auto-generated constructor stub
	}

	public LogRec(LogData login, LogData logout) {
		super();
		this.login = login;
		this.logout = logout;
	}

	public LogData getLogin() {
		return login;
	}

	public void setLogin(LogData login) {
		this.login = login;
	}

	public LogData getLogout() {
		return logout;
	}

	public void setLogout(LogData logout) {
		this.logout = logout;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return login + "|" + logout;
	}
}
