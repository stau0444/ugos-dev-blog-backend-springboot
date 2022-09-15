package com.project.ugosdevblog.core.content.application;

import com.project.ugosdevblog.core.content.domain.Comment;
import com.project.ugosdevblog.core.content.domain.CommentRepository;
import com.project.ugosdevblog.core.content.domain.Content;
import com.project.ugosdevblog.core.content.domain.ContentRepository;
import com.project.ugosdevblog.core.user.domain.User;
import com.project.ugosdevblog.core.user.domain.UserRepository;
import com.project.ugosdevblog.web.dto.commnet.CommentDto;
import com.project.ugosdevblog.web.dto.commnet.CommentReq;
import com.project.ugosdevblog.web.dto.commnet.CommentResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentManager implements CommentEditor,CommentFinder {

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public Long getNextVal() {
        return commentRepository.getNextVal();
    }

    @Transactional
    public void addComment(Long id, CommentReq commentReq) {
        Long nextVal = commentRepository.getNextVal();
        Optional<Content> byId = contentRepository.findById(id);
        Content content = byId.orElseThrow(RuntimeException::new);
        User user =  userRepository.findById(commentReq.getUserId()).orElseThrow(RuntimeException::new);
        Comment comment = null;
        if(commentReq.getRepliedCommentId()==null){
            comment = Comment.builder()
                    .id(nextVal)
                    .content(content)
                    .body(commentReq.getBody())
                    .repliedCommentId(nextVal)
                    .createAt(LocalDateTime.now())
                    .user(user)
                    .build();
        }else{
            Optional<Comment> isReplyTo = commentRepository.findById(commentReq.getRepliedCommentId());
            Comment repliedComment = isReplyTo.orElseThrow(RuntimeException::new);
            String replyTo = repliedComment.getUser().getUsername();
            Long repliedCommentId = repliedComment.getRepliedCommentId();
            comment = Comment.builder()
                    .id(nextVal)
                    .content(content)
                    .body(commentReq.getBody())
                    .repliedCommentId(repliedCommentId)
                    .replyTo(replyTo)
                    .createAt(LocalDateTime.now())
                    .user(user)
                    .build();
        }
        commentRepository.save(comment);
    }

//    @Transactional(readOnly = true)
    public List<CommentResp> getComments(Long id) {
        List<CommentDto> commentList = commentRepository.getComment(id);
        return commentList.stream().map((c) ->
                new CommentResp(
                        c.getCommentId(),
                        c.getBody(),
                        c.getRepliedCommentId(),
                        c.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 hh:mm:ss")),
                        c.getReplyTo(),
                        c.getUserName(),
                        c.getProfileUrl())
        ).collect(Collectors.toList());
    }
}
