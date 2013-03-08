#-*-coding:utf-8-*-
'''
Created on 2013-3-8

@author: jianfeihit
'''
from offpriceitem import OffPriceItem
import feedparser

def parser(url):
	result = feedparser.parse(url)
	offprice_list = []
	source = result['channel']['title']
	for element in result['entries']:
		link = element['guid'].replace('?p=','go/')
		item = OffPriceItem(element['title'],element['description'],link,source)
		offprice_list.append(item)
	return offprice_list
		