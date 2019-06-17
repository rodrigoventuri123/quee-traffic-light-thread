package com.unisociesc.bookfair.service;


import com.unisociesc.bookfair.domain.*;
import com.unisociesc.bookfair.domain.enumeration.Status;
import com.unisociesc.bookfair.dto.ResultGenreDTO;
import com.unisociesc.bookfair.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessService {

    private List<ResultGenreDTO> resultGenreDTOList = new ArrayList<>();

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private QueueService queueService;

    public void process() {
        while (this.queueRepository.count() != 0) {
            if(this.validAttendent1()) {
                this.attendant1();
            }
            if(this.validAttendent2()) {
                this.attendant2();
            }
        }

    }

    private Boolean validAttendent1() {
        Queue attendant = this.queueRepository.findByAttendant("attendent1");
        if(attendant == null) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean validAttendent2() {
        Queue attendant = this.queueRepository.findByAttendant("attendent2");
        if(attendant == null) {
            return true;
        } else {
            return false;
        }
    }

    public School getSchoolByName(String name) {
        for (School school : StaticData.SCHOOLS) {
            if (school.getName().equalsIgnoreCase(name)) {
                return school;
            }
        }
        return null;
    }

    @Async("attendant1")
    public void attendant1() {
        List<Queue> queueList = this.queueRepository.findAllByAttendantIsNullOrderById();
        if(queueList.size() > 0) {
            Queue queue = queueList.get(0);
            this.queueService.updateAttendant(queue.getId(), "attendant1");
            School school = this.getSchoolByName(queue.getNameSchool());
            System.out.println("Processando atendente 01" + school.getName());
            this.processSchool(school);
            this.queueRepository.delete(queue);
        }
    }

    @Async("attendant2")
    public void attendant2() {
        List<Queue> queueList = this.queueRepository.findAllByAttendantIsNullOrderById();
        if(queueList.size() > 0) {
            Queue queue = queueList.get(0);
            this.queueService.updateAttendant(queue.getId(), "attendant2");
            School school = this.getSchoolByName(queue.getNameSchool());
            System.out.println("Processando atendente 02" + school.getName());
            this.processSchool(school);
            this.queueRepository.delete(queue);
        }
    }

    private void processSchool(School school) {
        ResultGenreDTO resultGenreDTO = new ResultGenreDTO();
        for (Request request : school.getRequests()) {
            for (Genre genre : StaticData.GENRES) {
                if (request.getIdGenre().equals(genre.getId())) {
                    resultGenreDTO.setAmountRequest(request.getAmount());
                    resultGenreDTO.setNameGenre(genre.getName());
                    resultGenreDTO.setNameSchool(school.getName());
                    resultGenreDTO.setStatus(Status.PROCESSED);
                    resultGenreDTO.setPriceRequest(request.getAmount() * genre.getPrice());
                    resultGenreDTOList.add(resultGenreDTO);
                }
            }
        }

        //printResult();
    }

    private void printResult() {
        for (ResultGenreDTO resultGenreDTO : resultGenreDTOList) {
            System.out.println("");
            System.out.println("Status do pedido: " + resultGenreDTO.getStatus());
            System.out.println("Nome da Escola que fez o pedido: " + resultGenreDTO.getNameSchool());
            System.out.println("Nome do Gênero do pedido: " + resultGenreDTO.getNameGenre());
            System.out.println("Quantidade de livros do pedids: " + resultGenreDTO.getAmountRequest());
            System.out.println("Preço total do pedido: " + resultGenreDTO.getPriceRequest());
            System.out.println("");

        }
    }


}
