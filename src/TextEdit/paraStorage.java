package TextEdit;

public class paraStorage {
    private int id_;
    private int start_;
    private int size_;

    paraStorage(int id) {
        id_ = id;
        size_ = 0;
        start_ =0;
    }

    public int getID() {
        return this.id_;
    }

    public int getStart() {
        return this.start_;
    }

    public int getSize() {
        return this.size_;
    }

    public void setID(int id) {
        id_ = id;
    }

    public void setStart(int start) {
        start_ = start;
    }

    public void setSize(int size) {
        size_ = size;
    }
}




