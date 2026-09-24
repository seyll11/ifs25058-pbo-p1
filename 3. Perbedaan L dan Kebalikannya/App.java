import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // LinkedHashMap dipakai supaya urutan kemunculan pertama tiap nilai terjaga
        Map<Integer, Integer> freq = new LinkedHashMap<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("---")) break;
            if (line.isEmpty()) continue;
            int nilai = Integer.parseInt(line);
            freq.put(nilai, freq.getOrDefault(nilai, 0) + 1);
        }

        // Input kosong (langsung "---") -> tidak menampilkan apa pun
        if (freq.isEmpty()) {
            return;
        }

        Integer tertinggi = null, terendah = null;
        Integer terbanyak = null, tersedikit = null;
        Integer jumlahTertinggi = null, jumlahTerendah = null;

        for (int nilai : freq.keySet()) {
            if (tertinggi == null || nilai > tertinggi) tertinggi = nilai;
            if (terendah == null || nilai < terendah) terendah = nilai;
        }

        for (int nilai : freq.keySet()) {
            int f = freq.get(nilai);

            // Terbanyak: frekuensi tertinggi, seri -> nilai lebih besar
            if (terbanyak == null || f > freq.get(terbanyak)
                    || (f == freq.get(terbanyak) && nilai > terbanyak)) {
                terbanyak = nilai;
            }

            // Tersedikit: frekuensi terendah, seri -> nilai lebih kecil
            if (tersedikit == null || f < freq.get(tersedikit)
                    || (f == freq.get(tersedikit) && nilai < tersedikit)) {
                tersedikit = nilai;
            }

            long jumlah = (long) nilai * f;

            // Jumlah Tertinggi: nilai*frekuensi terbesar, seri -> nilai lebih besar
            if (jumlahTertinggi == null
                    || jumlah > (long) jumlahTertinggi * freq.get(jumlahTertinggi)
                    || (jumlah == (long) jumlahTertinggi * freq.get(jumlahTertinggi) && nilai > jumlahTertinggi)) {
                jumlahTertinggi = nilai;
            }

            // Jumlah Terendah: nilai*frekuensi terkecil, seri -> nilai lebih kecil
            if (jumlahTerendah == null
                    || jumlah < (long) jumlahTerendah * freq.get(jumlahTerendah)
                    || (jumlah == (long) jumlahTerendah * freq.get(jumlahTerendah) && nilai < jumlahTerendah)) {
                jumlahTerendah = nilai;
            }
        }

        System.out.println("Tertinggi: " + tertinggi);
        System.out.println("Terendah: " + terendah);
        System.out.println("Terbanyak: " + terbanyak + " (" + freq.get(terbanyak) + "x)");
        System.out.println("Tersedikit: " + tersedikit + " (" + freq.get(tersedikit) + "x)");
        System.out.println("Jumlah Tertinggi: " + jumlahTertinggi + " * " + freq.get(jumlahTertinggi)
                + " = " + ((long) jumlahTertinggi * freq.get(jumlahTertinggi)));
        System.out.println("Jumlah Terendah: " + jumlahTerendah + " * " + freq.get(jumlahTerendah)
                + " = " + ((long) jumlahTerendah * freq.get(jumlahTerendah)));
    }
}
