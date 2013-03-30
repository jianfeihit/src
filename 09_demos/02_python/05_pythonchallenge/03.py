#-*-coding:utf-8-*-
'''
Created on 2013-3-27
@author: jianfeihit
该题目涉及考点：
1、文件读取
2、统计字符出现次数
'''
result = {};
def satis(x):
    count = 0
    if(result.has_key(x)):
        count = result.get(x)
    count=count+1
    result[x]=count

def filterfun(x):
    return ord(x)>96 and ord(x)<123

data = open('./resources/03.txt','r').read()
#map(satis,data) 通过统计次数分析出来出现次数比较少的是a-z的字母，所以可以通过filter来进行过滤
#print result
print filter(filterfun,data)

