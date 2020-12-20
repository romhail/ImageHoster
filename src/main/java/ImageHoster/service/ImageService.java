package ImageHoster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.repository.ImageRepository;

/**
 * Class ImageService
 * This is a service class to image methods
 * @author Romil
 */
@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    /**
     * Method getAllImages
     * Call the getAllImages() method in the Repository and obtain a List of all the images in the database
     * @return List<Image>
     */
    public List<Image> getAllImages() {
        return imageRepository.getAllImages();
    }


    /**
     * Method uploadImage
     * The method calls the createImage() method in the Repository and passes the image to be persisted in the database
     */
    public void uploadImage(Image image) {
        imageRepository.uploadImage(image);
    }


    /**
     * Method getImageByTitle
     * The method calls the getImageByTitle() method in the Repository and passes the title of the image to be fetched
     * @param title
     * @return
     */
    public Image getImageByTitle(String title) {
        return imageRepository.getImageByTitle(title);
    }
    
    /**
     * Method getImageById
     * The method calls the getImageByTitle() method in the Repository and passes the title of the image to be fetched
     * @param id
     * @return Image
     */
    public Image getImageById(int id) {
        return imageRepository.getImage(id);
    }

    /**
     * Method getImage
     * The method calls the getImage() method in the Repository and passes the id of the image to be fetched
     * @param imageId
     * @return
     */
    public Image getImage(Integer imageId) {
        return imageRepository.getImage(imageId);
    }

    /**
     * Method updateImage
     * The method calls the updateImage() method in the Repository and passes the Image to be updated in the database
     * @param updatedImage
     */
    public void updateImage(Image updatedImage) {
        imageRepository.updateImage(updatedImage);
    }

    /**
     * Method deleteImage
     * The method calls the deleteImage() method in the Repository and passes the Image id of the image to be deleted in the database
     * @param imageId
     */
    public void deleteImage(Integer imageId) {
        imageRepository.deleteImage(imageId);
    }
}
