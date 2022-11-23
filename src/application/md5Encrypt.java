package application;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class md5Encrypt {

	public static String encryptPass(String password) {
		String pass = null;
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(password.getBytes());

			BigInteger bigInt = new BigInteger(1, messageDigest);

			pass = bigInt.toString(16);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pass;

	}
}