package ImageHoster.service;

import ImageHoster.model.Tag;
import ImageHoster.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class TagService This is a service class to tag methods
 * 
 * @author Romil
 */
@Service
public class TagService {
	@Autowired
	private TagRepository tagRepository;

	/**
	 * Method getTagByName
	 * 
	 * @param title
	 * @return Tag
	 */
	public Tag getTagByName(String title) {
		return tagRepository.findTag(title);
	}

	/**
	 * Method createTag
	 * 
	 * @param tag
	 * @return Tag
	 */
	public Tag createTag(Tag tag) {
		return tagRepository.createTag(tag);
	}
}
