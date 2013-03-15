#-*-coding:utf-8-*-
'''
Created on 2013-3-8

@author: jianfeihit
'''
import logging

class MyLog:
    def __init__(self,log_home):
        self.__log_home__ = log_home
        
    def info(self,text):
        logging.basicConfig(level=logging.DEBUG,
                format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
                datefmt='%a, %d %b %Y %H:%M:%S',
                filename=self.__log_home__)
        logging.info(text)
     
        