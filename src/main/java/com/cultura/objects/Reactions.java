package com.cultura.objects;

public enum Reactions {
    NON(0, "src/main/resources/com/cultura/images/thumbs-up.png"),
    LIKE(1,"src/main/resources/com/cultura/images/thumbs-up.png"),
    LOVE(2,"src/main/resources/com/cultura/images/pink-heart.png"),
    SMILE(3,"src/main/resources/com/cultura/images/smiling-face.png"),
    PARTY(4,"src/main/resources/com/cultura/images/partying-face.png"),
    WOW(5,"src/main/resources/com/cultura/images/hushed-face.png");

    private int id;
    private String imgSrc;

    Reactions(int id, String imgSrc) {
        this.id = id;
        this.imgSrc = imgSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
