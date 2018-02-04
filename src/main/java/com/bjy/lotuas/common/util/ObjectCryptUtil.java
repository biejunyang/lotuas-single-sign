
package com.bjy.lotuas.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;

import com.bjy.lotuas.common.exception.SymmetryCryptException;

public final class ObjectCryptUtil {

	private ObjectCryptUtil(){}
	
	public static String getRadixString(Object o,int radix) throws IOException{
		if(o==null){
			return null;	
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(os);
		oout.writeObject(o);
		oout.flush();
		oout.close();
		return getRadixString(os.toByteArray(),radix);
	}
	
	
	public static String getRadixString(byte[] b,int radix) throws IOException{
		if(b!=null){
			return new BigInteger(b).not().toString(radix);
		}
		return null;
	}
	
	public  static byte[] radixStringToByte(String radixString,int radix){
		return new BigInteger(radixString,radix).not().toByteArray();
	}
	
	public static byte[] encrypt(Key key,String transformation,byte[] inputBytes) throws SymmetryCryptException {
		try {
			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(inputBytes);
		} catch (Exception e) {
			throw new SymmetryCryptException(e);
		}
	}

	public static byte[] decrypt(Key key,String transformation,byte[] inputBytes) throws SymmetryCryptException {
		try {
			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(inputBytes);
		} catch (Exception e) {
			throw new SymmetryCryptException(e);
		}
	}
	
	public static Object getObjectFromFile(File file)throws SymmetryCryptException {
		ObjectInputStream oin = null;
		try {
			oin = new ObjectInputStream(new FileInputStream(file));
			return oin.readObject();
		} catch (Exception e) {
			return null;
		} finally {
			if (oin != null)
				try {
					oin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static Object getObjectFromBytes(byte[] b)throws SymmetryCryptException {
		ObjectInputStream oin = null;
		try {
			oin = new ObjectInputStream(new ByteArrayInputStream(b));
			return oin.readObject();
		} catch (Exception e) {
			throw new SymmetryCryptException(e);
		} finally {
			if (oin != null)
				try {
					oin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static Object getObjectFromFile(String path)throws SymmetryCryptException {
		return getObjectFromFile(new File(path));
	}
	
	public static void writeObjectToFile(Object o,File file) throws SymmetryCryptException{
		ByteArrayOutputStream os = null;
		ObjectOutputStream oout = null;
		try {
			os = new ByteArrayOutputStream();
			oout = new ObjectOutputStream(os);
			oout.writeObject(o);
			oout.flush();
			oout.close();
			FileUtils.writeByteArrayToFile(file, os.toByteArray());
		} catch (Exception e) {
			throw new SymmetryCryptException(e);
		}finally{
			if(os!=null){
			    try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
	}
	
	
	
	public static void writeObjectToFile(Object o,String path) throws SymmetryCryptException{
		writeObjectToFile(o,new File(path));
	}
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, SymmetryCryptException {
	}

}
