package com.java.hadoop.linux;

import java.io.IOException;
import java.io.InputStream;

import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.*;

/**
 * java 登录linux系统，并读取执行shell命令结果
 * @author wanghonggang
 * 2018-10-30
 */
public class LinuxShell {
 
	private static Session session;
 
	/**
	 * 远程登录
	 * @param host 主机ip
	 * @param port 端口号，默认22
	 * @param user 主机登录用户名
	 * @param password 主机登录密码
	 * @return
	 * @throws JSchException
	 */
	public static void login(String host, int port, String user,String password)  {
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);
			// 设置第一次登陆的时候提示，可选值:(ask | yes | no)
			session.setConfig("StrictHostKeyChecking", "no");
			// 连接超时
			session.connect(1000*10);
 
		} catch (JSchException e) {
			System.out.println("登录时发生错误！");
			e.printStackTrace();
		}
	}
 
	/**
	 * 执行shell脚本
	 * @param command shell命令脚本
	 * @return
	 * @throws Exception
	 */
	public static String executeShell(String command) throws Exception {
		
		byte[] tmp = new byte[1024]; 
		StringBuffer resultBuffer = new StringBuffer(); // 命令返回的结果
 
		Channel channel = session.openChannel("exec");
		ChannelExec exec = (ChannelExec) channel;
		// 返回结果流（命令执行错误的信息通过getErrStream获取）
		InputStream stdStream = exec.getInputStream();
 
		exec.setCommand(command);
		exec.connect();
 
		try {
			// 开始获得SSH命令的结果
			while (true) {
				while (stdStream.available() > 0) {
					int i = stdStream.read(tmp, 0, 1024);
					if (i < 0) break;
					resultBuffer.append(new String(tmp, 0, i));
				}
				if (exec.isClosed()) {
//					System.out.println(resultBuffer.toString());
					break;
				}
				try {
//					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} finally {
			//关闭连接
			channel.disconnect();
		}
		return resultBuffer.toString();
	}
 
	/**
	 * 关闭连接
	 */
	public static void close() {
		if (session.isConnected())
			session.disconnect();
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
 
		String ip="192.168.42.210";
		String username="root";
		String password="root";
		
		LinuxShell linux = new LinuxShell();
		
		linux.login(ip, 22, username,password);
		String command = "df -h";
		try {
			String result = linux.executeShell(command);
			System.out.println(result);
			linux.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class TestShell{
		private static  JSch jSch = new JSch();

		public static void main(String[] args) throws JSchException, IOException {
			Session hadoop = jSch.getSession("hadoop", "192.168.42.210", 22);
			hadoop.setPassword("hadoop");
			hadoop.setConfig("StrictHostKeyChecking", "no");
			hadoop.connect();
			ChannelShell shell = (ChannelShell) hadoop.openChannel("shell");
			shell.connect();
			InputStream inputStream = shell.getInputStream();



			hadoop.disconnect();
			shell.disconnect();
			inputStream.close();


		}

	}
}