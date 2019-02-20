#!/bin/bash
 iptables -I INPUT -p tcp --dport 8090 -j ACCEPT
 iptables -I INPUT -p tcp --dport 3306 -j ACCEPT
 iptables -I INPUT -p tcp --dport 6379 -j ACCEPT
 iptables -I INPUT -p tcp --dport 8080 -j ACCEPT
 service iptables save