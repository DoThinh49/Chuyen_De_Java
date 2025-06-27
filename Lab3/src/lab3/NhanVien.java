package lab3;

public class NhanVien {
    private String ma, ho, ten, phai, tuoi, luong;

    public NhanVien(String ma, String ho, String ten, String phai, String tuoi, String luong) {
        this.ma = ma;
        this.ho = ho;
        this.ten = ten;
        this.phai = phai;
        this.tuoi = tuoi;
        this.luong = luong;
    }

    public String getMa() { return ma; }
    public String getHo() { return ho; }
    public String getTen() { return ten; }
    public String getPhai() { return phai; }
    public String getTuoi() { return tuoi; }
    public String getLuong() { return luong; }

    public void updateNV(NhanVien nv) {
        this.ho = nv.ho;
        this.ten = nv.ten;
        this.phai = nv.phai;
        this.tuoi = nv.tuoi;
        this.luong = nv.luong;
    }
}
