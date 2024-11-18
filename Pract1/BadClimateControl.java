public class BadClimateControl {
    // погані назви змінних і методів
    private int t = 22; // temperature
    private boolean p = true; // power on
    private String userProfile;

    public BadClimateControl(String u) {
        userProfile = u;
    }

    public void sTemp(int t) {
        this.t = t;
    }

    public void swPower(boolean p) {
        this.p = p;
    }

    public void chk() {
        if (p && t > 30) {System.out.println("Too hot");}
        if (p && t < 16) {System.out.println("Too cold");}
    }

    public void apply(String up) {
        if (up == "John") {
            sTemp(25);
        } else if (up == "Jane") {
            sTemp(18);
        }
        swPower(true);
    }

    public static void main(String[] args) {
        BadClimateControl cc = new BadClimateControl("John");
        cc.apply("John");
        cc.chk();
    }
}
