#-*-coding:utf-8-*-
'''
Created on 2013-3-30

@author: jianfeihit
'''
import publicsuffix
import urllib

psl = publicsuffix.PublicSuffixList()
result = {}

class FilterResult(object):
    def __init__(self,url):
        self.url = url
        self.count = 1
    
    def addCount(self):
        self.count = self.count + 1
    
    def __str__(self):
        return 'count:'+str(self.count)+'|#first_url='+str.rstrip(self.url)


def getDomain(url):
    proto, rest = urllib.splittype(url)
    host, rest = urllib.splithost(rest)
    if host is not None:
        return psl.get_public_suffix(host)
    else:
        return None

    
def filterUrl(url):
    domain = getDomain(url)
    if domain is None:
        return
    if(result.has_key(domain)):
        fr = result.get(domain)
        fr.addCount()
    else:
        fr = FilterResult(url)
    result[domain]=fr
    
urls = open('urls.txt','r').readlines()
map(filterUrl,urls)

for domain in result.keys():
    print domain+'|#'+str(result.get(domain))