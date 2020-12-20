package ImageHoster.service;

import ImageHoster.model.Comment;
import ImageHoster.model.User;
import ImageHoster.repository.ImageRepository;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class CommentService This is a service class to user methods
 * @author Romil
 */
@Service
public class CommentService {

	@Autowired
    private ImageRepository imageRepository;

    /**
     * Method uploadComment
     * Method to upload Comment
     * @param comment
     */
    public void uploadComment(Comment comment) {
        imageRepository.uploadComment(comment);
    }
}
