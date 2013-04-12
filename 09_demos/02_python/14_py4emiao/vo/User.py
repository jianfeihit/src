#-*-coding:utf-8-*-
'''
Created on 2013-4-10

@author: jianfeihit
'''
import time

class User(object):
    
    def __init__(self,userId,avatar,email,password,username):
        self.userId = userId
        self.avatar = avatar
        self.email = email
        self.password = password
        self.username = username
        self.created_date = time.strftime('%Y-%m-%d',time.localtime(time.time()))
    
    def __str__(self):
        return 'userId='+self.userId+'|#avatar='+self.avatar+\
               '|#email='+self.email+'|#password='+self.password+\
               '|#password='+self.password+'|#username='+self.username+\
               '|#created_date='+self.created_date

if __name__ == '__main__':
    user = User('userId','avatar','email','pwd','useName')
    print user                 