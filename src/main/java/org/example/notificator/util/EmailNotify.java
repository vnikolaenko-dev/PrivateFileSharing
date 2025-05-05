package org.example.notificator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailNotify {

    private static final Logger log = LoggerFactory.getLogger(EmailNotify.class);

    public void notify(String data) {
        log.info("Отправлено письмо на адрес: {}", data);
    }
}

