package com.kuhn.sendMsgTestCode;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;

@SpringBootApplication
public class SendMsgTestCodeApplication {




	public SendMsgTestCodeApplication() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

		String serviceid = "ncp:sms:kr:314964386801:messagetest";

		Timestamp time = new Timestamp(System.currentTimeMillis());

		Instant instant = Instant.now();
		long currentTimestamp = instant.toEpochMilli();

		String space = " ";					// one space
		String newLine = "\n";					// new line
		String method = "GET";					// method
		String url = "/sms/v2/services/ncp:sms:kr:314964386801:messagetest/messages";	// url (include query string)
		String timestamp = String.valueOf(currentTimestamp);			// current timestamp (epoch)
		String accessKey = "{accessKey}";			// access key id (from portal or Sub Account)
		String secretKey = "{secretKey}";

		String message = new StringBuilder()
				.append(method)
				.append(space)
				.append(url)
				.append(newLine)
				.append(timestamp)
				.append(newLine)
				.append(accessKey)
				.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

		System.out.println(encodeBase64String);

	}



	public static void main(String[] args) {

		SpringApplication.run(SendMsgTestCodeApplication.class, args)


		;
	}

}
