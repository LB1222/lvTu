package com.example.lvtu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.widget.AdapterView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class wodeFragment extends Fragment {

    ListView listView;
    SimpleAdapter simpleAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wode, container, false);
        simpleAdapter = new SimpleAdapter(getActivity(),getData(),R.layout.wode_menu_layout,new String[]{"title","image"},new int[]{R.id.myMenu_name,R.id.myMenu_image});
        listView =view.findViewById(R.id.list_wode);
        listView.setAdapter(simpleAdapter);


        return view;
    }
    private List<Map<String,Object>> getData() {
        String [] titles={"请登录账号","用户协议","关于我们","联系我们","设置"};
        int [] images={R.drawable.biaoqing,R.drawable.wenjian,R.drawable.wenjian,
                R.drawable.email,R.drawable.shezhi,
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

}