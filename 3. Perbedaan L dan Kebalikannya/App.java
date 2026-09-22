import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());

        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                m[i][j] = Integer.parseInt(parts[j]);
            }
        }

        if (n == 1) {
            int tengah = m[0][0];
            System.out.println("Nilai L: Tidak Ada");
            System.out.println("Nilai Kebalikan L: Tidak Ada");
            System.out.println("Nilai Tengah: " + tengah);
            System.out.println("Perbedaan: Tidak Ada");
            System.out.println("Dominan: " + tengah);
            return;
        }

        if (n == 2) {
            int total = 0;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    total += m[i][j];
            System.out.println("Nilai L: Tidak Ada");
            System.out.println("Nilai Kebalikan L: Tidak Ada");
            System.out.println("Nilai Tengah: " + total);
            System.out.println("Perbedaan: Tidak Ada");
            System.out.println("Dominan: " + total);
            return;
        }

        // Nilai L: seluruh kolom pertama + sisa baris terakhir
        // (tanpa sel pojok kiri bawah yang sudah terhitung di kolom,
        // dan tanpa sel pojok kanan bawah)
        int nilaiL = 0;
        for (int i = 0; i < n; i++) {
            nilaiL += m[i][0];
        }
        for (int j = 1; j < n - 1; j++) {
            nilaiL += m[n - 1][j];
        }

        // Nilai Kebalikan L: seluruh kolom terakhir + sisa baris pertama
        // (tanpa sel pojok kiri atas dan tanpa sel pojok kanan atas
        // yang sudah terhitung di kolom)
        int nilaiKebalikanL = 0;
        for (int i = 0; i < n; i++) {
            nilaiKebalikanL += m[i][n - 1];
        }
        for (int j = 1; j < n - 1; j++) {
            nilaiKebalikanL += m[0][j];
        }

        int nilaiTengah;
        if (n % 2 == 1) {
            nilaiTengah = m[n / 2][n / 2];
        } else {
            int a = n / 2 - 1, b = n / 2;
            nilaiTengah = m[a][a] + m[a][b] + m[b][a] + m[b][b];
        }

        int perbedaan = Math.abs(nilaiL - nilaiKebalikanL);
        int dominan;
        if (perbedaan == 0) {
            dominan = nilaiTengah;
        } else {
            dominan = Math.max(nilaiL, nilaiKebalikanL);
        }

        System.out.println("Nilai L: " + nilaiL);
        System.out.println("Nilai Kebalikan L: " + nilaiKebalikanL);
        System.out.println("Nilai Tengah: " + nilaiTengah);
        System.out.println("Perbedaan: " + perbedaan);
        System.out.println("Dominan: " + dominan);
    }
}
