package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Class HomeController This is a controller class to map home methods
 * 
 * @author Romil
 */
@Controller
public class HomeController {

	@Autowired
	private ImageService imageService;

	/**
	 * Method to get all the Images
	 * 
	 * @param model
	 * @return String
	 */
	@RequestMapping("/")
	public String getAllImages(Model model) {
		List<Image> images = imageService.getAllImages();
		model.addAttribute("images", images);
		return "index";
	}
}