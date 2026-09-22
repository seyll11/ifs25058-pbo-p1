import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nim = sc.nextLine().trim();

        if (nim.length() != 8) {
            System.out.println("NIM harus 8 karakter");
            return;
        }

        Map<String, String> prodi = new HashMap<>();
        prodi.put("11S", "Sarjana Informatika");
        prodi.put("12S", "Sarjana Sistem Informasi");
        prodi.put("13S", "Sarjana Teknik Elektro");
        prodi.put("21S", "Sarjana Manajemen Rekayasa");
        prodi.put("22S", "Sarjana Teknik Metalurgi");
        prodi.put("31S", "Sarjana Teknik Bioproses");
        prodi.put("32S", "Sarjana Bioteknologi");
        prodi.put("114", "Diploma 4 Teknologi Rekayasa Perangkat Lunak");
        prodi.put("113", "Diploma 3 Teknologi Informasi");
        prodi.put("133", "Diploma 3 Teknologi Komputer");

        String prefix = nim.substring(0, 3);
        if (!prodi.containsKey(prefix)) {
            System.out.println("Kode tidak tersedia");
            return;
        }

        String kodeAngkatan = nim.substring(3, 5);
        int angkatan = Integer.parseInt("20" + kodeAngkatan);
        int urutan = Integer.parseInt(nim.substring(5, 8));

        System.out.println("Informasi NIM " + nim + ": ");
        System.out.println(">> Program Studi: " + prodi.get(prefix));
        System.out.println(">> Angkatan: " + angkatan);
        System.out.println(">> Urutan: " + urutan);
    }
}