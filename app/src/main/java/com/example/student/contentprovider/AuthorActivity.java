package com.example.student.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class AuthorActivity extends AppCompatActivity {
    EditText editText_maso, editText_diachi, editText_email, editText_hoten;
    Button button_select, button_save, button_update, button_delete, button_exit;
    GridView gridView_display;
    DBHelper dbHelper;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        mapview();

        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list_string = new ArrayList<>();
                String uri = "content://com.example.student.contentprovider/authordata";
                Uri author = Uri.parse(uri);
                Cursor cursor = getContentResolver().query(author, null, null, null, "name");
                if (cursor != null) {
                    cursor.moveToFirst();
                    do {
                        list_string.add(cursor.getInt(0) + "");
                        list_string.add(cursor.getString(1) + "");
                        list_string.add(cursor.getString(2) + "");
                        list_string.add(cursor.getString(3) + "");
                    } while (cursor.moveToNext());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AuthorActivity.this,
                            android.R.layout.simple_list_item_1, list_string);
                    gridView_display.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Không có kết quả !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("id_author", editText_maso.getText().toString());
                values.put("address", editText_diachi.getText().toString());
                values.put("name", editText_hoten.getText().toString());
                values.put("email", editText_email.getText().toString());
                String uri = "content://com.example.student.contentprovider/authordata";
                Uri author = Uri.parse(uri);
                Uri insert_uri = getContentResolver().insert(author, values);
                Toast.makeText(getApplicationContext(), "Lưu thành công !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mapview(){
        editText_maso = (EditText) findViewById(R.id.editText_maso);
        editText_hoten = (EditText) findViewById(R.id.editText_hoten);
        editText_diachi = (EditText) findViewById(R.id.editText_diachi);
        editText_email = (EditText) findViewById(R.id.editText_email);

        button_select = (Button) findViewById(R.id.button_select);
        button_save = (Button) findViewById(R.id.button_save);
        button_update = (Button) findViewById(R.id.button_update);
        button_delete = (Button) findViewById(R.id.button_delete);
        button_exit = (Button) findViewById(R.id.button_exit);

        gridView_display = (GridView) findViewById(R.id.gridView_display);
        dbHelper = new DBHelper(AuthorActivity.this);
    }
}
