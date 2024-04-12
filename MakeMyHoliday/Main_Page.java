package MakeMyHoliday;
import java.util.*;
import java.io.*;
public class Main_Page extends Login_Page{
    Scanner sc = new Scanner(System.in);
    protected File User;

    Main_Page(){
        this.User=null;
    }
    Main_Page(File user){
        this.User=user;
    }
    public int display(){
        System.out.println();
        System.out.println("\tWelcome to Make My Holiday");
        System.out.println("--------------------------------------");
        System.out.println("\t1. Check out Travel Packages");
        System.out.println("\t2. Customise the Trip");
        System.out.println("\t3. Check Ticket History");
        System.out.println("\t4. LogOut");
        System.out.println("--------------------------------------");
        int ch = sc.nextInt();
        switch (ch){
            case 1:{
                choosePackages();
                break;
            }
            case 2:{
                chooseDestinationsAndDates();
                break;
            }
            case 3:{
                getJourneyDetails(this.User);
                break;
            }
            case 4:{
                return 1;
            }
        }
        return 0;

    }
    protected void addJourney(File file, String toAdd){
        ArrayList<String> list = readFile(file);
        list.add(6,toAdd);
        try {
            PrintWriter filewriter = new PrintWriter(file);
            for(String e:list) filewriter.write(e+"\n");
            filewriter.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("An Error Ocurred");
        }
    }

    private void getJourneyDetails(File file){
        ArrayList<String> list = readFile(file);
        if(list.size()<=6){
            System.out.println();
            System.out.println("No Previous Journey Found");
            System.out.println();
            return;
        }
        System.out.println("--------------------------------------");
        System.out.println("\t\tYour Journeys");
        System.out.println("--------------------------------------");
        for(int j=6;j<list.size();j++){
            int start = list.get(j).indexOf("From");
            System.out.println((j-5)+". "+list.get(j).substring(start,list.get(j).indexOf(',', start)));
        }
        System.out.println("--------------------------------------");
        System.out.println("\t1: Printing a Journey Ticket");
        System.out.println("\t2: Exit");
        System.out.println("--------------------------------------");
        int choice = sc.nextInt();
        if(choice==1){
            System.out.print("Enter the number of Journey: ");
            int number = sc.nextInt();
            printTicket(list.get(number+5));
        }
    }

    protected void printTicket(String arr){
        String details[] = arr.split(",");
        for(String e:details){
            System.out.println(e);
        }
        System.out.println();
        System.out.print("Press 1: For Main Page: ");
        int ch = sc.nextInt();
    }
    private void choosePackages(){
        File packages = new File("C:\\JAVA\\OOPS\\src\\OOPS_Assignment\\TravelPackages\\All.txt");
        ArrayList<String> Types = readFile(packages);
        System.out.println("\tTypes of Packages");
        System.out.println("--------------------------------------");
        printFile(Types);
        System.out.println("--------------------------------------");
        System.out.println();
        System.out.print("\tChoose Type of Package: ");
        int userchoice = sc.nextInt();
        File chosen_package = new File("C:\\JAVA\\OOPS\\src\\OOPS_Assignment\\TravelPackages\\"+Types.get(userchoice - 1).substring(3)+".txt");
        ArrayList<String> packageDetails = readFile(chosen_package);
        System.out.println("\tAvailable Choices");
        System.out.println("--------------------------------------");
        printFile(packageDetails);
        System.out.println("--------------------------------------");
        System.out.println();
        System.out.print("\tChoose your Package: ");
        int ch = sc.nextInt();
        ArrayList<String> finalPackage = new ArrayList<>();
        int stInd = 0;
        for(String it : packageDetails){
            if((!it.equals("")) && it.charAt(0) == (ch + '0')){
                break;
            }
            stInd++;
        }
        for (int i = stInd; i <= stInd + 5; i++) {
            finalPackage.add(packageDetails.get(i));
        }
        Booking_Page book = new Booking_Page(this.User);
        book.getPackage(finalPackage, Types.get(userchoice - 1).substring(3));
    }

    protected void printFile(ArrayList<String> data){
        for(String it : data){
            System.out.println(it);
        }
    }
    private void chooseDestinationsAndDates(){
        System.out.println("\tChoose your preference");
        System.out.println("--------------------------------------");
        System.out.println("\t1. International Trips");
        System.out.println("\t2. Domestic Trips");
        System.out.println("--------------------------------------");
        int trip = sc.nextInt();
        ArrayList<String> tripType = new ArrayList<>();
        tripType.add("International");
        tripType.add("Domestic");
        File loc = new File("C:\\JAVA\\OOPS\\src\\OOPS_Assignment\\Places\\"+tripType.get(trip - 1)+".txt");
        ArrayList<String> trips = readFile(loc);
        ArrayList<String[]> tmp = new ArrayList<>();
        System.out.println("--------------------------------------");
        for(String it : trips){
            String chk[] = it.trim().split(" ");
            tmp.add(chk);
            System.out.println("\t" + chk[0] + chk[1]);
        }
        System.out.println("--------------------------------------");
        System.out.print("Choose Starting City: ");
        int st = sc.nextInt();
        System.out.print("Choose Destination: ");
        int des = sc.nextInt();
        System.out.print("How many days, Do you want to travel? ");
        int days = sc.nextInt();
        System.out.print("Enter Journey Date (DD/MM/YYYY): ");
        String stDate = sc.next();
        System.out.print("How many people are you travelling with? ");
        int people = sc.nextInt();
        System.out.println("Enter Name and Age of all Persons:");
        System.out.println("Name Age Gender");
        String details[][] = new String[people][3];
        for (int i = 0; i < people; i++) {
            details[i][0] = sc.next();
            details[i][1] = sc.next();
            details[i][2] = sc.next();
        }
        Booking_Page book = new Booking_Page(this.User);
        book.calcPrice(st, des, days, stDate, tmp, trip, details);
    }

}
