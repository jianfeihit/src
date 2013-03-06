#!/usr/bin/env python3
#coding: utf-8
import sys,utils
import logging

reload(sys)
sys.setdefaultencoding("utf-8")

LOG_FILENAME="/application/search/python/offprice/myapp.log"
handler=logging.FileHandler(LOG_FILENAME)
logging.basicConfig(level=logging.DEBUG,
                format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
                datefmt='%a, %d %b %Y %H:%M:%S',
                filename='myapp.log',
                filemode='w')
logger.addHandler(handler)

def haskeyword(text,keyword_list):
	flag = False
	if not keyword_list:
		return True
	for keyword in keyword_list:
		try:
			flag = text.index(keyword)>-1
		except:
			pass
	return flag

def log(log_text):
	logging.info(log_text)
	
if __name__ == '__main__':
	log('11')