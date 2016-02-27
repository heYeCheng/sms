package com.msg;

import java.util.ArrayList;
import java.util.List;
import org.smslib.ICallNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;
import org.smslib.Library;
import org.smslib.Service;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message.MessageTypes;
import org.smslib.Message.MessageEncodings;
import org.smslib.modem.SerialModemGateway;

import redis.RedisUtil;
import redis.clients.jedis.Jedis;

public class SmsHandle {
	Service srv;
	SerialModemGateway gateway;

	public SmsHandle() {
		try {
			System.out.println("Example: Read messages from a serial gsm modem.");
			System.out.println(Library.getLibraryDescription());
			System.out.println("Version: " + Library.getLibraryVersion());
			// Create new Service object - the parent of all and the main
			// interface
			// to you.
			this.srv = new Service();
			// Create the Gateway representing the serial GSM modem.
			this.gateway = new SerialModemGateway("modem.com4", "COM4", 115200, "wavecom", "");
			// Do we want the Gateway to be used for Inbound messages? If not,
			// SMSLib will never read messages from this Gateway.
			this.gateway.setInbound(true);
			// Do we want the Gateway to be used for Outbound messages? If not,
			// SMSLib will never send messages from this Gateway.
			this.gateway.setOutbound(true);
			this.gateway.setSimPin("0000");

			InboundNotification inboundNotification = new InboundNotification();
			// Create the notification callback method for inbound voice calls.
			CallNotification callNotification = new CallNotification();
			// Set up the notification methods.
			this.gateway.setInboundNotification(inboundNotification);
			this.gateway.setCallNotification(callNotification);

			OutboundNotification outboundNotification = new OutboundNotification();
			this.gateway.setOutboundNotification(outboundNotification);
			// Add the Gateway to the Service object.
			this.srv.addGateway(this.gateway);
			// Similarly, you may define as many Gateway objects, representing
			// various GSM modems, add them in the Service object and control
			// all of them.
			//
			// Start! (i.e. connect to all defined Gateways)
			this.srv.startService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getImformation() {
		System.out.println();
		System.out.println("Modem Information:");
		// System.out.println(" Manufacturer: " +
		// this.gateway.getManufacturer());
		// System.out.println(" Model: " + this.gateway.getModel());
		// System.out.println(" Serial No: " + this.gateway.getSerialNo());
		// System.out.println(" SIM IMSI: " + this.gateway.getImsi());
		// System.out.println(" Signal Level: " + this.gateway.getSignalLevel()
		// + "%");
		// System.out.println(" Battery Level: " +
		// this.gateway.getBatteryLevel() + "%");
		System.out.println();
	}

	public void sendMsg() throws Exception {
		// this.srv.startService();
		Jedis redis = RedisUtil.getJedis();
		while (true) {
			List<String> bb = redis.blpop(0, "list");
			OutboundMessage msg;
			if (bb.get(1).equals("1")) {
				msg = new OutboundMessage("18514235966", "hello canyousdfdf¿ªÀÊreceadve");
			} else {
				msg = new OutboundMessage("18514235966", "322323 sdfdf¿ªÀÊreceadve");
			}

			msg.setEncoding(MessageEncodings.ENCUCS2);
			this.srv.sendMessage(msg);
			System.out.println(msg);
		}

	}

	public void readMsg() throws Exception {
		System.out.println("dsf");
		List<InboundMessage> msgList;
		// // Create the notification callback method for Inbound & Status
		// Report
		// // messages.
		try {
			// Read Messages. The reading is done via the Service object and
			// affects all Gateway objects defined. This can also be more
			// directed to a specific
			// Gateway - look the JavaDocs for information on the Service method
			// calls.
			msgList = new ArrayList<InboundMessage>();
			this.srv.readMessages(msgList, MessageClasses.ALL);

			for (InboundMessage msg : msgList) {
				// System.out.println(msg);
				System.out.println();
				System.out.println("ddddddddddddddddddddddddddddddddd");
				System.out.println(msg.getDate());
				System.out.println(msg.getEncoding());
				System.out.println(msg.getOriginator());
				System.out.println(msg.getText());
				System.out.println(msg.getMemIndex());

				// if (msg.getOriginator().equalsIgnoreCase("852193193")){
				// this.srv.deleteMessage(msg); //åˆ é™¤çŸ­ä¿¡
				// System.out.println("this msg has been killed");
				// }
			}
			// Sleep now. Emulate real world situation and give a chance to the
			// notifications
			// methods to be called in the event of message or voice call
			// reception.
			System.out.println("Now Sleeping - Hit <enter> to terminate.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("finish recv");
			this.srv.stopService();
		}
	}

	public class InboundNotification implements IInboundMessageNotification {
		public void process(String gatewayId, MessageTypes msgType, InboundMessage msg) {
			if (msgType == MessageTypes.INBOUND)
				System.out.println(">>> New Inbound message detected from Gateway: " + gatewayId);
			else if (msgType == MessageTypes.STATUSREPORT)
				System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gatewayId);
			System.out.println(msg);
			try {
				// Uncomment following line if you wish to delete the message
				// upon arrival.
				// srv.deleteMessage(msg);
			} catch (Exception e) {
				System.out.println("Oops!!! Something gone bad...");
				e.printStackTrace();
			}
		}
	}

	public class OutboundNotification implements IOutboundMessageNotification {
		public void process(String gatewayId, OutboundMessage msg) {
			System.out.println("Outbound handler called from Gateway: " + gatewayId);
			System.out.println(msg);
		}
	}

	public class CallNotification implements ICallNotification {
		public void process(String gatewayId, String callerId) {
			System.out.println(">>> New call detected from Gateway: " + gatewayId + " : " + callerId);
		}
	}

	public static void main(String args[]) {
		SmsHandle app = new SmsHandle();
		try {
			// app.readMsg();
			// app.getImformation();
			app.sendMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}