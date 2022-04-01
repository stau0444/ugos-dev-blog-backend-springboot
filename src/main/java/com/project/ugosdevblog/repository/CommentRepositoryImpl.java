package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.dto.CommentDto;
import com.project.ugosdevblog.dto.QCommentDto;
import com.project.ugosdevblog.entity.Comment;
import com.project.ugosdevblog.entity.QComment;
import com.project.ugosdevblog.entity.QContent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.ugosdevblog.entity.QComment.comment;
import static com.project.ugosdevblog.entity.QContent.*;


@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentDto> getComment(Long contentId) {
//        List<Comment> results = queryFactory.selectFrom(comment)
//                .orderBy(comment.repliedCommentId.asc(), comment.createAt.asc())
//                .where(comment.content.contentId.eq(contentId))
//                .fetchResults().getResults();
        List<CommentDto> commentList = queryFactory.select(new QCommentDto(
                comment.id,
                comment.body,
                comment.repliedCommentId,
                comment.createAt,
                comment.user.username
        )).from(comment)
                .orderBy(comment.repliedCommentId.asc(), comment.createAt.asc())
                .where(comment.content.contentId.eq(contentId))
                .fetchResults().getResults();

        return commentList;
    }
}
