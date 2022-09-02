package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.dto.CommentDto;
import com.project.ugosdevblog.dto.QCommentDto;
import com.project.ugosdevblog.entity.Comment;
import com.project.ugosdevblog.entity.QComment;
import com.project.ugosdevblog.entity.QContent;
import com.project.ugosdevblog.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static com.project.ugosdevblog.entity.QComment.comment;
import static com.project.ugosdevblog.entity.QContent.*;
import static com.project.ugosdevblog.entity.QUser.user;


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
