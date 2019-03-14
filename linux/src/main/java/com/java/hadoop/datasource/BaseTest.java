package com.java.hadoop.datasource;

import org.junit.Test;

import java.sql.*;
import java.util.Arrays;

public class BaseTest {
    @Test
    public void testMed() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "lw_bpmn", "1");
//        Connection slaver = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "analysis_service", "1");

//        String sql ="select * from act_hq_procinst  where id_ in('79c8373f61f14640be2f2b5e3e9c8f39','d03a617c053a4b9a91d8a5e7edd5ddd9')";
//        String sql ="select * from act_hq_procinst";
        String sql = "select ahp.* from test_batch  ahp where rownum<20";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println(resultSet.last());
        int row = resultSet.getRow();
        System.out.println(row);
        ResultSetMetaData rsm = resultSet.getMetaData(); //获得列集
        int col = rsm.getColumnCount();   //获得列的个数
        System.out.println("获得列的个数====" + col);
        String colName[] = new String[col];
        //取结果集中的表头名称, 放在colName数组中
        for (int i = 0; i < col; i++) {   //第一列,从1开始.所以获取列名,或列值,都是从1开始
            colName[i] = rsm.getColumnName(i + 1); //获得列值的方式一:通过其序号
        }//End for
        resultSet.beforeFirst();
        System.out.println("列名-------" + Arrays.toString(colName));
        String data[][] = new String[row][col];
        //取结果集中的数据, 放在data数组中
        for (int i = 0; i < row; i++) {
            resultSet.next();
            for (int j = 0; j < col; j++) {
                data[i][j] = resultSet.getString(j + 1);
                System.out.println(data[i][j]);
            }
        }
//        测试批量添加数据
        batchInsert(connection);
    }

    public void batchInsert(Connection connection) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into test_batch values(?)");
//        preparedStatement.setObject(1,1);
//        preparedStatement.executeUpdate();
        for(int i=0;i<10;i++){
            preparedStatement.clearBatch();
            preparedStatement.setObject(1,i+100);
            preparedStatement.addBatch();
//            preparedStatement.executeBatch();
        }
        preparedStatement.executeBatch();

    }
}
