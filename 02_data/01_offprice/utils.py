#!/usr/bin/env python3
#coding: utf-8
import sys,utils

reload(sys)
sys.setdefaultencoding("utf-8")

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
	
if __name__ == '__main__':
	print haskeyword('11',[])