package model;

public interface IUsersDAO {
	boolean createUser(Users user);
	Users readUser(int userId);
	boolean deleteUser(int userId);
	boolean updatePassword(Users user);
}
