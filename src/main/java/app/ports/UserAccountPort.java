package app.ports;

import java.util.List;

import app.domain.models.UserAccount;

public interface UserAccountPort {
	  UserAccount login(String username, String password);
	  void changePassword(String username, String oldPassword, String newPassword);
	  void saveUser(UserAccount user);
	  void deleteUser(Long document);
	  UserAccount findByDocument(Long document);
	  UserAccount findByUserName(String username);
	  List<UserAccount> findAllUsers();
}