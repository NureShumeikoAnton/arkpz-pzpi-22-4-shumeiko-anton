public class GoodClimateControl {
    // Читабельні назви змінних і методів
    private int currentTemperature;
    private boolean isPowerOn;
    private String activeUserProfile;

    public GoodClimateControl(String userProfile) {
        this.activeUserProfile = userProfile;
        this.isPowerOn = false; // за замовчуванням вимкнено
        this.currentTemperature = 22; // стандартна температура
    }

    public void setTemperature(int temperature) {
        this.currentTemperature = temperature;
    }

    public void switchPower(boolean powerStatus) {
        this.isPowerOn = powerStatus;
    }

    public void checkClimate() {
        if (!isPowerOn) {
            System.out.println("System is off.");
            return;
        }

        if (currentTemperature > 30) {
            System.out.println("Warning: Temperature is too high!");
        } else if (currentTemperature < 16) {
            System.out.println("Warning: Temperature is too low!");
        } else {
            System.out.println("Climate is comfortable.");
        }
    }

    public void applyUserProfile(String userProfile) {
        switch (userProfile) {
            case "John":
                setTemperature(25);
                break;
            case "Jane":
                setTemperature(18);
                break;
            default:
                setTemperature(22); // стандартна температура
        }
        switchPower(true);
    }

    public static void main(String[] args) {
        GoodClimateControl climateControl = new GoodClimateControl("John");

        climateControl.applyUserProfile("John");

        climateControl.checkClimate();
    }
}
