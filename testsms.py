#!/usr/bin/env python
# -*- coding: utf-8 -*
 
import time
import serial
import serial.tools.list_ports



class SMS(object):
	"""docstring for SMS"""
	def __init__(self):
		# self.arg = arg
		print 'test'

	def start(self, port, bit):
		print 'start'
		'启动端口'
		ser = serial.Serial(port, bit)
		print ser
		ser.write("AT+CMGF=1/r")

		pdus4=""
		loop_time = 0
		while(pdus4.find('OK/r/n')<0):
			mydata4=ser.read(65536)
			pdus4=pdus4+mydata4
			time.sleep(0.1)
			print loop_time
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


sms = SMS()
sms.start('com3', 115200)

