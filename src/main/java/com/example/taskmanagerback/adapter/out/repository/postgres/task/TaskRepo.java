package com.example.taskmanagerback.adapter.out.repository.postgres.task;

import com.example.taskmanagerback.model.project.Project;
import com.example.taskmanagerback.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, String> {
    List<Task> findAllByProject(Project project);

    @Query(value =
            "SELECT MAX(CAST(SUBSTRING(t.key, LOCATE('-', t.key) + 1) AS long)) "
                    + "FROM Task t "
                    + "WHERE t.key LIKE CONCAT('%', :taskPrefix, '%')")
    Optional<Long> getCurrentTaskNumberByTaskPrefix(@Param("taskPrefix") String taskPrefix);
}
