/* 
   Display dialog for user to enter new query
*/
package Rstar;

/**************************/
/* Built-in java packages */
/**************************/
import java.awt.*;

@SuppressWarnings("deprecation")
class QueryDialog extends Dialog 
{
    public QueryDialog (int querytype, Object controller) 
    {
        super( ((TreeCreation)controller).f, "New Query", true);

        t = (TreeCreation)controller;
        this.querytype = querytype;
        rt = t.rt;
        
        //qf = ((TreeCreation)controller).qf;
        //mbrF = qf.getMbr();

        setFont(new Font("Times", Font.PLAIN, 16));

        Panel iPanel = new Panel();
        switch(querytype)
        {
            case Constants.RANGEQUERY:
                input = new TextField[4];
                iPanel.setLayout(new GridLayout(5, 1));
                iPanel.add(new Label("Enter coordinates of the range:"));
                for (int i=0; i<4; i++)
                    iPanel.add(input[i] = new TextField(Integer.toString(i*10), 5));
                break;
            case Constants.POINTQUERY:
                input = new TextField[2];
                iPanel.setLayout(new GridLayout(3, 1));
                iPanel.add(new Label("Enter coordinates of the point:"));
                for (int i=0; i<2; i++)
                    iPanel.add(input[i] = new TextField(Integer.toString(i*10), 5));
                break;
            case Constants.CIRCLEQUERY:
                input = new TextField[3];
                iPanel.setLayout(new GridLayout(5, 1));
                iPanel.add(new Label("Enter coordinates of the circle center:"));
                for (int i=0; i<2; i++)
                    iPanel.add(input[i] = new TextField(Integer.toString((i+20)*10), 5));
                iPanel.add(new Label("Enter value of the circle radius:"));
                iPanel.add(input[2] = new TextField(Integer.toString(10), 5));
                break;
            case Constants.RINGQUERY:
                input = new TextField[4];
                iPanel.setLayout(new GridLayout(6, 1));
                iPanel.add(new Label("Enter coordinates of the ring center:"));
                for (int i=0; i<2; i++)
                    iPanel.add(input[i] = new TextField(Integer.toString((i+20)*10), 5));
                iPanel.add(new Label("Enter value of the circle two radius's:"));
                for (int i=2; i<4; i++)
                    iPanel.add(input[i] = new TextField(Integer.toString(i*10), 5));
                break;
            
        }
        
        add("Center", iPanel);

        Panel bPanel = new Panel();
        bPanel.add(new Button("Ok"));
        bPanel.add(new Button("Cancel"));
        add("South", bPanel);

        if (querytype == Constants.CONSTQUERY) setSize(300, 700);
        	else setSize(300, 200);
        setLocation(300, 0);
    }

    public boolean action (Event e, Object o) {
        if (o.equals("Ok")) {
            processInput();
        }
        else if (o.equals("Cancel")) {
            this.dispose();
            //qf.show();
        }
        else
            super.action (e, o);
        return true;
    }

    public boolean handleEvent (Event e) {
        if (e.id == Event.WINDOW_DESTROY && e.target == this)
            dispose();
        else
            return super.handleEvent (e);
        return true;
    }

    private void processInput() {
        SortedLinList res;
        PPoint p;
        float[] mbr;
        
        switch (querytype)
        {
            case Constants.RANGEQUERY:
                mbr = new float[4];
                for (int i=0; i<4; i++)
                    try
                    {
                        mbr[i] = Float.valueOf(input[i].getText()).floatValue();
                    }
                    catch (NumberFormatException e)
                    {
                        requestFocus();
                    }

                res = new SortedLinList();
                rt.rangeQuery(mbr, res);
                System.out.println("query finished!");
                //(new QueryFrame("Range - " + input[0].getText() + ", "+ input[1].getText() + ", "+ input[2].getText() + ", "+ input[3].getText() + " #PA=" + rt.page_access, res)).setVisible();
                
                /*
                t.f.framedArea.area.queryres = new SortedLinList();
                for (Object obj = res.get_first(); obj != null; obj = res.get_next())
		        {
		        	t.f.framedArea.area.queryres.append(obj);
	            }*/

                t.f.framedArea.area.queryres = res;
                t.f.framedArea.area.drawRange(mbr);
                break;
            case Constants.POINTQUERY:
                p = new PPoint(2);
                
                for (int i=0; i<2; i++)
                    try
                    {
                        p.data[i] = Float.valueOf(input[i].getText()).floatValue();
                    }
                    catch (NumberFormatException e)
                    {
                        requestFocus();
                    }

                res = new SortedLinList();
                rt.point_query(p, res);
                t.f.framedArea.area.queryres = res;
                (new QueryFrame("Point - " + input[0].getText() + ", "+ input[1].getText()+ " #PA=" + rt.page_access, res)).show();
                break;
            case Constants.CIRCLEQUERY:
                p = new PPoint(2);
                
                for (int i=0; i<2; i++)
                    try
                    {
                        p.data[i] = Float.valueOf(input[i].getText()).floatValue();
                    }
                    catch (NumberFormatException e)
                    {
                        requestFocus();
                    }
                    
                float radius=0;
                try
                {
                    radius = Float.valueOf(input[2].getText()).floatValue();
                }
                catch (NumberFormatException e)
                {
                    requestFocus();
                }
                
                res = new SortedLinList();
                rt.rangeQuery(p, radius, res);
                t.f.framedArea.area.queryres = res;
                t.f.framedArea.area.drawRing(p, radius, 0);
                (new QueryFrame("Circle - " + input[0].getText() + ", "+ input[1].getText() + ", "+ input[2].getText()+ " #PA=" + rt.page_access, res)).show();
                break;
            case Constants.RINGQUERY:
                p = new PPoint(2);
                
                for (int i=0; i<2; i++)
                    try
                    {
                        p.data[i] = Float.valueOf(input[i].getText()).floatValue();
                    }
                    catch (NumberFormatException e)
                    {
                        requestFocus();
                    }
                    
                float radius1=0;
                try
                {
                    radius1 = Float.valueOf(input[2].getText()).floatValue();
                }
                catch (NumberFormatException e)
                {
                    requestFocus();
                }
                
                float radius2=0;
                try
                {
                    radius2 = Float.valueOf(input[3].getText()).floatValue();
                }
                catch (NumberFormatException e)
                {
                    requestFocus();
                }
                
                res = new SortedLinList();
                rt.ringQuery(p, radius1, radius2 , res);
                t.f.framedArea.area.drawRing(p, radius1, radius2);
                t.f.framedArea.area.queryres = res;
                (new QueryFrame("Ring - " + input[0].getText() + ", "+ input[1].getText() + ", "+ input[2].getText() + ", "+ input[3].getText()+ " #PA=" + rt.page_access, res)).show();
                break;
        }
        this.dispose();
    }

    private TextField input[];
    private Checkbox constraints[];
    //private float mbrF[] = new float[4];
    //private QueryFrame qf;
    private TreeCreation t;
    private int querytype;
    private RTree rt;
}
