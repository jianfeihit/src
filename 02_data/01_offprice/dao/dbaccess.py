#-*-coding:utf-8-*-
'''
Created on 2013-3-8

@author: jianfeihit
'''
import sqlite3

data_file = '/application/search/python/offprice/data.db'

class SQLite3Access:
	def __init__(self,data_file):
		self.data_file = data_file
		
	def create_table(self):
		conn = sqlite3.connect(self.data_file)
		cur = conn.cursor()
		cur.execute("create table if not exists offprice(link text)")
		conn.commit()
		conn.close()
	
	def insert_or_update(self,offpriceitem):
		conn = sqlite3.connect(self.data_file)
		cur = conn.cursor()
		rows  = cur.execute("select * from offprice where link='"+offpriceitem.link.encode('utf-8')+"'")
		flag = False
		count = len(rows.fetchall())
		if(count == 0):
			cur.execute("insert into offprice(link) values('"+offpriceitem.link.encode('utf-8')+"')")
			conn.commit()
			flag = True
			conn.close()
			return flag
		

