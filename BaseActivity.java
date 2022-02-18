package com.neotericinnovation.android.bookzone.activity;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.neotericinnovation.android.bookzone.dialogue.BusyDialog;
public abstract class BaseActivity extends AppCompatActivity {
// public void showBusy() {
// FragmentTransaction fragmentTransaction = 
getSupportFragmentManager().beginTransaction();
// Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
// if (prev != null) {
// fragmentTransaction.remove(prev);
// }
// fragmentTransaction.addToBackStack(null);
// DialogFragment dialogFragment = new BusyDialog();
// dialogFragment.setCancelable(false);
// dialogFragment.show(fragmentTransaction, "dialog");
// }
//
// public void hideBusy() {
// runOnUiThread(new Runnable() {
// @Override
// public void run() {
// FragmentTransaction fragmentTransaction = 
BaseActivity.this.getSupportFragmentManager().beginTransaction();
// Fragment prev = 
BaseActivity.this.getSupportFragmentManager().findFragmentByTag("dialog");
// if (prev != null) {
// DialogFragment dialogFragment = (DialogFragment) prev;
dialogFragment.dismiss();
// fragmentTransaction.remove(prev);
// }
// }
// });
//
// }
 boolean doubleBackToExitPressedOnce = false;
 @Override
 public void onBackPressed() {
 //Checking for fragment count on backstack
 if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
 getSupportFragmentManager().popBackStack();
 } else if (!doubleBackToExitPressedOnce) {
 this.doubleBackToExitPressedOnce = true;
 Toast.makeText(this,"Please click BACK again to exit.", 
Toast.LENGTH_SHORT).show();
 new Handler().postDelayed(new Runnable() {
 @Override
 public void run() {
 doubleBackToExitPressedOnce = false;
 }
 }, 2000);
 } else {
 super.onBackPressed();
 return;
 }
 } }
