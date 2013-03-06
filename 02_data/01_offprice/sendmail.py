#!/usr/bin/env python3
#coding: utf-8
import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender = 'jianfeihit@yahoo.com.cn'
subject = 'python email test'
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