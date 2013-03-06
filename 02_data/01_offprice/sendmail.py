#!/usr/bin/env python3
#coding: utf-8
import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender = 'jianfeihit@yahoo.com.cn'
subject = '最新促销信息'
smtpserver = 'smtp.mail.yahoo.com.cn'
username = 'jianfeihit'
password = '7139179yjf'

def sendMail(receiver,text):
	msg = MIMEText(text,'html','gb2312')
	msg['Subject'] = subject
	smtp = smtplib.SMTP()
	smtp.connect(smtpserver)
	smtp.login(username, password)
	smtp.sendmail(sender, receiver, msg.as_string())
	smtp.quit()
	
if __name__ == '__main__':
	to = ['yangjianfei@panguso.com','chenmutong@panguso.com']
	sendMail(to,'Test')