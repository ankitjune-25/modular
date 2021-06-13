package com.ankit.modular.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
@Slf4j
public class MessageSupplier {

    @Bean
    public Consumer<Object> consumeMessage() {
        return o -> log.info("message consume {}",o);
    }

}
