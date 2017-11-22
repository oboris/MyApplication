package com.example.user.myapplication.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.R;
import com.example.user.myapplication.adapter.MultiListAdapter;
import com.example.user.myapplication.api.ApiGoodLiftRecords;
import com.example.user.myapplication.dao.CPUDao;
import com.example.user.myapplication.dao.MotherBoardDao;
import com.example.user.myapplication.model.CPU;
import com.example.user.myapplication.model.MultiModel;
import com.example.user.myapplication.util.FABFloatOnScroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<MultiModel> multiModelList = new ArrayList<>();
    private RecyclerView rvMultiList;

    private RelativeLayout rlProgress;
    private TextView tvProgress;

    private CPUDao cpuDao;
    private MotherBoardDao motherBoardDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*MultiDbHelper mDBHelper = MultiDbHelper.getInstance(this);

        File database = getApplicationContext().getDatabasePath(MultiDbHelper.DB_NAME);
        if (!database.exists()) {
            mDBHelper.getReadableDatabase();
            if (copyDatabase(this)) {
                Toast.makeText(this, "Copy database success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
            }
        }*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.setBehavior(new FABFloatOnScroll(this, null));
        fab.setLayoutParams(layoutParams);*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rlProgress = findViewById(R.id.rl_progress);
        tvProgress = findViewById(R.id.tv_progress);

        cpuDao = new CPUDao(this);
        motherBoardDao = new MotherBoardDao(this);

        initList();
    }

    /*private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(MultiDbHelper.DB_NAME);
            String outFileName = getDatabasePath(MultiDbHelper.DB_NAME).getPath();
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/

    private void initList(){
        rvMultiList = findViewById(R.id.rv_multi_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMultiList.setLayoutManager(layoutManager);
        rvMultiList.setHasFixedSize(true);

        MultiListAdapter multiListAdapter = new MultiListAdapter(multiModelList);
        rvMultiList.setAdapter(multiListAdapter);

        multiModelList.addAll(motherBoardDao.selectAllMotherBoardFromDB());

       /* rvMultiList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                            @Override
                                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                                                    fab.show();
                                                }
                                                super.onScrollStateChanged(recyclerView, newState);

                                            }

                                            @Override
                                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                                                super.onScrolled(recyclerView, dx, dy);
                                                if (dy > 0 ||dy<0 && fab.isShown())
                                                    fab.hide();
                                            }
                                        }
        );*/

        loadDataFromServer(30, 1, 0);
    }

    private Callback<List<CPU>> loadDataCallback = new Callback<List<CPU>>() {
        @Override
        public void onResponse(@NonNull Call<List<CPU>> call, @NonNull Response<List<CPU>> response) {
            if (response.isSuccessful()) {
                List<CPU> cpus = response.body();
                if (cpus != null) {
                    multiModelList.addAll(cpus);
                    rvMultiList.getAdapter().notifyDataSetChanged();
                }

//                for (CPU cpu : cpus){
//                    DBHelper.getInstance(HomeActivity.this).insertCoinToDB(cpu);
//                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<CPU>> call, @NonNull Throwable t) {
        }
    };

    private void loadDataFromServer(int count, int firstPos, int codeFederation) {
        if (isOnline()) {
            cpuDao.deleteAllRecordsFromDB();
            LoadRecordsTask loadRecordsTask = new LoadRecordsTask();
            loadRecordsTask.execute(count,firstPos,codeFederation);
//            ApiGoodLiftRecords.get().getRecords(count, firstPos, codeFederation, loadDataCallback);
        }
        else Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    private class LoadRecordsTask extends AsyncTask<Integer, Integer, Boolean> {
        int totalCount;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Snackbar.make(rvMultiList, "Start synchronization from GoodLift Server...", Snackbar.LENGTH_LONG).show();
            rlProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            totalCount = params[0];
            int count = 0;
            List<CPU> recordsList;

            int recordsCount = 5;
            int startPosition = params[1];
            do {
                recordsList = ApiGoodLiftRecords.get().getRecords(recordsCount, startPosition, params[2], null);
                if (null != recordsList) {
//                        for (Record record : recordsList) {
//                            RecordDBHelper.getInstance(MainRecordsActivity.this).insertRecordToDB(record);
//                        }
                    cpuDao.insertListRecordsToDB(recordsList);
                    count += recordsList.size();
                }

                startPosition += recordsCount;

                publishProgress(count);
            } while (null != recordsList && recordsList.size() > 0 && totalCount > count);
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            tvProgress.setText(String.format(Locale.getDefault(), "%d%%",(int)((float) progress[0] / (float)totalCount * 100)));
            //if (prefs.getBoolean(PREFS_FIRST_RUN, true)) {
            //updateRecordsList();
            //}
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                rlProgress.setVisibility(View.INVISIBLE);
                Snackbar.make(rvMultiList, "Finish synchronization from GoodLift Server!", Snackbar.LENGTH_LONG).show();
                multiModelList.addAll(cpuDao.selectAllCPUFromDB());
                rvMultiList.getAdapter().notifyDataSetChanged();
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
