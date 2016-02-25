package entity;

public class sms {
	// 短信Gateway 实体
	protected String serial;
	protected int port;
	protected String method;

	protected Object smsInstance; // 设备具体实例
	protected String status = "send"; // 设备状态，send or recv
	
	protected Object objSend;
	protected Object objRecv;

	public sms(String serial, int port, String method) {
		this.serial = serial;
		this.port = port;
		this.method = method;

		smsInstance = serial;
		// 创建发短信的 Gateway
		System.out.println("init sms : [serial=" + serial + ", port=" + port + ", method=" + method + "]");
	}

	public String getSerial() {
		return serial;
	}

	public int getPort() {
		return port;
	}

	public String getMethod() {
		return method;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public Object getSmsInstance() {
		return smsInstance;
	}

}
