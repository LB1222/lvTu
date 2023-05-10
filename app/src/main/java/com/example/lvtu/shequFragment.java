package com.example.lvtu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapter.MyAdapter;
import bean.Note;
import date.NoteDbOpenHelper;
import util.SpfUtil;


public class shequFragment extends Fragment {
    public  FloatingActionButton actionA ;
//    public Toolbar toolbar;

    private View view;
    private RecyclerView mRecyclerView;

    private List<Note> mNotes;
    private MyAdapter mMyAdapter;

    private NoteDbOpenHelper mNoteDbOpenHelper;

    public static final int MODE_LINEAR = 0;
    public static final int MODE_GRID = 1;

    public static final String KEY_LAYOUT_MODE = "key_layout_mode";
    private int currentListLayoutMode = MODE_LINEAR;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public shequFragment() {
        // Required empty public constructor
    }


    public static shequFragment newInstance(String param1, String param2) {
        shequFragment fragment = new shequFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        toolbar = view.findViewById(R.id.tb);
//
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

       // toolbar.inflateMenu(R.menu.main_menu);


        view = inflater.inflate(R.layout.fragment_shequ, container, false);
        actionA = view.findViewById(R.id.Fa_bt);

        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),addActivity.class);
                startActivity(intent);
            }
        });
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshDataFromDb();
        setListLayout();
    }

    private void initView() {
        mRecyclerView = view.findViewById(R.id.rlv);
        mMyAdapter = new MyAdapter(getActivity(), mNotes);
    }

    private void setListLayout() {
        currentListLayoutMode = SpfUtil.getIntWithDefault(getActivity().getApplicationContext(), KEY_LAYOUT_MODE, MODE_LINEAR);
        if (currentListLayoutMode == MODE_LINEAR) {
            setToLinearList();
        } else {
            setToGridList();
        }
    }

    private void refreshDataFromDb() {
        mNotes = getDataFromDB();
        mMyAdapter.refreshData(mNotes);
    }

    private void initEvent() {
        mMyAdapter = new MyAdapter(getActivity().getApplicationContext(), mNotes);
        mRecyclerView.setAdapter(mMyAdapter);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
      mRecyclerView.setLayoutManager(linearLayoutManager);
      mMyAdapter.setViewType(MyAdapter.TYPE_LINEAR_LAYOUT);
        setListLayout();

    }

    private void initData() {
        mNotes = new ArrayList<>();
        mNoteDbOpenHelper = new NoteDbOpenHelper(getActivity().getApplicationContext());
//
//        for (int i = 0; i < 30; i++) {
//            Note note = new Note();
//            note.setTitle("这是标题"+i);
//            note.setContent("这是内容"+i);
//            note.setCreatedTime(getCurrentTimeFormat());
//            mNotes.add(note);
//        }

//        mNotes = getDataFromDB();

    }

    private List<Note> getDataFromDB() {
        return mNoteDbOpenHelper.queryAllFromDb();
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }


//    @Override
//    public  onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                mNotes = mNoteDbOpenHelper.queryFromDbByTitle(newText);
//                mMyAdapter.refreshData(mNotes);
//                return true;
//            }
//        });
//         super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public void onPrepareOptionsMenu(@NonNull Menu menu,MenuInflater inflater) {
//        super.onPrepareOptionsMenu(menu);
//        if (currentListLayoutMode == MODE_LINEAR) {
//            MenuItem item = menu.findItem(R.id.menu_linear);
//            item.setChecked(true);
//        } else {
//            menu.findItem(R.id.menu_grid).setChecked(true);
//        }
//    }

       @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.wode_menu, menu);
        if (currentListLayoutMode == MODE_LINEAR) {
            MenuItem item = menu.findItem(R.id.menu_linear);
            item.setChecked(true);
        } else {
            menu.findItem(R.id.menu_grid).setChecked(true);
        }
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mNotes = mNoteDbOpenHelper.queryFromDbByTitle(newText);
                mMyAdapter.refreshData(mNotes);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override

            public boolean onOptionsItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.menu_linear:
                        setToLinearList();
                        currentListLayoutMode = MODE_LINEAR;
                        SpfUtil.saveInt(getActivity().getApplicationContext(), KEY_LAYOUT_MODE, MODE_LINEAR);

                        return true;
                    case R.id.menu_grid:

                        setToGridList();
                        currentListLayoutMode = MODE_GRID;
                        SpfUtil.saveInt(getActivity().getApplicationContext(), KEY_LAYOUT_MODE, MODE_GRID);
                        return true;
                    default:
                        return super.onOptionsItemSelected(item);
        }
    }

    private void setToLinearList() {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mMyAdapter.setViewType(MyAdapter.TYPE_LINEAR_LAYOUT);
        mMyAdapter.notifyDataSetChanged();
    }


    private void setToGridList() {
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mMyAdapter.setViewType(MyAdapter.TYPE_GRID_LAYOUT);
        mMyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (currentListLayoutMode == MODE_LINEAR) {
            MenuItem item = menu.findItem(R.id.menu_linear);
            item.setChecked(true);
        } else {
            menu.findItem(R.id.menu_grid).setChecked(true);
        }

    }



//    public void add(View view) {
//        Intent intent = new Intent(getActivity().getApplicationContext(), addActivity.class);
//        startActivity(intent);
//    }


}


