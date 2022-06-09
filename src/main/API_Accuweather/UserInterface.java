import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
     private final Controller controller = new Controller();

     public void runApplication() {
         Scanner scanner = new Scanner(System.in);
         while (true) {
             System.out.println("Введите название города на английском");
             String town = scanner.nextLine();

             setGlobalTown(town);
             System.out.println("Введите: 1 - Получить текущую погоду," +
                     "2 - Получить погоду на следующие 5 дней"+
                     "3 - (exit) завершить работу");
             String result = scanner.nextLine();

             checkIsExit(result);

             try {
                 validateUserInput(result);
             }catch (IOException e) {
                 e.printStackTrace();
                 continue;
             }
             try {
                 notifyController(result);
             } catch (IOException e){
                 e.printStackTrace();
             }
         }
     }
     private void checkIsExit(String result){
         if (result.toLowerCase().equals("3") || result.toLowerCase().equals("exit")) {
             System.out.println("Завершаю работу");
             System.exit(0);
         }
     }

     private void validateUserInput(String userInput) throws IOException{
         if (userInput == null || userInput.length() !=1) {
             throw new IOException("Incorrect user input");
         }
         int answer = 0;
         try {
             answer = Integer.parseInt(userInput);
         } catch (NumberFormatException e) {
             throw new IOException("Incorrect user input");
         }

     }

     private void setGlobalTown(String town){
         ApplicationGlobalState.getInstance().setSelectedCity(town);
     }

     private void notifyController(String input) throws IOException{
         controller.onUserInput(input);
     }
}
