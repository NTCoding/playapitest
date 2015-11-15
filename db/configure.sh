#!/bin/bash -e

echo "Creating chatroomz database and apitests database"
mysql -e "create database if not exists chatroomz default charset utf8"
mysql -e "grant all privileges on chatroomz.* to 'chatty'@'%' identified by 'chatty'"
mysql -e "create database if not exists apitests default charset utf8"
mysql -e "grant all privileges on apitests.* to 'chatty'@'%' identified by 'chatty'"
echo "chatroomz Database created. Setting up database"
cat /vagrant/conf/db.sql | mysql chatroomz