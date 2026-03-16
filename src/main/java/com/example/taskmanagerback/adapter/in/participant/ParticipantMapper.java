package com.example.taskmanagerback.adapter.in.participant;

import com.example.taskmanagerback.adapter.in.participant.dto.ParticipantDto;
import com.example.taskmanagerback.model.participant.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ParticipantMapper {
    @Mapping(target="project", source = "participant.project.name")
    public abstract ParticipantDto participantToParticipantDto(Participant participant);
}
