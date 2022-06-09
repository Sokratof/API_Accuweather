public final class ApplicationGlobalState {

    private static ApplicationGlobalState INSTANCE;
    private String selectedCity = null;
    private final String API_KEY = "NllwBiea7DMJGaISBdNCGUHb2AA8dZRz";

    private ApplicationGlobalState(){
    }
    public static ApplicationGlobalState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApplicationGlobalState();
        }
        return INSTANCE;
    }
    public String getSelectedCity(){
        return selectedCity;
    }
    public void setSelectedCity(String selectedCity) {
        this.selectedCity = selectedCity;
    }
    public String getAPI_KEY() {
        return this.API_KEY;
    }

}
