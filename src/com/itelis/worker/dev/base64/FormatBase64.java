package com.itelis.worker.dev.base64;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class FormatBase64 {
	
	public FormatBase64(){
		
	}
	static byte[] encoded, decoded;

    public static byte[] getDecoded() {
		return decoded;
	}

	public static void setDecoded(byte[] decoded) {
		FormatBase64.decoded = decoded;
	}

	public static byte[] getEncoded() {
		return encoded;
	}

	public static void setEncoded(byte[] encoded) {
		FormatBase64.encoded = encoded;
	}

	/**
	 * 
	 * @param end
	 * @return
	 */
	public static String encodage(String end){
		
		return new String(Base64.encodeBase64(end.getBytes()));
	}
	/**
	 * 
	 * @param dec
	 * @return
	 */
	public static String decodage(String dec){
		
		return new String(Base64.decodeBase64(dec.getBytes()));
	}
	public static void main(String args[]) throws IOException {
        String orig="experteo";
        String filename="";
        //encoding  byte array into base 64
        encoded = Base64.encodeBase64(orig.getBytes());     
      
        System.out.println("Original String: " + orig );
        System.out.println("Base64 Encoded String : " + new String(encoded));
      
        //decoding byte array into base64
        decoded = Base64.decodeBase64(encoded);      
        System.out.println("Base 64 Decoded  String : " + new String(decoded));

    }


}
