package com.mutsa.mutsamarket.repository;

import com.mutsa.mutsamarket.entity.Comment;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.entity.enumtype.ItemStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueryRepositoryTest {

    @Autowired ItemQueryRepository repository;

    @Autowired CommentQueryRepository commentQueryRepository;

    @Autowired ItemRepository itemRepository;

    @Autowired UserRepository userRepository;

    @Autowired CommentRepository commentRepository;


    @Test
    void querydsl_동작확인() {
        Users user = Users.builder()
                .username("username")
                .password("passwordEncoder.encode(password)")
                .build();

        Item item = Item.builder()
                .user(user)
                .title("title")
                .description("description")
                .minPriceWanted(1000)
                .status(ItemStatus.SALE)
                .build();

        Comment comment = Comment.builder()
                .item(item)
                .user(user)
                .content("content")
                .build();

        userRepository.save(user);
        itemRepository.save(item);
        commentRepository.save(comment);

//        Item findItem = repository.findWithUser(1L);
//
//        System.out.println("findItem = " + findItem.getId());
//        System.out.println("findItem = " + findItem.getUser().getUsername());
//        System.out.println("findItem = " + findItem.getTitle());

        Comment findComment = commentQueryRepository.findWithItemUser(comment.getId());

        System.out.println("findComment.getContent() = " + findComment.getContent());
        System.out.println("findComment.getItem().getTitle() = " + findComment.getItem().getTitle());
        System.out.println("findComment.getUser().getUsername() = " + findComment.getUser().getUsername());
    }

}