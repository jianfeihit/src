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
        return "%s,%s(%s)" %(self.title,self.link,self.source)
    

if __name__ == '__main__':
    item = OffPriceItem(u'标题',u'描述',u'link',u'来源')
    print item
    
        
        