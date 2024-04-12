package MakeMyHoliday;
import java.util.*;
import java.io.*;
public class Booking_Page extends Main_Page
{
    protected
    File User;

    Booking_Page()
    {
        this.User = null;
    }
    Booking_Page(File user)
    {
        this.User = user;
    }
    Scanner sc = new Scanner(System.in);
    Random random = new Random();
    private double Distance(double lat1, double lon1, double lat2, double lon2)
    {
        final int R = 6400;
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return Math.round(distance);
    }
    protected void calcPrice(int st, int des, int days, String stDate, ArrayList<String[]> tmp, int trip, String[][] details)
    {
        double latSt = Double.parseDouble(tmp.get(st - 1)[2]);
        double lonSt = Double.parseDouble(tmp.get(st - 1)[3]);
        double latDes = Double.parseDouble(tmp.get(des - 1)[2]);
        double lonDes = Double.parseDouble(tmp.get(des - 1)[3]);
        double totalDist = Distance(latSt, lonSt, latDes, lonDes);
        int choice = 3;
        if (trip != 1)
        {
            System.out.println("\tSelect Mode of Transport");
            System.out.println("--------------------------------------");
            System.out.println("\t1. Bus");
            System.out.println("\t2. Train");
            System.out.println("\t3. Flight");
            System.out.println("--------------------------------------");
            choice = sc.nextInt();
        }
        double journeyTime = 0;
        int seatNo = 0;
        String modes[] = new String[]{"Bus", "Train", "Flight"};
        String Mode = modes[choice - 1];
        int Fare = 0;
        String ptb = "";
        String classtype = "";
        switch (choice)
        {
            case 1:
                block1:
                {
                    Bus bus = new Bus();
                    if (trip == 1 || totalDist >= 1000)
                    {
                        System.out.println("--------------------------------------");
                        System.out.println("No Buses available");
                        System.out.println("--------------------------------------");
                        System.out.println("Do you want to Check out trains?");
                        String ans = sc.next();
                        if (ans.equalsIgnoreCase("yes"))
                        {
                            break block1;
                        }
                        choice = -1;
                        break;
                    }
                    System.out.println("Select Bus type");
                    System.out.println("--------------------------------------");
                    System.out.println("1. Basic");
                    System.out.println("2. Volvo");
                    System.out.println("--------------------------------------");
                    int busType = sc.nextInt();
                    journeyTime = totalDist / bus.kmph[busType - 1];
                    Fare = bus.prices[busType - 1] * (int)totalDist;
                    seatNo = random.nextInt(1, 61);
                    classtype = bus.names[busType - 1];
                    ptb = "BUS";
                    break;
                }
            case 2:
                block2:
                {
                    if (trip == 1 || (random.nextInt(0, 30)) > 20)
                    {
                        System.out.println("--------------------------------------");
                        System.out.println("Sorry! No Trains available");
                        System.out.println("--------------------------------------");
                        System.out.println("Do you want to Check out flights?");
                        String ans = sc.next();
                        if (ans.equalsIgnoreCase("yes"))
                        {
                            break block2;
                        }
                        choice = -1;
                        break;
                    }
                    Train train = new Train();
                    System.out.println("Select Train type");
                    System.out.println("--------------------------------------");
                    System.out.println("\t1. 1st Class");
                    System.out.println("\t2. 2nd Class");
                    System.out.println("\t3. 3rd Seater");
                    System.out.println("--------------------------------------");
                    int trainType = sc.nextInt();
                    journeyTime = totalDist / train.kmph[trainType - 1];
                    Fare = train.prices[trainType - 1] * (int)totalDist;
                    ptb = "TRAIN";
                    seatNo = random.nextInt(1, 301);
                    classtype = train.names[trainType - 1];
                    break;
                }
            case 3:
                block3:
                {
                    Flight plane = new Flight();
                    System.out.println("Select Flight type");
                    System.out.println("--------------------------------------");
                    System.out.println("\t1. Economy Class");
                    System.out.println("\t2. Business Class");
                    System.out.println("--------------------------------------");
                    int flightType = sc.nextInt();
                    journeyTime = totalDist / plane.kmph[flightType - 1];
                    Fare = plane.prices[flightType - 1] * (int)totalDist;
                    ptb = "FLIGHT";
                    classtype = plane.names[flightType - 1];
                    seatNo = random.nextInt(1, 161);
                    break;
                }
        }
        if (choice != -1)
        {
            Fare *= details.length;
            createTicket(ptb, Fare, Math.round(journeyTime), st, des, totalDist, Mode, seatNo, stDate, tmp, details, classtype);
        }
        else
        {
            System.out.println("Process Terminated");
        }
    }
    private void createTicket(String ptb, int Fare, double journeyTime, int st, int des, double totalDist, String mode, int seatNo, String stDate, ArrayList<String[]> tmp, String[][] details, String classtype)
    {
        GregorianCalendar gc = new GregorianCalendar();
        Date time = gc.getTime();
        String seats = "";
        for (int i = 0; i < details.length; i++)
        {
            seats += "Name: " + details[i][0] + "\t" + "Age: " + details[i][1] + "\t" + details[i][2] + "\tSeat No: " + seatNo + ", ";
            seatNo++;
        }
        String ticket =
                "----------------------------------------------------------------," +
                        "                        " + ptb + " TICKET                  ," +
                        "----------------------------------------------------------------," +
                        " From:  " + tmp.get(st - 1)[1] + "\t\t" +
                        " To:  " + tmp.get(des - 1)[1] + "," +
                        " Duration:  " + journeyTime + " Hrs\t\t" +
                        " Date:  " + stDate + "," +
                        " Departure Time:  " + time.getHours() + ":" + time.getMinutes() + "," +
                        " Total Distance:  " + totalDist + " Kms, " +
                        seats + "," +
                        " Price:  " + Fare % (200000) + " INR," +
                        " Class:  " + classtype + "," +
                        "----------------------------------------------------------------," +
                        "                           Have a great journey!                ," +
                        "----------------------------------------------------------------,";
        File User = this.User;
        System.out.println("Press 1: Confirm Ticket");
        System.out.println("Press 2: Cancel Ticket");
        int choice = sc.nextInt();
        if (choice == 1)
        {
            System.out.println();
            System.out.println("Ticket Booked");
            System.out.println();
            System.out.println("Your Ticket");
            addJourney(User, ticket);
            printTicket(ticket);
        }
    }
    protected void getPackage(ArrayList<String> data, String packageType)
    {
        int start = data.get(0).indexOf(' ') + 1;
        System.out.print("How many people are you travelling with? ");
        int people = sc.nextInt();
        System.out.print("Enter Name and Age of all Persons:\n");
        System.out.print("Name Age Gender\n");
        String details[][] = new String[people][3];
        for (int i = 0; i < people; i++)
        {
            details[i][0] = sc.next();
            details[i][1] = sc.next();
            details[i][2] = sc.next();
        }
        int seatNo = random.nextInt(1, 31);
        String seats = "";
        for (int i = 0; i < details.length; i++)
        {
            seats += "Name: " + details[i][0] + "\t" + "Age: " + details[i][1] + "\t" + details[i][2] + "\tSeat No: " + seatNo + ", ";
            seatNo++;
        }
        int totalPrice = Integer.parseInt(data.get(5).trim().split(" ")[0]) * people;
        String ticket =
                "----------------------------------------------------------------," +
                        "                        " + packageType + " PACKAGE                  ," +
                        "----------------------------------------------------------------," +
                        " From:  Delhi\t\t" +
                        " To:  " + data.get(0).substring(start, data.get(0).indexOf(':')) + "," +
                        " Dates:  " + data.get(4) + "," +
                        seats + "," +
                        " Price:  " + totalPrice + " INR," +
                        "----------------------------------------------------------------," +
                        "                         Have a great journey!                ," +
                        "----------------------------------------------------------------,";
        File User = this.User;
        System.out.println("Press 1: Confirm Ticket");
        System.out.println("Press 2: Cancel Ticket");
        int choice = sc.nextInt();
        if (choice == 1)
        {
            System.out.println();
            System.out.println("Ticket Booked");
            System.out.println();
            System.out.println("Your Ticket");
            addJourney(User, ticket);
            printTicket(ticket);
        }
    }
}
abstract class modeOfTransport
{
    int choice;
    String names[];
    int prices[];
    int kmph[];
    public
    void setKmph(int[] kmph)
    {
        this.kmph = kmph;
    }
    void setChoice(int choice)
    {
        this.choice = choice;
    }
    void setPrices(int[] prices)
    {
        this.prices = prices;
    }
    void setNames(String[] names)
    {
        this.names = names;
    }
}
class Bus extends modeOfTransport
{
    Bus()
    {
        setChoice(2);
        setNames(new String[]{"Basic", "Volvo"});
        setPrices(new int[]{1, 2});
        setKmph(new int[]{60, 70});
    }
}
class Train extends modeOfTransport
{
    Train()
    {
        setChoice(3);
        setNames(new String[]{"1st Class", "2nd Class", "3rd Seater"});
        setPrices(new int[]{5, 4, 3});
        setKmph(new int[]{75, 75, 75});
    }
}
class Flight extends modeOfTransport
{
    Flight()
    {
        setChoice(2);
        setNames(new String[]{"Economy", "Business"});
        setPrices(new int[]{50, 120});
        setKmph(new int[]{200, 200});
    }
}
