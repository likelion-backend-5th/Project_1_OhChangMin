package com.mutsa.mutsamarket.repository.comment;

import com.mutsa.mutsamarket.entity.Comment;

public interface CommentQueryRepository {

    Comment getWithUser(Long id);

    Comment getWithItem(Long id);
}
