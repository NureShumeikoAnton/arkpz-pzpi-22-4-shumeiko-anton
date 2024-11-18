// поганий приклад
public class UserValidator1 {
    private boolean isValid;

    public boolean validateUser(String username, String password) {
        isValid = true;

        if (username == null || username.isEmpty()) {
            isValid = false;
        }

        if (password == null || password.length() < 8) {
            isValid = false;
        }

        if (isValid) {
            System.out.println("Користувач дійсний");
            return true;
        } else {
            System.out.println("Неправильний користувач");
            return false;
        }
    }
}
