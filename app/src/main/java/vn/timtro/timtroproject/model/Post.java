package vn.timtro.timtroproject.model;

import java.io.Serializable;

public class Post  implements Serializable {

    private String id,idUser, tieuDe, gia, diaChi, soDienThoai, dienTich,danhMuc, moTa, timePost;

    public Post() {
    }


    public Post(String id, String idUser,String tieuDe, String gia, String diaChi, String soDienThoai, String dienTich,String danhMuc, String moTa,String timePost) {
        this.id = id;
        this.idUser = idUser;
        this.tieuDe = tieuDe;
        this.gia = gia;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.dienTich = dienTich;
        this.danhMuc = danhMuc;
        this.moTa = moTa;
        this.timePost = timePost;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getTimePost() {
        return timePost;
    }

    public void setTimePost(String timePost) {
        this.timePost = timePost;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
