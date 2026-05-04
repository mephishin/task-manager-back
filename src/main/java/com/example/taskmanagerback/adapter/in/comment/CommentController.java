package com.example.taskmanagerback.adapter.in.comment;

import com.example.taskmanagerback.adapter.in.comment.dto.CommentDto;
import com.example.taskmanagerback.app.api.in.comment.*;
import com.example.taskmanagerback.model.task.TaskComment;
import com.example.taskmanagerback.util.ZipUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.util.Objects.nonNull;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("isAuthenticated()")
public class CommentController {
    SaveComment saveComment;
    CommentMapper commentMapper;
    GetTaskComments getTaskComments;
    GetAllCommentsFiles getAllCommentsFiles;
    DeleteCommentFile deleteCommentFile;
    DeleteComment deleteComment;
    UpdateComment updateComment;

    @PostMapping("task/{taskKey}/comment")
    public void saveComment(
            @PathVariable String taskKey,
            @RequestParam("text") String text,
            @RequestParam(value = "zippedFiles", required = false) MultipartFile zippedFiles,
            Principal principal
    ) throws IOException {
        log.info("Request to save task comment");

        saveComment.execute(
                taskKey,
                text,
                principal.getName(),
                (nonNull(zippedFiles) && !zippedFiles.isEmpty()) ? ZipUtils.unzip(zippedFiles) : null
        );
    }

    @PatchMapping("/comment/{commentId}")
    public void updateComment(
            @PathVariable String commentId,
            @RequestParam("text") String text,
            @RequestParam(value = "zippedFiles", required = false) MultipartFile zippedFiles,
            Principal principal
    ) throws IOException {
        log.info("Request to update task comment");

        updateComment.execute(
                commentId,
                text,
                principal.getName(),
                (nonNull(zippedFiles) && !zippedFiles.isEmpty()) ? ZipUtils.unzip(zippedFiles) : null
        );
    }

    @DeleteMapping("comment/{commentId}/file/{filename}")
    public void deleteCommentFile(@PathVariable String commentId, @PathVariable String filename) {
        log.info("Request to delete comment file");
        deleteCommentFile.execute(commentId, filename);
    }

    @DeleteMapping("comment/{commentId}")
    public void deleteCommentFile(@PathVariable String commentId) {
        log.info("Request to delete comment");
        deleteComment.execute(commentId);
    }

    @GetMapping("task/{taskKey}/comment")
    public List<CommentDto> getComments(@PathVariable String taskKey) {
        log.info("Request to get task comments");
        return commentMapper.commentListToCommentDtoList(
                getTaskComments.execute(taskKey).stream()
                        .sorted(Comparator.comparing(TaskComment::getCreated)).toList());
    }

    @GetMapping("/comment/file")
    public void getAllCommentFiles(
            @RequestParam List<String> commentIds,
            HttpServletResponse response
    ) {
        log.info("Requested all comment files");
        var mapOfCommentsFiles = getAllCommentsFiles.execute(commentIds);

        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            for (var entry : mapOfCommentsFiles.entrySet()) {
                for (var file : entry.getValue()) {
                    ZipEntry zipEntry = new ZipEntry(entry.getKey() + file.name());
                    zipEntry.setSize(file.bytes().length);
                    zipOut.putNextEntry(zipEntry);
                    StreamUtils.copy(new ByteArrayInputStream(file.bytes()), zipOut);
                    zipOut.closeEntry();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
