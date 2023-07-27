package com.mutsa.mutsamarket.api;

import com.mutsa.mutsamarket.api.request.CommentCreate;
import com.mutsa.mutsamarket.api.request.ReplyCreate;
import com.mutsa.mutsamarket.api.response.CommentResponse;
import com.mutsa.mutsamarket.api.response.Response;
import com.mutsa.mutsamarket.security.AuthorizedUserGetter;
import com.mutsa.mutsamarket.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/items/{itemId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Response create(@PathVariable Long itemId,
                           @Valid @RequestBody CommentCreate request) {
        String username = AuthorizedUserGetter.getUsername();
        commentService.addComment(itemId, username, request.getContent());
        return new Response("댓글이 등록되었습니다.");
    }

    @GetMapping
    public Page<CommentResponse> page(@PathVariable Long itemId,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "25") Integer limit) {
        return commentService.findComments(itemId, page, limit)
                .map(CommentResponse::fromEntity);
    }

    @PutMapping("{commentId}")
    public Response update(@PathVariable Long itemId,
                           @PathVariable Long commentId,
                           @Valid @RequestBody CommentCreate request) {
        String username = AuthorizedUserGetter.getUsername();
        commentService.modify(itemId, commentId, username, request.getContent());
        return new Response("댓글이 수정되었습니다.");
    }

    @PutMapping("{commentId}/reply")
    public Response addReply(@PathVariable Long itemId,
                             @PathVariable Long commentId,
                             @Valid @RequestBody ReplyCreate request) {
        String username = AuthorizedUserGetter.getUsername();
        commentService.addReply(itemId, commentId, username, request.getReply());
        return new Response("댓글에 답변이 추가되었습니다.");
    }

    @DeleteMapping("{commentId}")
    public Response delete(@PathVariable Long itemId,
                           @PathVariable Long commentId) {
        String username = AuthorizedUserGetter.getUsername();
        commentService.delete(itemId, commentId, username);
        return new Response("댓글을 삭제했습니다.");
    }
}
