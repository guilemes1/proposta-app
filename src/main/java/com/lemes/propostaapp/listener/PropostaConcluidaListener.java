package com.lemes.propostaapp.listener;

import com.lemes.propostaapp.dto.PropostaResponseDto;
import com.lemes.propostaapp.entity.Proposta;
import com.lemes.propostaapp.mapper.PropostaMapper;
import com.lemes.propostaapp.repository.PropostaRepository;
import com.lemes.propostaapp.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private WebSocketService webSocketService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta proposta) {
        atualizarProposta(proposta);
        PropostaResponseDto propostaResponseDto = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        webSocketService.notificar(propostaResponseDto);
    }

    public void atualizarProposta(Proposta proposta) {
        propostaRepository.atualizarProposta(proposta.getId(), proposta.getAprovada(), proposta.getObservacao());
    }
}
