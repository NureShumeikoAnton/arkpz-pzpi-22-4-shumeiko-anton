// гарний приклад
public class UserValidator2 {
    public boolean validateUser(String username, String password) {
        if (username == null || username.isEmpty()) {
            System.out.println("Неправильний користувач");
            return false;
        }

        if (password == null || password.length() < 8) {
            System.out.println("Неправильний користувач");
            return false;
        }

        System.out.println("Користувач дійсний");
        return true;
    }
}
