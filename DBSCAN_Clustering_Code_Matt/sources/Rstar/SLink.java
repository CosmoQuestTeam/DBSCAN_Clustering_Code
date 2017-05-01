package Rstar;

public class SLink {
    public Object d = null;
    public SLink next = null;
    public SLink prev = null;
    public SLink() {
    }
    public SLink(Object anObject) {
        this.d = anObject;
    }
}