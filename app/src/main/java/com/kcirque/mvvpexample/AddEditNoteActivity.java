package com.kcirque.mvvpexample;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_NOTE = "com.kcirque.mvvpexample.EXTRA_NOTE";

    private MaterialEditText editTextTitle;
    private MaterialEditText editTextDescription;

    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NOTE)) {
            Note note = (Note) intent.getSerializableExtra(EXTRA_NOTE);
            id = note.getId();
            editTextTitle.setText(note.getTitle());
            editTextDescription.setText(note.getDescription());
            setTitle("Edit Note");
        } else {
            setTitle("Add Note");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        if (TextUtils.isEmpty(editTextTitle.getText())) {
            editTextTitle.setError("Please enter title");
            return;
        }
        if (TextUtils.isEmpty(editTextDescription.getText())) {
            editTextDescription.setError("Please enter description");
        }

        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        Note note = new Note(title, description);
        if (id != -1) {
            note.setId(id);
        }
        Intent intent = new Intent(AddEditNoteActivity.this, MainActivity.class);
        intent.putExtra(EXTRA_NOTE, note);
        setResult(RESULT_OK, intent);
        finish();
    }
}
