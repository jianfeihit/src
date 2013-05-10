#-*-coding:utf-8-*-
'''
Created on 2013-5-5

@author: jianfeihit
'''
import re

def findPart(text):
    usample=unicode(text,'utf8')
    res=re.findall(u"[\u2E80-\u9FFF]", usample)
    if res:
        for r in res:
            print r.encode("utf8")
 
findPart("#who#helloworld#a中文x#")