#!/bin/bash -e

echo "Creating chatroomz database"
mysql -e "create database if not exists chatroomz default charset utf8"
mysql -e "grant all privileges on chatroomz.* to 'chatty'@'%' identified by 'chatty'"
mysql -e "create database if not exists acceptancetests default charset utf8"
mysql -e "grant all privileges on acceptancetests.* to 'chatty'@'%' identified by 'chatty'"
echo "chatroomz Database created. Setting up database"
cat /vagrant/conf/db.sql | mysql chatroomz