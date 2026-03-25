package com.example.taskmanagerback.adapter.in.comment;

import com.example.taskmanagerback.adapter.in.comment.dto.CommentDto;
import com.example.taskmanagerback.adapter.in.comment.dto.SaveCommentDto;
import com.example.taskmanagerback.app.api.comment.GetAllCommentsFiles;
import com.example.taskmanagerback.app.api.comment.GetTaskComments;
import com.example.taskmanagerback.app.api.comment.SaveComment;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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


    @PostMapping("task/{taskKey}/comment")
    public void saveComment(
            @PathVariable String taskKey,
            @RequestBody SaveCommentDto saveCommentDto,
            @RequestParam("commentfiles") MultipartFile zippedComments
    ) {
        log.info("Request to save task comment {}", saveCommentDto);
        saveComment.execute(taskKey,commentMapper.commentDtoToComment(saveCommentDto));
    }

    @GetMapping("task/{taskKey}/comment")
    public List<CommentDto> getComments(@PathVariable String taskKey) {
        log.info("Request to get task comments");
        return commentMapper.commentListToCommentDtoList(getTaskComments.execute(taskKey));
    }

    @GetMapping("/comment/file")
    public void getAllCommentFiles(
            @RequestParam List<String> commentIds,
            HttpServletResponse response
    ) {
        log.info("Requested all comment files");
        var mapOfCommentsFiles = getAllCommentsFiles.execute(commentIds);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"files.zip\"");

        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            for (var entry : mapOfCommentsFiles.entrySet()) {
                for (var file: entry.getValue()) {
                    ZipEntry zipEntry = new ZipEntry(entry.getKey() + "_" + file.name());
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
