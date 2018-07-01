//package com.kmecpp.enjinnews.database;
//
//import java.lang.Thread.State;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.concurrent.LinkedBlockingQueue;
//
//import com.voidflame.VoidFlameCore;
//
//public class DatabaseQueue {
//
//	private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
//	private static final QueueExecutor executor = new QueueExecutor();
//
//	public static void start() {
//		if (executor.getState() == State.NEW) {
//			executor.start();
//		}
//	}
//
//	public static void flush() {
//		while (!queue.isEmpty()) {
//			try {
//				Database.update(queue.take());
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public static void queue(String update) {
//		if (VoidFlameCore.isShuttingDown()) {
//			Database.update(update);
//		} else {
//			queue.add(update);
//		}
//	}
//
//	public static class QueueExecutor extends Thread {
//
//		public QueueExecutor() {
//			setName("Database Queue Executor");
//			setDaemon(true);
//		}
//
//		@Override
//		public void run() {
//			Connection connection = Database.getConnection();
//			while (true) {
//				try {
//					String update = queue.take();
//					if (Database.isClosed()) {
//						connection = Database.getConnection();
//					}
//					connection.createStatement().executeUpdate(update);
//					Database.update(update);
//				} catch (InterruptedException | SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//}
