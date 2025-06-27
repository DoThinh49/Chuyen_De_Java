package lab3;

import java.util.ArrayList;

public class PhongBan {
    private String ten;
    private ArrayList<NhanVien> danhSachNhanVien = new ArrayList<>();

    public PhongBan(String ten) {
        this.ten = ten;
    }

    public String getTen() {
        return ten;
    }

    public ArrayList<NhanVien> getDanhSachNhanVien() {
        return danhSachNhanVien;
    }

    public void addNhanVien(NhanVien nv) {
        danhSachNhanVien.add(nv);
    }

    public void removeNhanVien(NhanVien nv) {
        danhSachNhanVien.remove(nv);
    }

    public void updateNhanVien(NhanVien nv, int index) {
        NhanVien old = danhSachNhanVien.get(index);
        old.updateNV(nv);
    }
}
