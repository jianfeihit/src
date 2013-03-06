#-*-coding:utf-8-*-
import httplib,urllib; 
import json,sys

reload(sys)
sys.setdefaultencoding("utf-8")

#≈‰÷√≤Œ ˝
access_token = '***'

def send_weibo(text):
	conn = httplib.HTTPSConnection('api.weibo.com')
	params = urllib.urlencode({'status':text.encode('utf-8')})
	headers = {'Authorization':'OAuth2 '+access_token,"Content-Type":"application/x-www-form-urlencoded"}
	conn.request(method="POST",url="/2/statuses/update.json",body=params,headers=headers)
	response = conn.getresponse()
	conn.close();   