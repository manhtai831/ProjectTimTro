package vn.timtro.timtroproject.model;

public class Image {
    String idImage,idPost, link;

    public Image() {
    }

    public Image(String idImage,String idPost, String link) {
        this.idImage = idImage;
        this.idPost = idPost;
        this.link = link;
    }

    public Image(String idPost, String link) {
        this.idPost = idPost;
        this.link = link;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
