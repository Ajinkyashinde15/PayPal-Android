package tennis.parks.com.parkstennis;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>
{

    private final Activity context;
    private final String[] namelist;

    public CustomList(Activity context, String[] namelist) {
        super(context, R.layout.simplerow, namelist);
        this.context = context;
                this.namelist = namelist;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.simplerow, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtnamel);

        txtTitle.setText(namelist[position]);
        return rowView;
    }
}