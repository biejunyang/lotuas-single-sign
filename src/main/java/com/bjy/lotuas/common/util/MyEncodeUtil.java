package com.bjy.lotuas.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;


public final class MyEncodeUtil {
     private MyEncodeUtil(){}
     
     public static String byteToRadixString(byte[] b,int radix){
    	if(b==null || b.length==0){
    		return null;
    	}
 		return new BigInteger(b).toString(radix);
     }
     
	public static byte[] radixStringtoByte(String radixString, int radix) {
		if(radixString==null || "".equals(radixString)){
			return null;	
		}	
		return new BigInteger(radixString, radix).toByteArray();
		
	}
     
     public static String toRadixString(String src) throws UnsupportedEncodingException{
    	 if(src==null){
    		 return null;
    	 }
    	 if("".equals(src)){
    		 return src;
    	 }
    	 return byteToRadixString(src.getBytes("UTF-8"),16);
     }
     
     public static String restoreRadixString(String radixString){
    	 if(radixString==null){
    		 return null;
    	 }
    	 if("".equals(radixString)){
    		 return "";
    	 } 
    	 return new String(radixStringtoByte(radixString,16));
     }
     
     public static void main(String[] args) throws UnsupportedEncodingException{
    	 System.out.println(toRadixString("怎么怎么怎么"));
    	 System.out.println(restoreRadixString("-197f711b4677197f711b4677197f711b4678"));
     }
}
