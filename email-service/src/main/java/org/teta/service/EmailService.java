package org.teta.service;


import org.teta.event.SendEmailEvent;

public interface EmailService {
    void sendEmail(SendEmailEvent emailEvent);
}
