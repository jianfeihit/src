#-*- coding: gb2312 -*-
'''
Created on 2013-3-8

@author: jianfeihit
'''

class OffPriceItem(object):
    
    def __init__(self,title,desc,link,source):
        self.title = title
        self.desc = desc
        self.link = link
        self.source = source
    
    def __str__(self):
        return "%s:%s(%s)" %(self.title,self.link,self.source)
    
        
        