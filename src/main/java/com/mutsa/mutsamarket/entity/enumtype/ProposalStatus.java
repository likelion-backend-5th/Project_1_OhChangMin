package com.mutsa.mutsamarket.entity.enumtype;

public enum ProposalStatus {

    PROPOSAL("제안"),
    ACCEPT("수락"),
    REFUSE("거절"),
    CONFIRMED("확정");

    private final String value;

    ProposalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ProposalStatus fromValue(String value) {
        for (ProposalStatus status : ProposalStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("해당 value의 enum은 존재하지 않습니다.");
    }
}
