package com.springweb.models.projections;

import com.springweb.models.entities.User;
import org.springframework.data.rest.core.config.Projection;
import com.springweb.models.entities.Comment;


@Projection(name = "inlineComment", types = { Comment.class })
public interface CommentProjection {

    Integer getId();

    String getComment();

    User getUser();
}
