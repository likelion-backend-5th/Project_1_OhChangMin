package com.mutsa.mutsamarket.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutsa.mutsamarket.api.request.CommentCreate;
import com.mutsa.mutsamarket.api.request.ReplyCreate;
import com.mutsa.mutsamarket.entity.Comment;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.repository.CommentRepository;
import com.mutsa.mutsamarket.repository.ItemRepository;
import com.mutsa.mutsamarket.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.mutsa.mutsamarket.api.response.ResponseMessageConst.*;
import static com.mutsa.mutsamarket.util.EntityGetter.*;
import static com.mutsa.mutsamarket.util.EntityGetter.getComment;
import static com.mutsa.mutsamarket.util.LoginUtil.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentRestControllerTest {

    @Autowired MockMvc mockMvc;

    @Autowired ItemRepository itemRepository;

    @Autowired UserRepository userRepository;

    @Autowired CommentRepository commentRepository;

    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("댓글 등록 성공")
    void test1() throws Exception {

        String username = "lou0124";
        String password = "1234";
        Users user = getUser(username, password);
        Item item = getItem(user, "title", "description", 100000);
        userRepository.save(user);
        itemRepository.save(item);

        String content = "할인 가능하신가요?";
        CommentCreate commentCreate = new CommentCreate(content);
        String token = loginAndGetJwtToken(mockMvc, username, password);

        mockMvc.perform(post("/api/items/"+ item.getId() +"/comments")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentCreate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(COMMENT_CREATE))
                .andDo(print());

        List<Comment> all = commentRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        Comment comment = all.get(0);
        assertThat(comment.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("댓글 페이징 조회 성공")
    void test2() throws Exception {

        String username = "lou0124";
        String password = "1234";
        Users user = getUser(username, password);
        Item item = getItem(user, "title", "description", 100000);
        userRepository.save(user);
        itemRepository.save(item);

        Users user2 = getUser(username + 2, password);
        userRepository.save(user2);
        for (int i = 0; i < 30; i++) {
            Comment comment = getComment(item, user2, "content" + i);
            commentRepository.save(comment);
        }

        int limit = 25;

        mockMvc.perform(get("/api/items/"+ item.getId() +"/comments")
                        .param("page", "1")
                        .param("limit", String.valueOf(25)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(limit))
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void test3() throws Exception {

        String username = "lou0124";
        String password = "1234";
        Users user = getUser(username, password);
        Item item = getItem(user, "title", "description", 100000);
        userRepository.save(user);
        itemRepository.save(item);

        String commentUsername = "commentUsername";
        Users user2 = getUser(commentUsername, password);
        userRepository.save(user2);

        Comment comment = getComment(item, user2, "content");
        commentRepository.save(comment);

        String newContent = "new content";
        CommentCreate commentCreate = new CommentCreate(newContent);
        String token = loginAndGetJwtToken(mockMvc, commentUsername, password);

        mockMvc.perform(put("/api/items/"+ item.getId() +"/comments/" + comment.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentCreate)))
                .andExpect(jsonPath("$.message").value(COMMENT_UPDATE))
                .andDo(print());

        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertThat(findComment.getContent()).isEqualTo(newContent);
    }

    @Test
    @DisplayName("답변 추가 성공")
    void test4() throws Exception {

        String username = "lou0124";
        String password = "1234";
        Users user = getUser(username, password);
        Item item = getItem(user, "title", "description", 100000);
        userRepository.save(user);
        itemRepository.save(item);

        String commentUsername = "commentUsername";
        Users user2 = getUser(commentUsername, password);
        userRepository.save(user2);

        Comment comment = getComment(item, user2, "content");
        commentRepository.save(comment);

        String reply = "안 됩니다.";
        ReplyCreate replyCreate = new ReplyCreate(reply);
        String token = loginAndGetJwtToken(mockMvc, username, password);

        assertThat(comment.getReply()).isNullOrEmpty();

        mockMvc.perform(put("/api/items/"+ item.getId() +"/comments/" + comment.getId() + "/reply")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(replyCreate)))
                .andExpect(jsonPath("$.message").value(REPLY_ADD))
                .andDo(print());

        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertThat(findComment.getReply()).isEqualTo(reply);
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void test5() throws Exception {

        String username = "lou0124";
        String password = "1234";
        Users user = getUser(username, password);
        Item item = getItem(user, "title", "description", 100000);
        userRepository.save(user);
        itemRepository.save(item);

        String commentUsername = "commentUsername";
        Users user2 = getUser(commentUsername, password);
        userRepository.save(user2);

        Comment comment = getComment(item, user2, "content");
        commentRepository.save(comment);

        String token = loginAndGetJwtToken(mockMvc, commentUsername, password);

        assertThat(comment.getReply()).isNullOrEmpty();

        mockMvc.perform(delete("/api/items/"+ item.getId() +"/comments/" + comment.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(COMMENT_DELETE))
                .andDo(print());

        assertThatThrownBy(() -> commentRepository.findById(comment.getId()).get())
                .isInstanceOf(NoSuchElementException.class);
    }
}