package com.kcirque.mvvpexample;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    private OnItemClickListener itemClickListener;

    protected NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getTitle().equals(t1.getTitle())
                    && note.getDescription().equals(t1.getDescription());
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext().getApplicationContext())
                .inflate(R.layout.note_item, viewGroup, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
        Note note = getItem(i);
        noteHolder.textViewTitle.setText(note.getTitle());
        noteHolder.textViewDescription.setText(note.getDescription());
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public Note getNoteAt(int position){
        return getItem(position);
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null && getAdapterPosition() != -1) {
                        itemClickListener.onItemClick(getItem(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }
}
