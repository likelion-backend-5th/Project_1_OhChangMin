package com.mutsa.mutsamarket.api.response;

public interface ResponseMessageConst {
    String LOGIN_SUCCESS = "로그인이 완료되었습니다.";
    String SIGNUP_SUCCESS = "회원가입이 완료되었습니다.";

    String ITEM_CREATE = "등록이 완료되었습니다.";
    String ITEM_UPDATE = "물품이 수정되었습니다.";
    String ADD_IMAGE = "이미지가 등록되었습니다.";
    String ITEM_DELETE = "물품을 삭제했습니다.";

    String COMMENT_CREATE = "댓글이 등록되었습니다.";
    String COMMENT_UPDATE = "댓글이 수정되었습니다.";
    String REPLY_ADD = "댓글에 답변이 추가되었습니다.";
    String COMMENT_DELETE = "댓글을 삭제했습니다.";

    String PROPOSAL_CREATE = "구매 제안이 등록되었습니다.";
    String PROPOSAL_UPDATE = "제안이 수정되었습니다.";
    String PROPOSAL_DELETE = "제안을 삭제했습니다.";
    String PROPOSAL_RESPONSE = "제안의 상태가 변경되었습니다.";
    String PROPOSAL_CONFIRM = "구매가 확정되었습니다.";
    String NOT_CORRECT_STATUS = "올바른 Status 요청이 아닙니다.";

}
