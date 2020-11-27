package vn.timtro.timtroproject.model;

public class Post  {
    private String[] image;
    private String tieuDe, gia, diaChi, soDienThoai, dienTich, moTa;

    public Post() {
    }

    public Post(String[] image, String tieuDe, String gia, String diaChi, String soDienThoai, String dienTich, String moTa) {
        this.image = image;
        this.tieuDe = tieuDe;
        this.gia = gia;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.dienTich = dienTich;
        this.moTa = moTa;
    }

    public String[] getImage() {
        return image;
    }

    public void setImage(String[] image) {
        this.image = image;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDienTich() {
        return dienTich;
    }

    public void setDienTich(String dienTich) {
        this.dienTich = dienTich;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
