import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());

        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] tokens = sc.nextLine().trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(tokens[j]);
            }
        }

        // Kasus khusus 1x1
        if (n == 1) {
            System.out.println("Nilai L: Tidak Ada");
            System.out.println("Nilai Kebalikan L: Tidak Ada");
            System.out.println("Nilai Tengah: " + matrix[0][0]);
            System.out.println("Perbedaan: Tidak Ada");
            System.out.println("Dominan: " + matrix[0][0]);
            return;
        }

        // Kasus khusus 2x2
        if (n == 2) {
            int total = 0;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    total += matrix[i][j];

            System.out.println("Nilai L: Tidak Ada");
            System.out.println("Nilai Kebalikan L: Tidak Ada");
            System.out.println("Nilai Tengah: " + total);
            System.out.println("Perbedaan: Tidak Ada");
            System.out.println("Dominan: " + total);
            return;
        }

        // Nilai L: kolom pertama (baris 0..n-2) + baris terakhir (kolom 0..n-2)
        int nilaiL = 0;
        for (int i = 0; i < n - 1; i++) nilaiL += matrix[i][0];
        for (int j = 0; j < n - 1; j++) nilaiL += matrix[n - 1][j];

        // Nilai Kebalikan L: kolom terakhir (baris 1..n-1) + baris pertama (kolom 1..n-1)
        int nilaiKebalikanL = 0;
        for (int i = 1; i < n; i++) nilaiKebalikanL += matrix[i][n - 1];
        for (int j = 1; j < n; j++) nilaiKebalikanL += matrix[0][j];

        // Nilai Tengah
        int nilaiTengah;
        if (n % 2 == 1) {
            nilaiTengah = matrix[n / 2][n / 2];
        } else {
            int mid = n / 2;
            nilaiTengah = matrix[mid - 1][mid - 1] + matrix[mid - 1][mid]
                    + matrix[mid][mid - 1] + matrix[mid][mid];
        }

        int perbedaan = Math.abs(nilaiL - nilaiKebalikanL);
        int dominan = (perbedaan == 0) ? nilaiTengah : Math.max(nilaiL, nilaiKebalikanL);

        System.out.println("Nilai L: " + nilaiL);
        System.out.println("Nilai Kebalikan L: " + nilaiKebalikanL);
        System.out.println("Nilai Tengah: " + nilaiTengah);
        System.out.println("Perbedaan: " + perbedaan);
        System.out.println("Dominan: " + dominan);
    }
}
