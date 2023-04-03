import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SWEA_16801 {
    public static int[] map(String[] arr) {

        int[] tmp = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            tmp[i] = Integer.parseInt(arr[i]);
        }
        return tmp;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tc = scanner.nextInt();

        for (int i = 0; i<tc; i++){
            String inp = scanner.nextLine();
//            inp[0] = N:선수의 수, inp[1] = K:가능한 트레이닝 수
            String inp2 = scanner.nextLine();
//            먹기 능력치
            int[] A_list = map(inp2.split(" "));

            String inp3 = scanner.nextLine();
//            음식의 난이도
            int[] F_list = map(inp3.split(" "));






        }
    }
}