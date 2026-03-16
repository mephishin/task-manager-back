package com.example.taskmanagerback.adapter.repository.task;

import com.example.taskmanagerback.model.participant.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepo extends JpaRepository<Participant, String> {
    Optional<Participant> findByUsername(String username);
    List<Participant> findAllByUsernameIn(List<String> usernames);
}
