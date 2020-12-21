package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.model.UserProfile;
import ImageHoster.service.ImageService;
import ImageHoster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Class UserController This is a controller class to map user methods
 * 
 * @author Romil
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ImageService imageService;

	/**
	 * Method registration This method declares User type and UserProfile type
	 * object
	 * 
	 * @param model
	 * @return String
	 */
	@RequestMapping("users/registration")
	public String registration(Model model) {
		User user = new User();
		UserProfile profile = new UserProfile();
		user.setProfile(profile);
		model.addAttribute("User", user);
		return "users/registration";
	}

	/**
	 * Method registerUser This method calls the business logic and after the user
	 * record is persisted in the database, directs to login page
	 * 
	 * @param user
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "users/registration", method = RequestMethod.POST)
	public String registerUser(User user, Model model) {
		model.addAttribute("User", user);
		if (user.getUsername().equalsIgnoreCase("") | user.getPassword().equalsIgnoreCase("")) {
			model.addAttribute("validationError", true);
			return "/users/registration";
		}
		if (userService.checkIfUserExists(user.getUsername())) {
			model.addAttribute("userExistsError", true);
			return "/users/registration";
		}
		if (userService.registerUser(user)) {
			model.addAttribute("registrationSuccess", true);
			return "users/login";
		}
		model.addAttribute("passwordTypeError", true);
		return "/users/registration";
	}

	/**
	 * Method login This controller method is called when the request pattern is of
	 * type 'users/login'
	 * 
	 * @return String
	 */
	@RequestMapping("users/login")
	public String login() {
		return "users/login";
	}

	/**
	 * Method loginUser Method is used login user
	 * 
	 * @param user
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "users/login", method = RequestMethod.POST)
	public String loginUser(User user, HttpSession session, Model model) {
		User existingUser = userService.login(user);
		if (existingUser != null) {
			session.setAttribute("loggeduser", existingUser);
			return "redirect:/images";
		} else {
			model.addAttribute("incorrect", true);
			return "users/login";
		}
	}

	/**
	 * Method logout Method is used logout user
	 * 
	 * @param model
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "users/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) {
		session.invalidate();

		List<Image> images = imageService.getAllImages();
		model.addAttribute("images", images);
		return "index";
	}

}
