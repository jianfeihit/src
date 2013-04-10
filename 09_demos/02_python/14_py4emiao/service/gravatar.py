#-*-coding:utf-8-*-
'''
Created on 2013-4-10

@author: jianfeihit
'''
import urllib, hashlib

email = "someone@somewhere.com"
default = "http://www.example.com/default.jpg"
size = 40
 
def getAvatarUrl(email):
    # construct the url
    gravatar_url = "http://www.gravatar.com/avatar/" + hashlib.md5(email.lower()).hexdigest() + "?"
    gravatar_url += urllib.urlencode({'d':default, 's':str(size)})
    return gravatar_url

if __name__ == '__main__':
    email = 'yueyoum@gmail.com';
    print getAvatarUrl(email)