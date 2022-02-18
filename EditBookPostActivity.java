package com.neotericinnovation.android.bookzone.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.neotericinnovation.android.bookzone.R;
import com.neotericinnovation.android.bookzone.adapter.MyAdsFeedAdapter;
import com.neotericinnovation.android.bookzone.fragment.BaseFragment;
import com.neotericinnovation.android.bookzone.fragment.MyAdsFragment;
import com.neotericinnovation.android.bookzone.model.NewBookDTO;
import com.neotericinnovation.android.bookzone.model.NewUserDTO;
import java.io.File;
import java.util.List;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
public class EditBookPostActivity extends BaseActivity {
 private List<String> mPath;
 private String[] array_states,array_country,array_city,array_post_type;
  ImageView pickimage;
 Toolbar toolbar;
  String filePath;
 String generatedFilePath;
 private TextInputEditText 
mTitle,mAuthor,mDesc,mType,mCity,mState,mCountry,mPincode,mPrice;
 private Bitmap selectedImage;
 StorageReference storageRef;
 AppCompatButton updatAdBt;
 Uri pickedImageUri;
 String uid;
 String key;
 String newimageurl;
 String previousimageurl;
 MyAdsFeedAdapter mMyAdsFeedAdapter;
 int flag=0;
 String fullname,phonenumber;
 DatabaseReference mDatabaseReff,userdataReff;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_edit_book_post);
 this.doubleBackToExitPressedOnce = true;
 key = getIntent().getExtras().getString("myadkey","error").trim();
 initViews();
 initToolbar();
   FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
 uid=currentFirebaseUser.getUid();
 initProfileDatas()
 array_states = getResources().getStringArray(R.array.states);
 array_country = getResources().getStringArray(R.array.country);
 array_city = getResources().getStringArray(R.array.citys);
   array_post_type = getResources().getStringArray(R.array.postType)
// pickimage.setOnClickListener(new View.OnClickListener() {
// @Override
// public void onClick(View v) {
//
// Intent intent = new Intent(getApplicationContext(), ImageSelectActivity.class);
// intent.setType("image/*");
// intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
// intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
// intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
// startActivityForResult(intent, 1213);
     // }
// });
 ((TextInputEditText) findViewById(R.id.edit_ads_posttype)).setOnClickListener(new 
View.OnClickListener() {
 @Override
 public void onClick(View v) {
 showPostTypeChoiceDialog((TextInputEditText) v);
 mType.setError(null);
 }
 });
 ((TextInputEditText) findViewById(R.id.edit_ads_state)).setOnClickListener(new 
View.OnClickListener() {
 @Override
 public void onClick(View v) {
 showStateChoiceDialog((TextInputEditText) v);
 mState.setError(null);
 }
 });
   ((TextInputEditText) findViewById(R.id.edit_ads_country)).setOnClickListener(new 
View.OnClickListener() {
 @Override
 public void onClick(View v) {
 showCountryChoiceDialog((TextInputEditText) v);
 mCountry.setError(null);
 }
 });
 ((TextInputEditText) findViewById(R.id.edit_ads_city)).setOnClickListener(new 
View.OnClickListener() {
 @Override
 public void onClick(View v) {
 showCityChoiceDialog((TextInputEditText) v);
   mCity.setError(null);
 }
 });
 updatAdBt.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 if (isValidAdPost()) {
 String newTitle=mTitle.getText().toString().trim();
 String newAuthor=mAuthor.getText().toString().trim();
 String newDesc=mDesc.getText().toString().trim();
 String newType=mType.getText().toString().trim();
 String newPrice=mPrice.getText().toString().trim();
   String newCity=mCity.getText().toString().trim();
 String newState=mState.getText().toString().trim();
 String newCountry=mCountry.getText().toString().trim();
 String newPincode=mPincode.getText().toString().trim();
 FirebaseDatabase database = FirebaseDatabase.getInstance();
