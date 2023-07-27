package com.mutsa.mutsamarket.api;

import com.mutsa.mutsamarket.api.request.CommentCreate;
import com.mutsa.mutsamarket.api.request.ReplyCreate;
import com.mutsa.mutsamarket.api.response.CommentResponse;
import com.mutsa.mutsamarket.api.response.Response;
import com.mutsa.mutsamarket.api.response.ResponseMessageConst;
import com.mutsa.mutsamarket.security.AuthorizedUserGetter;
import com.mutsa.mutsamarket.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static com.mutsa.mutsamarket.api.response.ResponseMessageConst.*;

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
    public Response update(@PathVariable Long itemId,
                           @PathVariable Long commentId,
                           @Valid @RequestBody CommentCreate request) {
        String username = AuthorizedUserGetter.getUsername();
        commentService.modify(itemId, commentId, username, request.getContent());
        return new Response(COMMENT_UPDATE);
    }

    @PutMapping("{commentId}/reply")
    public Response addReply(@PathVariable Long itemId,
                             @PathVariable Long commentId,
                             @Valid @RequestBody ReplyCreate request) {
        String username = AuthorizedUserGetter.getUsername();
        commentService.addReply(itemId, commentId, username, request.getReply());
        return new Response(REPLY_ADD);
    }

    @DeleteMapping("{commentId}")
    public Response delete(@PathVariable Long itemId,
                           @PathVariable Long commentId) {
        String username = AuthorizedUserGetter.getUsername();
        commentService.delete(itemId, commentId, username);
        return new Response(COMMENT_DELETE);
    }
}
