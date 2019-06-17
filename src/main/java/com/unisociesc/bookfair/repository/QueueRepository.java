package com.unisociesc.bookfair.repository;

import com.unisociesc.bookfair.domain.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {

    public Queue findByNameSchool(String nameSchool);

    public List<Queue> findAllByAttendantIsNullOrderById();

    public Queue findByAttendant(String attendant);
}
