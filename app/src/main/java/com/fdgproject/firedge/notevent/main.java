package com.fdgproject.firedge.notevent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;


public class main extends Activity {

    private ArrayList<Event> events = new ArrayList<Event>();
    private Adapter adp;
    //LayoutInflater inflater = LayoutInflater.from(this);

    /********************************************************************************************/
    /*                                                                                          */
    /*                                   on Methods...                                          */
    /*                                                                                          */
    /********************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initcomponents();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            return add();
        }
        return super.onOptionsItemSelected(item);
    }

    /*                                  Context Menu                                            */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        int index = info.position;
        Object o = info.targetView.getTag();
        Adapter.ViewHolder vh = (Adapter.ViewHolder)o;
        if(id == R.id.action_delete){
            delete(index);
        } else if(id == R.id.action_modify){
            edit(index);
        }
        return super.onContextItemSelected(item);
    }

    /********************************************************************************************/
    /*                                                                                          */
    /*                               Auxiliary Methods...                                       */
    /*                                                                                          */
    /********************************************************************************************/

    private void initcomponents(){
        String[] months = {getString(R.string.january), getString(R.string.february), getString(R.string.march),
                getString(R.string.april), getString(R.string.may), getString(R.string.june),
                getString(R.string.july), getString(R.string.august), getString(R.string.september),
                getString(R.string.october), getString(R.string.november), getString(R.string.december),};
        Event.setMonthList(months);
        generateEvents();

        adp = new Adapter(this, R.layout.list_event, events);
        final ListView lv = (ListView)findViewById(R.id.lv_pral);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View vista = inflater.inflate(R.layout.dialog_view, null);
                alert.setView(vista);
                TextView et1 = (TextView) vista.findViewById(R.id.tv_text);
                et1.setText(events.get(i).getText());
                TextView et2 = (TextView) vista.findViewById(R.id.tv_date);
                et2.setText(events.get(i).getDate());
                TextView et3 = (TextView) vista.findViewById(R.id.tv_time);
                et3.setText(events.get(i).getTime());
                alert.setNegativeButton(android.R.string.ok,null);
                alert.show();
            }
        });

        registerForContextMenu(lv);
    }

    private void toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void actualization(){
        Collections.sort(events);
        adp.notifyDataSetChanged();
    }

    private void generateEvents(){
        events.add(new Event(8, 3, 2013, "Día para recordar", 20, 0));
        events.add(new Event(15, 8, 2014, "Primer día de clase", 8, 15));
        events.add(new Event(7, 0, 2015, "Segundo trimestre", 8, 15));
        events.add(new Event(10, 2, 2015, "Examen Final PMDM", 10, 15));
        events.add(new Event(10,9,2014, "Fecha finalización del proyecto", 11, 14));
        events.add(new Event(20,9,2014, "Entrega del proyecto", 24,00));
        events.add(new Event(23,10,2014, "Cita Medico consulta 8", 12,25));
        Collections.sort(events);
    }

    /********************************************************************************************/
    /*                                                                                          */
    /*                                onClick Methods...                                        */
    /*                                                                                          */
    /********************************************************************************************/

    //Metodo para añadir objetos de tipo Evento al ArrayList y al ListView
    private boolean add(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(R.string.new_event));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialog_main, null);
        alert.setView(vista);
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText et_texto = (EditText) vista.findViewById(R.id.et_texto);
                        DatePicker dt_date = (DatePicker) vista.findViewById(R.id.dp_date);
                        TimePicker tp_time = (TimePicker) vista.findViewById(R.id.tp_time);
                        String txt = et_texto.getText().toString();
                        txt=txt.trim();
                        if(txt.length()>0) {
                            events.add(new Event(dt_date.getDayOfMonth(), dt_date.getMonth(),
                                    dt_date.getYear(), txt, tp_time.getCurrentHour(),
                                    tp_time.getCurrentMinute()));
                            actualization();
                            toast(getResources().getString(R.string.event_add));
                        } else {
                            toast(getResources().getString(R.string.event_notadd));
                        }
                    }
                });
        alert.setNegativeButton(android.R.string.cancel,null);
        alert.show();
        return true;
    }

    //Metodo para modificar los objetos de tipo Evento del ArrayList y cargarlos en ListView
    private boolean edit(final int index){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(R.string.menu_modify));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialog_main, null);
        alert.setView(vista);
        final EditText et;
        et = (EditText) vista.findViewById(R.id.et_texto);
        et.setText(events.get(index).getText());
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText et_texto = (EditText) vista.findViewById(R.id.et_texto);
                        DatePicker dt_date = (DatePicker) vista.findViewById(R.id.dp_date);
                        TimePicker tp_time = (TimePicker) vista.findViewById(R.id.tp_time);
                        String txt = et_texto.getText().toString();
                        txt=txt.trim();
                        if(txt.length()>0) {
                            events.set(index, new Event(dt_date.getDayOfMonth(), dt_date.getMonth(),
                                    dt_date.getYear(), txt, tp_time.getCurrentHour(),
                                    tp_time.getCurrentMinute()));
                            actualization();
                            toast(getResources().getString(R.string.event_modify));
                        } else {
                            toast(getResources().getString(R.string.event_notmodify));
                        }
                    }
                });
        alert.setNegativeButton(android.R.string.cancel,null);
        alert.show();
        return true;
    }

    public boolean delete(final int index){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(R.string.delete_event));
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        events.remove(index);
                        actualization();
                        toast(getResources().getString(R.string.event_deleted));
                    }
                });
        alert.setNegativeButton(android.R.string.cancel,null);
        alert.show();
        return true;
    }
}
