#-*-coding:utf-8-*-
'''
Created on 2013-4-11

@author: jianfeihit
'''

result = []

src = 'D:/logs/hBasePutProfiler.log'
dst = 'D:/logs/hBasePutProfiler.csv'

def split(para):
    temp1 = para.split(';')
    values = []
    for ss in temp1:
        keys,value = ss.split('=')
        values.append(value)
    result.append(','.join(values))
    
logs=open(src,'r').readlines()
map(split,logs)
file(dst,'w').writelines(result)
    