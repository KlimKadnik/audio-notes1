package android.example.firsttestvkproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String fileName;
    private String thisFileNameWillBeRename;


    private ImageButton playMusicButton;
    private ImageButton pauseMusicButton;
    private ImageButton microphoneRecordButton;
    private ImageButton microphoneStopRecordButton;
    private TextView noRecordingsTextView;

    private RecyclerView recyclerView;
    private ArrayList<AudioModel> recordings = new ArrayList<>();

    private ArrayList<AudioModel> songsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noRecordingsTextView = findViewById(R.id.no_recordings);
        recyclerView = findViewById(R.id.list);

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATE_ADDED,
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection,null,null);

        while (cursor.moveToNext()) {
            AudioModel songData = new AudioModel(cursor.getString(0), cursor.getString(3), cursor.getString(2), cursor.getString(1));
            if (new File(songData.getPath()).exists())
                songsList.add(songData);
        }

        if (songsList.size() == 0) {
            noRecordingsTextView.setVisibility(View.VISIBLE);
        } else {
            //recyclerview
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            RecordingAdapter recordingAdapter = new RecordingAdapter(recordings, this);
            recyclerView.setAdapter(new RecordingAdapter(songsList, getApplicationContext()));
        }




        // устанавливаем для списка адаптер


        uploadRecordingsTo();
//        recyclerView.getLayoutManager() = LinearLayoutManager(this);
//        playMusicButton = findViewById(R.id.ib_play_music_button);
//        pauseMusicButton = findViewById(R.id.ib_pause_music_button);
//        microphoneRecordButton = findViewById(R.id.ib_microphone_record_button);
//        microphoneStopRecordButton = findViewById(R.id.ib_microphone_stop_record_button);


        if (!checkStoragePermission()){
            requestStoragePermission();
            return;
        }

        if (!checkRecordPermission()){
            requestRecordPermission();
            return;
        }



//        View.OnClickListener onAudioPlayClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // todo play audio
//                playMusicButton.setVisibility(View.GONE);
//                pauseMusicButton.setVisibility(View.VISIBLE);
//            }
//        };
//
//        View.OnClickListener onAudioPauseClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // todo pause audio
//                pauseMusicButton.setVisibility(View.GONE);
//                playMusicButton.setVisibility(View.VISIBLE);
//            }
//        };
//
//        View.OnClickListener onRecordClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // todo record
//                microphoneRecordButton.setVisibility(View.GONE);
//                microphoneStopRecordButton.setVisibility(View.VISIBLE);
//            }
//        };
//
//        View.OnClickListener onStopRecordClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // todo stop record
//                microphoneStopRecordButton.setVisibility(View.GONE);
//                microphoneRecordButton.setVisibility(View.VISIBLE);
//            }
//        };
//
//        playMusicButton.setOnClickListener(onAudioPlayClickListener);
//        pauseMusicButton.setOnClickListener(onAudioPauseClickListener);
//        microphoneRecordButton.setOnClickListener(onRecordClickListener);
//        microphoneStopRecordButton.setOnClickListener(onStopRecordClickListener);





    }

    private void uploadRecordingsTo() {

        ArrayList<File> files = new ArrayList<>();


    }


    public void recordStart(View v) {

        try {
            releaseRecorder();

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            thisFileNameWillBeRename = getRecordingFilePath();
            mediaRecorder.setOutputFile(thisFileNameWillBeRename);
            mediaRecorder.prepare();

            microphoneRecordButton = findViewById(R.id.ib_microphone_record_button);
            microphoneStopRecordButton = findViewById(R.id.ib_microphone_stop_record_button);
            microphoneRecordButton.setVisibility(View.GONE);
            microphoneStopRecordButton.setVisibility(View.VISIBLE);

            mediaRecorder.start();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void recordStop(View v) {
        if (mediaRecorder != null) {

            microphoneRecordButton = findViewById(R.id.ib_microphone_record_button);
            microphoneStopRecordButton = findViewById(R.id.ib_microphone_stop_record_button);
            microphoneStopRecordButton.setVisibility(View.GONE);
            microphoneRecordButton.setVisibility(View.VISIBLE);

            mediaRecorder.pause();

            //Получаем вид с файла prompt.xml, который применим для диалогового окна:
            LayoutInflater li = LayoutInflater.from(context);
            //View promptsView = li.inflate(R.layout.prompt, null);

            //Создаем AlertDialog
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

            //Настраиваем prompt.xml для нашего AlertDialog:
           // mDialogBuilder.setView(promptsView);

            //Настраиваем отображение поля для ввода текста в открытом диалоге:
            //final EditText userInput = promptsView.findViewById(R.id.input_text);

            //Настраиваем сообщение в диалоговом окне:
            mDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Вводим текст и отображаем в строке ввода на основном экране:
                          //          fileName = userInput.getText().toString();
                                    fileName = convertCyrillicToLatin(fileName);

                                    ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
                                    File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);

                                    if (musicDirectory.exists()){
                                        File fileToRename = new File(thisFileNameWillBeRename);
                                        System.out.println("\n\n\n\n\n\nthisFileNameWillBeRename ==========" + thisFileNameWillBeRename + "\n\n\n\n\n\n");
                                        File FileWithNewName = new File(musicDirectory, fileName + ".3gpp");
                                        System.out.println("\n\n\n\n\n\nFIlENAME ===========" + fileName + "\n\n\n\n\n");
                                        System.out.println("\n\n\n\n\n\nfileToRename.exists() ===========" + fileToRename.exists() + "\n\n\n\n\n");

                                        if (fileToRename.exists())
                                            fileToRename.renameTo(FileWithNewName);
                                    }
                                }
                            })
                    .setNegativeButton("Отмена",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //todo добавить диалоговое окно с подтверждением действия

                                    File fileToDelete = new File(thisFileNameWillBeRename);
                                    fileToDelete.delete();
                                    dialog.cancel();
                                }
                            });

            //Создаем AlertDialog:
            AlertDialog alertDialog = mDialogBuilder.create();

            //и отображаем его:
            alertDialog.show();

            //todo говорить что имя уже занято

//            while(fileName.equals("null")) {
//                alertDialog.show();
//            }

            mediaRecorder.stop();

        }
    }
// todo
//    public void playStart(View v) {
//        try {
//            releasePlayer();
//            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setDataSource(fileName);
//            mediaPlayer.prepare();
//
////            playMusicButton = findViewById(R.id.ib_play_music_button);
////            pauseMusicButton = findViewById(R.id.ib_pause_music_button);
//            playMusicButton.setVisibility(View.GONE);
//            pauseMusicButton.setVisibility(View.VISIBLE);
//
//            mediaPlayer.start();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void playStop(View v) {
//        if (mediaPlayer != null) {
//
////            playMusicButton = findViewById(R.id.ib_play_music_button);
////            pauseMusicButton = findViewById(R.id.ib_pause_music_button);
//            pauseMusicButton.setVisibility(View.GONE);
//            playMusicButton.setVisibility(View.VISIBLE);
//
//            mediaPlayer.stop();
//
//        }
//    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        releaseRecorder();
    }






    // Permissions

    boolean checkStoragePermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this, "Чтение хранилища необходимо для работы приложения. Пожалуйста, дайте к нему допуск в настройках", Toast.LENGTH_SHORT).show();
        } else
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
    }


    boolean checkRecordPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public static String convertCyrillicToLatin(String message){
        char[] abcCyr =   {' ','а','б','в','г','д','ѓ','е', 'ж','з','ѕ','и','ј','к','л','љ','м','н','њ','о','п','р','с','т', 'ќ','у', 'ф','х','ц','ч','џ','ш', 'А','Б','В','Г','Д','Ѓ','Е', 'Ж','З','Ѕ','И','Ј','К','Л','Љ','М','Н','Њ','О','П','Р','С','Т', 'Ќ', 'У','Ф', 'Х','Ц','Ч','Џ','Ш','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','/','-'};
        String[] abcLat = {" ","a","b","v","g","d","]","e","zh","z","y","i","j","k","l","q","m","n","w","o","p","r","s","t","'","u","f","h", "c",";", "x","{","A","B","V","G","D","}","E","Zh","Z","Y","I","J","K","L","Q","M","N","W","O","P","R","S","T","KJ","U","F","H", "C",":", "X","{", "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","/","-"};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            for (int x = 0; x < abcCyr.length; x++ ) {
                if (message.charAt(i) == abcCyr[x]) {
                    builder.append(abcLat[x]);
                }
            }
        }
        return builder.toString();
    }

    void requestRecordPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.RECORD_AUDIO)){
            Toast.makeText(MainActivity.this, "Запись микрофона необходима для работы приложения. Пожалуйста, дайте к ней допуск в настройках", Toast.LENGTH_SHORT).show();
        } else
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 456);
    }

    private String getRecordingFilePath(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        String imagerFileName = "this file name will be rename";

        int nameChangingCounter = 0;

        while (new File(musicDirectory, imagerFileName + ".3gpp").exists()){
            imagerFileName += nameChangingCounter++;
        }

        File file = new File(musicDirectory, imagerFileName + ".3gpp");
        return file.getPath();
    }

//
//    public static File commonDocumentDirPath(String FolderName) {
//        File dir = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + FolderName);
//        } else {
//            dir = new File(Environment.getExternalStorageDirectory() + "/" + FolderName);
//        }
//
//
//        if (!dir.exists()) {
//            boolean success = dir.mkdirs();
//            if (!success) {
//                dir = null;
//            }
//        }
//        return dir;
//    }

}













