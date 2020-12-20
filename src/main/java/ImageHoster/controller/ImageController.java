package ImageHoster.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ImageHoster.model.Image;
import ImageHoster.model.Tag;
import ImageHoster.model.User;
import ImageHoster.service.ImageService;
import ImageHoster.service.TagService;

/**
 * Class ImageController
 * This is a controller class to map image methods
 * @author Romil
 */
@Controller
public class ImageController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private TagService tagService;
	


	/**
	 * Method getUserImages
	 * This method displays all the images in the user home page after successful Login
	 * @param model
	 * @return String
	 */
	@RequestMapping("images")
	public String getUserImages(Model model) {
		List<Image> images = imageService.getAllImages();
		model.addAttribute("images", images);
		return "images";
	}

	/**
	 * Method showImage
	 * This method is called when the details of the specific image with id
	 * @param id
	 * @param model
	 * @return String
	 */
	@RequestMapping("/images/{id}")
	public String showImage(@PathVariable("id") int id, Model model) {
		Image image = imageService.getImageById(id);
		model.addAttribute("image", image);
		model.addAttribute("tags", image.getTags());
		model.addAttribute("comments", image.getComments());
		model.addAttribute("editError", false);
		return "images/image";
	}

	/**
	 * Method newImage
	 * This controller method is called when the request pattern is of type 'images/upload'
	 * @return String
	 */
	@RequestMapping("/images/upload")
	public String newImage() {
		return "images/upload";
	}

	/**
	 * Method createImage
	 * This controller method is called to create new Image
	 * @param file
	 * @param tags
	 * @param newImage
	 * @param session
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/images/upload", method = RequestMethod.POST)
	public String createImage(@RequestParam("file") MultipartFile file, @RequestParam("tags") String tags,
			Image newImage, HttpSession session) throws IOException {
		User user = (User) session.getAttribute("loggeduser");
		newImage.setUser(user);
		String uploadedImageData = convertUploadedFileToBase64(file);
		newImage.setImageFile(uploadedImageData);
		List<Tag> imageTags = findOrCreateTags(tags);
		newImage.setTags(imageTags);
		newImage.setDate(new Date());
		imageService.uploadImage(newImage);
		return "redirect:/images";
	}

	/**
	 * Method editImage
	 * This method fetches the image with the corresponding id from the database and adds it to the model with the key as 'image'
	 * @param imageId
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/editImage")
	public String editImage(@RequestParam("imageId") Integer imageId, Model model, HttpSession session) {
		Image image = imageService.getImage(imageId);
		User existingUser = (User) session.getAttribute("loggeduser");
		if (existingUser.getUsername().equalsIgnoreCase(image.getUser().getUsername())) {
			String tags = convertTagsToString(image.getTags());
			model.addAttribute("image", image);
			model.addAttribute("tags", tags);
			return "images/edit";
		} else {
			model.addAttribute("editError", true);
			model.addAttribute("image", image);
			model.addAttribute("tags", image.getTags());
			return "images/image";
		}

	}

	/**
	 * Method editImageSubmit
	 * The method is used to save image
	 * @param file
	 * @param imageId
	 * @param tags
	 * @param updatedImage
	 * @param session
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/editImage", method = RequestMethod.PUT)
	public String editImageSubmit(@RequestParam("file") MultipartFile file, @RequestParam("imageId") Integer imageId,
			@RequestParam("tags") String tags, Image updatedImage, HttpSession session) throws IOException {

		Image image = imageService.getImage(imageId);
		String updatedImageData = convertUploadedFileToBase64(file);
		List<Tag> imageTags = findOrCreateTags(tags);

		if (updatedImageData.isEmpty())
			updatedImage.setImageFile(image.getImageFile());
		else {
			updatedImage.setImageFile(updatedImageData);
		}

		updatedImage.setId(imageId);
		User user = (User) session.getAttribute("loggeduser");
		updatedImage.setUser(user);
		updatedImage.setTags(imageTags);
		updatedImage.setDate(new Date());

		imageService.updateImage(updatedImage);
		return "redirect:/images/" + updatedImage.getTitle();
	}

	/**
	 * Method deleteImageSubmit
	 * This method is used to delete image 
	 * @param imageId
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteImage", method = RequestMethod.DELETE)
	public String deleteImageSubmit(@RequestParam(name = "imageId") Integer imageId,  Model model, HttpSession session) {
		Image image = imageService.getImage(imageId);
		User existingUser = (User) session.getAttribute("loggeduser");
		if (existingUser.getUsername().equalsIgnoreCase(image.getUser().getUsername())) {
			imageService.deleteImage(imageId);
			return "redirect:/images";
		} else {
			model.addAttribute("deleteError", true);
			model.addAttribute("image", image);
			model.addAttribute("tags", image.getTags());
			return "images/image";
		}

	}
	
	// This method converts the image to Base64 format
	private String convertUploadedFileToBase64(MultipartFile file) throws IOException {
		return Base64.getEncoder().encodeToString(file.getBytes());
	}

	// findOrCreateTags() method has been implemented, which returns the list of
	// tags after converting the ‘tags’ string to a list of all the tags and also
	// stores the tags in the database if they do not exist in the database. Observe
	// the method and complete the code where required for this method.
	// Try to get the tag from the database using getTagByName() method. If tag is
	// returned, you need not to store that tag in the database, and if null is
	// returned, you need to first store that tag in the database and then the tag
	// is added to a list
	// After adding all tags to a list, the list is returned
	private List<Tag> findOrCreateTags(String tagNames) {
		StringTokenizer st = new StringTokenizer(tagNames, ",");
		List<Tag> tags = new ArrayList<Tag>();

		while (st.hasMoreTokens()) {
			String tagName = st.nextToken().trim();
			Tag tag = tagService.getTagByName(tagName);

			if (tag == null) {
				Tag newTag = new Tag(tagName);
				tag = tagService.createTag(newTag);
			}
			tags.add(tag);
		}
		return tags;
	}

	// The method receives the list of all tags
	// Converts the list of all tags to a single string containing all the tags
	// separated by a comma
	// Returns the string
	private String convertTagsToString(List<Tag> tags) {
		StringBuilder tagString = new StringBuilder();

		for (int i = 0; i <= tags.size() - 2; i++) {
			tagString.append(tags.get(i).getName()).append(",");
		}

		Tag lastTag = tags.get(tags.size() - 1);
		tagString.append(lastTag.getName());

		return tagString.toString();
	}
}
