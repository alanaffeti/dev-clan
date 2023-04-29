/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.tools;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
/**
 *
 * @author Skymil
 */
public class SmsAPI {
    public static void send(String numero,String contenu) {

        final String AUTH_TOKEN = "b2a2cb44a9904b77db3df1665916cc59";
        final String ACCOUNT_SID = "AC4e4fedbf3c5b6e032a7fcdf83b2a7ba5";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber(numero), // TO
                new PhoneNumber("+15673612899"), // FROM
                contenu
        ).create();

        System.out.println(message.getSid());
    }

 
}
