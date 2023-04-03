import java.util.Scanner;

public class sb {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(System.in);

        sb.append(sc.nextLine() + ' ');
        sb.append(sc.nextLine() + ' ');
        sb.append(sc.nextLine());
        System.out.println(sb.toString());
    }
}
