package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.Comment;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.repository.comment.CommentRepository;
import com.mutsa.mutsamarket.repository.item.ItemRepository;
import com.mutsa.mutsamarket.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mutsa.mutsamarket.entity.Comment.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public void addComment(Long itemId, String username, String content) {
        Item item = itemRepository.getItemById(itemId);
        Users user = userRepository.getUserByUsername(username);

        commentRepository.save(createComment(item, user, content));
    }

    @Transactional(readOnly = true)
    public Page<Comment> findComments(Long itemId, Integer page, Integer limit) {
        Item item = itemRepository.getItemById(itemId);

        return commentRepository.findByItemWithUser(item, PageRequest.of(page - 1, limit));
    }

    public void modify(Long commentId, String username, String content) {
        Comment comment = commentRepository.getWithUser(commentId);

        comment.change(username, content);
    }

    public void addReply(Long commentId, String username, String reply) {
        Comment comment = commentRepository.getWithItem(commentId);

        comment.addReply(username, reply);
    }

    public void delete(Long commentId, String username) {
        Comment comment = commentRepository.getWithUser(commentId);

        comment.checkUser(username);
        commentRepository.delete(comment);
    }
}
