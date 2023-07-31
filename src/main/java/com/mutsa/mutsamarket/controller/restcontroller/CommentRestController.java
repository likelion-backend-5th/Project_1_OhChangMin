package com.mutsa.mutsamarket.controller.restcontroller;

import com.mutsa.mutsamarket.controller.request.CommentCreate;
import com.mutsa.mutsamarket.controller.request.ReplyCreate;
import com.mutsa.mutsamarket.controller.response.CommentResponse;
import com.mutsa.mutsamarket.controller.response.Response;
import com.mutsa.mutsamarket.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.mutsa.mutsamarket.controller.response.ResponseMessageConst.*;

@Slf4j
@RestController
@RequestMapping("/api/items/{itemId}/comments")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping
    public Response create(@PathVariable Long itemId,
                           @Valid @RequestBody CommentCreate request,
                           Authentication auth) {
        commentService.addComment(itemId, auth.getName(), request.getContent());
        return new Response(COMMENT_CREATE);
    }

    @GetMapping
    public Page<CommentResponse> page(@PathVariable Long itemId,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "25") Integer limit) {
        return commentService.findComments(itemId, page, limit)
                .map(CommentResponse::fromEntity);
    }

    @PutMapping("{commentId}")
    public Response update(@PathVariable Long commentId,
                           @Valid @RequestBody CommentCreate request,
                           Authentication auth) {
        commentService.modify(commentId, auth.getName(), request.getContent());
        return new Response(COMMENT_UPDATE);
    }

    @PutMapping("{commentId}/reply")
    public Response addReply(@PathVariable Long commentId,
                             @Valid @RequestBody ReplyCreate request,
                             Authentication auth) {
        commentService.addReply(commentId, auth.getName(), request.getReply());
        return new Response(REPLY_ADD);
    }

    @DeleteMapping("{commentId}")
    public Response delete(@PathVariable Long commentId,
                           Authentication auth) {
        commentService.delete(commentId, auth.getName());
        return new Response(COMMENT_DELETE);
    }
}
