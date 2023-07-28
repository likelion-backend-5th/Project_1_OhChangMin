package com.mutsa.mutsamarket.api.response;

import com.mutsa.mutsamarket.entity.Comment;
import lombok.Data;

@Data
public class CommentResponse {

    private Long id;
    private String username;
    private String content;
    private String reply;

    public static CommentResponse fromEntity(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setUsername(comment.getUser().getUsername());
        commentResponse.setContent(comment.getContent());
        commentResponse.setReply(comment.getReply());
        return commentResponse;
    }
}