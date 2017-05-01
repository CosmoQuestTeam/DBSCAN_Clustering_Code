package Rstar;

/**************************/
/* Built-in java packages */
/**************************/

/**
 * Sorted double link-list
 */
public class SortedLinList extends LinList {
    /*************************************************/
    /* Declaration/Initialization of class variables */
    /*************************************************/
    private boolean IamIncreasing = true; //true if sort increasingly

    /**
     * sort IamIncreasing or decreasing
     */
    public void set_sorting(boolean _increasing) {
        this.IamIncreasing = _increasing;
    }

    /**
     * Insert an object in a sorted list
     */
    public void insert(Object obj) {
        SLink sd;
        // allocate new Storage for object
        sd = new SLink(obj);
        // List empty
        if (this.myFirst == null) {
            this.myFirst = sd;
            this.myLast = sd;
            this.myCount = 1;
            sd.next = sd.prev = null;
            return;
        }
        // position the myCurrent pointer
        if (this.IamIncreasing)
            for (this.myCurrent = this.myFirst;
                 this.myCurrent != null &&
                         (((Comparable) this.myCurrent.d).compare(obj) < 0);
                 this.myCurrent = this.myCurrent.next)
                ;
        else
            for (this.myCurrent = this.myFirst;
                 this.myCurrent != null &&
                         (((Comparable) this.myCurrent.d).compare(obj) > 0);
                 this.myCurrent = this.myCurrent.next)
                ;
        // neues Element muss vor myCurrent eingefuegt werden -. Zeiger umbiegen
        if (this.myCurrent != null) {
            if (this.myCurrent == this.myFirst) {
                this.myFirst = sd;
            } else {
                //append just before this.myCurrent
                this.myCurrent.prev.next = sd;
            }
            sd.next = this.myCurrent;
            sd.prev = this.myCurrent.prev;
            this.myCurrent.prev = sd;
        } else
        // append the new element at the myLast
        {
            sd.prev = this.myLast;
            sd.next = null;
            this.myLast.next = sd;
            this.myLast = sd;
        }
        // adjust myCount and current_index;
        ++this.myCount;
        this.myCurrent_index = -1;
        if (this.isTraceable()) {
            System.out.println("SortedLinList.insert(" + obj + "): inserted.");
            this.check();
        }
    }
//-------------------------------------------------------------------------

    /**
     * Sort the list according to the input sorting order
     */
    public void sort(boolean _increasing) {
        this.set_sorting(_increasing);
        this.sort();
    }
//-------------------------------------------------------------------------

    /**
     * Sort the list according to my sorting order using bubble sort
     */
    public void sort() {
        //Use Bubble sort
        boolean should_swap = false;
        boolean should_continue = true;
        while (should_continue) {
            should_continue = false;  //assume no more swapping is needed
            SLink old_current = null; //myLast myCurrent
            for (this.myCurrent = this.myFirst; this.myCurrent != null;
                 this.myCurrent = this.myCurrent.next) {
                // check whether sorting is needed
                if (old_current != null) {
                    Comparable current_obj = (Comparable) this.myCurrent.d;
                    if (this.IamIncreasing)
                        should_swap = (current_obj.compare(old_current.d) > 0);
                    else
                        should_swap = (current_obj.compare(old_current.d) < 0);
                    if (should_swap) {
                        // do the swapping
                        Object temp = this.myCurrent.d;
                        this.myCurrent.d = old_current.d;
                        old_current.d = temp;
                        // The loop need continue after the swapping
                        should_continue = true;
                    }
                }
                old_current = this.myCurrent;
            }
        }
        this.myCurrent_index = -1;
        if (this.isTraceable()) {
            System.out.println("SortedLinList.sort(IamIncreasing=" + this.IamIncreasing + "): finished.");
            this.check();
        }
    }
//--------------------------------------------------------------------------

    /**
     * Check integrity of list, true means list is valid
     */
    public boolean check() {
        // LinList consistence ?
        if (super.check() == false)
            return false;
        // correct sorting ?
        Object prev_obj = null;
        for (SLink sd = this.myFirst; sd != null; sd = sd.next) {
            Comparable current_obj = (Comparable) sd.d;
            if (prev_obj != null)
                if ((this.IamIncreasing && (current_obj.compare(prev_obj) < 0))
                        || (!this.IamIncreasing && (current_obj.compare(prev_obj) > 0))) {
                    System.out.println("SortedLinList.check(): List unsorted");
                    return false;
                }
            prev_obj = current_obj;
        }
        return true;
    }
}
