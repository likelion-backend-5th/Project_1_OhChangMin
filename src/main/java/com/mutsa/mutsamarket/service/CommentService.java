package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.Comment;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.repository.CommentQueryRepository;
import com.mutsa.mutsamarket.repository.CommentRepository;
import com.mutsa.mutsamarket.repository.ItemRepository;
import com.mutsa.mutsamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;

    public void addComment(Long itemId, String username, String content) {
        Item item = itemRepository.getById(itemId);
        Users user = userRepository.getByUsername(username);

        Comment comment = Comment.createComment(item, user, content);
        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findComments(Long itemId, Integer page, Integer limit) {
        Item item = itemRepository.getById(itemId);

        return commentRepository.findByItem(item, PageRequest.of(page - 1, limit));
    }

    public void modify(Long itemId, Long commentId, String username, String content) {
        Item item = itemRepository.getById(itemId);
        Comment comment = commentQueryRepository.findWithItemUser(commentId, item);
        Users user = userRepository.getByUsername(username);

        comment.change(user, content);
    }

    public void addReply(Long itemId, Long commentId, String username, String reply) {
        Item item = itemRepository.getById(itemId);
        Comment comment = commentQueryRepository.findWithItemUser(commentId, item);
        Users user = userRepository.getByUsername(username);

        comment.addReply(user, reply);
    }

    public void delete(Long itemId, Long commentId, String username) {
        Item item = itemRepository.getById(itemId);
        Comment comment = commentQueryRepository.findWithItemUser(commentId, item);
        Users user = userRepository.getByUsername(username);

        comment.checkUser(user);
        commentRepository.delete(comment);
    }
}
