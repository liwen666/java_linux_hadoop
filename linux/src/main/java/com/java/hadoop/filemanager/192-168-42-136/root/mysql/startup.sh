#!/bin/bash
package=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)
echo $package
HOSTNAME="192.168.42.136"  #数据库信息
PORT="3306"
USERNAME="root"
PASSWORD=$2
GP_URL="{\"auth_user\":\"gpadmin\",\"auth_pass\":\"gpadmin\",\"jdbc_url\":\"jdbc:postgresql://172.16.103.105:5432/anyest3_financial_cloud_$1?reWriteBatchedInserts=true\"}"
echo ----------------开始$GP_URL------------------------------
TENANT_NO=$1
if [ x"$1" = x ]; then
    echo "please set tenant-no  请指定租户id"
    exit 1
fi
if [ x"$2" = x ]; then
    echo "please set pasword  请输入密码"
    exit 1
fi
echo ------------租户号是$1-------------

DBNAME="anyest3_financial_cloud_"$1  #数据库名称

#创建数据库
create_db_sql="create database IF NOT EXISTS ${DBNAME}"
init_est_data="call est_data_init($1,'$GP_URL')"
echo init_est_data

mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} -e "${create_db_sql}"
cd $package;
for f in `ls $package/*.sql`
do
echo $f;
mysql  -h${HOSTNAME} -P${PORT} -u$USERNAME -p$PASSWORD -f $DBNAME -e "source $f";
#mv $f $f.done;
done
echo ----------begin init est_data--------------
mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} -f $DBNAME -e "${init_est_data}"
echo 'finished!'
