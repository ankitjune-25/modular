package com.ankit.modular.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessagePublisher {

    @Autowired
    private StreamBridge streamBridge;

    public void publishMessage(Object message){
        try {
            streamBridge.send("accountInsert-out-0", message);
        } catch(Exception ex){
            log.error("Error in creating connection with publisher.");
        }
    }
}
