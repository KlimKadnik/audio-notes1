package android.example.firsttestvkproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecordingAdapter extends RecyclerView.Adapter<RecordingAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<AudioModel> recordings;


    public RecordingAdapter(ArrayList<AudioModel> recordings, Context context) {
        this.recordings = recordings;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public RecordingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recordings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordingAdapter.ViewHolder holder, int position) {
        AudioModel songData = recordings.get(position);
        holder.nameView.setText(songData.getName());

    }


//        Recording recording = recordings.get(position);
//        holder.nameView.setText(recording.getName());
//        holder.dateAndTimeOfRecordingView.setText(recording.getDateAndTimeOfRecording());
//        holder.listeningTimeView.setText(recording.getListeningTime());
//        holder.recordingTimeView.setText(recording.getRecordingTime());


    @Override
    public int getItemCount() {
        return recordings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        final TextView dateAndTimeOfRecordingView;
        final TextView listeningTimeView;
        final TextView recordingTimeView;

        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
            dateAndTimeOfRecordingView = view.findViewById(R.id.date_and_time_of_recording);
            listeningTimeView = view.findViewById(R.id.listening_time);
            recordingTimeView = view.findViewById(R.id.recording_time);

        }
    }
}










