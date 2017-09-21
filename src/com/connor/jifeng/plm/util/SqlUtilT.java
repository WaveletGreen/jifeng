package com.connor.jifeng.plm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.teamcenter.rac.util.MessageBox;

/**
 * ��ֹ��̳������final��,��ֹ�󽨶��������˽�й��췽��,Ϊ����ʹ��������С�� PreparedStatement��������ResultSet��������С
 * 
 * @author hub 2015-02-06
 */
public final class SqlUtilT {
	public static Connection connection = null;
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;

	// private static Registry reg = Registry.getRegistry(SqlUtilT.class);
	/**
	 * ˽�й���������new���󣬷�ֹ���ö���
	 */
	private SqlUtilT() {

	}

	static {
		String driver = "oracle.jdbc.driver.OracleDriver"; // Util.getProperties("DRIVER");
		if (driver == null) {
			driver = "oracle.jdbc.driver.OracleDriver";
		}

		System.out.println("driver   " + driver);
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("ORACEL DRIVER IS EXCEPTION");
			e.printStackTrace();
		}
	}

	/**
	 * �������ݿ�
	 */
	public static Connection getConnection() {
		try {
			String driver_temp = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@192.168.1.248:1521:TOPPROD";// getProperties("URL");
			String dbName = "jfgroup";// Util.getProperties("dbName");
			String dbPassword = "jfgroup";// Util.getProperties("dbPassword");

			/*
			 * if(driver_temp == null ||url==null|| dbName == null ||dbPassword
			 * == null ){
			 * 
			 * MessageBox.post("�������ݿ���ѡ���Ƿ�������ȷ","������ʾ",MessageBox.ERROR);
			 * 
			 * }
			 */
			System.out.println(url + dbName + dbPassword);
			connection = DriverManager.getConnection(url, dbName, dbPassword);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("ORACEL CONNECT EXCEPTION");
			e.printStackTrace();
			return null;
			// MessageBox.post("ORACEL CONNECT EXCEPTION \n"+e.getMessage(),"ERROR",MessageBox.ERROR);
		}
		return connection;
	}

	/**
	 * �������ݿ�
	 */
	public static Connection getConnection(String url, String dbName,
			String dbPassword) {
		try {
			String driver_temp = "oracle.jdbc.driver.OracleDriver";

			System.out.println(url + dbName + dbPassword);
			connection = DriverManager.getConnection(url, dbName, dbPassword);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// MessageBox.post("MSG ==>"+url + dbName + dbPassword,"",1);
			System.out.println("ORACEL CONNECT EXCEPTION");
			e.printStackTrace();
			MessageBox.post(" MSG = >" + e.getMessage(), "222", 1);
			// MessageBox.post("ORACEL CONNECT EXCEPTION \n"+e.getMessage(),"ERROR",MessageBox.ERROR);
			return null;
		}
		return connection;
	}

	/**
	 * ƴ��SELECT ���
	 * 
	 * @param tableName
	 * @param selectElement
	 * @param args
	 * @return
	 */
	public static String getSelectSql(String tableName, String[] selectElement,
			String... args) {
		StringBuffer valuesSB = new StringBuffer("SELECT   ");
		if (selectElement != null) {
			for (String element : selectElement) {
				valuesSB.append(element).append(",");
			}
			valuesSB.delete(valuesSB.length() - 1, valuesSB.length());
		}
		valuesSB.append(" FROM ");
		valuesSB.append(tableName);
		valuesSB.append(" WHERE");
		for (int i = 0; i < args.length; i++) {
			valuesSB.append(" ");
			valuesSB.append(args[i]);
			valuesSB.append("=? ");
			valuesSB.append("AND");
		}

		valuesSB.delete(valuesSB.length() - 3, valuesSB.length());
		return valuesSB.toString();
	}

	/**
	 * ƴ��SELECT ���
	 * 
	 * @param tableName
	 * @param args
	 * @return
	 */
	public static String getSelectSql(String tableName, String... args) {
		StringBuffer valuesSB = new StringBuffer("SELECT * FROM ");
		valuesSB.append(tableName);
		valuesSB.append(" WHERE");
		for (int i = 0; i < args.length; i++) {
			valuesSB.append(" ");
			valuesSB.append(args[i]);
			valuesSB.append("=? ");
			valuesSB.append("AND");
		}

		valuesSB.delete(valuesSB.length() - 3, valuesSB.length());
		return valuesSB.toString();
	}

	/**
	 * ƴ�Ӹ������
	 * 
	 * @param tableName
	 * @param args
	 * @param args2
	 * @return
	 */
	public static String getUpdataSQL(String tableName, String[] args,
			String[] args2) {
		StringBuffer updateSB = new StringBuffer("UPDATE ");
		updateSB.append(tableName);
		updateSB.append(" SET ");
		// ƴ�Ӹ������
		for (int i = 0; i < args.length; i++) {
			if (args[i].toUpperCase().equals("CREATED_DATE")
					|| args[i].toUpperCase().equals("LAST_UPDATE_DATE")) {
				updateSB.append(args[i]).append(
						"=to_date(?,'yyyy-MM-dd HH24:mi:ss') ,");
			} else {
				updateSB.append(args[i]).append("=? ,");
			}
		}
		updateSB.delete(updateSB.length() - 2, updateSB.length());
		;
		updateSB.append(" WHERE ");
		for (int i = 0; i < args2.length; i++) {
			updateSB.append(args2[i]).append("=? AND ");
		}
		updateSB.delete(updateSB.length() - 4, updateSB.length());
		return updateSB.toString();
	}

	/**
	 * ƴ��SQL��insert���
	 * 
	 * @param tableName
	 * @param args
	 * @return
	 */
	public static String getInsertSql(String tableName, String... args) {
		StringBuffer insertSql = new StringBuffer("insert into ");
		StringBuffer values = new StringBuffer("values(");

		if (tableName != null && args != null && args.length > 0) {
			insertSql.append(tableName);
			insertSql.append("(");
			for (int i = 0; i < args.length; i++) {
				insertSql.append(args[i]);
				insertSql.append(", ");
				if (args[i].toUpperCase().equals("LAST_UPDATE_DATE")
						|| args[i].toUpperCase().equals("CREATED_DATE")) {
					values.append("to_date(?,'yyyy-MM-dd HH24:mi:ss'), ");
				} else {
					values.append("?, ");
				}

			}
		} else {
			return null;
		}
		insertSql.delete(insertSql.length() - 2, insertSql.length());
		values.delete(values.length() - 2, values.length());
		insertSql.append(") ").append(values).append(")");
		return insertSql.toString();
	}

	/**
	 * �õ�����������ݿ����
	 * 
	 * @param tableName
	 * @param args
	 *            ����������
	 * @param args2
	 *            ����������
	 * @return ƴ�Ӻ�Ĵ������ݿ�����
	 */
	public final static String GetCreateTableSQL(String tableName,
			String[] args, String[] args2) {

		if (args == null || args2 == null || args.length != args2.length) {
			System.out.println("THE INPUT PRAGREMS IS ERROR");
			return null;
		}
		StringBuffer createSQL = new StringBuffer("create table ");
		createSQL.append(tableName);
		createSQL.append("(");
		for (int i = 0; i < args.length; i++) {
			createSQL.append(args[i] + " ");
			createSQL.append(args2[i] + ", ");
		}
		createSQL.delete(createSQL.length() - 2, createSQL.length());
		createSQL.append(")");
		return createSQL.toString();
	}

	/**
	 * �õ�Statement
	 */
	public final static PreparedStatement getPs(String sql) throws SQLException {
		return getPs(sql, null);
	}

	/**
	 * �õ�Statement
	 */
	public final static PreparedStatement getPs(Object[] argments, String sql)
			throws SQLException {
		return getPs(sql, argments);
	}

	/**
	 * �õ�Statement
	 */
	public final static PreparedStatement getPs(String sql, Object[] argments)
			throws SQLException {
		SqlUtilT.ps = SqlUtilT.connection.prepareStatement(sql);
		if (argments != null) {
			for (int i = 0; i < argments.length; i++) {
				SqlUtilT.ps.setObject(i + 1, argments[i]);
			}
		}
		return SqlUtilT.ps;
	}

	/**
	 * ����
	 */
	public final static int write(String sql) {
		return write(sql, null);
	}

	/**
	 * ����
	 */
	public final static int write(Object[] argments, String sql) {
		return write(sql, argments);
	}

	/**
	 * ����
	 */
	public final static int write(String sql, Object[] argments) {

		return update(sql, argments);
	}

	/**
	 * ɾ��
	 */
	public final static int delete(String sql) {
		return delete(sql, null);
	}

	/**
	 * ɾ��
	 */
	public final static int delete(Object[] argments, String sql) {
		return delete(sql, argments);
	}

	/**
	 * ɾ��
	 */
	public final static int delete(String sql, Object[] argments) {
		return update(sql, argments);
	}

	/**
	 * �޸�
	 */
	public final static int update(String sql) {
		return update(sql, null);
	}

	/**
	 * �޸�
	 */
	public final static int update(String[] argments, String sql) {
		return update(sql, argments);
	}

	/**
	 * �޸�(��ɾ�����ն�Ҫ���ô˷������˴������쳣�����رճ�Connection��Ϊ�Ķ���)
	 */
	public final static int update(String sql, Object[] argments) {
		int i = -1;
		try {
			i = SqlUtilT.getPs(argments, sql).executeUpdate();
			SqlUtilT.connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			free(SqlUtilT.rs, SqlUtilT.ps);
		}
		return i;
	}

	/**
	 * ��ѯ
	 */
	public final static ResultSet read(String sql) throws SQLException {
		return read(sql, null);
	}

	/**
	 * ��ѯ
	 */
	public final static ResultSet read(Object[] argments, String sql)
			throws SQLException {
		return read(sql, argments);
	}

	/**
	 * ��ѯ(���в�ѯ���ն�Ҫ���ô˷������������治�����쳣���׳����ɵ����ߴ���������finally������ͷ���Դ)
	 * 
	 * @throws SQLException
	 */
	public final static ResultSet read(String sql, Object[] argments)
			throws SQLException {
		return SqlUtilT.rs = SqlUtilT.getPs(argments, sql).executeQuery();
	}

	/**
	 * ������
	 */
	public final static boolean createTable(String sql) {
		return go(sql, null);
	}

	/**
	 * ɾ����
	 */
	public final static boolean dropTable(String sql) {
		return go(sql, null);
	}

	/**
	 * �޸ı�
	 */
	public final static boolean alterTable(String sql) {
		return go(sql, null);
	}

	/**
	 * �˷�������ִ��DDL���(������,�޸ı�,ɾ����)
	 */
	private final static boolean go(String sql, Object[] argments) {
		boolean flag = false;
		try {
			flag = SqlUtilT.getPs(sql, argments).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			free(SqlUtilT.rs, SqlUtilT.ps);
		}
		if (flag) {
			try {
				SqlUtilT.connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * �ͷ���Դ
	 */
	public final static void free(ResultSet resultSet) {
		free(resultSet, null, null);
	}

	/**
	 * �ͷ���Դ
	 */
	public final static void free(Statement statement) {
		free(null, statement, null);
	}

	/**
	 * �ͷ���Դ
	 */
	public final static void free(Connection connection) {
		free(null, null, connection);
	}

	/**
	 * �ͷ���Դ
	 */
	public final static void free(ResultSet resultSet, Statement statement) {
		free(resultSet, statement, null);
	}

	/**
	 * �ͷ���Դ
	 */
	public final static void free(Statement statement, Connection connection) {
		free(null, statement, connection);
	}

	/**
	 * �ͷ���Դ(��������Ĭ�ϲ���)
	 */
	public final static void free() {
		free(SqlUtilT.rs, SqlUtilT.ps);
	}

	/**
	 * �ͷ���Դ(ȫ���ͷ�)
	 */
	public final static void freeAll() {
		free(SqlUtilT.rs, SqlUtilT.ps, SqlUtilT.connection);
	}

	/**
	 * �ͷ���Դ(��������ҲҪȫ���ͷ�)
	 */
	public final static void free(ResultSet resultSet, Statement statement,
			Connection connection) {
		try {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} finally {
			try {
				if (statement != null) {
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
