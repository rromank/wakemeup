package biz.wakemeup.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
	private String password;
	private String cryptedPassword;
	private static final String SALT = "WakeMeUpSalt";

	public PasswordHash(String password) {
		this.password = password;
		crypt();
	}

	private void crypt() {
		MessageDigest md;
		String saltedPassword = password + SALT;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(saltedPassword.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			cryptedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			System.err.println("No such alghoritm");
		}
	}

	public String getCryptedPassword() {
		return cryptedPassword;
	}
}
