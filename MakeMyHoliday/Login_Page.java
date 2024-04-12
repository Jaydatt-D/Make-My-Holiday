package MakeMyHoliday;
import java.io.*;
import java.util.*;

public class Login_Page
{
    protected File User;
    public int start()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println(" ");
        System.out.println("--------------------------------------");
        System.out.println("\tWelcome to Make My Holiday:");
        System.out.println(" ");
        System.out.println("--------------------------------------");
        System.out.println("\t 1. Login                           ");
        System.out.println("\t 2. Register                        ");
        System.out.println("\t 3. Delete Account                  ");
        System.out.println("\t 4. Exit                          ");
        System.out.println("--------------------------------------");
        System.out.println(" ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice)
        {
            case 1:
            {
                System.out.print("Enter your Username: ");
                String username = sc.nextLine();
                System.out.print("Enter your Password: ");
                String password = sc.nextLine();
                File existing_User = new File("C:\\JAVA\\OOPS\\src\\OOPS_Assignment\\Database\\" + username + ".txt");
                if (existing_User.exists())
                {
                    try
                    {
                        this.User = existing_User;
                        Scanner filereader = new Scanner(existing_User);
                        String tmp = filereader.nextLine();
                        ArrayList<String> userDetail = readFile(existing_User);
                        if (tmp.equals(password))
                        {
                            System.out.println("--------------------------------------");
                            System.out.println("\t\tLogin Successful");
                            System.out.println("--------------------------------------");
                            System.out.println("\t\tWelcome " + userDetail.get(1).split(" ")[1]);
                            System.out.println("--------------------------------------");
                            return 1;
                        }
                        else
                            System.out.println("Password or Username Incorrect");
                    }
                    catch (FileNotFoundException e)
                    {
                        System.out.println("No Data Found");
                    }
                }
                else
                {
                    System.out.println("No User Found!");
                    System.out.println("Please Register!");
                }
                break;
            }

            case 2:
            {
                System.out.print("Enter your Username: ");
                String username = sc.nextLine();
                System.out.print("Enter your Password: ");
                String password = sc.nextLine();

                File new_User = new File("C:\\JAVA\\OOPS\\src\\OOPS_Assignment\\Database\\" + username + ".txt");
                try
                {
                    new_User.createNewFile();
                    FileWriter fr = new FileWriter(new_User, true);
                    BufferedWriter br = new BufferedWriter(fr);
                    PrintWriter out = new PrintWriter(br);
                    out.println(password);
                    create_Passenger_Details pass = new create_Passenger_Details();
                    pass.getDetails();
                    pass.addNewUser(out);
                    System.out.println("--------------------------------------");
                    System.out.println("\t\tRegistration Successfull");
                    System.out.println("\t\tPlease Login again to enter: ");
                    System.out.println("--------------------------------------");
                    out.close();
                    br.close();
                    fr.close();
                }
                catch (IOException e)
                {
                    System.out.println("Errored occured!");
                }
                break;
            }
            case 3:{
                System.out.print("Enter Username to delete: ");
                String delAcc = sc.nextLine();
                delAcc.trim();
                System.out.print("Enter Password: ");
                String chkDel = sc.nextLine();
                File existing_User = new File("C:\\JAVA\\OOPS\\src\\OOPS_Assignment\\Database\\" + delAcc + ".txt");
                if (existing_User.exists())
                {
                    try
                    {
                        Scanner filereader = new Scanner(existing_User);
                        String tmp = filereader.nextLine();
                        filereader.close();
                        if (tmp.equals(chkDel))
                        {
                            new FileWriter(existing_User, false).close();
                            existing_User.delete();
                            System.out.println("--------------------------------------");
                            System.out.println("\tAccount Successfully Deleted");
                            System.out.println("--------------------------------------");
                            return 2;
                        }
                        else
                            System.out.println("Password or Username Incorrect");
                    }
                    catch (FileNotFoundException e)
                    {
                        System.out.println("No Data Found");
                    } catch (IOException e) {
                        System.out.println("An Error Ocurred");
                    }
                }
                else
                {
                    System.out.println("No User Found!");
                }
            }
            case 4:
            {
                return 2;
            }
        }
        return 0;
    }
    protected ArrayList<String> readFile(File given){
        ArrayList<String> readfile = new ArrayList<>();
        try {
            Scanner filereader = new Scanner(given);
            while(filereader.hasNextLine()){
                readfile.add(filereader.nextLine());
            }
        }
        catch (FileNotFoundException e){
            System.out.println("An Error Ocurred");
        }
        return readfile;
    }
}
class create_Passenger_Details
{
    String name, phone_number, gender, email_id;
    int age = 7;

    protected void getDetails()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        this.name = sc.nextLine();
        System.out.print("Enter your Phone no.: ");
        this.phone_number = sc.nextLine();
        System.out.print("Enter your Gender: ");
        this.gender = sc.nextLine();
        System.out.print("Enter your Email: ");
        this.email_id = sc.nextLine();
        System.out.print("Enter your Age: ");
        this.age = sc.nextInt();
    }
    protected void addNewUser(PrintWriter out)
    {
        out.println("Name: " + name);
        out.println("Phone no.: " + phone_number);
        out.println("Email: " + email_id);
        out.println("Gender: " + gender);
        out.println("Age: " + age);
    }
}