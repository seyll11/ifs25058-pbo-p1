import java.util.Scanner;

/**
 * Studi kasus 3: Perbedaan L dan Kebalikannya pada matriks n x n.
 *
 * - Nilai L           : seluruh kolom pertama + baris terakhir (tanpa pojok kanan bawah).
 * - Nilai Kebalikan L : seluruh kolom terakhir + baris pertama (tanpa pojok kiri atas).
 * - Nilai Tengah      : elemen tengah (n ganjil) atau jumlah 4 elemen tengah (n genap).
 * - Untuk n = 1 dan n = 2, L dan Kebalikan L dianggap "Tidak Ada".
 */
public class App {

    private static final int UKURAN_MINIMAL_UNTUK_L = 3;
    private static final String TIDAK_ADA = "Tidak Ada";

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = bacaUkuran(sc);
            if (n < 1) {
                System.out.println("Ukuran matriks tidak valid");
                return;
            }

            int[][] matriks = bacaMatriks(sc, n);
            if (matriks == null) {
                System.out.println("Data matriks tidak valid");
                return;
            }

            tampilkanHasil(matriks);
        }
    }

    // ------------------------------------------------------------------
    // Input
    // ------------------------------------------------------------------

    /** Mengembalikan ukuran matriks, atau -1 jika input tidak valid. */
    private static int bacaUkuran(Scanner sc) {
        if (!sc.hasNextLine()) {
            return -1;
        }
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Membaca matriks n x n. Mengembalikan null jika baris kurang atau isinya bukan bilangan. */
    private static int[][] bacaMatriks(Scanner sc, int n) {
        int[][] matriks = new int[n][n];
        for (int i = 0; i < n; i++) {
            if (!sc.hasNextLine()) {
                return null;
            }
            String[] token = sc.nextLine().trim().split("\\s+");
            if (token.length != n) {
                return null;
            }
            for (int j = 0; j < n; j++) {
                try {
                    matriks[i][j] = Integer.parseInt(token[j]);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return matriks;
    }

    // ------------------------------------------------------------------
    // Logika
    // ------------------------------------------------------------------

    private static long hitungNilaiL(int[][] m) {
        int n = m.length;
        long total = 0;
        for (int i = 0; i < n; i++) {
            total += m[i][0];
        }
        for (int j = 1; j < n - 1; j++) {
            total += m[n - 1][j];
        }
        return total;
    }

    private static long hitungNilaiKebalikanL(int[][] m) {
        int n = m.length;
        long total = 0;
        for (int i = 0; i < n; i++) {
            total += m[i][n - 1];
        }
        for (int j = 1; j < n - 1; j++) {
            total += m[0][j];
        }
        return total;
    }

    private static long hitungNilaiTengah(int[][] m) {
        int n = m.length;
        if (n % 2 == 1) {
            return m[n / 2][n / 2];
        }
        int a = n / 2 - 1;
        int b = n / 2;
        return (long) m[a][a] + m[a][b] + m[b][a] + m[b][b];
    }

    // ------------------------------------------------------------------
    // Output
    // ------------------------------------------------------------------

    private static void tampilkanHasil(int[][] matriks) {
        long nilaiTengah = hitungNilaiTengah(matriks);

        if (matriks.length < UKURAN_MINIMAL_UNTUK_L) {
            tampilkan(TIDAK_ADA, TIDAK_ADA, nilaiTengah, TIDAK_ADA, nilaiTengah);
            return;
        }

        long nilaiL = hitungNilaiL(matriks);
        long nilaiKebalikanL = hitungNilaiKebalikanL(matriks);
        long perbedaan = Math.abs(nilaiL - nilaiKebalikanL);
        long dominan = (perbedaan == 0) ? nilaiTengah : Math.max(nilaiL, nilaiKebalikanL);

        tampilkan(String.valueOf(nilaiL), String.valueOf(nilaiKebalikanL),
                nilaiTengah, String.valueOf(perbedaan), dominan);
    }

    private static void tampilkan(String nilaiL, String nilaiKebalikanL,
                                  long nilaiTengah, String perbedaan, long dominan) {
        System.out.println("Nilai L: " + nilaiL);
        System.out.println("Nilai Kebalikan L: " + nilaiKebalikanL);
        System.out.println("Nilai Tengah: " + nilaiTengah);
        System.out.println("Perbedaan: " + perbedaan);
        System.out.println("Dominan: " + dominan);
    }
}
