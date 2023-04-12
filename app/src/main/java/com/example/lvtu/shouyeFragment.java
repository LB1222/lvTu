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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import Adapter.MyAdapter;

import bean.Note;
import date.NoteDbOpenHelper;


public class shouyeFragment extends Fragment implements View.OnClickListener {
Intent i1,i2,i3,i4,i5,i6;
Button bt_jd;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private Button button;
    private List<Note> mNotes;
    private MyAdapter mMyAdapter;
    private NoteDbOpenHelper mNoteDbOpenHelper;
    private LinearLayout jingdian1,jingdian2,jingdian3,jingdian4,jingdian5;
    //private Spinner spinner;
    //private ArrayAdapter arrayAdapter;
    //定义字符串数组,指定数组的元素
    //private String[] spinner1 = new String[]{"全部","上海","深圳","北京"};
    public shouyeFragment() {
        // Required empty public constructor
    }


    public static shouyeFragment newInstance(String param1, String param2,String param3,String param4,String param5) {
       Spinner spinner;
        shouyeFragment fragment = new shouyeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shouye,container,false);
        Spinner spinner = v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.spinner_options, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // Inflate the layout for this fragment

     spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //处理选择事件
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //处理未选择事件
        }
    });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
        jingdian1 = view.findViewById(R.id.shouye1);
        jingdian2 = view.findViewById(R.id.shouye2);
        jingdian3 = view.findViewById(R.id.shouye3);
        jingdian4 = view.findViewById(R.id.shouye4);
        jingdian5 = view.findViewById(R.id.shouye5);
        jingdian1.setOnClickListener(this);
        jingdian2.setOnClickListener(this);
        jingdian3.setOnClickListener(this);
        jingdian4.setOnClickListener(this);
        jingdian5.setOnClickListener(this);
        bt_jd = view.findViewById(R.id.bt_jiudian);
        bt_jd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i6 = new Intent(getActivity(),jiudianActivity.class);
                startActivity(i6);
            }
        });



    }

    private void initDate() {
    }


    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.shouye1:
                i1 = new Intent(getActivity(),jingdianActivity1.class);
                startActivity(i1);
                break;
            case R.id.shouye2:
                i2 = new Intent(getActivity(),jingdianActivity2.class);
                startActivity(i2);
                break;
            case R.id.shouye3:
                i3 = new Intent(getActivity(),jingdianActivity3.class);
                startActivity(i3);
                break;
            case R.id.shouye4:
                i4 = new Intent(getActivity(),jingdianActivity4.class);
                startActivity(i4);
                break;
            case R.id.shouye5:
                i5 = new Intent(getActivity(),jingdianActivity5.class);
                startActivity(i5);
                break;

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