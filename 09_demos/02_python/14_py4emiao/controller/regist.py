#-*-coding:utf-8-*-
'''
Created on 2013-4-10

@author: jianfeihit
'''
import web
from service import gravatar
from vo.User import User

urls = ('/regist', 'regist',)
app = web.application(urls, globals())

class regist(object):
    def POST(self):
        username,email,password = web.input().username, web.input().email, web.input().password
        if len(password) <6 :
            return 'password is too short.'
        avatar = gravatar.getAvatarUrl(email)
        #构造User，然后调用DAO入库，DAO层略去
        return 'Hello world!'
    
if __name__ == '__main__':
    app.run()