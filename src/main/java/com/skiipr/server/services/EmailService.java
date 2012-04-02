/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.services;



import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;


import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import java.util.Collections;

import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Michael
 */
@Service("EmailService") 
public class EmailService {
    
    public static final String ACCESS_KEY = "AKIAIEUXQ7HCYQPYPN2Q";
    public static final String SECRET_KEY = "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";
    
//    public EmailService() {
//        String sender = "noreply@skiipr.com"; // should be verified email
//        LinkedList<String> recipients = new LinkedList<String>();
//        recipients.add("shrapnelbikes@gmail.com"); // again a verified email, if you are in sandbox
//        SendMail(sender, recipients, "Straight from AWS SES", "Hey, did you know that this message was sent via Simple Email Service programmatically using AWS Java SDK.");
//	}
 
	public static void SendMail(String sender, LinkedList<String> recipients, String subject, String body) {
		Destination destination = new Destination(recipients);
 
		Content subjectContent = new Content(subject);
		Content bodyContent = new Content(body);
		Body msgBody = new Body(bodyContent);
		Message msg = new Message(subjectContent, msgBody);
 
		SendEmailRequest request = new SendEmailRequest(sender, destination, msg);
 
		AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonSimpleEmailServiceClient sesClient = new AmazonSimpleEmailServiceClient(credentials);
		SendEmailResult result = sesClient.sendEmail(request);
		
		System.out.println(result);
	}

    
}
