import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GajiKaryawan extends JFrame {
    private final long[] arrayGaji = {5000000, 6500000, 9500000};
    private final int[] arrayLembur = {30, 32, 34, 36, 38};
    private final JComboBox<String> pilihanGolongan;
    private final JSpinner inputJamLembur;
    private final JLabel labelGajiPokok, labelGajiLembur, labelJumlahPenghasilan, labelPersenLembur;
    private final JPanel panelHasil;

    public GajiKaryawan() {
        setTitle("Penghitung Gaji dan Lembur Karyawan");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel input
        JPanel panelUtama = new JPanel();
        panelUtama.setLayout(new BoxLayout(panelUtama, BoxLayout.Y_AXIS));
        panelUtama.setBackground(new Color(248, 250, 252));
        panelUtama.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Judul
        JLabel judul = new JLabel("Penghitung Gaji dan Lembur Karyawan");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 20));
        judul.setForeground(new Color(15, 23, 42));
        judul.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelUtama.add(judul);
        panelUtama.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel panelForm = new JPanel(new GridLayout(0, 1, 8, 8));
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1), new EmptyBorder(15, 15, 15, 15)
        ));

        panelForm.add(buatLabelTebal("Golongan Karyawan"));
        String[] daftarGolongan = {"Golongan A", "Golongan B", "Golongan C"};
        pilihanGolongan = new JComboBox<>(daftarGolongan);
        pilihanGolongan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelForm.add(pilihanGolongan);

        panelForm.add(buatLabelTebal("Jam Lembur"));
        inputJamLembur = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        inputJamLembur.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelForm.add(inputJamLembur);

        JButton tombolHitung = new JButton("Hitung Jumlah Penghasilan");
        tombolHitung.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tombolHitung.setBackground(new Color(37, 99, 235));
        tombolHitung.setForeground(Color.WHITE);
        tombolHitung.setFocusPainted(false);
        tombolHitung.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tombolHitung.addActionListener(event -> hitungGaji());
        panelForm.add(Box.createRigidArea(new Dimension(0, 5)));
        panelForm.add(tombolHitung);

        panelUtama.add(panelForm);
        panelUtama.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Panel hasil
        panelHasil = new JPanel();
        panelHasil.setLayout(new BoxLayout(panelHasil, BoxLayout.Y_AXIS));
        panelHasil.setBackground(Color.WHITE);
        panelHasil.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1), new EmptyBorder(15, 15, 15, 15)
        ));
        panelHasil.setVisible(false);

        JLabel judulHasil = new JLabel("RINCIAN SLIP GAJI");
        judulHasil.setFont(new Font("Segoe UI", Font.BOLD, 16));
        judulHasil.setForeground(new Color(30, 41, 59));
        judulHasil.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelHasil.add(judulHasil);
        panelHasil.add(Box.createRigidArea(new Dimension(0, 10)));

        labelPersenLembur = new JLabel("Persen Lembur: -");
        labelPersenLembur.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelHasil.add(labelPersenLembur);

        labelGajiPokok = new JLabel("Gaji Pokok: Rp 0");
        labelGajiPokok.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelHasil.add(labelGajiPokok);

        labelGajiLembur = new JLabel("Gaji Lembur: Rp 0");
        labelGajiLembur.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelHasil.add(labelGajiLembur);

        panelHasil.add(Box.createRigidArea(new Dimension(0, 8)));
        JSeparator garisPemisah = new JSeparator();
        garisPemisah.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        panelHasil.add(garisPemisah);
        panelHasil.add(Box.createRigidArea(new Dimension(0, 8)));

        labelJumlahPenghasilan = new JLabel("Jumlah Penghasilan: Rp 0");
        labelJumlahPenghasilan.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelJumlahPenghasilan.setForeground(new Color(22, 163, 74));
        panelHasil.add(labelJumlahPenghasilan);

        panelUtama.add(panelHasil);
        add(panelUtama);
    }

    private JLabel buatLabelTebal(String teks) {
        JLabel label = new JLabel(teks);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(71, 85, 105));
        return label;
    }
    
    private void hitungGaji() {
        int nomorGolongan = pilihanGolongan.getSelectedIndex();
        int jamLembur = (Integer) inputJamLembur.getValue();
        int indeksGaji;

        switch (nomorGolongan) {
            case 0 -> indeksGaji = 0; // Golongan A
            case 1 -> indeksGaji = 1; // Golongan B
            case 2 -> indeksGaji = 2; // Golongan C
            default -> throw new IllegalStateException("Pilihan Golongan tidak valid.");
        }

        long gajiPokok = arrayGaji[indeksGaji];
        int indeksLembur = -1;
        if (jamLembur == 1) {
            indeksLembur = 0;
        } else if (jamLembur == 2) {
            indeksLembur = 1;
        } else if (jamLembur == 3) {
            indeksLembur = 2;
        } else if (jamLembur == 4) {
            indeksLembur = 3;
        } else if (jamLembur >= 5) {
            indeksLembur = 4;
        }

        int persenLembur = 0;
        if (indeksLembur != -1) {
            persenLembur = arrayLembur[indeksLembur];
        }

        long gajiLembur = gajiPokok * persenLembur / 100;
        long jumlahPenghasilan = gajiPokok + gajiLembur;

        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        String rupiahPokok = formatRupiah.format(gajiPokok).replace(",00", "");
        String rupiahLembur = formatRupiah.format(gajiLembur).replace(",00", "");
        String rupiahJumlah = formatRupiah.format(jumlahPenghasilan).replace(",00", "");

        labelPersenLembur.setText("Tarif Lembur (" + jamLembur + " jam): " + persenLembur + "%");
        labelGajiPokok.setText("Gaji Pokok: " + rupiahPokok);
        labelGajiLembur.setText("Gaji Lembur: " + rupiahLembur);
        labelJumlahPenghasilan.setText("Jumlah Penghasilan: " + rupiahJumlah);
        panelHasil.setVisible(true);
        pack();
        setSize(450, 600);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GajiKaryawan().setVisible(true));
    }
}