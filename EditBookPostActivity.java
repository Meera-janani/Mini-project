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
DatabaseReference mDatabaseRef = 
database.getReference().child("books").child(key);
// if(flag==0){
// newimageurl=previousimageurl;
// }else if (flag==1){
//
// pickedImageUri = Uri.fromFile(new File(filePath));
//
// File file = new File(String.valueOf(pickedImageUri));
//
// FirebaseStorage storage = FirebaseStorage.getInstance();
//
// final StorageReference storageRef = 
storage.getReference().child("bookimages/"+pickedImageUri.getLastPathSegment());
//
// storageRef.putFile(pickedImageUri).addOnSuccessListener(new 
OnSuccessListener<UploadTask.TaskSnapshot>() {
// @Override
// public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
// storageRef.getDownloadUrl().addOnSuccessListener(new 
OnSuccessListener<Uri>() {
// @Override
// public void onSuccess(Uri uri) {
// Uri downloadUrl = uri;
// generatedFilePath = downloadUrl.toString();
// newimageurl=generatedFilePath;
// hideBusy();
//
// }
// })
// .addOnFailureListener(new OnFailureListener() {
 // @Override
// public void onFailure(@NonNull Exception e) {
// hideBusy();
// Toast.makeText(getApplicationContext(), "Post Uploading Failed", 
Toast.LENGTH_SHORT).show();
// }
// });
// }
// });
// }
 mDatabaseRef.child("tile").setValue(newTitle);
 mDatabaseRef.child("author").setValue(newAuthor);
 mDatabaseRef.child("description").setValue(newDesc);
 mDatabaseRef.child("type").setValue(newType);
 mDatabaseRef.child("price").setValue(newPrice);
 mDatabaseRef.child("city").setValue(newCity);
 mDatabaseRef.child("state").setValue(newState);
 mDatabaseRef.child("country").setValue(newCountry);
 mDatabaseRef.child("pincode").setValue(newPincode);
 // mDatabaseRef.child("imageUrl").setValue(newimageurl);
//
 // Toast.makeText(EditBookPostActivity.this, String.valueOf(flag), 
Toast.LENGTH_LONG).show();
 Toast.makeText(EditBookPostActivity.this, "updated succesfully", 
Toast.LENGTH_SHORT).show();
 initProfileDatas();
 onBackPressed();
 // mMyAdsFeedAdapter.notifyDataSetChanged();
// final MyAdsFragment pf = new MyAdsFragment();
// pf.updateAdapter();
 }
 }
 });
 }
 private void initViews() {
 pickimage= findViewById(R.id.edit_ads_image);
 updatAdBt=findViewById(R.id.edit_ads_update);
 mTitle=findViewById(R.id.edit_ads_title);
 mAuthor=findViewById(R.id.edit_ads_author);
 mDesc=findViewById(R.id.edit_ads_description);
 mType=findViewById(R.id.edit_ads_posttype);
   mPrice=findViewById(R.id.edit_ads_price);
 mCity=findViewById(R.id.edit_ads_city);
 mState=findViewById(R.id.edit_ads_state);
 mCountry=findViewById(R.id.edit_ads_country);
 mPincode=findViewById(R.id.edit_ads_pincode);
 }
 private void showStateChoiceDialog(final TextInputEditText stateEt) {
 final AlertDialog.Builder builder = new AlertDialog.Builder(this);
 builder.setCancelable(true);
 builder.setTitle("Choose State");
 builder.setSingleChoiceItems(array_states, -1, new DialogInterface.OnClickListener() {
 @Override
 public void onClick(DialogInterface dialog, int which) {
 dialog.dismiss();
 stateEt.setTextColor(Color.BLACK);
 stateEt.setText(array_states[which]);
 }
 });
  builder.show();
 }
 private void showCountryChoiceDialog(final TextInputEditText countryEt) {
 final AlertDialog.Builder builder = new AlertDialog.Builder(this);
 builder.setCancelable(true);
 builder.setTitle("Choose Country");
 builder.setSingleChoiceItems(array_country, -1, new DialogInterface.OnClickListener() {
 @Override
 public void onClick(DialogInterface dialog, int which) {
 dialog.dismiss();
 countryEt.setTextColor(Color.BLACK);
 countryEt.setText(array_country[which]);
 }
 });
 builder.show();
 }
 private void showCityChoiceDialog(final TextInputEditText cityEt) {
 final AlertDialog.Builder builder = new AlertDialog.Builder(this);
 builder.setCancelable(true);
 builder.setTitle("Choose City");
 builder.setSingleChoiceItems(array_city,-1, new DialogInterface.OnClickListener() {
 @Override
 public void onClick(DialogInterface dialog, int which) {
 dialog.dismiss();
 cityEt.setTextColor(Color.BLACK);
 cityEt.setText(array_city[which]);
 }
 });
 builder.show();
   }
 private void showPostTypeChoiceDialog(final TextInputEditText posttypeEt) {
 final AlertDialog.Builder builder = new AlertDialog.Builder(this);
 builder.setCancelable(true);
 builder.setTitle("Select Post Type");
 builder.setSingleChoiceItems(array_post_type,-1, new DialogInterface.OnClickListener() {
 @Override
 public void onClick(DialogInterface dialog, int which) {
 dialog.dismiss();
 posttypeEt.setTextColor(Color.BLACK);
 posttypeEt.setText(array_post_type[which]);
 }
 });
 builder.show();
 }
 @Override
 public void onActivityResult(int requestCode, int resultCode, Intent data) {
 super.onActivityResult(requestCode, resultCode, data);
 if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {
 pickedImageUri = data.getData();
 filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
 selectedImage = BitmapFactory.decodeFile(filePath);
 pickimage.setImageBitmap(selectedImage);
 flag=1;
 }
 }
 private void initToolbar() {
 toolbar = (Toolbar) findViewById(R.id.edit_ads_toolbar);
 toolbar.setTitle("Edit Ad");
  setSupportActionBar(toolbar);
 }
 private void initProfileDatas() {
 DatabaseReference mReference = 
FirebaseDatabase.getInstance().getReference("books").child(key);
 mReference.addListenerForSingleValueEvent(new ValueEventListener() {
 @Override
 public void onDataChange(DataSnapshot dataSnapshot) {
 NewBookDTO newBookDTO = dataSnapshot.getValue(NewBookDTO.class);
 String title =newBookDTO.getTile();
 mTitle.setText(title);
 String author=newBookDTO.getAuthor();
 mAuthor.setText(author);
 String desc=newBookDTO.getDescription();
 mDesc.setText(desc);
 String type=newBookDTO.getType();
 mType.setText(type);
 String price=newBookDTO.getPrice();
 mPrice.setText(price);
 String city=newBookDTO.getCity();
 mCity.setText(city);
 String state=newBookDTO.getState();
 mState.setText(state);
 String country=newBookDTO.getCountry();
 mCountry.setText(country);
 String pincode=newBookDTO.getPincode();
 mPincode.setText(pincode);
 previousimageurl=newBookDTO.getImageUrl();
 Glide.with(getApplicationContext()).load(previousimageurl).into(pickimage);
  }
 @Override
 public void onCancelled(DatabaseError databaseError) {
 }
 });
 }
 private boolean isValidAdPost() {
 boolean isValidationFailed = true;
 if (TextUtils.isEmpty(mTitle.getText().toString())) {
 isValidationFailed = false;
 mTitle.setError(getString(R.string.cannot_be_empty));
 } else {
 mTitle.setError(null);
 }
 if (TextUtils.isEmpty(mAuthor.getText().toString())) {
 isValidationFailed = false;
 mAuthor.setError(getString(R.string.cannot_be_empty));
 } else {
 mAuthor.setError(null);
 }
 if (TextUtils.isEmpty(mDesc.getText().toString())) {
 isValidationFailed = false;
 mDesc.setError(getString(R.string.cannot_be_empty));
 } else {
 mDesc.setError(null);
 }
 if (TextUtils.isEmpty(mType.getText().toString())) {
 isValidationFailed = false;
 mType.setError(getString(R.string.cannot_be_empty));
  } else {
 mType.setError(null);
 }
 if (TextUtils.isEmpty(mPrice.getText().toString())) {
 isValidationFailed = false;
 mPrice.setError(getString(R.string.cannot_be_empty));
 } else {
 mPrice.setError(null);
 }
 if (TextUtils.isEmpty(mCity.getText().toString())) {
 isValidationFailed = false;
 mCity.setError(getString(R.string.cannot_be_empty));
 } else {
 mCity.setError(null);
 }
 if (TextUtils.isEmpty(mState.getText().toString())) {
 isValidationFailed = false;
 mState.setError(getString(R.string.cannot_be_empty));
 } else {
 mState.setError(null);
 }
 if (TextUtils.isEmpty(mCountry.getText().toString())) {
 isValidationFailed = false;
 mCountry.setError(getString(R.string.cannot_be_empty));
 } else {
 mCountry.setError(null);
 }
 if (TextUtils.isEmpty(mPincode.getText().toString())) {
 isValidationFailed = false;
 mPincode.setError(getString(R.string.cannot_be_empty));
} else {
 mPincode.setError(null);
 }
// if (selectedImage == null) {
// isValidationFailed = false;
// Toast.makeText(getApplicationContext(), "Pick an image", 
Toast.LENGTH_LONG).show();
// }
 return isValidationFailed;
 }
 @Override
 public void onBackPressed() {
 Intent returnIntent = new Intent();
 returnIntent.putExtra("hasBackPressed",true);
 setResult(Activity.RESULT_OK,returnIntent);
 finish();
