package domain;

public class Image {

    private String href;
    private boolean templated;

    public Image(String href, boolean templated) {
        this.href = href;
        this.templated = templated;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setTemplated(boolean templated) {
        this.templated = templated;
    }



    @Override
    public String toString() {
        return "Image{" +
                "href='" + href + '\'' +
                ", templated=" + templated +
                '}';
    }

}
