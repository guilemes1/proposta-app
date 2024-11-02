package com.pieropan.propostaapp.service;

import com.pieropan.propostaapp.entity.Proposta;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoRabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void notificar(Proposta proposta, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", proposta);
    }

    public void notificar(Proposta proposta, String exchange, MessagePostProcessor messagePostProcessor) {
        rabbitTemplate.convertAndSend(exchange, "", proposta, messagePostProcessor);
    }


}
