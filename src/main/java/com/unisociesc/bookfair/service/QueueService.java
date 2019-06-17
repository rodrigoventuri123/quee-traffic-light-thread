package com.unisociesc.bookfair.service;

import com.unisociesc.bookfair.domain.Queue;
import com.unisociesc.bookfair.domain.School;
import com.unisociesc.bookfair.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

    public void addQueue(School school) {
        Queue queue = new Queue();
        queue.setNameSchool(school.getName());
        this.queueRepository.save(queue);
    }

    public Queue getNextQueue() {
        return this.queueRepository.findAllByAttendantIsNullOrderById().get(0);
    }

    public void updateAttendant(Long id, String attendant) {
        Optional<Queue> queue = this.queueRepository.findById(id);
        queue.get().setAttendant(attendant);
        this.queueRepository.save(queue.get());
    }
}
