import sqlite3
import utils
import offpriceitem

data_file = 'data.db'

def create_table():
	conn = sqlite3.connect(data_file)
	cur = conn.cursor()
	cur.execute("create table if not exists offprice(link text)")
	conn.commit()
	conn.close()
	
def insert_or_update(offpriceitem):
	conn = sqlite3.connect(data_file)
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
	
if __name__ == '__main__':
	create_table();
	offpriceitem = offpriceitem.offpriceitem('title','desc','http://www2.baidu.com')
	print insert_or_update(offpriceitem)
		

