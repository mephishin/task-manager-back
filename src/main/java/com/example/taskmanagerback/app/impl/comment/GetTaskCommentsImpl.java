package com.example.taskmanagerback.app.impl.comment;

import com.example.taskmanagerback.adapter.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.comment.GetTaskComments;
import com.example.taskmanagerback.model.task.Task;
import com.example.taskmanagerback.model.task.TaskComment;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetTaskCommentsImpl implements GetTaskComments {
    TaskRepo taskRepo;

    @Override
    public List<TaskComment> execute(String taskKey) {
        return taskRepo.findById(taskKey)
                .map(Task::getTaskComments)
                .orElseGet(List::of);
    }
}
