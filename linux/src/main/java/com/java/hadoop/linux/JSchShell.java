package com.java.hadoop.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;

public class JSchShell {
    Logger logger = null;
    private String charset = "UTF-8"; //
    private static String user="hadoop"; //
    private static String passwd="hadoop"; //
    private static String host="192.168.42.210"; //
    private static int port=22; //


    public static void main(String[] args) throws Exception {
        
        

        String command1="ls -ltr";
//        String command1="ls -l";
//        String command1="java";
        try{
            
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(user, host, port);
            session.setPassword(passwd);
            session.setConfig(config);
            session.connect();

            System.out.println("Connected");

            ChannelShell channel=(ChannelShell) session.openChannel("shell");
            InputStream in = channel.getInputStream();
            channel.setPty(true);
            channel.connect();
            System.out.println("exit-status: "+channel.getExitStatus());
            OutputStream os = channel.getOutputStream();
            os.write((command1 + "\r\n").getBytes());
            os.flush();

            byte[] tmp=new byte[1024];
            while(true){
              while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                String s = new String(tmp, 0, i);
                if (s.indexOf("--More--") >= 0 ) {
                    os.write((" ").getBytes());
                    os.flush();
                }
                System.out.print(s);
              }
                try{Thread.sleep(1000);}catch(Exception ee){}
              if(!(in.available()>0)){
                  break;
              }
            }
            os.close();
            in.close();
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    static class ColorTest{
        public static void main(String[] args) {

            System.out.println("\033[31;4m我滴个颜什\033[0m");
            System.out.println("\033[30;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[31;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[32;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[33;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[34;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[35;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[36;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[37;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[40;31;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[41;32;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[42;33;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[43;34;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[44;35;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[45;36;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[46;37;4m" + "我滴个颜什" + "\033[0m");
            System.out.println("\033[47;4m" + "我滴个颜什" + "\033[0m");
        }
    }
    
}