
---------------------------------------------------------------
--
-- etl_create_zip_table_multi_pkey
-- 函数描述：创建每日拉链表
--     参数：ods_table，ods表名称
--          pkey，表主键
--          biz_date，业务日期
--   返回值：void
--   创建人：**
--
---------------------------------------------------------------
CREATE OR REPLACE FUNCTION etl_create_zip_table_multi_pkey(ods_table VARCHAR, pkey TEXT[], biz_date INT, debug INT DEFAULT 0)
RETURNS void AS $$
DECLARE
    snap_job_status int4;
    zip_job_status int4;
    start_time timestamp;
    end_time timestamp;
    execute_time int8;
    affected_rows int8;
    new_rows int8;
    updated_rows int8;
    sqlStmt VARCHAR;
    snap_fields VARCHAR;
    his_fields VARCHAR;
    i INT;
    pkey_cons TEXT[];
BEGIN
    -- 检查sys_batch_job_log表当天的每日快照数据是否已经成功生成(step='DAILY_SNAP' AND result = 'SUCCESS')
    EXECUTE 'SELECT COUNT(1) FROM sys_batch_job_log WHERE biz_date = '|| biz_date || 'AND table_name = '''|| ods_table ||''' AND step = ''DAILY_SNAP'' AND result = ''SUCCESS''' INTO snap_job_status; 
    IF snap_job_status = 0 THEN
        RAISE EXCEPTION '日终快照任务未完成';
    END IF;

    -- 检查sys_batch_job_log表当天的拉链数据是否已经成功生成(step='HIS' AND result = 'SUCCESS')
    EXECUTE 'SELECT COUNT(1) FROM sys_batch_job_log WHERE biz_date = '|| biz_date || ' AND table_name = '''|| ods_table ||''' AND result = ''SUCCESS'' AND step = ''HIS''' INTO zip_job_status; 
    PERFORM log_info(format('zip_job_status: %1$s; snap_job_status: %2$s', zip_job_status, snap_job_status), debug);

    -- 2.1.2如果拉链表表里面。没有当天拉链数据，进行数据跑入
    IF (zip_job_status = 1 AND snap_job_status = 1) THEN
        RETURN;
    END IF;
    
    --====第三步：创建增量数据临时表表
    IF (zip_job_status = 0 AND snap_job_status = 1) THEN
        start_time = NOW();
        
        -- 从information schema中找到表里所有字段，并拼接前缀snap，his
        sqlStmt = 'SELECT STRING_AGG(''snap.'' || column_name || '''', '','') AS snap_fields, STRING_AGG(''his.'' || column_name || '''', '','') AS his_fields
                     FROM information_schema.columns
                    WHERE table_name = ''' || ods_table || '''';
        PERFORM log_info(sqlStmt, debug);        
        EXECUTE  sqlStmt into snap_fields, his_fields;

        --删除临时增量表
        EXECUTE 'DROP TABLE IF EXISTS tmp_incr_' || ods_table;

        --创建临时增量数据表
        EXECUTE 'CREATE TABLE tmp_incr_' || ods_table || ' (LIKE ' || ods_table || '_his)';

        --拼接复合主键查询
        pkey_cons = ARRAY[]::TEXT[];
        FOR i IN array_lower(pkey, 1) .. array_upper(pkey, 1)
        LOOP
            pkey_cons = array_append(pkey_cons, 'snap.' || pkey[i] || ' = his.' || pkey[i]);
        end LOOP;
        PERFORM log_info(format('%1$s', array_to_string(pkey_cons, ' AND ')), debug);

        --对比整行MD5, 创建状态更新增量表
        sqlStmt = 'INSERT INTO tmp_incr_' || ods_table || ' 
                        SELECT ' || biz_date || ' AS his_start_time,
                               99991231 AS his_end_time,
                               snap.*,
                               ' || to_int_date(now()::DATE) || ' AS etl_date
                          FROM ' || ods_table || '_snap snap
                    INNER JOIN ' || ods_table || '_his his ON ' || array_to_string(pkey_cons, ' AND ') || ' AND his.his_end_time = 99991231
                         WHERE MD5(ROW(' || snap_fields || '):: TEXT) <> MD5(ROW(' || his_fields || '):: TEXT)';
        PERFORM log_info(sqlStmt, debug);
        EXECUTE  sqlStmt;
        
        GET DIAGNOSTICS updated_rows = ROW_COUNT;  

        --把新增数据插入增量表
        sqlStmt =  'INSERT INTO tmp_incr_' || ods_table || '  
                         SELECT ' || biz_date || ' AS his_start_time,
                                99991231 AS his_end_time,
                                *,
                                ' || to_int_date(now()::DATE) || ' AS etl_date
                           FROM ' || ods_table || '_snap snap
                          WHERE ' || array_to_string(pkey, ' || ') || ' NOT IN (
                         SELECT ' || array_to_string(pkey, ' || ') || ' FROM ' || ods_table || '_his WHERE his_end_time = 99991231)';
        PERFORM log_info(sqlStmt, debug);
        EXECUTE  sqlStmt;
        
        GET DIAGNOSTICS new_rows = ROW_COUNT;  

        --给拉链表做关链
        sqlStmt =  'UPDATE ' || ods_table || '_his 
                       SET his_end_time = ' || to_int_date((to_date(biz_date::text, 'YYYYMMDD') + interval '-1 day')::DATE) || ' 
                     WHERE his_end_time = 99991231 AND ' || array_to_string(pkey, ' || ') || ' IN (
                    SELECT ' || array_to_string(pkey, ' || ') || ' FROM tmp_incr_' || ods_table || ')';
        PERFORM log_info(sqlStmt, debug);
        EXECUTE  sqlStmt;
        
        GET DIAGNOSTICS affected_rows = ROW_COUNT;  

        --把增量数据插入拉链表
        EXECUTE 'INSERT INTO ' || ods_table || '_his SELECT * FROM tmp_incr_' || ods_table || '';

        --删除临时表
        EXECUTE 'DROP TABLE IF EXISTS tmp_incr_' || ods_table;
        
        affected_rows = new_rows + updated_rows + affected_rows;
        end_time = now();
        select extract(epoch FROM (end_time - start_time)) INTO execute_time;

        -- 每日拉链生成完成，记录日志
        INSERT INTO sys_batch_job_log VALUES(biz_date,start_time,end_time,execute_time,ods_table,'ODS','HIS','SUCCESS',affected_rows,new_rows,updated_rows,null);
        RETURN;
    END IF;
        
    EXCEPTION
        WHEN others THEN
            RAISE EXCEPTION '[etl_create_zip_table_multi_pkey][%] %', ods_table, SQLERRM;
            RETURN;
        RETURN;
    END;
$$ language plpgsql;
-- 
--  TRUNCATE ods_anytxn_bm_cc_customer_lmt;
--  TRUNCATE ods_anytxn_bm_cc_customer_lmt_daily_snap;
--  TRUNCATE ods_anytxn_bm_cc_customer_lmt_his;
--  TRUNCATE sys_batch_job_log;
-- SELECT etl_create_daily_snap_table('ods_anytxn_bm_acct_loan', 20200201, 1);
-- SELECT etl_create_daily_snap_table('ods_anytxn_bm_cc_customer_lmt', 20200208, 0);
-- SELECT etl_create_zip_table_multi_pkey('ods_anytxn_bm_cc_customer_lmt', ARRAY['cust_nbr', 'lmt_id', 'contract_no'], 20200208, 0);
;
---------------------------------------------------------------
--
-- 函数名称：gb_first_value_sfunc
--         在分组计算中取对应日期最早时间的值  
-- 函数描述：gb_first_value聚合函数的状态转换函数
--     参数：in_s_value 中间状态
--        ：in_date   日期
--        ：in_num    数值
--        ：condition 过滤条件，为TRUE才计算，FALSE直接忽略
--   返回值：二维数组，【【日期，值】】
--   创建人：北京江融信科技有限公司
--
---------------------------------------------------------------   
CREATE OR REPLACE FUNCTION "gb_first_value_sfunc"("in_s_value" _numeric, "in_date" timestamp, "in_num" numeric, "condition" bool)
    RETURNS "pg_catalog"."_numeric" AS $BODY$
    DECLARE
        int_date int;
        s_value numeric[];
    BEGIN
        IF (condition IS NOT NULL) AND (condition = FALSE) THEN
            return in_s_value;
        END IF;

        int_date = cast(extract(epoch from in_date) as integer);

        IF in_s_value IS NULL OR (int_date < in_s_value[1]) THEN
            s_value[1] = int_date;
            s_value[2] = in_num;
        ELSE
            s_value[1] = in_s_value[1];
            s_value[2] = in_s_value[2];
        END IF;

        return s_value;
    END;
    $BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

---------------------------------------------------------------
--
-- 函数名称：gb_first_value_sfunc
--         在分组计算中取对应日期最早时间的值  
-- 函数描述：gb_first_value聚合函数的最终处理函数
--     参数：s_value 中间状态
--   返回值：数值型
--   创建人：北京江融信科技有限公司
--
---------------------------------------------------------------  
CREATE OR REPLACE FUNCTION "gb_first_value_ffunc"("s_value" _numeric)
    RETURNS "pg_catalog"."numeric" AS $BODY$
    BEGIN
        RETURN s_value[2];
    END;
    $BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

---------------------------------------------------------------
--
-- 函数名称：gb_first_value
--         在分组计算中取对应日期最早时间的值  
-- 函数描述：聚合函数
--     参数：in_date   日期
--        ：in_num    数值
--        ：condition 过滤条件，为TRUE才计算，FALSE直接忽略
--   返回值：数值型
--   创建人：北京江融信科技有限公司
--
---------------------------------------------------------------  
DROP AGGREGATE IF EXISTS gb_first_value(timestamp, numeric, bool);
CREATE AGGREGATE gb_first_value(timestamp, numeric, bool) (
    SFUNC = gb_first_value_sfunc,
    STYPE = numeric[],
    FINALFUNC = gb_first_value_ffunc
);

-- SELECT gb_first_value('2019-01-01'::date, 3);;

---------------------------------------------------------------
--
-- 函数名称：gb_dsum_sfunc
--         去重累计计算
-- 函数描述：gb_dsum聚合函数的状态转换函数
--     参数：in_s_value 中间状态
--        ：in_key   去重键值
--        ：in_num   数值
--   返回值：二维数组，【【日期，值】】
--   创建人：北京江融信科技有限公司
--
---------------------------------------------------------------  
CREATE OR REPLACE FUNCTION "gb_dsum_sfunc"("in_s_value" _numeric, "in_key" numeric, "in_num" numeric)
    RETURNS "pg_catalog"."_numeric" AS $BODY$
    DECLARE
        i INT;
    BEGIN
        IF in_s_value IS NULL OR in_key IS NULL THEN
            RETURN ARRAY[[in_key, in_num]];
        END IF;
     
          
        FOR i IN array_lower(in_s_value, 1) .. array_upper(in_s_value, 1)
        LOOP
          IF in_s_value[i][1] = in_key THEN
              RETURN in_s_value;
          END IF;
        END LOOP;

        RETURN in_s_value || ARRAY[in_key, in_num];
    END;
    $BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
	
---------------------------------------------------------------
--
-- 函数名称：gb_dsum_sfunc
--         去重累计计算 
-- 函数描述：gb_dsum聚合函数的状态转换函数
--    参数：in_s_value 中间状态
--   返回值：数值
--   创建人：北京江融信科技有限公司
--
---------------------------------------------------------------  
	CREATE OR REPLACE FUNCTION "gb_dsum_ffunc"("s_value" _numeric)
  RETURNS "pg_catalog"."numeric" AS $BODY$
  DECLARE
     i INT;
     tmp_value numeric; 
   BEGIN
     tmp_value = 0;
     FOR i IN array_lower(s_value, 1) .. array_upper(s_value, 1)
     LOOP
       tmp_value = tmp_value + s_value[i][2];
     END LOOP;
       
     RETURN tmp_value;
   END;
   $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
	
---------------------------------------------------------------
--
-- 函数名称：gb_dsum
--         去重累计计算  
-- 函数描述：聚合函数
--     参数：in_key   去重键值
--        ：in_num   数值
--   返回值：数值型
--   创建人：北京江融信科技有限公司
--
---------------------------------------------------------------  
DROP AGGREGATE IF EXISTS dsum(numeric, numeric);
CREATE AGGREGATE dsum(numeric, numeric) (
    SFUNC = gb_dsum_sfunc,
    STYPE = numeric[],
    FINALFUNC = gb_dsum_ffunc);;
---------------------------------------------------------------
--
-- 函数名称：gb_last_value_sfunc
--         在分组计算中取对应日期最大时间的值  
-- 函数描述：gb_last_value聚合函数的状态转换函数
--     参数：in_s_value 中间状态
--        ：in_date   日期
--        ：in_num    数值
--        ：condition 过滤条件，为TRUE才计算，FALSE直接忽略
--   返回值：二维数组，【【日期，值】】
--   创建人：北京江融信科技有限公司
--
---------------------------------------------------------------   
CREATE OR REPLACE FUNCTION "gb_last_value_sfunc"("in_s_value" _numeric, "in_date" timestamp, "in_num" numeric, "condition" bool)
  RETURNS "pg_catalog"."_numeric" AS $BODY$
  DECLARE
     int_date int;
     s_value numeric[];
   BEGIN
       IF (condition IS NOT NULL) AND (condition = FALSE) THEN
           return in_s_value;
       END IF;

       int_date = cast(extract(epoch from in_date) as integer);
       
       IF in_s_value IS NULL OR (int_date > in_s_value[1]) THEN
           s_value[1] = int_date;
           s_value[2] = in_num;
       ELSE
           s_value[1] = in_s_value[1];
           s_value[2] = in_s_value[2];
       END IF;
       
       return s_value;
   END;
   $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

---------------------------------------------------------------
--
-- 函数名称：gb_last_value_sfunc
--         在分组计算中取对应日期最大时间的值  
-- 函数描述：gb_last_value聚合函数的最终处理函数
--     参数：s_value 中间状态
--   返回值：数值型
--   创建人：北京江融信科技有限公司
--
---------------------------------------------------------------  
CREATE OR REPLACE FUNCTION "gb_last_value_ffunc"("s_value" _numeric)
    RETURNS "pg_catalog"."numeric" AS $BODY$
    BEGIN
        RETURN s_value[2];
    END;
    $BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
  
---------------------------------------------------------------
--
-- 函数名称：gb_last_value
--         在分组计算中取对应日期最大时间的值  
-- 函数描述：聚合函数
--     参数：in_date   日期
--        ：in_num    数值
--        ：condition 过滤条件，为TRUE才计算，FALSE直接忽略
--   返回值：数值型
--   创建人：北京江融信科技有限公司
--
---------------------------------------------------------------  
DROP AGGREGATE IF EXISTS gb_last_value(timestamp, numeric, bool);
CREATE AGGREGATE gb_last_value(timestamp, numeric, bool) (
   SFUNC = gb_last_value_sfunc,
   STYPE = numeric[],
   FINALFUNC = gb_last_value_ffunc);;
CREATE OR REPLACE FUNCTION "etl_create_daily_snap_table"("ods_table" varchar, "biz_date" int4, "debug" int4=0)
    RETURNS "pg_catalog"."void" AS $BODY$
DECLARE
    job_status int4;
    start_time timestamp;
    end_time timestamp;
    execute_time int8;
    affected_rows int8;
    sqlStmt VARCHAR;
BEGIN
    -- 检查dw_batch_job_log表当天的每日快照数据是否已经成功生成(step='DAILY_SNAP' AND result = 'SUCCESS')
    EXECUTE 'SELECT COUNT(1) FROM sys_batch_job_log WHERE biz_date = '|| biz_date || 'AND table_name = '''||ods_table||''' AND step = ''DAILY_SNAP'' AND result = ''SUCCESS''' INTO job_status; 
    PERFORM log_info(format('%1$s', job_status), debug);

        -- 如果sys_batch_job_log当天的每日快照数据没有成功生成(未开始生成或者之前有失败记录)，则进行数据跑入
        IF (job_status = 0) THEN 
            start_time = NOW();

            --====第一步：创建当前快照临时表用于缓存数据
            EXECUTE 'DROP TABLE IF EXISTS ' || ods_table || '_snap';
            sqlStmt = 'CREATE TABLE ' || ods_table || '_snap AS SELECT ods.* FROM ' || ods_table || ' ods ';
            PERFORM log_info(format('%1$s', sqlStmt), debug);
            EXECUTE  sqlStmt;

            --====第二步：备份当日快照数据，存储到每日快照表,（我这里在ods层加了一张表，用来保存30天每日快照，只保留30天，防止拉链做错了，回溯不行）
            sqlStmt = 'INSERT INTO ' || ods_table || '_daily_snap SELECT ' || biz_date || '::INT AS biz_date, * FROM ' || ods_table || '_snap';
            PERFORM log_info(format('%1$s', sqlStmt), debug);
            EXECUTE  sqlStmt;

            -- 获取影响行数
            GET DIAGNOSTICS affected_rows = ROW_COUNT;  

            end_time = NOW();
            select extract(epoch FROM (end_time - start_time)) INTO execute_time;

            -- 每日快照生成完成，记录日志
            INSERT INTO sys_batch_job_log VALUES
                    (biz_date,start_time,end_time,execute_time,ods_table,'ODS','DAILY_SNAP','SUCCESS',affected_rows,affected_rows,0,null);
        END IF;

        return;

        EXCEPTION
            WHEN OTHERS THEN
                RAISE EXCEPTION '[etl_create_daily_snap_table][%]  %', ods_table, SQLERRM;
                return;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

-- SELECT etl_create_daily_snap_table('ods_anytxn_bm_acct_loan', 20200201, 1);
-- 
-- SELECT * FROM sys_batch_job_log WHERE biz_date = 20200207;
-- 
-- 
-- 
-- 
--  CREATE OR REPLACE FUNCTION "public"."etl_create_ods_daily_snap"(etl_date int4)
--   RETURNS "pg_catalog"."varchar" AS $BODY$
--   BEGIN
--     PERFORM etl_create_daily_snap_table('ods_anytxn_bm_acct_loan', 'txa_number', etl_date);
--      
--      RETURN 'ETLOK';
--      EXCEPTION
--     WHEN others THEN    
--     RETURN 'ETLERROR: ' || SQLERRM; 
--      
--   END;
--   $BODY$
--   LANGUAGE plpgsql VOLATILE
--   COST 100;
    
    
--  SELECT etl_create_ods_daily_snap(20200204);
--  
--  
--  SELECT biz_date, COUNT(*) FROM ods_anytxn_bm_acct_loan_daily_snap GROUP BY biz_date;
    
-- SELECT * FROM sys_batch_job_log;
-- SELECT * FROM dw_batch_job_log ORDER BY batch_start_time DESC;

-- 存储过程的几个问题：
-- 1. 讨论下到底返回值是TRUE，FALSE，还是是错误信息，或者状态码？现在是字符串，得考虑是BOOL，还是状态码。这些状态码如何被角度平台捕捉。
-- 2. 日终跑批检查后面不用放ELSE
-- 3.SELECT COUNT(1) 写法有风险，还是用状态来判断比较好。如果当天没有数据，就是会为0，但是实际任务完成了。
-- 4. SUCCESS -> SUCCESS;

---------------------------------------------------------------
--
-- 函数名称：to_int_mth
-- 函数描述：日期转月份转整型
--     参数：indate，日期型
--   返回值：整数类型年月
--   创建人：**
--
---------------------------------------------------------------
CREATE OR REPLACE FUNCTION "to_int_mth"("indate" date)
    RETURNS "pg_catalog"."int4" AS $BODY$
  DECLARE
  BEGIN
   RETURN to_char(inDate, 'YYYYMM')::INTEGER;
  END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
COST 100;


---------------------------------------------------------------
--
-- 函数名称：to_int_date
-- 函数描述：日期转整型
--     参数：indate，日期型
--   返回值：整数类型日期
--   创建人：**
--
---------------------------------------------------------------
CREATE OR REPLACE FUNCTION "to_int_date"("indate" date)
    RETURNS "pg_catalog"."int4" AS $BODY$
    DECLARE
    BEGIN
     RETURN to_char(inDate, 'YYYYMMDD')::INTEGER;
    END;
    $BODY$
    LANGUAGE plpgsql VOLATILE
COST 100;



---------------------------------------------------------------
--
-- date_add_by_day
-- 函数描述：日期计算，按天
--     参数：biz_date，业务日期
--          num_of_days，天数
--   返回值：整数类型日期
--   创建人：**
--
---------------------------------------------------------------
CREATE OR REPLACE FUNCTION "date_add_by_day"("biz_date" int4, "num_of_days" int4)
RETURNS "pg_catalog"."int4" AS $BODY$
DECLARE
    res_date int;
    sql_stmt varchar;
BEGIN
    sql_stmt = 'SELECT to_int_date((to_date(' || biz_date || '::text, ''YYYYMMDD'') + interval ''' || num_of_days || ' day'')::DATE)';

    EXECUTE sql_stmt INTO res_date;
    
    RETURN res_date;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;



---------------------------------------------------------------
--
-- 函数名称：to_int_mth
-- 函数描述：整数日期转月份转整型
--     参数：indate，整型
--   返回值：整数类型年月
--   创建人：**
--
--------------------------------------------------------------- 
CREATE OR REPLACE FUNCTION "to_int_mth"("indate" int4)
  RETURNS "pg_catalog"."int4" AS $BODY$
  DECLARE
  BEGIN
     RETURN substr(inDate::VARCHAR, 1, 6)::INTEGER;
  END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
COST 100;


---------------------------------------------------------------
--
-- 函数名称：isleapyear
-- 函数描述：闰年
--     参数：iyear，整型
--   返回值：布尔
--   创建人：**
--
--------------------------------------------------------------- 
CREATE OR REPLACE FUNCTION "isleapyear"("iyear" int4)
  RETURNS "pg_catalog"."bool" AS $BODY$
  BEGIN
    RETURN ((iYear % 4) = 0 AND ((iYear % 100) <> 0 OR (iYear % 400) = 0));
  END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


---------------------------------------------------------------
--
-- 函数名称：ismonthlastday
-- 函数描述：每个月的最后一天是哪天（日期）
--     参数：idate，日期型
--   返回值：整数型日期
--   创建人：**
--
---------------------------------------------------------------
  CREATE OR REPLACE FUNCTION "ismonthlastday"("idate" date)
  RETURNS "pg_catalog"."bool" AS $BODY$
    DECLARE
     dateYear INT;
     dateMth INT;
     dateDay INT;
         maxDay INT;
    BEGIN
    dateYear = EXTRACT(year FROM iDate);
    dateMth = EXTRACT(month FROM iDate);
    dateDay = EXTRACT(day FROM iDate);

    IF dateMth = 2 THEN
          IF isLeapYear(dateYear) THEN
                maxDay = 29;
            ELSE
                maxDay = 28;
            END IF;
        ELSEIF dateMth = 4 OR dateMth = 6 OR dateMth = 9  OR dateMth = 11  THEN
            maxDay = 30;
        ELSE
            maxDay = 31;
        END IF;


    RETURN (dateDay = maxDay);
  END;
   $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


---------------------------------------------------------------
--
-- 函数名称：ismonthlastday
-- 函数描述：每个月的最后一天是哪天（整数）
--     参数：idate，整型
--   返回值：整数型日期
--   创建人：**
--
--------------------------------------------------------------- 
CREATE OR REPLACE FUNCTION "ismonthlastday"("idate" int4)
  RETURNS "pg_catalog"."bool" AS $BODY$
    DECLARE
     dateYear INT;
     dateMth INT;
     dateDay INT;
         maxDay INT;
    BEGIN
    dateYear = substr(iDate::VARCHAR, 1, 4)::INTEGER;
    dateMth = substr(iDate::VARCHAR, 5, 2)::INTEGER;
    dateDay = substr(iDate::VARCHAR, 7, 2)::INTEGER;

--     RAISE NOTICE 'isLeapYear %:% ', dateMth, dateDay;

    IF dateMth = 2 THEN
          IF isLeapYear(dateYear) THEN
                maxDay = 29;
            ELSE
                maxDay = 28;
            END IF;
        ELSIF dateMth = 4 OR dateMth = 6 OR dateMth = 9  OR dateMth = 11  THEN
            maxDay = 30;
        ELSE
            maxDay = 31;
        END IF;

--     RAISE NOTICE 'maxDay %', maxDay;

    RETURN (dateDay = maxDay);
  END;
   $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


---------------------------------------------------------------
--
-- 函数名称：datediff
-- 函数描述：计算两个整数日期之间的间隔（年、月、日）
--     参数：field，文本（枚举）：year, month, day
--              start_date，整型  
--              end_date，整型
--   返回值：整数类型年、月、日
--   创建人：**
--
---------------------------------------------------------------
  CREATE OR REPLACE FUNCTION "datediff"("field" text, "start_date" int4, "end_date" int4)
  RETURNS "pg_catalog"."int4" AS $BODY$
    DECLARE
        year_diff int;
        mth_diff int;
   BEGIN
       IF field = 'year' THEN
           RETURN int_date_part(field, start_date) - int_date_part(field, end_date);
       ELSEIF field = 'month' THEN
         year_diff = int_date_part('year', start_date) - int_date_part('year', end_date);
             mth_diff = int_date_part('month', start_date) - int_date_part('month', end_date);
           RETURN year_diff * 12 + mth_diff;
       ELSE
           RETURN to_date(start_date::TEXT,'YYYYMMDD') - to_date(end_date::TEXT,'YYYYMMDD');
       END IF;
   END;
   $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;



---------------------------------------------------------------
--
-- 函数名称：datediff
-- 函数描述：计算两个整数日期之间的间隔（年、月、日）
--     参数：field，文本（枚举）：year, month, day
--              start_date，整型  
--              end_date，整型
--   返回值：整数类型年、月、日
--   创建人：**
--
---------------------------------------------------------------
  CREATE OR REPLACE FUNCTION "datediff"("field" text, "start_date" timestamp, "end_date" timestamp)
  RETURNS "pg_catalog"."int4" AS $BODY$
    DECLARE
        year_diff int;
        mth_diff int;
   BEGIN
       IF field = 'year' THEN
           RETURN int_date_part(field, start_date) - int_date_part(field, end_date);
       ELSEIF field = 'month' THEN
         year_diff = int_date_part('year', start_date) - int_date_part('year', end_date);
             mth_diff = int_date_part('month', start_date) - int_date_part('month', end_date);
           RETURN year_diff * 12 + mth_diff;
       ELSE
           RETURN to_date(start_date::TEXT,'YYYYMMDD') - to_date(end_date::TEXT,'YYYYMMDD');
       END IF;
   END;
   $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


---------------------------------------------------------------
--
-- 函数名称：datediff
-- 函数描述：计算两个时间戳之间的间隔（年、月、日、小时、分钟、秒）
--     参数：field，文本（枚举）：year, month, day，hour，minute，second
--              start_date，时间戳  
--              end_date，时间戳
--   返回值：整数类型年、月、日、小时、分钟、秒
--   创建人：**
--
---------------------------------------------------------------
CREATE OR REPLACE FUNCTION "datediff"("field" varchar, "start_date" TIMESTAMPTZ, "end_date" TIMESTAMPTZ)
RETURNS "pg_catalog"."int4" AS $BODY$
  DECLARE
      year_diff int;
      mth_diff int;
      day_diff int;
      hour_diff int;
      minute_diff int;
 BEGIN
     IF field = 'year' THEN
         RETURN DATE_PART('year', end_date) - DATE_PART('year', start_date);
     ELSEIF field = 'month' THEN
         year_diff = DATE_PART('year', end_date) - DATE_PART('year', start_date);
         mth_diff = DATE_PART('month', end_date) - DATE_PART('month', start_date);
         RETURN year_diff * 12 + mth_diff;
     ELSEIF field = 'day' THEN
         RETURN DATE_PART('day', end_date - start_date);
     ELSEIF field = 'hour' THEN
         day_diff = DATE_PART('day', end_date - start_date);

         RETURN day_diff * 24 + DATE_PART('hour', end_date - start_date);
     ELSEIF field = 'minute' THEN
         day_diff = DATE_PART('day', end_date - start_date);
         hour_diff = day_diff * 24 + DATE_PART('hour', end_date - start_date);

         RETURN hour_diff * 60 + DATE_PART('minute', end_date - start_date);
     ELSEIF field = 'second' THEN
         day_diff = DATE_PART('day', end_date - start_date);
         hour_diff = day_diff * 24 + DATE_PART('hour', end_date - start_date);
         minute_diff = hour_diff * 60 + DATE_PART('minute', end_date - start_date);

         RETURN minute_diff * 60 + DATE_PART('second', end_date - start_date);
     ELSE
         RETURN DATE_PART('day', end_date - start_date);
     END IF;
 END;
 $BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

---------------------------------------------------------------
--
-- 函数名称：int_date_part
-- 函数描述：取年、月、日
--     参数：field，文本  in_date，整型
--   返回值：整数类型年、月、日
--   创建人：**
--
--------------------------------------------------------------- 
  CREATE OR REPLACE FUNCTION "int_date_part"("field" text, "in_date" int4)
  RETURNS "pg_catalog"."int4" AS $BODY$
   BEGIN
       IF field = 'year' THEN
           RETURN substr(in_date::TEXT, 1, 4)::INTEGER;
       ELSEIF field = 'month' THEN
           RETURN substr(in_date::TEXT, 5, 2)::INTEGER;
       ELSEIF field = 'day' THEN
           RETURN substr(in_date::TEXT, 7, 2)::INTEGER;
         ELSE
           RETURN in_date;
         END IF;
   END;
   $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
  
 ---------------------------------------------------------------
--
-- 函数名称：int_mth_add
-- 函数描述：整数月份转日期月份转整数月份
--     参数：in_mth numeric, 1 numeric
--   返回值：整数类型年月
--   创建人：**
--
---------------------------------------------------------------  
CREATE OR REPLACE FUNCTION "int_mth_add"("in_mth" numeric, "n" numeric)
  RETURNS "pg_catalog"."numeric" AS $BODY$
  DECLARE 
     i int;
     s_in_mth int;        
 BEGIN
  s_in_mth = in_mth;
  IF n > 0 THEN
    FOR i IN 1 .. n 
     LOOP
     s_in_mth = to_int_mth((to_date(s_in_mth::text || '01' , 'YYYYMMDD') + interval '1 month')::DATE);
     END LOOP;
  END IF;
   RETURN s_in_mth;
 END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  

  CREATE OR REPLACE FUNCTION "int_date_part"("field" text, "in_date" int4)
  RETURNS "pg_catalog"."int4" AS $BODY$
   BEGIN
     IF field = 'year' THEN
       RETURN substr(in_date::TEXT, 1, 4)::INTEGER;
     ELSEIF field = 'month' THEN
       RETURN substr(in_date::TEXT, 5, 2)::INTEGER;
     ELSEIF field = 'day' THEN
       RETURN substr(in_date::TEXT, 7, 2)::INTEGER;
     ELSE
       RETURN in_date;
     END IF;
   END;
   $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
;
---------------------------------------------------------------
--
-- table_exist
-- 函数描述：判断表是否存在
--    参数：table_name 表名称
--         schema_name schema名称
--  返回值：空
--  创建人：--
--
---------------------------------------------------------------
CREATE OR REPLACE FUNCTION "public"."table_existed"("schema_name" text,"table_name" text)
    RETURNS "pg_catalog"."bool" AS $BODY$
    DECLARE tableName text;
    BEGIN
        SELECT c.relname into tableName FROM pg_namespace n,pg_class c WHERE c.relname = table_name and n.nspname=schema_name and c.relnamespace = n.oid;
        
        IF tableName IS NULL THEN
            return false;
        ELSE
            return true;
        END IF;
    END;
    $BODY$
LANGUAGE plpgsql VOLATILE
COST 100;;
---------------------------------------------------------------
--
-- log_info
-- 函数描述：判断表是否存在
--    参数：table_name 表名称
--         schema_name schema名称
--  返回值：空
--  创建人：--
--
---------------------------------------------------------------
CREATE OR REPLACE FUNCTION log_info(msg VARCHAR, show_log INT DEFAULT 1, log_level VARCHAR DEFAULT 'info')
    returns VOID as $$
BEGIN
        IF show_log = 0 THEN
        RETURN;
    END IF;

        IF log_level = 'info' THEN
          RAISE INFO '%', msg;
            
        ELSIF log_level = 'notice' THEN
          RAISE NOTICE '%', msg;
            
        ELSIF log_level = 'warning' THEN
          RAISE WARNING '%', msg;
            
        ELSIF log_level = 'exception' THEN
          RAISE EXCEPTION '%', msg;
    END IF;
END;
$$ language plpgsql;

-- select ; 

-- SELECT log_info(format('Hello %2$s and %1$s', 'Jane', 'Joe'), 1, 'exception');;
CREATE OR REPLACE FUNCTION "public"."etl_create_zip_table"("ods_table" varchar, "pkey" varchar, "biz_date" int4, "debug" int4=0)
    RETURNS "pg_catalog"."void" AS $BODY$
DECLARE
    snap_job_status int4;
    zip_job_status int4;
    start_time timestamp;
    end_time timestamp;
    execute_time int8;
    affected_rows int8;
    new_rows int8;
    updated_rows int8;
    sqlStmt VARCHAR;
    snap_fields VARCHAR;
    his_fields VARCHAR;
BEGIN
    -- 检查sys_batch_job_log表当天的每日快照数据是否已经成功生成(step='DAILY_SNAP' AND result = 'SUCCESS')
    EXECUTE 'SELECT COUNT(1) FROM sys_batch_job_log WHERE biz_date = '|| biz_date || 'AND table_name = '''|| ods_table ||''' AND step = ''DAILY_SNAP'' AND result = ''SUCCESS''' INTO snap_job_status; 
    IF snap_job_status = 0 THEN
        RAISE EXCEPTION '日终快照任务未完成';
    END IF;

    -- 检查sys_batch_job_log表当天的拉链数据是否已经成功生成(step='HIS' AND result = 'SUCCESS')
    EXECUTE 'SELECT COUNT(1) FROM sys_batch_job_log WHERE biz_date = '|| biz_date || ' AND table_name = '''|| ods_table ||''' AND result = ''SUCCESS'' AND step = ''HIS''' INTO zip_job_status; 
    PERFORM log_info(format('zip_job_status: %1$s; snap_job_status: %2$s', zip_job_status, snap_job_status), debug);

    -- 2.1.2如果拉链表表里面。没有当天拉链数据，进行数据跑入
    IF (zip_job_status = 1 AND snap_job_status = 1) THEN
        RETURN;
    END IF;

    -- 创建拉链表
    IF (zip_job_status = 0 AND snap_job_status = 1) THEN
        start_time = NOW();
        sqlStmt = 'SELECT STRING_AGG(''snap.'' || column_name || '''', '','') AS snap_fields, STRING_AGG(''his.'' || column_name || '''', '','') AS his_fields
                     FROM information_schema.columns
                    WHERE table_name = ''' || ods_table || '''';
        PERFORM log_info(sqlStmt, debug);
        EXECUTE sqlStmt into snap_fields, his_fields;
        -- PERFORM log_info(format('snap_fields: %1$s; snap_fields: %2$s', snap_fields, his_fields), debug);

        --删除临时增量表
        sqlStmt = 'DROP TABLE IF EXISTS tmp_incr_' || ods_table;
        PERFORM log_info(sqlStmt, debug);
        EXECUTE sqlStmt;
                
        --创建临时增量数据表
        sqlStmt = 'CREATE TABLE tmp_incr_' || ods_table || ' (LIKE ' || ods_table || '_his)';
        PERFORM log_info(sqlStmt, debug);
        EXECUTE sqlStmt;
     
        --对比整行MD5, 创建增量表(有变更的记录)
        sqlStmt = 'INSERT INTO tmp_incr_' || ods_table || ' 
                        SELECT ' || biz_date || ' AS his_start_time,
                                         99991231 AS his_end_time,
                                         snap.*,
                                 ' || to_int_date(now()::DATE) || ' AS etl_date
                                FROM ' || ods_table || '_snap snap
                INNER JOIN ' || ods_table || '_his his ON snap.' || pkey || '=his.' || pkey || ' AND his.his_end_time = 99991231
                     WHERE MD5(ROW(' || snap_fields || '):: TEXT) <> MD5(ROW(' || his_fields || '):: TEXT)';
        PERFORM log_info(sqlStmt, debug);
        EXECUTE sqlStmt;

        GET DIAGNOSTICS updated_rows = ROW_COUNT;  
                
        --新增的记录
        sqlStmt = 'INSERT INTO tmp_incr_' || ods_table || '  
                     SELECT ' || biz_date || ' AS his_start_time,
                                    99991231 AS his_end_time,
                                    *,
                                    ' || to_int_date(now()::DATE) || ' AS etl_date
                         FROM ' || ods_table || '_snap snap
                     WHERE ' || pkey || ' NOT IN (SELECT ' || pkey || ' FROM ' || ods_table || '_his WHERE his_end_time = 99991231)';
        PERFORM log_info(sqlStmt, debug);
        EXECUTE sqlStmt;                

        GET DIAGNOSTICS new_rows = ROW_COUNT;  

        --已变更的旧数据做关链
        --给拉链表做关链
        EXECUTE 'UPDATE ' || ods_table || '_his 
                     SET his_end_time = ' || to_int_date((to_date(biz_date::text, 'YYYYMMDD') + interval '-1 day')::DATE) || ' 
                 WHERE his_end_time = 99991231 AND ' || pkey || ' IN (SELECT ' || pkey || ' FROM tmp_incr_' || ods_table || ')';

        GET DIAGNOSTICS affected_rows = ROW_COUNT;  


        --把新增数据和变更的99991231数据 插入拉链表
        EXECUTE 'INSERT INTO ' || ods_table || '_his SELECT * FROM tmp_incr_' || ods_table || '';

        affected_rows = new_rows + updated_rows + affected_rows;
        end_time = now();
        select extract(epoch FROM (end_time - start_time)) INTO execute_time;
        
        -- 每日拉链生成完成，记录日志
        INSERT INTO sys_batch_job_log VALUES(biz_date,start_time,end_time,execute_time,ods_table,'ODS','HIS','SUCCESS',affected_rows,new_rows,updated_rows,null);

        RETURN;
    END IF;
    
    EXCEPTION
        WHEN others THEN
            RAISE EXCEPTION '[etl_create_zip_table][%] %', ods_table, SQLERRM;
            RETURN;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

-- SELECT etl_create_zip_table('ods_anytxn_bm_acct_loan', 'txa_number', 20200201, 1);;
---------------------------------------------------------------
--
-- array_sort
-- 函数描述：数值类型数组排序
--  返回值：数字数组
--  创建人：北京江融信科技有限公司
--
---------------------------------------------------------------  
CREATE OR REPLACE FUNCTION "array_sort"("array_to_sort" _numeric)
  RETURNS "pg_catalog"."_numeric" AS $BODY$
  DECLARE 
      tmp int;
      result _numeric;
  BEGIN
      FOR tmp in select unnest(array_to_sort) as a order by a asc
      LOOP
        result = array_append(result,tmp::numeric);
      end LOOP;

      RETURN result;
  END;
   $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;;
--======================================================
--
-- Author: 北京江融信科技有限公司
-- Create date: 2019年8月3日
-- Description: 创建拉链表
-- Modify:
--
--======================================================
DROP FUNCTION IF EXISTS etl_ods_create_daily_snap_table(int);
CREATE OR REPLACE FUNCTION "etl_ods_create_daily_snap_table"("biz_date" int)
RETURNS VARCHAR AS $BODY$
BEGIN
    --额度利率表
    PERFORM etl_create_daily_snap_table('ods_anytxn_bm_cc_customer_lmt', biz_date);
	
	--贷款借据表
	PERFORM etl_create_daily_snap_table('ods_anytxn_bm_acct_loan', biz_date);
   	--还款计划表
	PERFORM etl_create_daily_snap_table('ods_anytxn_bm_cc_loan_plan', biz_date);

	--利息表
	PERFORM etl_create_daily_snap_table('ods_anytxn_bm_acct_intr', biz_date);

	--利息累计表
	PERFORM etl_create_daily_snap_table('ods_anytxn_bm_intr_accu_acct', biz_date);

	--延滞表
	PERFORM etl_create_daily_snap_table('ods_anytxn_bm_acct_delq', biz_date);


	--贷款本金表
	PERFORM etl_create_daily_snap_table('ods_anytxn_bm_acct_loan_detl', biz_date);


    RETURN 'OK';
	
    EXCEPTION
        WHEN others THEN
            RETURN 'ERROR: [etl_ods_create_daily_snap_table][' || biz_date || '] ' || SQLERRM;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

-- SELECT etl_ods_create_zip_table('ods_anytxn_', '2020-02-02');
-- 1824.153s;
--======================================================
--
-- Author: 北京江融信科技有限公司
-- Create date: 2019年8月3日
-- Description: 创建拉链表
-- Modify:
--
--======================================================
DROP FUNCTION IF EXISTS etl_ods_create_zip_table(int);
CREATE OR REPLACE FUNCTION "etl_ods_create_zip_table"("biz_date" int)
RETURNS VARCHAR AS $BODY$
    BEGIN

    --额度利率表
    PERFORM etl_create_zip_table_multi_pkey('ods_anytxn_bm_cc_customer_lmt', ARRAY['cust_nbr', 'lmt_id', 'contract_no'], biz_date);

    --贷款借据表
    PERFORM etl_create_zip_table('ods_anytxn_bm_acct_loan', 'txa_number', biz_date);

    --还款计划表
    PERFORM etl_create_zip_table('ods_anytxn_bm_cc_loan_plan', 'plan_detl_id', biz_date);

	--利息表
	PERFORM etl_create_zip_table('ods_anytxn_bm_acct_intr','txa_number', biz_date);

    --利息累计表
    PERFORM etl_create_zip_table('ods_anytxn_bm_intr_accu_acct', 'txa_number', biz_date);


	--延滞表
	PERFORM etl_create_zip_table('ods_anytxn_bm_acct_delq',  'txa_number',biz_date);


	--贷款本金表
	PERFORM etl_create_zip_table('ods_anytxn_bm_acct_loan_detl', 'txa_number', biz_date);

    RETURN 'OK';
    
    EXCEPTION
        WHEN others THEN
            RETURN 'ERROR: [etl_ods_create_daily_snap_table][' || biz_date || '] ' || SQLERRM;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

-- SELECT etl_ods_create_zip_table('ods_anytxn_', '2020-02-02');
-- 1824.153s;
---------------------------------------------------------------
--
-- 函数名称：check_txn_batch_status
-- 函数描述：检查业务日期txn跑批结果
--     参数：etl_date，整型
--   返回值：是否完成标识
--   创建人：**
--
--------------------------------------------------------------- 
CREATE OR REPLACE FUNCTION "check_txn_batch_status"(etl_date int4)
  RETURNS int4 AS $BODY$
	DECLARE
	  res_status int;
    sql_stmt varchar;
  BEGIN
	  -- 检查表
	  EXECUTE 'SELECT COUNT(1) FROM ods_anytxn_batch_quartz_sign WHERE business_date = date_add_by_day(' || etl_date || ', -1)::TEXT AND status = 1 AND quartz_id = 2' INTO res_status;
	
    RETURN res_status;
  END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;;
---------------------------------------------------------------
--
-- sys_get_partion_size
-- 函数描述：获取所有分取表尺寸
--  返回值：尺寸（数值型）
--  创建人：北京江融信科技有限公司
--
---------------------------------------------------------------  
CREATE OR REPLACE FUNCTION "sys_get_partion_size"()
  RETURNS "pg_catalog"."int8" AS $BODY$
Declare
  tbl_raw_size BIGINT;
BEGIN
  EXECUTE 'SELECT SUM(pg_relation_size(psut.relid)) as raw_data_size
             FROM pg_statio_user_tables  psut
       INNER JOIN pg_inherits pi ON psut.relid = pi.inhrelid
            WHERE pi.inhparent = ' || tbl_pid  
		INTO tbl_raw_size;
				
  return tbl_raw_size;
END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
---------------------------------------------------------------
--
-- sys_create_dw_overview
-- 函数描述：生成数据集市元数据数据报告
--  返回值：void
--  创建人：北京江融信科技有限公司
--
---------------------------------------------------------------  
CREATE OR REPLACE FUNCTION "sys_create_dw_overview"()
  RETURNS "pg_catalog"."void" AS $BODY$
    DECLARE
        v_val INT;
        rec_tbl RECORD;
        tbl_cnt BIGINT;
        tbl_col_cnt INT;
        tbl_raw_size BIGINT;
        tbl_rb_raw_size TEXT;
        tbl_partition_cnt INT;
        his_start_time INT;
        his_end_time INT;
        mth_cnt INT;
        year_cnt INT;

        tbl_1m_raw_size BIGINT;
        tbl_1m_rb_size TEXT;

        last_day_cnt BIGINT;
        last_30day_cnt BIGINT;
        last_60day_cnt BIGINT;
        last_90day_cnt BIGINT;
    BEGIN

        -- 遍历数仓基础层、中间层和集市层所有表
        FOR rec_tbl IN SELECT psut.relid, 
                              psut.relname,
                              substr(psut.relname, 1, 2) as layer
                         FROM pg_statio_user_tables psut 
                    LEFT JOIN pg_inherits pi ON psut.relid = pi.inhrelid
                       WHERE schemaname = 'public' 
                         AND pi.inhparent IS NULL 
                         AND substr(psut.relname, 1, 2) IN ('wh', 'md', 'dm') -- LIMIT 1
        LOOP
            -- 获取表列数
            EXECUTE 'SELECT COUNT(1) FROM information_schema.columns WHERE table_name = ''' || rec_tbl.relname  || '''' INTO tbl_col_cnt;

            -- 获取行数
            EXECUTE 'SELECT count(1) FROM ' || rec_tbl.relname INTO tbl_cnt;

            -- 获取分区数
            EXECUTE 'SELECT COUNT(1) FROM pg_inherits WHERE inhparent = ' || rec_tbl.relid INTO tbl_partition_cnt;
                    
            -- 获取表尺寸(如果有分区，则主表空间为0, 需要基于分区表计算总容量
            IF tbl_partition_cnt > 0 THEN
                EXECUTE 'SELECT sys_get_partion_size(' || rec_tbl.relid || ')' INTO tbl_raw_size;
            ELSE 
                EXECUTE 'SELECT pg_relation_size(' || rec_tbl.relid || ')' INTO tbl_raw_size;
            END IF;
                    
            -- 获取可读的表尺寸
            EXECUTE 'SELECT pg_size_pretty(' || tbl_raw_size || '::bigint)' INTO tbl_rb_raw_size;

            -- 获取最小时间
            EXECUTE 'SELECT min(his_start_time) FROM ' || rec_tbl.relname INTO his_start_time;
            --           
            -- 获取最大时间
            EXECUTE 'SELECT max(his_start_time) FROM ' || rec_tbl.relname INTO his_end_time;
                    
            -- 获取月份数
            mth_cnt = datediff('mth', his_start_time, his_end_time);

            -- 获取年份数
            year_cnt = datediff('year', his_start_time, his_end_time);
                    
            -- 100w行数据容量
                        IF tbl_cnt > 0 THEN
                tbl_1m_raw_size = tbl_raw_size * 1000000 / tbl_cnt;
                        ELSE 
                            tbl_1m_raw_size = 0;
                        END IF;
                    
            -- 获取可读的表尺寸
            EXECUTE 'SELECT pg_size_pretty(' || tbl_1m_raw_size || '::bigint)' INTO tbl_1m_rb_size;
            --           tbl_avg_1m_rb_size = ;

            -- 获取最大时间
            EXECUTE 'SELECT COUNT(1) FROM ' || rec_tbl.relname || ' WHERE his_start_time = ' || to_int_date(now()::date) INTO last_day_cnt;

            -- 获取最大时间
            EXECUTE 'SELECT COUNT(1) FROM ' || rec_tbl.relname || ' WHERE his_start_time <= ' || to_int_date(now()::date) || ' AND his_start_time >= ' || to_int_date((now() - INTERVAL '30 day')::date) INTO last_30day_cnt;

            -- 获取最大时间
            EXECUTE 'SELECT COUNT(1) FROM ' || rec_tbl.relname || ' WHERE his_start_time <= ' || to_int_date(now()::date) || ' AND his_start_time >= ' || to_int_date((now() - INTERVAL '60 day')::date) INTO last_60day_cnt;

            -- 获取最大时间
            EXECUTE 'SELECT COUNT(1) FROM ' || rec_tbl.relname || ' WHERE his_start_time <= ' || to_int_date(now()::date) || ' AND his_start_time >= ' || to_int_date((now() - INTERVAL '90 day')::date) INTO last_90day_cnt;
                
                
            -- 获取表尺寸
            -- EXECUTE 'SELECT pg_size_pretty(' || tbl_raw_size || ')' INTO tbl_rb_raw_size;
                    
            -- RAISE NOTICE '%, %, %, %, %, %, %, %', rec_tbl, tbl_cnt, tbl_raw_size, tbl_rb_raw_size, tbl_partition_cnt, tbl_1m_raw_size, tbl_1m_rb_size, tbl_col_cnt;

            -- RAISE NOTICE '%,%,%', last_day_cnt,ceil(tbl_raw_size * last_day_cnt / tbl_cnt),pg_size_pretty(ceil(tbl_raw_size * last_day_cnt / tbl_cnt)::BIGINT);

            INSERT INTO sys_table_status VALUES (
                rec_tbl.relid, -- 表格ID
                rec_tbl.relname, -- 表格名称
                rec_tbl.layer, -- 数仓层次
                tbl_cnt, -- 表格数据量
                166, -- 表格列数
                tbl_partition_cnt, -- 表格分区数
                tbl_raw_size, -- 表格容量（raw）
                tbl_rb_raw_size, -- 表格容量（格式化）
                his_start_time,-- 数据起始时间（格式化）
                his_end_time,-- 数据结束时间（格式化）
                mth_cnt,-- 数据月份周期
                year_cnt,-- 数据年份周期
                tbl_1m_raw_size,-- 100万条数据容量（raw）
                tbl_1m_rb_size,-- 100万条数据容量（格式化）
                to_int_date(now()::date)
            );
                
        END LOOP;
END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;;
CREATE OR REPLACE FUNCTION "check_dw_job_can_start"(etl_date int4, delay_time int4)
  RETURNS int4 AS $BODY$
    DECLARE
      res_status int;
  BEGIN
      -- 检查表
      EXECUTE 'SELECT CASE WHEN DATEDIFF(''minute'', update_time, NOW()) >= ' || delay_time || ' THEN 1 ELSE 0 END
                 FROM ods_anytxn_batch_quartz_sign 
                WHERE business_date = date_add_by_day(' || etl_date || ', -1)::TEXT 
                  AND status = 1 
                  AND quartz_id = 2' INTO res_status;
    
    RETURN res_status;
  END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;;
