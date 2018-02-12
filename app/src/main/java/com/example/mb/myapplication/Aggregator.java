package com.example.mb.myapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Aggregator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TableRow createRow() {
        TableRow row = new TableRow(this);
        return row;
    }

    private ImageView createImage() {
        ImageView image = new ImageView(this);


        Bitmap bitmap = Bitmap.createBitmap(100, 100,
                Bitmap.Config.ARGB_8888);
        // Закрашиваем синим цветом
        bitmap.eraseColor(Color.BLUE);
        // Выведем результат в ImageView для просмотра
        //image.setImageBitmap(bitmap);
        image.setBackgroundResource(R.drawable.img);
        image.setMinimumHeight(3);
        image.setMinimumWidth(3);
        return image;
    }

    private TextView createDialog(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setBackgroundResource(R.drawable.bottom_borders);
        return textView;
    }

    private View createLine() {
        View view = new View(this);
        view.setMinimumHeight(3);
        view.setMinimumWidth(10);
        view.setBackgroundColor(0xFF000000);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Объявление визуальных объектов
        //---------------------------------------------------------------------------------------
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        //---------------------------------------------------------------------------------------
        //endregion

        VKSdk.login(this, VKScope.MESSAGES);

        final Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(5000);
                        ScrollView scrollView = findViewById(R.id.scroll);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                VKParameters vkParameters = VKParameters.from();
                                vkParameters.put(VKApiConst.COUNT, 50);
                                VKRequest request = new VKRequest("messages.getDialogs", vkParameters);

                                request.executeWithListener(new VKRequest.VKRequestListener()

                                {
                                    @Override
                                    public void onComplete(VKResponse response) {
                                        final TableLayout table = (TableLayout) findViewById(R.id.table);
                                        try {
                                            JSONObject jsonObject = (JSONObject) response.json.get("response");
                                            JSONArray items = (JSONArray) jsonObject.get("items");

                                            for (int x = 0; x < 50; x = x + 1) {
                                                JSONObject item = (JSONObject) items.get(x);
                                                JSONObject message = (JSONObject) item.get("message");
                                                String body = (String) message.get("body");
                                                System.out.println(message);
                                                System.out.println(body);

                                                TableRow first_row = createRow();
                                                first_row.addView(createImage());

                                                TableRow second_row = createRow();
                                                second_row.addView(createDialog(body));

                                                TableRow main_row = createRow();

                                                main_row.addView(first_row);
                                                main_row.addView(second_row);

                                                table.addView(main_row, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                                        TableRow.LayoutParams.WRAP_CONTENT));

                                                ScrollView scrollView = findViewById(R.id.scroll);
                                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                            }
                                        } catch (JSONException e) {
                                            System.out.println("ERROR");
                                            System.out.println(e);
                                        }
                                    }

                                    @Override
                                    public void onError(VKError error) {
                                        System.out.println("ERROR");
                                        System.out.println(error);
                                    }

                                    @Override
                                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                                        System.out.println("STRANGE");
                                    }
                                });

                                TableLayout table = findViewById(R.id.table);
                                table.removeAllViews();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
            };
        };

        thread.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
