


iptables -I INPUT -p tcp --dport 8083 -j ACCEPT;


11.11.1.79:8083/connectors

-- -- bin/connect-standalone.sh config/connect-standalone.properties /home/liwen/application/kafka_2.12-2.1.1/mysql.properties

bin/connect-distributed.sh config/connect-distributed.properties 

GET /Connectors：返回活跃的 Connector 列表
http://11.11.1.79:8083/connectors   inventory-connector

http://11.11.1.79:8083/connectors/inventory-connector/config



POST /Connectors：创建一个新的 Connector；请求的主体是一个包含字符串name字段和对象 config 字段（Connector 的配置参数）的 JSON 对象。

curl -s http://11.11.1.79:8083/connectors -X POST -H "Content-Type: application/json" --data \
'{"name": "wyk_csdn_mysql_debezium_connector2","config": {"connector.class": "io.debezium.connector.mysql.MySqlConnector", "database.hostname": "11.11.1.79", "database.port": "3306", "database.user": "root", "database.password": "root", "database.server.id": "123", "database.server.name": "wyk_sandbox", "database.whitelist": "canal_manager", "database.history.kafka.bootstrap.servers": "11.11.1.79:9092", "database.history.kafka.topic": "wyk_csdn_debezium_mysql2", "include.schema.changes": "true","include.query":"true" }}'


GET /Connectors/{name}：获取指定 Connector 的信息
GET /Connectors/{name}/config：获取指定 Connector 的配置参数
PUT /Connectors/{name}/config：更新指定 Connector 的配置参数
GET /Connectors/{name}/status：获取 Connector 的当前状态，包括它是否正在运行，失败，暂停等。
GET /Connectors/{name}/tasks：获取当前正在运行的 Connector 的任务列表。
GET /Connectors/{name}/tasks/{taskid}/status：获取任务的当前状态，包括是否是运行中的，失败的，暂停的等，
PUT /Connectors/{name}/pause：暂停连接器和它的任务，停止消息处理，直到 Connector 恢复。
PUT /Connectors/{name}/resume：恢复暂停的 Connector（如果 Connector 没有暂停，则什么都不做）
POST /Connectors/{name}/restart：重启 Connector（Connector 已故障）
POST /Connectors/{name}/tasks/{taskId}/restart：重启单个任务 (通常这个任务已失败)
DELETE /Connectors/{name}：删除 Connector, 停止所有的任务并删除其配置



curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '{
  "name": "inventory-connector",
  "config": {
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "database.hostname": "10.0.8.10",
    "database.port": "3306",
    "database.user": "any",
    "database.password": "anywd1234",
    "database.server.id": "100",
    "database.server.name": "fullfillment",
    "database.include.list": "aa_loyalty_accout_11",
    "database.history.kafka.bootstrap.servers": "localhost:9092",
    "database.history.kafka.topic": "dbhistory.fullfillment",
    "database.history.skip.unparseable.ddl": "true",
    "include.schema.changes": "false"
  }
}'


curl -i -X PO
ST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '{
  "name": "local_test",
  "config": {
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "database.hostname": "11.11.1.79",
    "database.port": "3306",
    "database.user": "root",
    "database.password": "root",
    "database.server.id": "100",
    "database.server.name": "local_test",
    "database.include.list": "any_data_hub",
    "database.history.kafka.bootstrap.servers": "11.11.1.79:9092",
    "database.history.kafka.topic": "dbhistory.local_test1",
    "database.history.skip.unparseable.ddl": "true",
    "include.schema.changes": "false"
  }
}'



REST API

由于 Kafka Connect 的目的是作为一个服务运行，提供了一个用于管理 Connector 的 REST API。默认情况下，此服务的端口是8083。以下是当前支持的终端入口：
GET /Connectors：返回活跃的 Connector 列表
POST /Connectors：创建一个新的 Connector；请求的主体是一个包含字符串name字段和对象 config 字段（Connector 的配置参数）的 JSON 对象。
GET /Connectors/{name}：获取指定 Connector 的信息
GET /Connectors/{name}/config：获取指定 Connector 的配置参数
PUT /Connectors/{name}/config：更新指定 Connector 的配置参数
GET /Connectors/{name}/status：获取 Connector 的当前状态，包括它是否正在运行，失败，暂停等。
GET /Connectors/{name}/tasks：获取当前正在运行的 Connector 的任务列表。
GET /Connectors/{name}/tasks/{taskid}/status：获取任务的当前状态，包括是否是运行中的，失败的，暂停的等，
PUT /Connectors/{name}/pause：暂停连接器和它的任务，停止消息处理，直到 Connector 恢复。
PUT /Connectors/{name}/resume：恢复暂停的 Connector（如果 Connector 没有暂停，则什么都不做）
POST /Connectors/{name}/restart：重启 Connector（Connector 已故障）
POST /Connectors/{name}/tasks/{taskId}/restart：重启单个任务 (通常这个任务已失败)
DELETE /Connectors/{name}：删除 Connector, 停止所有的任务并删除其配置

 

Kafka Connector 还提供了获取有关 Connector plugins 信息的 REST API：
GET /Connector-plugins：返回已在 Kafka Connect 集群安装的 Connector plugin 列表。请注意，API 仅验证处理请求的 worker 的 Connector。这以为着你可能看不不一致的结果，特别是在滚动升级的时候（添加新的 Connector jar）
PUT /Connector-plugins/{Connector-type}/config/validate ：对提供的配置值进行验证，执行对每个配置验证，返回验证的建议值和错误信息