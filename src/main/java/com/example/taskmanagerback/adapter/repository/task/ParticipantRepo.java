package com.example.taskmanagerback.adapter.repository.task;

import com.example.taskmanagerback.model.participant.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepo extends JpaRepository<Participant, String> {
    Optional<Participant> findByFullname(String fullname);
}
