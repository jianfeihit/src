#-*-coding:utf-8-*-
'''
Created on 2013-3-8

@author: jianfeihit
'''
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