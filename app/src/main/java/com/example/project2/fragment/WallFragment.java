package com.example.project2.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.R;
import com.example.project2.adapter.WallAdapter;
import com.example.project2.model.ImageUtil;
import com.example.project2.model.WallMessage;
import com.example.project2.repository.WallRepository;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class WallFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 222;
    private static final int REQUEST_CAMERA_PHOTO = 333;
    private static final  String USERNAME_KEY ="userNameKey";
    private static final String INDEX_ID_KEY ="indexIdKey";

    @BindView(R.id.recycleViewWall)
    RecyclerView recyclerViewWall;
    @BindView(R.id.cameraWall)
    FloatingActionButton cameraBtn;
    @BindView(R.id.sendWall)
    FloatingActionButton sendBtn;
    @BindView(R.id.messageWall)
    TextInputEditText textWall;


    private WallAdapter wallAdapter;
    private WallRepository wallRepository;
    private StorageReference storageReference;
    private File file;
    private String username;
    private String index;

    public static WallFragment newInstance(){
        WallFragment wallFragment = new WallFragment();
        return wallFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.fragment_wall,container,false);
        ButterKnife.bind(this,view);
        init(view);
        observers();
        return view;
    }

    private void init(View view){

        this.wallRepository = new WallRepository();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        this.storageReference = firebaseStorage.getReference().child("photos");

        wallAdapter = new WallAdapter();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        this.recyclerViewWall.setLayoutManager(manager);
        this.recyclerViewWall.setAdapter(wallAdapter);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);

        username = sharedPreferences.getString(USERNAME_KEY,null);
        index = sharedPreferences.getString(INDEX_ID_KEY,null);



    }

    private void observers(){

        this.wallRepository.getWallLiveData().observe(this, new Observer<List<WallMessage>>() {
            @Override
            public void onChanged(List<WallMessage> wallMessages) {
                wallAdapter.setData(wallMessages);
            }
        });

        this.cameraBtn.setOnClickListener( e -> {
            takePhoto();
        });

        this.sendBtn.setOnClickListener( e -> {

            WallMessage wm = new WallMessage();

            wm.setName(username);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            wm.setDate(dateFormat.format(date));


            wm.setMeesage(this.textWall.getText().toString());
            wm.setUrl(null);

            this.textWall.getText().clear();

            wallRepository.addWallMessage(wm);

        });

    }


    private void takePhoto(){

        if (!hasAnyFeature(PackageManager.FEATURE_CAMERA)) {
            return;
        }

        if (hasPermissions(Manifest.permission.CAMERA)) {

            try {
                file = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            Uri photoURI = FileProvider.getUriForFile(getContext(), getString(R.string.app_file_provider),
                    file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            startActivityForResult(intent, REQUEST_CAMERA_PHOTO);
        } else {
            requestPermissions(REQUEST_CAMERA_PERMISSION, Manifest.permission.CAMERA);
        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode != REQUEST_CAMERA_PHOTO) {
            return;
        }

        if (resultCode != RESULT_OK) {
            Toast.makeText(getContext(), "No photo", Toast.LENGTH_SHORT).show();
            return;
        }

        //Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath());
        Bitmap bitmap = ImageUtil.getRotatedBitmap(file.getAbsolutePath());
        //mImageIv.setImageBitmap(bitmap);

        Uri uri = Uri.fromFile(file);

        StorageReference photoReference = this.storageReference.child(file.getName());

        photoReference.putFile(uri)
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return photoReference.getDownloadUrl();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                //ovde pravim model za bazu

                WallMessage wm = new WallMessage();
                wm.setName(username);

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                wm.setDate(dateFormat.format(date));


                wm.setMeesage(null);
                wm.setUrl(uri.toString());

                wallRepository.addWallMessage(wm);

            }
        });


    }

    protected boolean hasAnyFeature(String... features){
        for (String feature : features) {
            if (getContext().getPackageManager().hasSystemFeature(feature)){
                return true;
            }
        }
        return false;
    }

    protected boolean hasPermissions(String... permissions){
        for (String permission : permissions) {
            boolean hasPermission = ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED;
            if(!hasPermission) {
                return false;
            }
        }
        return true;
    }

    protected void requestPermissions(int requestCode, String... permissions){
        ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
}
