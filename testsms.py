#!/usr/bin/env python
# -*- coding: utf-8 -*
 
import time
import serial
import serial.tools.list_ports

# at
# OK
# at cmgf=1
# ERROR
# AT+CMGF=1
# OK
# AT+CMGS="+8618514235966"
# > IAMPIGZ
# > DSF
# +CMGS: 0

# OK
# 



class SMS(object):
	"""docstring for SMS"""
	def __init__(self, port, bit):
		# self.arg = arg
		self.start(port, bit)

	def start(self, port, bit):
		'启动端口'
		ser = serial.Serial(port, bit)
		ser.write("AT+CMGF=1/r")

		pdus4=""
		loop_time = 0
		while(pdus4.find('OK/r/n')<0):
			mydata4=ser.read(65536)
			pdus4=pdus4+mydata4
			time.sleep(0.1)
			if loop_time > 5:
				break
			else:
				loop_time += 1

		print ser
		print pdus4

	def send(self, cont):
		#start send message'已收到短信');
		if self.ser.isOpen():
			pass
			  

		print 'start send message'
		ser.write("AT+CMGS=47/r");


sms = SMS('com3', 115200)