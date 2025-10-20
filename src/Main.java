import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String[]> list = new ArrayList<>();

        String filePath = "src/cards.txt";

        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line = br.readLine();

        while (line != null) {
            String[] row = line.split(",");
            list.add(row);
            line = br.readLine();
        }

        br.close();

        String[][] array = list.toArray(new String[0][0]);

        header();

        Scanner scanner = new Scanner(System.in);

        boolean isLogin = false;
        int userNum = 0;
        double balance;
        double amount;
        boolean blocked = Boolean.parseBoolean(array[userNum][4]);
        String option;

        System.out.print("Enter the last 4 digits of your card number: ");
        String cardNumber = scanner.next();

        for(int i = 0; i < array.length; i++){
            if(array[i][0].equals(cardNumber)){
                userNum = i;
                blocked = Boolean.parseBoolean(array[i][4]);

                if(blocked) {
                    System.out.println("""
                                ************************************
                                            Card blocked
                                          contact your bank
                                ************************************
                                """);
                    System.exit(0);
                }

                for(int j = 0; j < 3 ; j++){
                    System.out.print("Enter password: ");
                    String password = scanner.next();

                    if(password.equals(array[i][1])){
                        isLogin = true;
                        break;
                    }else if(j == 2 && !password.equals(array[i][1])){
                        System.out.println(""" 
                                                ************************************
                                                           Wrong password!
                                                           Card blocked
                                                           contact your bank
                                                ************************************
                                                """);
                        blocked = true;
                        array[userNum][4] = Boolean.toString(blocked);
                    }
                }
            }
            if(i == array.length){
                System.out.println("invalid card number");
                System.exit(0);
            }
        }

        if(isLogin){
            System.out.println("Hello, " + array[userNum][2]);

            balance =  Double.parseDouble(array[userNum][3]);
            System.out.println("Balance: $" + balance);

            System.out.println("""
                    (W)ithdraw       (P)assword
                    (D)eposit        (Q)uit
                    """);
            System.out.print("Select your option:");
            option = scanner.next().toUpperCase();
            if(option.equals("W")){
                System.out.print("Enter amount to withdraw: ");
                amount = scanner.nextDouble();

                balance =  balance - amount;
                System.out.println("Balance: $" + balance);
                array[userNum][3] = String.valueOf(balance);
            }else if(option.equals("D")){
                System.out.print("Enter amount to Deposit: ");
                amount = scanner.nextDouble();

                balance =  balance + amount;
                System.out.println("Balance: $" + balance);
                array[userNum][3] = String.valueOf(balance);
            } else if(option.equals("P")){
                System.out.print("Enter your new card password: ");
                String password = scanner.next();

                array[userNum][1] = password;
                System.out.println("password change successful");
            } else if(option.equals("Q")) {
                System.exit(0);
            }else{
                System.out.println("Invalid option");
            }
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));

        for (String[] row : list) {
            String rowStr = String.join(",", row);
            bw.write(rowStr);
            bw.newLine();
        }

        bw.close();
        scanner.close();
    }

    static void header(){
        System.out.println("""
                *******************************
                             ATM
                *******************************
                """);
    }
}
