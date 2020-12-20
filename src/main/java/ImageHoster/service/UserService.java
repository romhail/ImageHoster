package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class UserService This is a service class to user methods
 * @author Romil
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Method registerUser
	 * Method to Call the registerUser() method in the UserRepository class to persist the user record in the database
	 * @param newUser
	 * @return boolean
	 */
	public boolean registerUser(User newUser) {
		if (!checkPassword(newUser.getPassword()))
			return false;
		userRepository.registerUser(newUser);
		return true;
	}

	/**
	 * Mthod login
	 * @param user
	 * @return User
	 */
	public User login(User user) {
		User existingUser = userRepository.checkUser(user.getUsername(), user.getPassword());
		if (existingUser != null) {
			return existingUser;
		} else {
			return null;
		}
	}

	/**
	 * Method checkPassword Method to check if password is valid
	 * 
	 * @param password
	 * @return boolean
	 */
	private boolean checkPassword(String password) {
		if (password.matches(".*[a-zA-Z]+.*")) {
			if (password.matches(".*[0-1]+.*")) {
				String str = password.replaceAll("[a-zA-Z]", "");
				str = str.replaceAll("[0-9]", "");
				if (!str.equalsIgnoreCase("")) {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Method checkIfUserExists Method to check if user exists
	 * 
	 * @param userId
	 * @return boolean
	 */
	public boolean checkIfUserExists(String userId) {
		if (userRepository.checkIfUserExists(userId)) {
			return true;
		}
		return false;
	}
}
