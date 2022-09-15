package com.project.ugosdevblog.core.content.domain;

import com.project.ugosdevblog.web.dto.commnet.CommentDto;
import com.project.ugosdevblog.web.dto.commnet.QCommentDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.ugosdevblog.core.content.domain.QComment.comment;
import static com.project.ugosdevblog.core.user.domain.QUser.user;


@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentDto> getComment(Long contentId) {

        List<CommentDto> commentList = queryFactory.select(new QCommentDto(
                comment.id,
                comment.body,
                comment.repliedCommentId,
                comment.createAt,
                user.username,
                user.profileUrl,
                comment.replyTo
        )).from(comment)
                .join(user)
                .on(comment.user.id.eq(user.id))
                .orderBy(comment.repliedCommentId.asc(), comment.createAt.asc())
                .where(comment.content.contentId.eq(contentId))
                .fetchResults().getResults();


        return commentList;
    }
}
