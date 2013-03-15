#-*-coding:utf-8-*-
'''
Created on 2013-3-15

@author: jianfeihit
'''
import httplib,urllib; 

conn = httplib.HTTPConnection('127.0.0.1:8000')
params = urllib.urlencode({'data':"1234".encode('utf-8')})
conn.request(method="POST",url="/",body=params)
resp = conn.getresponse().read()
print resp
conn.close()