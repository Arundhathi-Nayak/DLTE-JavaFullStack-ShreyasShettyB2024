package app.mobilebanking;

import app.mobilebanking.entity.Account;
import app.mobilebanking.middleware.FileStorageTarget;
import app.mobilebanking.remotes.StorageTarget;
import app.mobilebanking.services.AccountService;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static StorageTarget storageTarget;
    private static AccountService services;
    private static Scanner scanner = new Scanner(System.in);
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("application") ;
    private static  Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String[] args) {
        int option=0;
        String username=null,password;
        storageTarget=new FileStorageTarget();
        //storageTarget=new DatabaseTarget();
        services=new AccountService(storageTarget);
        //services.callAddTransactions();
        //System.out.println( services.callFinaAll().toString());
        System.out.println(resourceBundle.getString("app.greet"));
        System.out.println(resourceBundle.getString("app.login.menu"));
        option = scanner.nextInt();
        if(option==1){
            boolean validate=false;
            System.out.println("Enter Your Username");
            username = scanner.next();
            System.out.println("Enter Password");
            password = scanner.next();
            if(services.callVerifyPassword(username,password)){
                logger.log(Level.INFO,resourceBundle.getString("login.Successful")+":"+username);
                System.out.println(resourceBundle.getString("login.Successful"));
            while (true) {
                System.out.println(resourceBundle.getString("app.dashboard.menu"));
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                    case 2:
                    case 4:
                    case 5:
                    case 6:
                        System.out.println(resourceBundle.getString("app.page.under.development"));
                        break;
                    case 3:
                        double amount = 0;
                         do {

                             try {
                                 System.out.println(resourceBundle.getString("Amount.withdraw"));
                                 amount = scanner.nextDouble();
                                 validate = true;
                             } catch (InputMismatchException inputMismatchException) {
                                 System.out.println(resourceBundle.getString("Enter.number"));
                                 scanner.nextLine();
                             }
                         }while (!validate);
                        System.out.println(resourceBundle.getString("Enter.Password"));
                        password = scanner.next();
                        services.callWithdraw(username, password, amount);
                        break;
                    default:
                        System.out.println(resourceBundle.getString("Thank.you"));
                        System.exit(0);
                }
            }
            }
        }
        else{
            System.out.println(resourceBundle.getString("app.page.under.development"));
            System.exit(0);
        }

    }
}


