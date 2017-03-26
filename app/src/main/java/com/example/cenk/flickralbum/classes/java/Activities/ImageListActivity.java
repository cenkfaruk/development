package com.example.cenk.flickralbum.classes.java.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cenk.flickralbum.R;
import com.example.cenk.flickralbum.classes.java.Adapters.PhotoAdapter;
import com.example.cenk.flickralbum.classes.java.Helpers.NameValuePair;
import com.example.cenk.flickralbum.classes.java.Helpers.PhotoItem;
import com.example.cenk.flickralbum.classes.java.Helpers.ServiceConnection;
import com.example.cenk.flickralbum.classes.java.Helpers.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

public class ImageListActivity extends AppCompatActivity {
    //private ArrayList<PhotoItem> photoItems;// List of PhotoItem
    private PhotoAdapter    photoAdapter;// Custom adapter for photoRecyclerView.
    private RecyclerView  photoRecyclerView;//photo container.
    private boolean isDoubleBack=false;// Flags if double pressed at back for exit
    private static int DOUBLE_BACK_DELAY_TIME=2000;
    private boolean isSearchVisible=false;// flag for search bar animation emphises its visibility
    private boolean isNumOfColVisible=false;// flag for number of column bar animation emphises its visibility
    android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        SetActionBar();
        //Get api response from previous activity
        String apiResponse=getIntent().getStringExtra("response");
        setRecylerView(apiResponse,3);
    }

    /* Exits app if double tapped to back button
       and prevents app to go back to splash page.
     */
    @Override
    public void onBackPressed() {
        if(!isSearchVisible&&!isNumOfColVisible) {
            if (isDoubleBack) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            this.isDoubleBack = true;
            Toast.makeText(ImageListActivity.this, "Double tap to exit", Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isDoubleBack = false;
                }
            }, DOUBLE_BACK_DELAY_TIME);
        }
        else if(isSearchVisible){
            //search bar visible hide with animation
            final RelativeLayout searchLay=(RelativeLayout)actionBar.getCustomView().findViewById(R.id.search_lay);
            hideSearchBar(searchLay);
            }
            else{
            //Column number picker visible hide with animation.
            hideNumOfColumn();
        }
    }


    /*
     Set action bar specific to this actiityand get button actions for search and column picker.
     */
    private void SetActionBar() {
        try {
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setHomeButtonEnabled(false);
            // set custom view to action bar
            actionBar.setCustomView(R.layout.action_bar);
            final RelativeLayout searchLay=(RelativeLayout)actionBar.getCustomView().findViewById(R.id.search_lay);
            searchLay.setBackground(getOvalBackground());
            LinearLayout columnLay=(LinearLayout)actionBar.getCustomView().findViewById(R.id.column_lay);
            columnLay.setBackground(getOvalBackground());
            final ImageView search=(ImageView)actionBar.getCustomView().findViewById(R.id.search_image);
            final ImageView columnEdit=(ImageView)actionBar.getCustomView().findViewById(R.id.num_of_column);
            View.OnClickListener actionBarClick=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.search_image:{
                            showSearchBar(searchLay);
                            break;
                        }
                        case R.id.num_of_column:{
                            showNumOfColumn();
                            break;
                        }
                    }

                }
            };
            search.setOnClickListener(actionBarClick);
            columnEdit.setOnClickListener(actionBarClick);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //sets oval background shape to search bar layout
    private GradientDrawable getOvalBackground(){
        GradientDrawable gradientDrawable= new GradientDrawable();
        gradientDrawable.setColor(Color.WHITE);
        gradientDrawable.setCornerRadii(new float[] { 5, 5, 5, 5, 5, 5, 5, 5 });
        return gradientDrawable;
    }




    /*
        Consumes a Json formated string and parse all
        photo information into list
     */
    private void parseJsonIntoList(String _jsonResponse, ArrayList<PhotoItem> _photoItems){
        String image_size="m";// Medium size
        try {
            JSONObject photoJson = new JSONObject(_jsonResponse);
            JSONObject photo=photoJson.getJSONObject("photos");//Accessing the jason object that contains JasonArray of photo info.
            JSONArray photos=photo.getJSONArray("photo");//JsonArray that contains list of photo information.
            for(int i=0;i<photos.length();i++){
                JSONObject currPhoto=(JSONObject)photos.get(i);//Specific JasonObject that contains photo info.
                _photoItems.add(new PhotoItem(currPhoto.getInt("farm"),
                                              currPhoto.getString("server"),
                                              currPhoto.getString("id"),
                                              currPhoto.getString("secret"),
                                              image_size));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }


    //Shows searc layout with animation and shows keyboard to make page ready for typing
    private void showSearchBar(final RelativeLayout searchLay){
        ValueAnimator righ_to_left = ValueAnimator.ofInt(0, actionBar.getCustomView().getWidth());
        righ_to_left.setDuration(350);
        righ_to_left.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //increass width of the layout step by  step
                Integer currentValue = (Integer) animation.getAnimatedValue();
                searchLay.getLayoutParams().width=currentValue;
                searchLay.requestLayout();
            }
        });
        righ_to_left.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                //Animation End
                final EditText searchText=(EditText)searchLay.findViewById(R.id.search_edittext);
                searchText.requestFocus();
                searchText.setFocusableInTouchMode(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchText, InputMethodManager.SHOW_FORCED);

                searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if(Tools.isConnectted(ImageListActivity.this)){
                            new AsyncTask<String, String, String>() {
                                List<NameValuePair> propList;// List of NameValuePair that contains specific features that will add on url.
                                ProgressBar progressBar;
                                @Override
                                protected void onPreExecute() {
                                    super.onPreExecute();
                                    propList=new ArrayList<>();
                                    propList.add(new NameValuePair("method", Tools.SEARCH));
                                    propList.add(new NameValuePair("api_key",Tools.API_KEY));
                                    propList.add(new NameValuePair("format","json"));
                                    propList.add(new NameValuePair("nojsoncallback","1"));
                                    propList.add(new NameValuePair("tags",searchText.getText().toString()));
                                    hideSearchBar(searchLay);
                                    progressBar=(ProgressBar)findViewById(R.id.progressBar);
                                    progressBar.setVisibility(View.VISIBLE);
                                }

                                @Override
                                protected String doInBackground(String... params) {
                                    try {
                                        //Creates service connection object and run function for service respose
                                        ServiceConnection connection = new ServiceConnection(Tools.BASE_URL, propList);
                                        return connection.getServiceResponse();
                                    } catch (Exception ex) {
                                        return null;
                                    }
                                }
                                @Override
                                protected void onPostExecute(String _response) {
                                    if(_response!=null) {
                                        setRecylerView(_response,3);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }

                            }.execute();
                        }
                        return true;
                    }
                    return false;
                }
            });
            }
        });
        righ_to_left.start();
        isSearchVisible=true;
    }



    //Hides search bar layout with slide left to right animation
    private void hideSearchBar(final RelativeLayout searchLay){
        ValueAnimator left_to_right = ValueAnimator.ofInt(actionBar.getCustomView().getWidth(),0);
        left_to_right.setDuration(350);
        left_to_right.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Decreases width of the layout step by step
                Integer currentValue = (Integer) animation.getAnimatedValue();
                searchLay.getLayoutParams().width=currentValue;
                searchLay.requestLayout();
            }
        });
        left_to_right.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation) {
                // Hides keyboard and reset focus on edittext before start animation
                EditText searchText=(EditText)searchLay.findViewById(R.id.search_edittext);
                searchText.setText("");
                if (searchText.hasFocus() ) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                }
            }});
        left_to_right.start();

        isSearchVisible=false;
    }


    //Updates RecylerView with related properties.
    private void setRecylerView(String _apiResponse,int _numOfColumns){
        ArrayList<PhotoItem> photoItems= new ArrayList<>();// List of PhotoItem
        //Parse response to list of PhotoItem
        parseJsonIntoList(_apiResponse,photoItems);

        //set adapter and settings of RecyclerView
        photoAdapter = new PhotoAdapter(ImageListActivity.this,photoItems);
        //add GridLayout manager that RecylerView will have GridLayout's visual features.
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),_numOfColumns);
        photoRecyclerView = (RecyclerView) findViewById(R.id.photo_recyle_list);
        photoRecyclerView.setLayoutManager(mLayoutManager);
        photoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        photoRecyclerView.setAdapter(photoAdapter);

    }


// shows number of column layout with animation
    private void showNumOfColumn(){
        final LinearLayout columnContainer= (LinearLayout)findViewById(R.id.column_lay); //Container
        ValueAnimator righ_to_left = ValueAnimator.ofInt(0, actionBar.getCustomView().getWidth());
        righ_to_left.setDuration(200);
        righ_to_left.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer currentValue = (Integer) animation.getAnimatedValue();
                columnContainer.getLayoutParams().width=currentValue;
                columnContainer.requestLayout();
            }
        });
        righ_to_left.start();
        isNumOfColVisible=true;

    }


    //hides number of column options layout with animation
    private void hideNumOfColumn(){
        final LinearLayout columnContainer= (LinearLayout)findViewById(R.id.column_lay);// Container layout. Contains options
        ValueAnimator righ_to_left = ValueAnimator.ofInt( actionBar.getCustomView().getWidth(),0);
        righ_to_left.setDuration(200);
        righ_to_left.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer currentValue = (Integer) animation.getAnimatedValue();
                columnContainer.getLayoutParams().width=currentValue;// updates width of the layout step by step
                columnContainer.requestLayout();
            }
        });
        righ_to_left.start();
        isNumOfColVisible=false;
    }


    //updates the number of the column accourdig to user's choice
    public void editNumOfColListener(View v) {
        TextView colText=(TextView)v;
        int numCol=Integer.parseInt(colText.getText().toString());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),numCol);
        photoRecyclerView.setLayoutManager(mLayoutManager);
        hideNumOfColumn();
    }
}
