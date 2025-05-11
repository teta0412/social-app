package org.teta.service;


import event.SendEmailEvent;

public interface EmailService {
    void sendEmail(SendEmailEvent emailEvent);
}
