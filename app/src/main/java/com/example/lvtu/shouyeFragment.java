package com.example.lvtu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.MyAdapter;

import Adapter.RecyclehotelAdapter;
import bean.Hotel;
import bean.Note;
import date.NoteDbOpenHelper;


public class shouyeFragment extends Fragment implements View.OnClickListener {
Intent i1,i2,i3,i4,i5,i6,i11,i22;
Button bt_jd;
    GridView gridView;
    RecyclerView recyclerView;
    private List<Hotel>bookList = new ArrayList<>();
    SimpleAdapter simpleAdapter;

    private LinearLayout jingdian1,jingdian2,jingdian3,jingdian4,jingdian5;
    //private Spinner spinner;
    //private ArrayAdapter arrayAdapter;
    //定义字符串数组,指定数组的元素
    //private String[] spinner1 = new String[]{"全部","上海","深圳","北京"};
    public shouyeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shouye,container,false);

        simpleAdapter = new SimpleAdapter(getActivity(),getData(),R.layout.shouye_menu_layout,new String[]{"image","title"},new int[]{R.id.hotel_Iv,R.id.hotel_name});
        gridView =v.findViewById(R.id.grid_shouye);
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        i11 = new Intent(getActivity(), zone_1_Activity.class);
                        startActivity(i11);
                        break;
                    case 1:
                        i22 = new Intent(getActivity(), zone_2_Activity.class);
                        startActivity(i22);
                        break;
                }
            }
        });

       changeGridView();
        initBooks();
        recyclerView=  v.findViewById(R.id.lv);
       LinearLayoutManager linearLayoutManager = new  LinearLayoutManager(getActivity());
       linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new
              StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        RecyclehotelAdapter recycleBookAdapter = new RecyclehotelAdapter(bookList);
        //bookAdapter adapter = new bookAdapter(bookList);
        recyclerView.setAdapter(recycleBookAdapter);
        return v;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param context   上下文
     * @param dpValue   dp值
     * @return  px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    private void changeGridView() {
        int size = getData().size();
        int itemwidth = dip2px(getActivity(),70);
        int itemPaddingH = dip2px(getActivity(),1);
        int gridviewWidth = size * (itemwidth);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params);
        gridView.setColumnWidth(itemwidth);
        gridView.setHorizontalSpacing(itemPaddingH);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size);
    }

    private List<Map<String,Object>> getData() {
        String [] titles={"酒店","机票","火车票","汽车票","门票"};
        int [] images={R.mipmap.hotel,R.mipmap.plane,R.mipmap.sub,
                R.mipmap.car,R.mipmap.men,
        };
        List<Map<String,Object>> list= new ArrayList<>();
        for(int i=0;i<5;i++){
            Map  map = new HashMap();
            map.put("title",titles[i]);
            map.put("image",images[i]);
            list.add(map);
        }
        return list;

    }
    private void initBooks()
    {
        for (int i = 0;i<6; i++){
            Hotel a = new Hotel("速八酒店",R.mipmap.suba);
            bookList.add(a);
            Hotel b = new Hotel("汉庭酒店",R.mipmap.hanting);
            bookList.add(b);
            Hotel c = new Hotel("如家酒店",R.mipmap.rujia);
            bookList.add(c);
            Hotel d = new Hotel("商客酒店",R.mipmap.shangke);
            bookList.add(d);
            Hotel e = new Hotel("益丰酒店",R.mipmap.yifeng);
            bookList.add(e);
            Hotel f = new Hotel("洲际酒店",R.mipmap.zhouji);
            bookList.add(f);


        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //。initDate();
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
//        bt_jd = view.findViewById(R.id.bt_jiudian);
//        bt_jd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                i6 = new Intent(getActivity(),jiudianActivity.class);
//                startActivity(i6);
//            }
//        });



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