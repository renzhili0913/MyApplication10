package com.example.week04_03.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.week04_03.R;
import com.example.week04_03.bean.PhotoBean;
import com.example.week04_03.view.AddSubView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter <MyRecyclerAdapter.ViewHolder>{
    private Context context;
    private List<PhotoBean.DataBean.ListBean> list;
    private Map<String,String> map=new HashMap<>();

    public MyRecyclerAdapter(Context context) {
        this.context = context;
    }
    //添加数据

    public void setList(PhotoBean photoBean) {
        if(list == null){
            list = new ArrayList<>();
        }
        //第一层遍历商家和商品
        for (PhotoBean.DataBean databean: photoBean.getData()
             ) {
            //把商品的id和商品的名称添加到map集合里 ,,为了之后方便调用
            map.put(databean.getSellerid(),databean.getSellerName());
            for (int i =0;i<databean.getList().size();i++){
                //添加到list集合里
                list.add(databean.getList().get(i));
            }
        }
        //调用方法 设置显示或隐藏 商铺名
        setFirst(list);
        notifyDataSetChanged();
    }
    /**
     * 设置数据源,控制是否显示商家
     * */
    private void setFirst(List<PhotoBean.DataBean.ListBean> list) {
        if (list.size()>0){
            //如果是第一条数据就设置isFirst为1
            list.get(0).setIsFirst(1);
            //从第二条开始遍历
            for (int i =1;i<list.size();i++){
                //如果和前一个商品是同一家商店的
                if (list.get(i).getSellerid()==list.get(i-1).getSellerid()){
                    //设置成2不显示商铺
                    list.get(i).setIsFirst(2);
                }else{
                    list.get(i).setIsFirst(1);
                    //如果当前条目选中,把当前的商铺也选中
                    if (list.get(i).isItem_check()==true){
                        list.get(i).setShop_check(list.get(i).isItem_check());
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recycle_adapter,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyRecyclerAdapter.ViewHolder viewHolder, final int i) {
        /**
         * 设置商铺的 shop_checkbox和商铺的名字 显示或隐藏
         * */
        if (list.get(i).getIsFirst()==1){
            //显示商家
            viewHolder.shop_checkbox.setVisibility(View.VISIBLE);
            viewHolder.shop_name.setVisibility(View.VISIBLE);
            //设置shop_checkbox的选中状态
            viewHolder.shop_checkbox.setChecked(list.get(i).isShop_check());
            viewHolder.shop_name.setText(map.get(String.valueOf(list.get(i).getSellerid())));
        }else{
            //隐藏商家
            viewHolder.shop_checkbox.setVisibility(View.INVISIBLE);
            viewHolder.shop_name.setVisibility(View.INVISIBLE);
        }
        //拆分images字段
        String[] split = list.get(i).getImages().split("\\|");
        //设置商品的图片
        Glide.with(context).load(split[0]).into(viewHolder.item_face);
        //控制商品的item_checkbox,,根据字段改变
        viewHolder.item_checkbox.setChecked(list.get(i).isItem_check());
        viewHolder.item_name.setText(list.get(i).getTitle());
        viewHolder.item_price.setText("价格："+list.get(i).getPrice());
        //调用addsubview里面的方法设置 加减号中间的数字
        viewHolder.addSubView.setEditText(list.get(i).getNum());
        //商铺的shop_checkbox点击事件 ,控制商品的item_checkbox
        viewHolder.shop_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先改变数据源中的shop_check
                list.get(i).setShop_check(viewHolder.shop_checkbox.isChecked());
                for (int a=0;a<list.size();a++){
                    //如果是同一家商铺的 都给成相同状态
                    if (list.get(a).getSellerid()==list.get(i).getSellerid()){
                        //当前条目的选中状态 设置成 当前商铺的选中状态
                        list.get(a).setItem_check(viewHolder.shop_checkbox.isChecked());
                    }
                }
                //刷新适配器
                notifyDataSetChanged();
                //调用求和的方法
                sum(list);
            }
        });
        //商品的item_checkbox点击事件,控制商铺的shop_checkbox
        viewHolder.item_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先改变数据源中的item_checkbox
                list.get(i).setItem_check(viewHolder.item_checkbox.isChecked());
                //反向控制商铺的shop_checkbox
                for (int a=0;a<list.size();a++){
                    for (int b=0;b<list.size();b++){
                        //如果两个商品是同一家店铺的 并且 这两个商品的item_checkbox选中状态不一样
                        if(list.get(a).getSellerid()==list.get(b).getSellerid() && !list.get(b).isItem_check()){
                            //就把商铺的shop_checkbox改成false
                            list.get(a).setShop_check(false);
                            break;
                        }else{
                            //同一家商铺的商品 选中状态都一样,就把商铺shop_checkbox状态改成true
                            list.get(a).setShop_check(true);
                        }
                        }
                }
                //刷新适配器
                notifyDataSetChanged();
                //调用求和的方法
                sum(list);
            }
        });
        //删除条目的点击事件
        viewHolder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(i);//移除集合中的当前数据
                //删除完当前的条目 重新判断商铺的显示隐藏
                setFirst(list);
                //调用重新求和
                sum(list);
                notifyDataSetChanged();
            }
        });
        //加减号的监听,
        viewHolder.addSubView.setOnCustomListener(new AddSubView.CustomListener() {
            @Override
            public void subNum(int count) {
                //改变数据源中的数量
                list.get(i).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }

            @Override
            public void add(int count) {
                list.get(i).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }
        });

    }
    /**
     * 计算总价的方法
     * */
    private void sum(List<PhotoBean.DataBean.ListBean> list) {
        int totalNum = 0;//初始的总价为0
        float totalMoney = 0.0f;
        boolean allCheck = true;
        for (int i=0;i<list.size();i++){
            if (list.get(i).isItem_check()){
                totalNum+=list.get(i).getNum();
                totalMoney+=(float) (list.get(i).getNum()*list.get(i).getPrice());
            }else{
                //如果有个未选中,就标记为false
                allCheck=false;
            }

        }
        //接口回调出去 把总价 总数量 和allcheck 传给view层
        updateListener.setTotal(totalMoney+"",totalNum+"",allCheck);
        }
    //view层调用这个方法, 点击quanxuan按钮的操作
    public void quanXuan(boolean bool){
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setShop_check(bool);
            list.get(i).setItem_check(bool);
        }
        notifyDataSetChanged();
        sum(list);
    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox shop_checkbox,item_checkbox;
        private TextView shop_name,item_name,item_price;
        private AddSubView addSubView;
        private ImageView item_face,item_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_checkbox=itemView.findViewById(R.id.shop_checkbox);
            item_checkbox=itemView.findViewById(R.id.item_checkbox);
            shop_name=itemView.findViewById(R.id.shop_name);
            item_name=itemView.findViewById(R.id.item_name);
            item_price=itemView.findViewById(R.id.item_price);
            addSubView=itemView.findViewById(R.id.addsubview);
            item_face=itemView.findViewById(R.id.item_face);
            item_delete=itemView.findViewById(R.id.item_delete);
        }
    }
    //声明接口
    UpdateListener updateListener;
    public void setUpdateListener(UpdateListener updateListener){
        this.updateListener = updateListener;
    }
    //接口
    public interface UpdateListener{
        void setTotal(String total,String num,boolean allCheck);
    }
}
