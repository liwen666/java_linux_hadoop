#!/bin/bash
yum install -y net-tools

systemctl stop firewalld
systemctl mask firewalld
yum install -y iptables
yum install -y iptables-services
systemctl start iptables.service
systemctl restart iptables.service

systemctl enable iptables.service


yum install -y telnet
