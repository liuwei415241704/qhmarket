package com.qh.app.resource.pay;


public class MessageSign {
	private static String baseKey="kmt123098kmt";
	
	public static String getSignString(String message,String code){
		String sign=null;
		int size=(int) (Long.parseLong(code)%message.length());
		message=message.substring(0,size)+baseKey+message.substring(size);
		sign=MD5Encrypt.encryptMD5(message);
		
		return sign;
	}


/*	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		//String msg="123456";
		//MD5 md5 = new MD5();  
		//byte[] resultBytes = md5.eccrypt(msg);  
		//MessageSign a=new MessageSign();
		System.out.println("�����ǣ�" + getSignString("01234567PPPP89abcdefghigklmnopqrstuvwxyz","1429843902332"));  

	}*/
}
