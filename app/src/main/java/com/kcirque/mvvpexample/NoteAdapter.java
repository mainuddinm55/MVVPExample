package com.kcirque.mvvpexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> allNotes = new ArrayList<>();

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext().getApplicationContext())
                .inflate(R.layout.note_item, viewGroup, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
        Note note = allNotes.get(i);
        noteHolder.textViewTitle.setText(note.getTitle());
        noteHolder.textViewDescription.setText(note.getDescription());
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    public void setAllNotes(List<Note> allNotes) {
        this.allNotes = allNotes;
        notifyDataSetChanged();
    }

    public static class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
        }
    }
}
