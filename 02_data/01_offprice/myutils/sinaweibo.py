#-*-coding:utf-8-*-
'''
Created on 2013-3-8

@author: jianfeihit
'''
import httplib,urllib,json; 

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
			
		
if __name__ == '__main__':
	sinaweibo = SinaWeibo('***')
	sinaweibo.send_weibo('Test测试中文111')