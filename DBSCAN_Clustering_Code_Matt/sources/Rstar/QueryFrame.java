/* 
   Handle the Query Result Window
*/
package Rstar;

/**************************/
/* Built-in java packages */
/**************************/
import java.awt.*;

@SuppressWarnings("deprecation")
public class QueryFrame extends Frame 
{
    private TextArea ta;

    QueryFrame (String title, SortedLinList res)
    {
        super("Query: " + title);

        setSize(300,250);
        setLocation(350,0);
        ta = new TextArea(res.toString());
        add("Center",ta);
    }
    
    public boolean handleEvent (Event e) 
    {
        if (e.id == Event.WINDOW_DESTROY && e.target == this)
            dispose();
        else
            return super.handleEvent (e);
        return true;
    }
}
