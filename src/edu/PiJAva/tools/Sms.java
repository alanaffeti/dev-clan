/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.tools;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Sms {
    public static void send(String numero,String contenu) {

        final String AUTH_TOKEN = "f873470ab92ef875fff21bfcf3062929";
        final String ACCOUNT_SID = "AC194835b5383b773145c44de5cfac7079";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber(numero), // TO
                new PhoneNumber("+16203839539"), // FROM
                contenu
        ).create();

        System.out.println(message.getSid());
    }

 
}