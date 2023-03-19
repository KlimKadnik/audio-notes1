package android.example.firsttestvkproject;

public class Recording {

    private String name;
    private String dateAndTimeOfRecording;
    private String listeningTime;
    private String recordingTime;

    public Recording(String name, String dateAndTimeOfRecording, String listeningTime, String recordingTime) {
        this.name = name;
        this.dateAndTimeOfRecording = dateAndTimeOfRecording;
        this.listeningTime = listeningTime;
        this.recordingTime = recordingTime;
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
}
