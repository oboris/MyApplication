package com.example.user.myapplication.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.R;
import com.example.user.myapplication.adapter.MultiListAdapter;
import com.example.user.myapplication.api.ApiGoodLiftRecords;
import com.example.user.myapplication.dao.CPUDao;
import com.example.user.myapplication.model.CPU;
import com.example.user.myapplication.model.MotherBoard;
import com.example.user.myapplication.model.MultiModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<MultiModel> multiModelList = new ArrayList<>();

    private RelativeLayout rlProgress;
    private TextView tvProgress;

    private CPUDao cpuDao;

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

    RecyclerView rvMultiList;

    private void initList(){
        rvMultiList = findViewById(R.id.rv_multi_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMultiList.setLayoutManager(layoutManager);
        rvMultiList.setHasFixedSize(true);

        MultiListAdapter multiListAdapter = new MultiListAdapter(multiModelList);
        rvMultiList.setAdapter(multiListAdapter);

        loadDataFromServer(1000, 1, 0);

        multiModelList.add(new CPU("1 i7", 2500));
        multiModelList.add(new CPU("2 i3", 1500));

        List<String> list = new ArrayList<>();
        list.clear();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        multiModelList.add(new MotherBoard("1 ASUS", "Z170", list));

        multiModelList.add(new CPU("3 i7", 2500));
        multiModelList.add(new CPU("4 i3", 1500));

        List<String> list2 = new ArrayList<>();
        list2.clear();
        list2.add("s1");
        list2.add("r2");
        list2.add("f3");
        list2.add("s4");
        list2.add("r5");
        list2.add("f6");
        multiModelList.add(new MotherBoard("2 ASUS", "Z170", list2));

        multiModelList.add(new CPU("5 i7", 2500));
        multiModelList.add(new CPU("6 i3", 1500));
        multiModelList.add(new CPU("7 i7", 2500));
        multiModelList.add(new CPU("8 i3", 1500));

    }

    private Callback<List<CPU>> loadDataCallback = new Callback<List<CPU>>() {
        @Override
        public void onResponse(Call<List<CPU>> call, Response<List<CPU>> response) {
            if (response.isSuccessful()) {
                List<CPU> cpus = response.body();
                multiModelList.addAll(cpus);
                rvMultiList.getAdapter().notifyDataSetChanged();

//                for (CPU cpu : cpus){
//                    DBHelper.getInstance(HomeActivity.this).insertCoinToDB(cpu);
//                }
            }
        }

        @Override
        public void onFailure(Call<List<CPU>> call, Throwable t) {
        }
    };

    private void loadDataFromServer(int count, int firstPos, int codeFederation) {
        if (isOnline()) {
            cpuDao.deleteAllRecordsFromDB();
            LoadRecordsTask loadRecordsTask = new LoadRecordsTask();
            loadRecordsTask.execute(count);
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

            int recordsCount = 50;
            int startPosition = 0;
            do {
                recordsList = ApiGoodLiftRecords.get().getRecords(recordsCount, startPosition, 0, null);
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
