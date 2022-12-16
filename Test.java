import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        testDelta(sc);

        sc.close();
    }

    //Subtract less value from more value
    public static int subLessFromMore(int p1, int p2) {
        int r;
        if (p1 > p2) {
            r = p1-p2;
        } else {
            r = p2 - p1;
        }
        return r;
    }

    public static void testDelta(Scanner sc) {
        System.out.println();
        System.out.println("Delta testing:\n");

        System.out.print("Enter your first number: ");
        int firstInp = sc.nextInt();

        System.out.println();

        System.out.print("Enter your second number: ");
        int secondInp = sc.nextInt();

        System.out.println("\nYour delta is: " + subLessFromMore(firstInp, secondInp));
    }
}
