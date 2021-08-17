package sg.edu.rp.c346.id20027025.l13_record_weight_new;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Weight> weightList;
    CustomAdapter adapter;
    int requestCode = 9;
    Button btnShowEntries;
    Button btnInsertNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.lv);
        btnShowEntries = (Button) this.findViewById(R.id.btnShowEntries);
        btnInsertNew = (Button) this.findViewById(R.id.btnInsertNew);


        DBHelper dbh = new DBHelper(this);
        weightList = dbh.getAllWeight();
        dbh.close();

        adapter = new CustomAdapter(this, R.layout.row, weightList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.entry_edit, null);

                final EditText etDescriptionDia = viewDialog.findViewById(R.id.etDescriptionDia);
                final EditText etWeightDia = viewDialog.findViewById(R.id.etWeightDia);
                final EditText etIDDia = viewDialog.findViewById(R.id.etIDDia);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                myBuilder.setTitle("Edit Entry");
                myBuilder.setView(viewDialog);
                myBuilder.setCancelable(false);
                final Weight currentSong = weightList.get(position);
                etIDDia.setText(currentSong.getId() + "");
                etDescriptionDia.setText(currentSong.getDescription() + "");
                etWeightDia.setText(currentSong.getWeight() + "");

                myBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent y = getIntent();
                        DBHelper dbh = new DBHelper(MainActivity.this);
                        //currentSong.setName(etName.getText().toString().trim());
                        currentSong.setDescription(etDescriptionDia.getText().toString().trim());

                        int weight = 0;
                        try {
                            weight = Integer.valueOf(etWeightDia.getText().toString().trim());
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Invalid weight kg", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        currentSong.setWeight(weight);

                        int result = dbh.updateWeight(currentSong);
                        if (result > 0) {
                            Toast.makeText(MainActivity.this, "Weight updated", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Weight failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper dbh = new DBHelper(MainActivity.this);
                        int result = dbh.deleteWeight(currentSong.getId());
                        if (result > 0) {
                            Toast.makeText(MainActivity.this, "Weight entry deleted", Toast.LENGTH_SHORT).show();
                            Intent y = new Intent();
                            setResult(RESULT_OK);
                        } else {
                            Toast.makeText(MainActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                myBuilder.setNeutralButton("Cancel", null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnInsertNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


        btnShowEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                weightList.clear();
                weightList.addAll(dbh.getAllWeightBy(50));
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == RESULT_OK) {
            DBHelper dbh = new DBHelper(this);
            weightList.clear();
            weightList.addAll(dbh.getAllWeight());
            dbh.close();
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}