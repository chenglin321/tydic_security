package com.tydic.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import com.tydic.BaseTests;

public class HashMacTests extends BaseTests{

		public void testDefaultHashMac(){
			String text = "中国heasdfsadfsadllo,leo.";
			String m = HashMac.hash(text);
			System.out.println(m);
			assertNotNull(m);
		}
		
		public void testHashMacWithKey() throws NoSuchAlgorithmException{
			Key key = KeyFactory.getKeyFactory().getKey(256, "HmacSHA256");
			String text = "中国heasdfsadfsadllo,leo.";
			String m = new HashMac().hash(text,key);
			System.out.println(m);
			assertNotNull(m);
		}
		
		public void testHashMacWithKey1() throws NoSuchAlgorithmException{
			Key key = KeyFactory.getKeyFactory().getKey("e".getBytes(), "HmacSHA256");
			String text = "中国heasdfsadfsadllo,leo.";
			String m = new HashMac().hash(text,key);
			System.out.println(m);
			assertNotNull(m);
		}
		
		public void testHashMacWithKey2(){
			HashMac hmac = new HashMac();
			String text = "userid";
			String key = "pwd";//不能为空
			String m = hmac.hash(text, key);
			System.out.println(m);
			assertNotNull(m);
			
			text = "13213111";
			key = "aae3";
			m = hmac.hash(text, key);
			System.out.println("text["+text+"] key["+key+"]  hmac["+m+"]");
			
			text = "_33192dI";
			key = ",.&%";
			m = hmac.hash(text, key);
			System.out.println("text["+text+"] key["+key+"]  hmac["+m+"]");
			
			text = "中文";
			key = "D#&";
			m = hmac.hash(text, key);
			System.out.println("text["+text+"] key["+key+"]  hmac["+m+"]");
		}
}
