package MakeMyHoliday;
import java.io.*;
import java.util.*;
public class MainProgram{
    public static void main(String[] args){
        outer: while (true) {
            Login_Page starting = new Login_Page();
            int chk = starting.start();
            while (chk != 1){
                if(chk == 2){
                    break outer;
                }
                chk = starting.start();
            }
            Main_Page mid = new Main_Page(starting.User);
            int x = mid.display();
            while (x == 0){
                if (x == 1) {
                    break;
                }
                x = mid.display();
            }
        }
    }
}
