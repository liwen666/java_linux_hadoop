##-- MySQL 初始化表
/*Table structure for table `order_info` */
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `consignee` varchar(100) DEFAULT NULL COMMENT '收货人',
  `consignee_tel` varchar(20) DEFAULT NULL COMMENT '收件人电话',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '总金额',
  `order_status` varchar(20) DEFAULT NULL COMMENT '订单状态,1表示下单，2表示支付',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `payment_way` varchar(20) DEFAULT NULL COMMENT '付款方式',
  `delivery_address` varchar(1000) DEFAULT NULL COMMENT '送货地址',
  `order_comment` varchar(200) DEFAULT NULL COMMENT '订单备注',
  `out_trade_no` varchar(50) DEFAULT NULL COMMENT '订单交易编号（第三方支付用)',
  `trade_body` varchar(200) DEFAULT NULL COMMENT '订单描述(第三方支付用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `expire_time` datetime DEFAULT NULL COMMENT '失效时间',
  `tracking_no` varchar(100) DEFAULT NULL COMMENT '物流单编号',
  `parent_order_id` bigint(20) DEFAULT NULL COMMENT '父订单编号',
  `img_url` varchar(200) DEFAULT NULL COMMENT '图片路径',
  `province_id` int(20) DEFAULT NULL COMMENT '地区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单表';
-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info`
VALUES (476, 'lAXjcL', '13408115089', 433.00, '2', 10, '2', 'OYyAdSdLxedceqovndCD', 'ihjAYsSjrgJMQVdFQnSy', '8728720206', '', '2020-06-18 02:21:38', NULL, NULL, NULL, NULL, NULL, 9);
INSERT INTO `order_info`
VALUES (477, 'QLiFDb', '13415139984', 772.00, '1', 90, '2', 'OizYrQbKuWvrvdfpkeSZ', 'wiBhhqhMndCCgXwmWVQq', '1679381473', '', '2020-06-18 09:12:25', NULL, NULL, NULL, NULL, NULL, 3);
INSERT INTO `order_info`
VALUES (478, 'iwKjQD', '13320383859', 88.00, '1', 107, '1', 'cbXLKtNHWOcWzJVBWdAs', 'njjsnknHxsxhuCCeNDDi', '0937074290', '', '2020-06-18 15:56:34', NULL, NULL, NULL, NULL, NULL, 7);

/*Table structure for table `order_detail` */
CREATE TABLE `order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单编号',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(200) DEFAULT NULL COMMENT 'sku名称（冗余)',
  `img_url` varchar(200) DEFAULT NULL COMMENT '图片名称（冗余)',
  `order_price` decimal(10,2) DEFAULT NULL COMMENT '购买价格(下单时sku价格）',
  `sku_num` varchar(200) DEFAULT NULL COMMENT '购买个数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单明细表';

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail`
VALUES (1329, 476, 8, 'Apple iPhone XS Max (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://XLMByOyZDTJQYxphQHNTgYAFzJJCKTmCbzvEJIpz', 8900.00, '3', '2020-06-18 02:21:38');
INSERT INTO `order_detail`
VALUES (1330, 477, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://ixOCtlYmlxEEgUfPLiLdjMftzrleOEIBKSjrhMne', 2452.00, '4', '2020-06-18 09:12:25');
INSERT INTO `order_detail`
VALUES (1331, 478, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://RqfEFnAOqnqRnNZLFRvBuwXxwNBtptYJCILDKQYv', 1442.00, '1', '2020-06-18 15:56:34');
INSERT INTO `order_detail`
VALUES (1332, 478, 8, 'Apple iPhone XS Max (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://IwhuCDlsiLenfKjPzbJrIoxswdfofKhJLMzlJAKV', 8900.00, '3', '2020-06-18 15:56:34');
INSERT INTO `order_detail`
VALUES (1333, 478, 8, 'Apple iPhone XS Max (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://bbfwTbAzTWapywODzOtDJMJUEqNTeRTUQuCDkqXP', 8900.00, '1', '2020-06-18 15:56:34');

#创建cdc
-- 创建订单信息表

CREATE TABLE order_info(
    id BIGINT,
    user_id BIGINT,
    create_time TIMESTAMP(0),
    operate_time TIMESTAMP(0),
    province_id INT,
    order_status STRING,
    total_amount DECIMAL(10, 5)
  ) WITH (
    'connector' = 'mysql-cdc',
    'hostname' = '192.168.60.136',
    'port' = '3306',
    'username' = 'root',
    'password' = 'root',
    'database-name' = 'sql-demo',
    'table-name' = 'order_info'
);


CREATE TABLE order_detail(
    id BIGINT,
    order_id BIGINT,
    sku_id BIGINT,
    sku_name STRING,
    sku_num BIGINT,
    order_price DECIMAL(10, 5),
    create_time TIMESTAMP(0)
 )  WITH (
       'connector' = 'mysql-cdc',
       'hostname' = '192.168.60.136',
       'port' = '3306',
       'username' = 'root',
       'password' = 'root',
       'database-name' = 'sql-demo',
       'table-name' = 'order_detail'
   );
   
   #查询结果
   
   SELECT
       od.id,
       oi.id order_id,
       oi.user_id,
       oi.province_id,
       od.sku_id,
       od.sku_name,
       od.sku_num,
       od.order_price,
       oi.create_time,
       oi.operate_time
   FROM
      (
       SELECT * 
       FROM order_info
       WHERE 
            order_status = '2'-- 已支付
      ) oi
      JOIN
     (
       SELECT *
       FROM order_detail
     ) od 
     ON oi.id = od.order_id;