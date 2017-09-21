package com.connor.jifeng.plm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.Registry;

/**
 * 防止误继承因此是final类,防止误建对象因此用私有构造方法,为方便使用类名短小， PreparedStatement对象名和ResultSet对象名短小
 * 
 * @author hub 2015-02-06
 */
public final class SqlUtil {
	public static Connection connection = null;
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;
	private static Registry reg = Registry.getRegistry(SqlUtil.class);

	/**
	 * 私有构造器不能new对象，防止误用对象
	 */
	private SqlUtil() {

	}

	static {
		String driver = reg.getString("ORACEL_DRIVER"); // Util.getProperties("DRIVER");
		if (driver == null) {
			driver = "oracle.jdbc.driver.OracleDriver";
		}

		System.out.println("driver   " + driver);
		try {
			//载入访问数据库的
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("ORACEL DRIVER IS EXCEPTION");
			e.printStackTrace();
		}
	}

	/**
	 * 链接数据库
	 */
	public static Connection getConnection() {
		try {
			String driver_temp = reg.getString("ORACEL_DRIVER");
			String url = reg.getString("ORACEL_URL");// getProperties("URL");
			String dbName = reg.getString("ORACEL_NAME");// Util.getProperties("dbName");
			String dbPassword = reg.getString("ORACEL_PASSWORD");// Util.getProperties("dbPassword");

			if (driver_temp == null || url == null || dbName == null
					|| dbPassword == null) {

				MessageBox.post("请检查数据库首选项是否配置正确", "错误提示", MessageBox.ERROR);

			}
			// else{
			// MessageBox.post(driver_temp+" | "+url+" | "+dbName+" | "+dbPassword,"错误提示",MessageBox.ERROR);
			//
			// }
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
	 * 链接数据库
	 */
	public static Connection getConnection(String url, String dbName,
			String dbPassword) {
		try {
			String driver_temp = reg.getString("ORACEL_DRIVER");

			System.out.println(url + dbName + dbPassword);
			connection = DriverManager.getConnection(url, dbName, dbPassword);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("ORACEL CONNECT EXCEPTION");
			e.printStackTrace();
			// MessageBox.post("ORACEL CONNECT EXCEPTION \n"+e.getMessage(),"ERROR",MessageBox.ERROR);
			return null;
		}
		return connection;
	}

	/**
	 * 拼接SELECT 语句
	 * 
	 * @param tableName
	 * @param selectElement
	 * @param args
	 * @return
	 */
	public static String getSelectSql(String tableName, String[] selectElement,
			String... args) {
		StringBuffer valuesSB = new StringBuffer("SELECT  ");
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
	 * 拼接SELECT 语句
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
	 * 拼接更新语句
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
		// 拼接更新语句
		for (int i = 0; i < args.length; i++) {
			if (args[i].toUpperCase().equals("OPERATE_TIME")
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
	 * 拼接SQL的insert语句
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
				if (args[i].toUpperCase().equals("COMMIT_TIME")
						|| args[i].toUpperCase().equals("CREATE_DATE")
						|| args[i].toUpperCase().equals("INVALID_DATE")) {
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
	 * 得到创建表格数据库语句
	 * 
	 * @param tableName
	 * @param args
	 *            参数的名称
	 * @param args2
	 *            参数的类型
	 * @return 拼接后的创建数据库的语句
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
	 * 得到Statement
	 */
	public final static PreparedStatement getPs(String sql) throws SQLException {
		return getPs(sql, null);
	}

	/**
	 * 得到Statement
	 */
	public final static PreparedStatement getPs(Object[] argments, String sql)
			throws SQLException {
		return getPs(sql, argments);
	}

	/**
	 * 得到Statement
	 */
	public final static PreparedStatement getPs(String sql, Object[] argments)
			throws SQLException {
		SqlUtil.ps = SqlUtil.connection.prepareStatement(sql);
		if (argments != null) {
			for (int i = 0; i < argments.length; i++) {
				SqlUtil.ps.setObject(i + 1, argments[i]);
			}
		}
		return SqlUtil.ps;
	}

	/**
	 * 增加
	 */
	public final static int write(String sql) {
		return write(sql, null);
	}

	/**
	 * 增加
	 */
	public final static int write(Object[] argments, String sql) {
		return write(sql, argments);
	}

	/**
	 * 增加
	 */
	public final static int write(String sql, Object[] argments) {

		return update(sql, argments);
	}

	/**
	 * 删除
	 */
	public final static int delete(String sql) {
		return delete(sql, null);
	}

	/**
	 * 删除
	 */
	public final static int delete(Object[] argments, String sql) {
		return delete(sql, argments);
	}

	/**
	 * 删除
	 */
	public final static int delete(String sql, Object[] argments) {
		return update(sql, argments);
	}

	/**
	 * 修改
	 */
	public final static int update(String sql) {
		return update(sql, null);
	}

	/**
	 * 修改
	 */
	public final static int update(String[] argments, String sql) {
		return update(sql, argments);
	}

	/**
	 * 修改(增删改最终都要调用此方法，此处进行异常处理，关闭除Connection以为的对象)
	 */
	public final static int update(String sql, Object[] argments) {
		int i = -1;
		try {
			i = SqlUtil.getPs(argments, sql).executeUpdate();
			// 2015-11-19
			// SqlUtil.connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			free(SqlUtil.rs, SqlUtil.ps);
		}
		return i;
	}

	/**
	 * 查询
	 */
	public final static ResultSet read(String sql) throws SQLException {
		return read(sql, null);
	}

	/**
	 * 查询
	 */
	public final static ResultSet read(Object[] argments, String sql)
			throws SQLException {
		return read(sql, argments);
	}

	/**
	 * 查询(所有查询最终都要调用此方法，方法里面不处理异常，抛出后由调用者处理，方便在finally语句中释放资源)
	 * 
	 * @throws SQLException
	 */
	public final static ResultSet read(String sql, Object[] argments)
			throws SQLException {

		return SqlUtil.rs = SqlUtil.getPs(argments, sql).executeQuery();
	}

	/**
	 * 创建表
	 */
	public final static boolean createTable(String sql) {
		return go(sql, null);
	}

	/**
	 * 删除表
	 */
	public final static boolean dropTable(String sql) {
		return go(sql, null);
	}

	/**
	 * 修改表
	 */
	public final static boolean alterTable(String sql) {
		return go(sql, null);
	}

	/**
	 * 此方法用来执行DDL语句(创建表,修改表,删除表)
	 */
	private final static boolean go(String sql, Object[] argments) {
		boolean flag = false;
		try {
			flag = SqlUtil.getPs(sql, argments).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			free(SqlUtil.rs, SqlUtil.ps);
		}
		if (flag) {
			try {
				SqlUtil.connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 释放资源
	 */
	public final static void free(ResultSet resultSet) {
		free(resultSet, null, null);
	}

	/**
	 * 释放资源
	 */
	public final static void free(Statement statement) {
		free(null, statement, null);
	}

	/**
	 * 释放资源
	 */
	public final static void free(Connection connection) {
		free(null, null, connection);
	}

	/**
	 * 释放资源
	 */
	public final static void free(ResultSet resultSet, Statement statement) {
		free(resultSet, statement, null);
	}

	/**
	 * 释放资源
	 */
	public final static void free(Statement statement, Connection connection) {
		free(null, statement, connection);
	}

	/**
	 * 释放资源(不传参数默认部分)
	 */
	public final static void free() {
		free(SqlUtil.rs, SqlUtil.ps);
	}

	/**
	 * 释放资源(全部释放)
	 */
	public final static void freeAll() {
		free(SqlUtil.rs, SqlUtil.ps, SqlUtil.connection);
	}

	/**
	 * 释放资源(传满参数也要全部释放)
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
