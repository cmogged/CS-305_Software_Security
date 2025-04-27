package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}


@RestController
class ServerController{
   
   // convert the byte array to a hexadecimal string
   public static String bytesToHex(byte[] bytes)
   {
      StringBuilder hexString = new StringBuilder();
      
      for (byte b : bytes)
      {
         String hex = String.format("%02x", b & 0xFF);
         hexString.append(hex);
      }

      return hexString.toString();
   }
   
   // hash function to return the checksum value for the string   
   public static String hashString(String input)
   {
      try{
         // create a MessageDigest instance for SHA-256
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         
         // perform the hashing
         byte[] hashBytes = digest.digest(input.getBytes());
         
         // convert to hex
         String hexString = bytesToHex(hashBytes);
         
         return hexString.toString();
         
         } catch (NoSuchAlgorithmException e) {
         throw new RuntimeException(e);
      }
   }

    @RequestMapping("/hash")
    public String myHash(){
      String data = "Hello World Check Sum!";
       
       String hashedOutput = hashString(data);
       
        return "<p>data:"+data+"<BR>\n<BR>\nSHA-256: Checksum Value:"+hashedOutput;
    }
}
