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
		ser = serial.Serial(port, bit, timeout = 2)
		print ser,'\n\n'
		ser.write("AT+CMGF=1/r")

		data = ""
		loop_time = 0
		while ser.inWaiting() > 0:
			data += ser.read(1)
			print data
			print loop_time

			if loop_time > 4:
				break
			else:
				loop_time += 1

		ser.close()

	def send(self, cont):
		#start send message'已收到短信');
		if self.ser.isOpen():
			pass
			  

		print 'start send message'
		ser.write("AT+CMGS=47/r");


sms = SMS()
sms.start('com3', 115200)

