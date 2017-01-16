package by.tilalis.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
	private static MessageDigest md;
	
	static {
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	private String result;
	
	public SHA256(final String from) {
		
        byte[] result = md.digest(from.getBytes());
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        this.result = sb.toString();
	}
	
	@Override
	public String toString() {
		return this.result;
	}
}
