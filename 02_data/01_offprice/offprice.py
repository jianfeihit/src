#!/usr/bin/env python3
#coding: gb2312
import sendmail,smzdm
import sinaweibo,sys
import utils,dbaccess
from offpriceitem import offpriceitem

#设置编码方式
reload(sys)
sys.setdefaultencoding("gb2312")

#设置keyword和
keyword_list = []
to='yangjianfei@panguso.com,chenmutong@panguso.com'

dbaccess.create_table()
offprice_list = smzdm.parse_smzdm()
mail_text = ''
for offprice_item in offprice_list:
	if dbaccess.insert_or_update(offprice_item):
		sinaweibo.send_weibo(offprice_item.title+' 购买地址：'+offprice_item.link)
		utils.log('send sina_weibo:'+offprice_item.title+' 购买地址：'+offprice_item.link)
		if(utils.haskeyword(offprice_item.title.encode('gb2312'),keyword_list)):
			mail_text = mail_text+offprice_item.title+' 购买地址：'+offprice_item.link+'<br/>'
			
if(mail_text):		
	sendmail.sendMail(to,mail_text)
	utils.log('send email to='+to+',content='+mail_text)
else:
	utils.log('done without any result')

