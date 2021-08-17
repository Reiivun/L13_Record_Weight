package sg.edu.rp.c346.id20027025.l13_record_weight_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    EditText etDescription, etWeightNew;
    Button btnInsert, btnShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_main));

        etDescription = findViewById(R.id.etDescription);
        etWeightNew = findViewById(R.id.etWeightNew);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String description = etDescription.getText().toString().trim();
                if (etWeightNew.length() == 0) {
                    Toast.makeText(SecondActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                    return;
                }

                String weight_str = etWeightNew.getText().toString().trim();
                int weight = Integer.valueOf(weight_str);

                DBHelper dbh = new DBHelper(SecondActivity.this);
                long result = dbh.insertWeight(weight, description);

                if (result != -1) {
                    Toast.makeText(SecondActivity.this, "Weight entry inserted", Toast.LENGTH_LONG).show();
                    etWeightNew.setText("");
                    etDescription.setText("");
                } else {
                    Toast.makeText(SecondActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(SecondActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });
    }
}