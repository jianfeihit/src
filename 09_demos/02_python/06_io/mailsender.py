#-*-coding:utf-8-*-
'''
Created on 2013-3-8

@author: jianfeihit
'''
import smtplib
from email.mime.text import MIMEText

class MailSender:
	def __init__(self,sender='yangjianfei@panguso.com',subject='myip',smtpserver='smtp.panguso.com',username='yangjianfei',password='*******',to='yangjianfei@panguso.com'):
		self.__sender = sender
		self.__subject = subject
		self.__smtpserver = smtpserver
		self.__username = username
		self.__password = password
		self.__to = to
		
	def sendMail(self,text):
		msg = MIMEText(text,'html','gb2312')
		msg['Subject'] = self.__subject
		smtp = smtplib.SMTP()
		smtp.connect(self.__smtpserver)
		smtp.login(self.__username, self.__password)
		smtp.sendmail(self.__sender, self.__to, msg.as_string())
		smtp.quit()
	
if __name__ == '__main__':
	mailSender = MailSender()
	mailSender.sendMail('Test mail')