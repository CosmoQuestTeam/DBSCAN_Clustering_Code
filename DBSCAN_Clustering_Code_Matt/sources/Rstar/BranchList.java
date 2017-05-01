package Rstar;

class BranchList implements Sortable {
    boolean section;
    float mindist;
    float minmaxdist;
    int entry_number;

    private float compute_erg(Sortable s, int sortCriterion) {
        float erg = (float) 0.0;
        switch (sortCriterion) {
            case Constants.SORT_MINDIST:
                erg = this.mindist - ((BranchList) s).mindist;
                break;
        }
        return erg;
    }

    public boolean lessThan(Sortable s, int sortCriterion) {
        float erg = compute_erg(s, sortCriterion);
        return erg < (float) 0.0;
    }

    public boolean greaterThan(Sortable s, int sortCriterion) {
        float erg = compute_erg(s, sortCriterion);
        return erg > (float) 0.0;
    }
}
