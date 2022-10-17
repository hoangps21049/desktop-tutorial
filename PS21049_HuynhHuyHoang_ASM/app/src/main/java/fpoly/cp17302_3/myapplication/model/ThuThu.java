package fpoly.cp17302_3.myapplication.model;

public class ThuThu {
    private String matt;
    private String hoten;
    private String matkhau;
    private String loaitaikhoan;

    public ThuThu(String matt, String hoten, String matkhau, String loaitaikhoan) {
        this.matt = matt;
        this.hoten = hoten;
        this.matkhau = matkhau;
        this.loaitaikhoan = loaitaikhoan;
    }

    public String getMatt() {
        return matt;
    }

    public void setMatt(String matt) {
        this.matt = matt;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getLoaitaikhoan() {
        return loaitaikhoan;
    }

    public void setLoaitaikhoan(String loaitaikhoan) {
        this.loaitaikhoan = loaitaikhoan;
    }
}
