package com.qh.app.resource.pay;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class PaymentResult {
	public static Map<String,String> getPaymentResultMap(String result){
		Map<String,String> map=new HashMap<String,String>();
		
		try{
			StringReader xmlReader=new StringReader(result);    
			InputSource xmlSource=new InputSource(xmlReader);   

			SAXBuilder builder=new SAXBuilder();
			Document doc=builder.build(xmlSource);
			Element root=doc.getRootElement();
			map.put("partner", root.getChildText("partner"));
			map.put("seller", root.getChildText("seller_email"));
			map.put("orderId", root.getChildText("out_trade_no"));
			map.put("orderName", root.getChildText("subject"));
			map.put("tradeNo", root.getChildText("trade_no"));
			map.put("amount", root.getChildText("total_fee"));
			map.put("buyerId", root.getChildText("buyer_id"));
			map.put("buyer", root.getChildText("buyer_email"));
			map.put("tradeStatus", root.getChildText("trade_status")); 
		}
		catch(Exception e){
			map=null;
		}
		return map;
	}
}
