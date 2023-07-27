package com.mutsa.mutsamarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProposal is a Querydsl query type for Proposal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProposal extends EntityPathBase<Proposal> {

    private static final long serialVersionUID = -1465621340L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProposal proposal = new QProposal("proposal");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    public final EnumPath<com.mutsa.mutsamarket.entity.enumtype.ProposalStatus> status = createEnum("status", com.mutsa.mutsamarket.entity.enumtype.ProposalStatus.class);

    public final NumberPath<Integer> suggestedPrice = createNumber("suggestedPrice", Integer.class);

    public final QUsers user;

    public QProposal(String variable) {
        this(Proposal.class, forVariable(variable), INITS);
    }

    public QProposal(Path<? extends Proposal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProposal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProposal(PathMetadata metadata, PathInits inits) {
        this(Proposal.class, metadata, inits);
    }

    public QProposal(Class<? extends Proposal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.user = inits.isInitialized("user") ? new QUsers(forProperty("user")) : null;
    }

}

