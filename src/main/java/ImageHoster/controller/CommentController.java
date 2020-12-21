package ImageHoster.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;

/**
 * Class CommentController This is a controller class to map comment methods
 * 
 * @author Romil
 */
@Controller
public class CommentController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private CommentService commentService;

	/**
	 * Method updatedComment This method is used to update Comment
	 * 
	 * @param comment
	 * @param model
	 * @param imageId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/image/comments")
	public String updatedComment(Comment comment, Model model, @RequestParam(name = "imageId") Integer imageId,
			HttpSession session) {
		User existingUser = (User) session.getAttribute("loggeduser");
		Image image = imageService.getImageById(imageId);
		comment.setUser(existingUser);
		comment.setImage(image);
		comment.setCreatedDate(new Date());
		commentService.uploadComment(comment);
		return "redirect:/images/" + imageId;
	}
}
