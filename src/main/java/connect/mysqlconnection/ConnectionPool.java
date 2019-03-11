package connect.mysqlconnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
	private static long expiredTime ;
	private static int connectionPoolSize;
	
	private final List<Connection> availableConnections = Collections.synchronizedList(new ArrayList<Connection>());
	private final List<Connection> inUseConnections = Collections.synchronizedList(new ArrayList<Connection>());
	
	private final AtomicInteger count = new AtomicInteger(0);
	private final AtomicBoolean waiting = new AtomicBoolean(false);
	
	public synchronized Connection getConnection() {
		// neu con connection trong pool thi lay dung
		if(!availableConnections.isEmpty()) {
			Connection con = availableConnections.remove(0);
			inUseConnections.add(con);
			return con;
		}
		//nếu pool is full thì đợi đến khi có connection available
		if(count.get() == connectionPoolSize) {
			this.waitingUntilAvailable();
			return this.getConnection();
		}
		//nếu pool chưa đầy thì thêm connection vào pool
		Connection con = this.createConnection();
		inUseConnections.add(con);
		return con;
	}
	// tra connection về pool
	public synchronized void release(Connection con) {
		inUseConnections.remove(con);
		availableConnections.add(con);
		//log
	}
	
	private void waitingUntilAvailable() {
		if(waiting.get()) {
			waiting.set(false);
			System.out.println("no connection available");
		}
		waiting.set(true);
		waiting(expiredTime);
	}
	private Connection createConnection() {
		//tạo 1 connection
		Connection connection = MysqlConnection.getConnection();
		
		return connection;
	}
	
	//đợi
	private void waiting(long time) {
		try {
			TimeUnit.MILLISECONDS.sleep(time);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void freePool() {
		
	}
}
