package beans;

public class Image {

    private int ID;
    private String path;
    private int postID;

    public Image() {}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPostID() { return postID; }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}
