//package com.kmecpp.enjinnews.database;
//
//import java.io.File;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
//import org.bukkit.Bukkit;
//import org.bukkit.util.StringUtil;
//
//import com.kmecpp.enjinnews.Logger;
//import com.kmecpp.enjinnews.database.DatabaseQueue.QueueExecutor;
//import com.kmecpp.enjinnews.file.Directory;
//import com.kmecpp.jlib.utils.IOUtil;
//import com.voidflame.CoreData;
//import com.voidflame.database.sqlite.DBResult;
//import com.voidflame.database.sqlite.DBRow;
//import com.voidflame.database.sqlite.DBUtil;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;
//
//public class Database {
//
//	private static HikariDataSource database;
//	private static CountDownLatch latch = new CountDownLatch(1);
//
//	private Database() {
//	}
//
//	public static void start() {
//		try {
//			Logger.info("Using SQLite for database storage");
//
//			HikariConfig config = new HikariConfig();
//
//			config.setJdbcUrl("jdbc:sqlite:" + Directory.PLUGIN + File.separator + "data.db");
//			config.setDriverClassName("org.sqlite.JDBC");
//
//			config.setMinimumIdle(3);
//			config.setMaximumPoolSize(10);
//			config.setConnectionTimeout(3000L);
//			config.setConnectionTestQuery("SELECT 1");
//			database = new HikariDataSource(config);
//			latch.countDown();
//		} catch (PoolInitializationException e) {
//			Logger.error("Invalid database configuration!");
//			e.printStackTrace();
//			Bukkit.getServer().shutdown();
//		}
//		DatabaseQueue.start();
//	}
//
//	public static String getTableName(Class<?> tableClass) {
//		return SQLite.getTable(tableClass).getName();
//	}
//
//	public static void replaceInto(Class<?> cls, Object obj) {
//		updateAsync(DBUtil.createReplaceInto(cls, obj));
//	}
//
//	/**
//	 * Queues the SQL query to execute asynchronously sometime in the future.
//	 * This method has the same effect as executeUpdate() but does not wait for
//	 * a response, resulting in an execution time ~5µs on average, as well as
//	 * having the benefit of not having the server hang on a slow connection
//	 * 
//	 * @param update
//	 *            the SQL query to execute
//	 */
//	public static void updateAsync(String update) {
//		DatabaseQueue.queue(update);
//	}
//
//	/**
//	 * Executes the given SQL statement which may be an INSERT, UPDATE, or
//	 * DELETE statement or an SQL statement that returns nothing, such as an SQL
//	 * DDL statement.
//	 * 
//	 * @param update
//	 *            the SQL statement to execute
//	 */
//	public static void update(String update) {
//		Logger.debug("Executing update" + (Thread.currentThread().getClass().equals(QueueExecutor.class) ? " asynchronously" : "") + ": '" + update + "'");
//		Connection connection = null;
//		Statement statement = null;
//		try {
//			connection = getConnection();
//			statement = connection.createStatement();
//			statement.executeUpdate(update);
//		} catch (SQLException e) {
//			Logger.error("Failed to execute database update!");
//			if (!CoreData.DEBUG) {
//				Logger.error("Failed update: '" + update + "'");
//			}
//			e.printStackTrace();
//		} finally {
//			IOUtil.close(connection, statement);
//		}
//	}
//
//	public static void setAll(Class<?> tableClass, String column, Object value) {
//		update("UPDATE " + getTableName(tableClass) + " SET " + DBUtil.getColumnName(column) + "='" + value + "'");
//	}
//
//	public static <T> T getFirst(Class<T> tableClass, OrderBy orderBy, String columns, Object... values) {
//		DBResult result = query("SELECT * FROM " + SQLite.getTable(tableClass).getName() + " WHERE " + DBUtil.createWhere(columns.split(","), values) + " " + orderBy);
//		return result.isEmpty() ? null : result.first().as(tableClass);
//	}
//
//	public static <T> T getFirst(Class<T> tableClass, OrderBy orderBy) {
//		return orderBy(tableClass, orderBy, 1).get(0);
//	}
//
//	public static <T> ArrayList<T> orderBy(Class<T> tableClass, OrderBy orderBy, int limit) {
//		return query("SELECT * FROM " + SQLite.getTable(tableClass).getName() + " " + orderBy + " LIMIT " + limit).as(tableClass);
//	}
//
//	public static <T> ArrayList<T> orderBy(Class<T> tableClass, OrderBy orderBy, int min, int max) {
//		return query("SELECT * FROM " + SQLite.getTable(tableClass).getName() + " " + orderBy + " LIMIT " + min + "," + max).as(tableClass);
//	}
//
//	public static <T> ArrayList<T> orderBy(Class<T> tableClass, String orderBy, int min, int max) {
//		return orderBy(tableClass, OrderBy.desc(orderBy), min, max);
//	}
//
//	public static <T> T get(Class<T> tableClass, Object... primaryKeys) {
//		return getOrDefault(tableClass, null, primaryKeys);
//	}
//
//	public static <T> T getOrDefault(Class<T> tableClass, T defaultValue, Object... primaryKeys) {
//		ArrayList<T> list = query(tableClass, primaryKeys);
//		return list.isEmpty() ? defaultValue : list.get(0);
//	}
//
//	public static <T> ArrayList<T> query(Class<T> table, Object... primaryKeys) {
//		return query(table, SQLite.getTable(table).getPrimaryColumns(), primaryKeys);
//	}
//
//	public static <T> ArrayList<T> query(Class<T> table, String columns, Object... values) {
//		return query(table, columns.split(","), values);
//	}
//
//	public static <T> ArrayList<T> query(Class<T> table, String[] columns, Object... values) {
//		ArrayList<T> list = new ArrayList<>();
//		DBResult result = query("SELECT * FROM " + SQLite.getTable(table).getName() + " WHERE " + DBUtil.createWhere(columns, values));
//		for (int i = 0; i < result.size(); i++) {
//			list.add(result.get(i).as(table));
//			//			list.add(table.createRecord(result.get(i)));
//		}
//		return list;
//	}
//
//	/**
//	 * Executes an SQL query on the database and gets the result
//	 * 
//	 * @param query
//	 *            the query to execute
//	 * @return the result of the query
//	 */
//	public static DBResult query(String query) {
//		Connection connection = null;
//		Statement statement = null;
//		ResultSet resultSet = null;
//		try {
//			Logger.debug("Executing query: '" + query + "'");
//			connection = getConnection();
//			statement = connection.createStatement();
//			resultSet = statement.executeQuery(query);
//			return new DBResult(resultSet);
//		} catch (SQLException e) {
//			Logger.error("Failed to execute database query: '" + query + "'");
//			e.printStackTrace();
//			return null;
//		} finally {
//			IOUtil.close(connection, statement, resultSet);
//		}
//	}
//
//	/**
//	 * Executes an SQL query on the database that should return a single row
//	 * 
//	 * @param query
//	 *            the query to execute
//	 * @return the row retrieved
//	 */
//	public static DBRow queryRow(String query) {
//		return query(query).only();
//	}
//
//	/**
//	 * Filters the rows of the specified table with the given filters. Filters
//	 * should be valid SQLite WHERE clauses.
//	 * 
//	 * @param table
//	 *            the table name to query
//	 * @param filters
//	 *            the filters to apply
//	 * @return the results matching the given filters
//	 */
//	public static DBResult filter(String table, String... filters) {
//		return Database.query("SELECT * FROM " + table + " WHERE " + StringUtil.join(filters, " AND "));
//	}
//
//	/**
//	 * Gets whether or not the server is using MySQL for database storage or not
//	 * 
//	 * @return true if MySQL is being used and false if not
//	 */
//	public static boolean usingMySql() {
//		return usingMySql;
//	}
//
//	//	/**
//	//	 * Gets the SQLTable in the database with the given name, case insensitive
//	//	 * 
//	//	 * @param table
//	//	 *            the name of the table to get
//	//	 * @return the {@link SQLTable} instance
//	//	 */
//	//	public static <T extends DBTable<? extends DBRecord>> T getTable(Class<T> tableClass) {
//	//		return SQLite.getTable(tableClass);
//	//	}
//
//	//	/**
//	//	 * Creates the given {@link DBTable} if it does not exist. If the table
//	//	 * already exists, this method will fail silently.
//	//	 * 
//	//	 * @param table
//	//	 *            the table to create
//	//	 */
//	//	public static void createTable(DBTable<?> table) {
//	//		//		executeUpdate("CREATE TABLE IF NOT EXISTS " + table.getName() + " " + table.buildSchema() + ";");
//	//	}
//
//	public static void renameTable(String oldName, String newName) {
//		update("ALTER TABLE " + oldName + " RENAME TO " + newName);
//	}
//
//	/**
//	 * Creates a database table with the given name representing the specified
//	 * class. If the table already exists, this method will fail silently.
//	 * 
//	 * @param name
//	 *            the name of the table to create
//	 * @param cls
//	 *            the class whose database representation to create
//	 */
//	public static void createTable(String name, Class<?> cls) {
//		TableData data = TableData.create(name, cls);
//
//		DBResult result = query("PRAGMA TABLE_INFO(" + data.getName() + ")");
//
//		if (result != null) {
//			List<String> newColumns = result.getColumns();
//			//TODO: IMPLEMENT TABLE SAFETY
//
//			//			//Fix structure
//			//			if (newColumns.size() != data.getColumnCount()) {
//			//				CoreLogger.warn("Database table structure modification detected! Attempting to repair changes...");
//			//				String tempName = "'old_" + name + "." + System.currentTimeMillis() + "'";
//			//				update("ALTER TABLE " + name + " RENAME TO " + tempName + ";"
//			//						+ DBUtil.createTable(name, data)
//			//						+ "INSERT INTO " + name + " SELECT " + (result.size() > data.getColumnCount()
//			//								? StringUtil.join(data.getColumns(), ", ") //DELETED
//			//								: "*") //ADDED
//			//						+ " FROM " + tempName + ";");
//			//			}
//
//			//		//Fix types
//			//		for (Field field : data.getFields()) {
//			//		}
//
//			for (int i = 0; i < newColumns.size(); i++) {
//
//			}
//
//		}
//
//		update(DBUtil.createTable(name, data));
//	}
//
//	/**
//	 * Gets a connection from the connection pool or null if a Connection cannot
//	 * be established
//	 * 
//	 * @return a Connection to the data source or null
//	 */
//	public static Connection getConnection() {
//		try {
//			try {
//				latch.await();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			return database.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public static boolean isActive() {
//		return !database.isClosed();
//	}
//
//	public static boolean isClosed() {
//		return database.isClosed();
//	}
//
//	/**
//	 * Shuts down the connection pool
//	 */
//	public static void shutdown() {
//		if (!database.isClosed()) {
//			DatabaseQueue.flush(); //Queue should already have connection
//			database.close();
//		}
//	}
//
//	public static void reload() {
//		Logger.info("Reestablishing database connection");
//		latch = new CountDownLatch(1);
//		shutdown();
//		start();
//	}
//
//}
