#!/bin/bash
 iptables -I INPUT -p tcp --dport 8090 -j ACCEPT
 iptables -I INPUT -p tcp --dport 3306 -j ACCEPT
 iptables -I INPUT -p tcp --dport 6379 -j ACCEPT
 iptables -I INPUT -p tcp --dport 8080 -j ACCEPT
 iptables -I INPUT -p tcp --dport 9000 -j ACCEPT
 iptables -I INPUT -p tcp --dport 50010 -j ACCEPT
 iptables -I INPUT -p tcp --dport 50075 -j ACCEPT
 iptables -I INPUT -p tcp --dport 50020 -j ACCEPT
 iptables -I INPUT -p tcp --dport 39911 -j ACCEPT
 iptables -I INPUT -p tcp --dport 50090 -j ACCEPT
 iptables -I INPUT -p tcp --dport 50070 -j ACCEPT
 iptables -I INPUT -p tcp --dport 25 -j ACCEPT
 iptables -I INPUT -p tcp --dport 54786 -j ACCEPT
 iptables -I INPUT -p tcp --dport 54784 -j ACCEPT
 iptables -I INPUT -p tcp --dport 18088 -j ACCEPT
 iptables -I INPUT -p tcp --dport 18025 -j ACCEPT
 iptables -I INPUT -p tcp --dport 18030 -j ACCEPT
 iptables -I INPUT -p tcp --dport 18040 -j ACCEPT
 iptables -I INPUT -p tcp --dport 18141 -j ACCEPT
 service iptables save