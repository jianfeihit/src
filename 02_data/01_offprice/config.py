'''
Created on 2013-3-8

@author: jianfeihit
'''
import yaml

class Config:
    def __init__(self,con_file):
        config = yaml.load(open(con_file))
        self.log_home = config['log_home']
        self.mail_sender = config['mail_sender']
        self.mail_subject = config['mail_subject']
        self.mail_smtpserver = config['mail_smtpserver']
        self.mail_username = config['mail_username']
        self.mail_password = config['mail_password']
        self.mail_to = config['mail_to'].split(',')
        self.sina_accesstoken = config['sina_accesstoken']
        self.yiqifa_appkey = config['yiqifa_appkey']
        self.yiqifa_secret = config['yiqifa_secret']
        self.yiqifa_url = config['yiqifa_url']
        self.smzdm_url = config['smzdm_url']
        self.smzdt_url = config['smzdt_url']
        self.keyword = config['keyword']
        self.sqlite3_data = config['sqlite3_data']