package com.threadjava.postReactions;

import com.threadjava.postReactions.dto.ReceivedPostReactionDto;
import com.threadjava.postReactions.dto.ResponsePostReactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import static com.threadjava.auth.TokenService.getUserId;

@RestController
@RequestMapping("/api/postreaction")
public class PostReactionController {
    @Autowired
    private PostReactionService postsService;
    @Autowired
    private SimpMessagingTemplate template;

    @PutMapping
    public Optional<ResponsePostReactionDto> setReaction(@RequestBody ReceivedPostReactionDto postReaction){
        postReaction.setUserId(getUserId());
        var reaction = postsService.setReaction(postReaction);

        if (reaction.isPresent() && reaction.get().getUserId() != getUserId() && reaction.get().getIsLike() == postReaction.getIsLike()) {
            if(reaction.get().getIsLike()) {
                template.convertAndSend("/topic/like", "Your post was liked!");
            } else {
                template.convertAndSend("/topic/dislike", "Your post was disliked :(");
            }
        }
        return reaction;
    }
}
