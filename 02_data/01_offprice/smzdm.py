#-*-coding:utf-8-*-
import feedparser 
from offpriceitem import offpriceitem

smzdm_url='http://feed.smzdm.com/'
def parse_smzdm():
	result = feedparser.parse(smzdm_url)
	offprice_list = []
	for element in result['entries']:
		link = element['guid'].replace('?p=','go/')
		item = offpriceitem(element['title'],element['description'],element['guid'])
		offprice_list.append(item)
	return offprice_list
		