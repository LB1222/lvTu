package com.example.lvtu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import Adapter.MyAdapter;

import bean.Note;
import date.NoteDbOpenHelper;


public class shouyeFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Note> mNotes;
    private MyAdapter mMyAdapter;
    private NoteDbOpenHelper mNoteDbOpenHelper;
    private LinearLayout jingdian1,jingdian2;

    public shouyeFragment() {
        // Required empty public constructor
    }


    public static shouyeFragment newInstance(String param1, String param2) {
        shouyeFragment fragment = new shouyeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shouye, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
        jingdian1 = view.findViewById(R.id.shouye1);
        jingdian2 = view.findViewById(R.id.shouye2);
        jingdian1.setOnClickListener(this);
        jingdian2.setOnClickListener(this);
    }

    private void initDate() {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.shouye1:
                Intent i1 = new Intent(getActivity(),jingdianActivity1.class);
                startActivity(i1);
                break;
                case R.id.shouye2:
                    Intent i2 = new Intent(getActivity(),jingdianActivity2.class);
            default:
                break;
        }


    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
//        setHasOptionsMenu(true);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                mNotes =  mNoteDbOpenHelper.queryFromDbByTitle(newText);
//                mMyAdapter.refreshData(mNotes);
//                return true;
//            }
//        });
//         super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    private MenuInflater getMenuInflater() {
//        return getMenuInflater();
//    }
}