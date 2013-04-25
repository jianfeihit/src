#-*-coding:utf-8-*-
'''
Created on 2013-3-8

@author: jianfeihit
'''
import httplib,urllib,json
import sys 

reload(sys) 
sys.setdefaultencoding('utf-8')

class SinaWeibo:
	def __init__(self,token,code='',client_id='',client_secret='',redirect_uri=''):
		self.__token__ = token
		self.__code = code
		self.__client_id = client_id
		self.client_secret = client_secret
		self.redirect_uri = redirect_uri

	def send_weibo(self,text):
		conn = httplib.HTTPSConnection('api.weibo.com')
		params = urllib.urlencode({'status':text.encode('utf-8')})
		headers = {'Authorization':'OAuth2 '+self.__token__,"Content-Type":"application/x-www-form-urlencoded"}
		conn.request(method="POST",url="/2/statuses/update.json",body=params,headers=headers)
		conn.getresponse()
		conn.close()   
	
	def get_token(self):
		conn = httplib.HTTPSConnection('api.weibo.com')
		access_token_params = 'client_id='+self.client_id+'&'\
					  'client_secret='+self.client_secret+'&'\
					  'grant_type=authorization_code'+'&'\
					  'code='+self.code+'&'\
					  'redirect_uri='+self.redirect_uri
		conn.request(method="POST",url="/oauth2/access_token?"+access_token_params)
		response = conn.getresponse().read()
		json_array = json.loads(response)
		if json_array.has_key('access_token'):
			return json_array['access_token']
		else:
			return ''
	
	def get_userId_nickname(self,nickname):	
		conn = httplib.HTTPSConnection('api.weibo.com/2/users/domain_show.json?domain=%s&access_token=%s'%(nickname,self.__token__))	
		response = conn.getresponse().read()
		json_array = json.loads(response)
		return json_array['id']


	def get_user_weibo(self,uid):
		conn = httplib.HTTPSConnection('api.weibo.com')	
		conn.request(method="GET",url="/2/statuses/user_timeline.json?uid=%d&access_token=%s&feature=1&trim_user=1"%(uid,self.__token__))
		response = conn.getresponse().read()
		json_array = json.loads(response)
		weiboItems = json_array['statuses']
		totalNum = json_array['total_number']
		result = []
		for weibo in weiboItems:
			result.append(str(weibo['id'])+','+weibo['text'])
		return totalNum,result
		
if __name__ == '__main__':
	sinaweibo = SinaWeibo('***')
	total,contents = sinaweibo.get_user_weibo(1844559843)
	for tt in contents:
		print tt