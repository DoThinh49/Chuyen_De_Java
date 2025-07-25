package lab5_6;

public class Student {
    private String maSV;
    private String hoSV;
    private String tenSV;
    private boolean phai;
    private String ngaySinh;
    private String noiSinh;
    private String maKH;
    private float hocBong;

    public Student(String maSV, String hoSV, String tenSV, boolean phai, String ngaySinh,
                   String noiSinh, String maKH, float hocBong) {
        this.maSV = maSV;
        this.hoSV = hoSV;
        this.tenSV = tenSV;
        this.phai = phai;
        this.ngaySinh = ngaySinh;
        this.noiSinh = noiSinh;
        this.maKH = maKH;
        this.hocBong = hocBong;
    }

    public String getMaSV() { return maSV; }
    public String getHoSV() { return hoSV; }
    public String getTenSV() { return tenSV; }
    public boolean isPhai() { return phai; }
    public String getNgaySinh() { return ngaySinh; }
    public String getNoiSinh() { return noiSinh; }
    public String getMaKH() { return maKH; }
    public float getHocBong() { return hocBong; }
}