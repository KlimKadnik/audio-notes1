package android.example.firsttestvkproject;

import java.io.Serializable;

public class AudioModel implements Serializable {
    private String name;
    private String dateAndTimeOfRecording;
    private String listeningTime;
    private String recordingTime;
    private String path;

    public AudioModel(String name, String dateAndTimeOfRecording, String recordingTime, String path) {
        this.name = name;
        this.dateAndTimeOfRecording = dateAndTimeOfRecording;
        this.recordingTime = recordingTime;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAndTimeOfRecording() {
        return dateAndTimeOfRecording;
    }

    public void setDateAndTimeOfRecording(String dateAndTimeOfRecording) {
        this.dateAndTimeOfRecording = dateAndTimeOfRecording;
    }

    public String getListeningTime() {
        return listeningTime;
    }

    public void setListeningTime(String listeningTime) {
        this.listeningTime = listeningTime;
    }

    public String getRecordingTime() {
        return recordingTime;
    }

    public void setRecordingTime(String recordingTime) {
        this.recordingTime = recordingTime;
    }

    public String getPath() {
        return "file:///" + path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
