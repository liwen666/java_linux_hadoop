package com.java.hadoop.datasource;

import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopeTable {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
        Connection master = DriverManager.getConnection("jdbc:oracle:thin:@192.168.9.174:1521:orcl", "test_id_01", "test_id_01");
        System.out.println(master + "000");
        Statement statement = master.createStatement();
        ResultSet resultSet = statement.executeQuery("select * FROM act_id_user");
        while (resultSet.next()) {
            String id_ = resultSet.getString("id_");
            System.out.println(id_);

        }
        Connection slaver = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "analysis_service", "1");
        System.out.println(slaver + "000");
        Statement statementSlaver = slaver.createStatement();
        System.out.println("--------------------");
        ResultSet resultSetSlaver = statementSlaver.executeQuery("select * FROM act_id_user");
        while (resultSetSlaver.next()) {
            String id_ = resultSetSlaver.getString("id_");
            System.out.println(id_);

        }
        master.close();
        slaver.close();

    }
    @Test
    public void copyTable() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
        Connection master = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "analysis_client", "1");
        Connection slaver = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "analysis_service", "1");
        slaver.setAutoCommit(false);
//        String tableName="act_ge_bytearray";
        String tableName="act_hq_taskanaly";
//        String tableName="act_hq_tem_def";
//        String tableName="act_hq_tem_category";
        String sql = "select * from "+tableName;
        PreparedStatement preparedStatement = master.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println(resultSet.last());
        int row = resultSet.getRow();
        System.out.println("一共   "+row+ "   条数据");
        ResultSetMetaData rsm = resultSet.getMetaData(); //获得列集
        int col = rsm.getColumnCount();   //获得列的个数
        System.out.println("获得列的个数====" + col);
        String colName[] = new String[col];
        String colType[] = new String[col];
        //取结果集中的表头名称, 放在colName数组中
        for (int i = 0; i < col; i++) {   //第一列,从1开始.所以获取列名,或列值,都是从1开始
            colName[i] = rsm.getColumnName(i + 1); //获得列值的方式一:通过其序号
            colType[i] = rsm.getColumnTypeName(i+1);
//            System.out.println(rsm.getColumnType(i+1));
        }//End for
        resultSet.beforeFirst();
        System.out.println("列名-------" + Arrays.toString(colName));
        System.out.println("列类名-------" + Arrays.toString(colType));
        Object data[][] = new Object[row][col];
        //取结果集中的数据, 放在data数组中
        for (int i = 0; i < row; i++) {
            resultSet.next();
            for (int j = 0; j < col; j++) {
//                System.out.println(colName[j]);
                if(colType[j].equals("NUMBER")||colType[j].equals("VARCHAR2")||colType[j].equals("VARCHAR")){
                    data[i][j] = resultSet.getString(j + 1);
//                }else if(colType[j].equals("NUMBER")||colType[j].equals("int")){
//                    System.out.println(resultSet.getInt(j+1));
//                    data[i][j] = resultSet.getInt(j + 1);
                }else if(colType[j].equals("TIMESTAMP")){
                    data[i][j] = resultSet.getTimestamp(j + 1);
                }else if(colType[j].equals("BLOB")){
                    data[i][j] = resultSet.getBlob(j + 1);
                }
//                System.out.println(data[i][j]);
            }
        }
        System.out.println(Arrays.toString(data));
        StringBuffer insertSql = new StringBuffer("insert into "+tableName+" values(");
        for(int insr=0;insr<col;insr++){
            insertSql.append("?,");
        }
        String exesql = insertSql.substring(0, insertSql.length() - 1) + ")";


//
        for(int var1=0;var1<row;var1++){
            PreparedStatement preparedStatementSlaver = slaver.prepareStatement(exesql);
            for(int var2=0;var2<col;var2++){
                Object o = data[var1][var2];
//                if(colType[var2].equals("VARCHAR2")||colType[var2].equals("VARCHAR")){
//                    preparedStatementSlaver.setString(var2+1,(String) o);
//                }else if(colType[var2].equals("NUMBER")||colType[var2].equals("int")){
//                    preparedStatementSlaver.setInt(var2+1,(Integer) o);
//
//                }else if(colType[var2].equals("TIMESTAMP")){
//                    preparedStatementSlaver.setTimestamp(var2+1,(Timestamp) o);
//
//                }else if(colType[var2].equals("BLOB")){
//                    preparedStatementSlaver.setBlob(var2+1,(Blob) o);
//                }
                preparedStatementSlaver.setObject(var2+1, o);

            }
            preparedStatementSlaver.executeUpdate();
            break;
//            preparedStatementSlaver.close();
        }

        //批量执行对act_hq_tem_def表有00600错误
//        for(int var1=0;var1<row;var1++){
//            //JDBC批量插入
//            for(int var2=0;var2<col;var2++){
//                Object o = data[var1][var2];
////                System.out.println(o);
////                if(colType[var2].equals("VARCHAR2")||colType[var2].equals("VARCHAR")){
////                    preparedStatementSlaver.setString(var2+1,(String) o);
////                }else if(colType[var2].equals("NUMBER")||colType[var2].equals("int")){
////                    preparedStatementSlaver.setInt(var2+1,(Integer) o);
////
////                }else if(colType[var2].equals("TIMESTAMP")){
////                    preparedStatementSlaver.setTimestamp(var2+1,(Timestamp) o);
////
////                }else if(colType[var2].equals("BLOB")){
////                    preparedStatementSlaver.setBlob(var2+1,(Blob) o);
////                }
//                preparedStatementSlaver.setObject(var2+1, o);
//
//            }
//            preparedStatementSlaver.addBatch();
//        }
//            preparedStatementSlaver.executeBatch();

        slaver.commit();

        System.out.println("数据拷贝成功");
        master.close();
        slaver.close();

    }


}

