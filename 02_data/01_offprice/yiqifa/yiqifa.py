# -*-coding:utf-8-*-
'''
Created on 2013-3-8

@author: jianfeihit
'''
from feedparser import base64
from offpriceitem import OffPriceItem
import hmac
import httplib
import json
import time
import urllib

class Yiqifa:
    def __init__(self, basePath, app_key, app_secret):
        self.__basePath = basePath
        self.__app_key = app_key
        self.app_secret = app_secret
    
    def _sign(self, base_uri='/', parameters={}):
        p = "%s&%s&%s" % (
                          'GET',
                          urllib.quote(base_uri) ,
                          '%26'.join(k + '%3D' + urllib.quote(str(v)) for k, v in sorted(parameters.iteritems()))
        )
        sign = base64.b64encode(hmac.new(self.app_secret + '&openyiqifa', p).hexdigest())
        return sign

    def _get_request_header(self):
        return {
             'Content-type': 'application/x-www-form-urlencoded',
             "Cache-Control": "no-cache",
             "Connection": "Keep-Alive",
             }

    def _api_request(self,api_request_path='/', api_request_parameters={}, timeout=30):
        connection = httplib.HTTPConnection(self.__basePath, 80, timeout)
        sys_parameters = {
                          'oauth_consumer_key': self.__app_key,
                          'oauth_signature_method': "HMAC-SHA1",
                          'oauth_version': '1.0',
                          'oauth_nonce': int(time.time()),
                          'oauth_timestamp': int(time.time()),
                          'oauth_token': 'openyiqifa',
                          }
        sys_parameter_str = ",".join('%s="%s"' % (k, v) for k, v in sorted(sys_parameters.iteritems()))
        sign_parameter = sys_parameters.copy()
        sign_parameter.update(api_request_parameters)
        sign = self._sign(api_request_path, sign_parameter)
        authorization = 'OAuth ' + sys_parameter_str + ',oauth_signature="' + sign + '"'
        api_request_parameters['Authorization'] = authorization
        header = self._get_request_header()
        header.update({
                       'Authorization': authorization,
                       })
        body = urllib.urlencode(api_request_parameters)
        connection.connect()
        connection.request('GET', api_request_path, body=body, headers=header)
        response = connection.getresponse()
        status = response.status
        content = response.read().decode('gb2312').encode('utf8')
        if status is not 200:
            return []
        else:
            return content

    def parse_discount(self):
        result = self._api_request(api_request_path='/discount.json')
        s = json.loads(result)
        offprice_list = []
        for item in s['gwkdiscounts']:
            item = OffPriceItem(item['name'], item['searchstring'], item['unicode'],u'亿起发联盟')
            offprice_list.append(item)
        return offprice_list

if __name__ == '__main__':
    yiqifa = Yiqifa('openapi.yiqifa.com','13623838876268506','770be8d548092a8af17d05d8f2654e23')
    result_list = yiqifa.parse_discount();
    for item in result_list:
        print item
