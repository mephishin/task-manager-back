package com.example.taskmanagerback.app.impl.comment;

import com.example.taskmanagerback.adapter.out.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.in.comment.GetTaskComments;
import com.example.taskmanagerback.app.api.out.postgres.UsersRepo;
import com.example.taskmanagerback.model.task.Task;
import com.example.taskmanagerback.model.task.TaskComment;
import com.example.taskmanagerback.model.users.Users;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetTaskCommentsImpl implements GetTaskComments {
    TaskRepo taskRepo;
    UsersRepo usersRepo;

    @Override
    @Transactional
    public List<TaskComment> execute(String taskKey) {
        var comments = taskRepo.findById(taskKey)
                .map(Task::getTaskComments)
                .orElseGet(List::of);
        var users = usersRepo.findAllById(comments.stream()
                .map(TaskComment::getAuthor)
                .map(Users::getId).toList())
                .stream()
                .collect(Collectors.toMap(
                        Users::getId,
                        user -> user
                ));

        return comments.stream()
                .peek(comment -> comment.setAuthor(users.get(comment.getAuthor().getId())))
                .toList();
    }
}
