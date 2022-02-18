package com.neotericinnovation.android.bookzone.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.neotericinnovation.android.bookzone.R;
import com.neotericinnovation.android.bookzone.fragment.ChatFragment;
import com.neotericinnovation.android.bookzone.fragment.HomeFragment;
import com.neotericinnovation.android.bookzone.fragment.MyAdsFragment;
import com.neotericinnovation.android.bookzone.fragment.PostAdsFragment;
import com.neotericinnovation.android.bookzone.fragment.ProfileFragment;
import com.neotericinnovation.android.bookzone.utils.ViewAnimation;
import java.io.File;
public class DashBoard extends BaseActivity {
 private static final String TAG = "DashBoard";
 private TextView mTextMessage;
 private BottomNavigationView navigation;
 final Fragment fragment1 = new HomeFragment();
 final Fragment fragment2 = new ProfileFragment();
 final Fragment fragment4 = new PostAdsFragment();
 final Fragment fragment5 = new MyAdsFragment();
 final FragmentManager fm = getSupportFragmentManager();
 Fragment active = fragment1;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_dash_board);
 FirebaseMessaging.getInstance().subscribeToTopic("userABC");
 fm.beginTransaction().add(R.id.fragment_container, fragment5, 
"4").hide(fragment5).commit();
 fm.beginTransaction().add(R.id.fragment_container, fragment4, 
"4").hide(fragment4).commit();
 fm.beginTransaction().add(R.id.fragment_container, fragment2, 
"2").hide(fragment2).commit();
 fm.beginTransaction().add(R.id.fragment_container,fragment1, "1").commit();
 initComponent();
// FirebaseInstanceId.getInstance().getInstanceId()
// .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
// @Override
// public void onComplete(@NonNull Task<InstanceIdResult> task) {
// if (!task.isSuccessful()) {
// Log.w(TAG, "getInstanceId failed", task.getException());
// return;
// }
// // Get new Instance ID token
// String token = task.getResult().getToken();
//
// // Log and toast
// String msg = getString(R.string.fcm_token, token);
// Log.d(TAG, msg);
// Toast.makeText(DashBoard.this, msg, Toast.LENGTH_SHORT).show();
// }
// });
// FirebaseMessaging.getInstance().subscribeToTopic("newbooksnotification");
 }
 private void initComponent() {
 navigation = (BottomNavigationView) findViewById(R.id.navigation);
 navigation.setOnNavigationItemSelectedListener(new 
BottomNavigationView.OnNavigationItemSelectedListener() {
  @Override
 public boolean onNavigationItemSelected(@NonNull MenuItem item) {
 switch (item.getItemId()) {
 case R.id.navigation_home:
 fm.beginTransaction().hide(active).show(fragment1).commit();
 active = fragment1;
 return true;
 case R.id.navigation_profile:
 fm.beginTransaction().hide(active).show(fragment2).commit();
 active = fragment2;
 return true;
// case R.id.navigation_chat:
// fm.beginTransaction().hide(active).show(fragment3).commit();
// active = fragment3;
// return true;
 case R.id.navigation_postAds:
 fm.beginTransaction().hide(active).show(fragment4).commit();
 active = fragment4;
 return true;
 case R.id.navigation_myAds:
 fm.beginTransaction().hide(active).show(fragment5).commit();
 active = fragment5;
 return true;
 }
 return false;
 }
 });
// NestedScrollView nested_content = (NestedScrollView)
   @Override
 public boolean onNavigationItemSelected(@NonNull MenuItem item) {
 switch (item.getItemId()) {
 case R.id.navigation_home:
 fm.beginTransaction().hide(active).show(fragment1).commit();
 active = fragment1;
 return true;
 case R.id.navigation_profile:
 fm.beginTransaction().hide(active).show(fragment2).commit();
 active = fragment2;
 return true;
// case R.id.navigation_chat:
// fm.beginTransaction().hide(active).show(fragment3).commit();
// active = fragment3;
// return true;
 case R.id.navigation_postAds:
 fm.beginTransaction().hide(active).show(fragment4).commit();
 active = fragment4;
 return true;
 case R.id.navigation_myAds:
 fm.beginTransaction().hide(active).show(fragment5).commit();
 active = fragment5;
 return true;
 }
   return false;
 }
 });
// NestedScrollView nested_content = (NestedScrollView) 
findViewById(R.id.nested_scroll_view);
  // nested_content.setOnScrollChangeListener(new 
NestedScrollView.OnScrollChangeListener() {
// @Override
// public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int 
oldScrollX, int oldScrollY) {
// if (scrollY < oldScrollY) { // up
// animateNavigation(false);
// }
// if (scrollY > oldScrollY) { // down
// animateNavigation(true);
//
// }
//}
// });
 }
 boolean isNavigationHide = false;
 private void animateNavigation(final boolean hide) {
 if (isNavigationHide && hide || !isNavigationHide && !hide) return;
 isNavigationHide = hide;
 int moveY = hide ? (2 * navigation.getHeight()) : 0;
 navigation.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
 }
 public void refreshCurrentFragment() {
 Fragment currentFragment = 
this.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
 FragmentTransaction fragmentTransaction = 
getSupportFragmentManager().beginTransaction();
 fragmentTransaction.detach(currentFragment);
 fragmentTransaction.attach(currentFragment);
 fragmentTransaction.commit();
 }
  public void signout(){
 FirebaseAuth.getInstance().signOut();
// deleteAppData();
 deleteCache(this);
 Intent intent= new Intent(this, SplashActivity.class);
 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 startActivity(intent);
 finish();
}
 public static void deleteCache(Context context) {
 try {
 File dir = context.getCacheDir();
   deleteDir(dir);
 } catch (Exception e) { e.printStackTrace();}
 }
 public static boolean deleteDir(File dir) {
 if (dir != null && dir.isDirectory()) {
 String[] children = dir.list();
 for (int i = 0; i < children.length; i++) {
 boolean success = deleteDir(new File(dir, children[i]));
 if (!success) {
 return false;
 }
 }
   return dir.delete();
 } else if(dir!= null && dir.isFile()) {
 return dir.delete();
 } else {
 return false;
 }
   }
 private void deleteAppData() {
 try {
 // clearing app data
 String packageName = getApplicationContext().getPackageName();
 Runtime runtime = Runtime.getRuntime();
 runtime.exec("pm clear "+packageName);
 } catch (Exception e) {
 e.printStackTrace();
 } }
}
  
