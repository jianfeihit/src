#-*-coding:utf-8-*-
import httplib,urllib; 
import json

#≈‰÷√≤Œ ˝
code = ''
client_id = ''
client_secret = ''
redirect_uri = 'http://jianfeihit.iteye.com'

conn = httplib.HTTPSConnection('api.weibo.com')
access_token_params = 'client_id='+client_id+'&'\
					  'client_secret='+client_secret+'&'\
					  'grant_type=authorization_code'+'&'\
					  'code='+code+'&'\
					  'redirect_uri='+redirect_uri
conn.request(method="POST",url="/oauth2/access_token?"+access_token_params)	
response = conn.getresponse().read();
print response
json_array = json.loads(response)
if json_array.has_key('access_token'):
	print json_array['access_token']
else:
    print 'has error'
conn.close(); 