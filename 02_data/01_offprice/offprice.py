# -*-coding:utf-8-*-
'''
Created on 2013-3-8

@author: jianfeihit
'''
from config import Config
from dao.dbaccess import SQLite3Access
from feed import parser
from myutils import myutils
from myutils.mailsender import MailSender
from myutils.mylog import MyLog
from myutils.sinaweibo import SinaWeibo
from yiqifa.yiqifa import Yiqifa

config = Config('config.yaml')
sqlite_access = SQLite3Access(config.sqlite3_data)
sqlite_access.create_table()
mylog = MyLog(config.log_home)
sinaweibo = SinaWeibo(config.sina_accesstoken)
mylog = MyLog(config.log_home)
mail_sender = MailSender(config.mail_sender,config.mail_subject,config.mail_smtpserver,config.mail_username,config.mail_password,config.mail_to)
yiqifa = Yiqifa(config.yiqifa_url, config.yiqifa_appkey, config.yiqifa_secret)

offprice_list = []
offprice_list.extend(parser.parser(config.smzdm_url))
offprice_list.extend(parser.parser(config.smzdt_url))
offprice_list.extend(yiqifa.parse_discount())
mail_text = ''
for item in offprice_list:
	if sqlite_access.insert_or_update(item):
		sinaweibo.send_weibo(str(item))
		mylog.info('send sina_weibo:' + str(item))
		if(myutils.haskeyword(item.title.encode('gb2312'), config.keyword)):
			mail_text = mail_text + str(item) + '<br/>'
			
if(mail_text):	
	mail_sender.sendMail(mail_text)
	mylog.info('send email to=' + ",".join(config.mail_to) + ',content=' + mail_text)
else:
	mylog.info('done without any result')

