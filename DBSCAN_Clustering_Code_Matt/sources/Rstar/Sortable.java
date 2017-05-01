/**
 * interface Sortable should be implemented at each Object used in
 * the Constants.quickSort() algorithm. In this project classes SortMbr
 * and BranchList implement it.
 */
package Rstar;

public interface Sortable {
    // returns true if this<s according to the sortCriterion
    boolean lessThan(Sortable s, int sortCriterion);

    // returns true if this>s according to the sortCriterion
    boolean greaterThan(Sortable s, int sortCriterion);
}