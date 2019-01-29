# 一种优雅的方式来使用RecyclerView

使得RecyclerView各种情况的多类型条目更简单！

[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0)
[![JCenter](https://img.shields.io/badge/%20JCenter%20-1.1.1-5bc0de.svg?style=flat-square)](https://bintray.com/xuehuayous/maven/DelegationAdapter/_latestVersion)
[![MinSdk](https://img.shields.io/badge/%20MinSdk%20-%2014%2B%20-f0ad4e.svg?style=flat-square)](https://android-arsenal.com/api?level=14)

## 示例图片

<table>
  <tr>
    <td>
    	<img src="https://raw.githubusercontent.com/xuehuayous/DelegationAdapter/master/sample/pic/01.jpg" width="300" />
    </td>
    <td>
    	<img src="https://raw.githubusercontent.com/xuehuayous/DelegationAdapter/master/sample/pic/02.jpg" width="300" />
	</td>
	<td>
        <img src="https://raw.githubusercontent.com/xuehuayous/DelegationAdapter/master/sample/pic/03.jpg" width="300" />
    </td>
  </tr>
  <tr>
    <td>
      <img src="https://raw.githubusercontent.com/xuehuayous/DelegationAdapter/master/sample/pic/04.jpg" width="300" />
    </td>
    <td>
      <img src="https://raw.githubusercontent.com/xuehuayous/DelegationAdapter/master/sample/pic/02.png" width="300" />
    </td>
  </tr>
</table>

## 下载体验

<img src="https://raw.githubusercontent.com/xuehuayous/DelegationAdapter/master/sample/pic/qrcode.png" width="200" />

## 核心思想

　　想必大家都遇到过，在一个列表中显示不同样式的需求。在RecyclerView中可以通过ViewType进行区分，如果样式特别多的时候就会使得代码非常冗余，不利于开发及维护。那么有没有一种优雅的方法解决这个问题呢？

　　技术经理给你说，接下来的项目由你负责，明天下班前把排期同步出来。这时你应该怎么做？由于你是Android端的RD，你对Android的排期是比较了解的，但是iOS端、FE端、Server端的排期怎么办呢？聪明的你立即把任务派发下去了，给每个端的负责人说，明天中午之前把排期汇总给我。

　　没错，在多样式列表的设计中也可以采用这种策略，给RecyclerView设置的Adapter不做具体的处理，而是由它派发出去。

## 实现方案

1. **addDelegate** 向Adapter中注册委托Adapter;
2. **addDataList** 设置数据；
3. **layout** 渲染布局，Adapter查找到对应的委托Adapter，由委托Adapter去做具体渲染。

![](https://raw.githubusercontent.com/xuehuayous/DelegationAdapter/master/show.gif)

## 引入

```
implementation 'com.kevin:delegationadapter:1.1.2'
// 扩展库，扩展支持了item click、item long click、databinding
implementation 'com.kevin:delegationadapter-extras:1.1.2'
```

## 如何使用

### 简单用法

1. 带RecyclerView的布局

	```
	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent">
	
	    <android.support.v7.widget.RecyclerView
	        android:id="@+id/recycler_view"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent" />
	
	</LinearLayout>
	```

2. 初始化RecyclerView

	Adapter为DelegationAdapter，然后向DelegationAdapter中注册委托Adapter。

	```
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        // ① 设置 LayoutManager
        // LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        // ② 创建 DelegationAdapter 对象
        DelegationAdapter delegationAdapter = new DelegationAdapter();
        // ③ 向Adapter中注册委托Adapter
        delegationAdapter.addDelegate(new CompanyAdapterDelegate());
        // ④ 设置Adapter
        recyclerView.setAdapter(delegationAdapter);
    }
	```

3. 委托Adapter编写

	委托Adapter继承自AdapterDelegate，需要两个泛型，第一个为该委托Adapter可处理数据的数据类型(这里为String)，第二个参数为ViewHolder。剩下的就按照之前怎么写Adapter来写委托Adapter就可以啦。比如：在onCreateViewHolder创建ViewHolder，在onBindViewHolder中绑定数据到视图控件。

	```
    public class CompanyAdapterDelegate extends AdapterDelegate<String, CompanyAdapterDelegate.ViewHolder> {
    
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }
    
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position, final String item) {
            holder.tvName.setText(item);
        }
    
        static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvName;
    
            public ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(android.R.id.text1);
            }
        }
    }
	```

4. 设置数据

	```
    protected void onCreate(Bundle savedInstanceState) {
        // ... ...
    
        List<String> companies = new ArrayList<>();
        companies.add("🇨🇳 Baidu");
        companies.add("🇨🇳 Alibaba");
        companies.add("🇨🇳 Tencent");
        companies.add("🇺🇸 Google");
        companies.add("🇺🇸 Facebook");
        companies.add("🇺🇸 Microsoft");
        // ⑤ 设置数据
        delegationAdapter.setDataItems(companies);
    }
	```
	
<img src="https://raw.githubusercontent.com/xuehuayous/DelegationAdapter/master/sample/pic/company_01.jpg" width="300" />
	
### 复杂用法

如果想区分🇨🇳公司为红色，美国公司为蓝色，怎么办呢？

1. 编写CNCompanyAdapterDelegate

    ```
    public class CNCompanyAdapterDelegate extends AdapterDelegate<String, CNCompanyAdapterDelegate.ViewHolder> {
    
        @Override
        public boolean isForViewType(String item, int position) {
            return item.contains("🇨🇳");
        }
    
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }
    
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position, final String item) {
            holder.tvName.setText(item);
            holder.tvName.setTextColor(Color.RED);
        }
        
        static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvName;
    
            public ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(android.R.id.text1);
            }
        }
    }
    ```

2. 编写USCompanyAdapterDelegate

    ```
    public class USCompanyAdapterDelegate extends AdapterDelegate<String, USCompanyAdapterDelegate.ViewHolder> {
    
        @Override
        public boolean isForViewType(String item, int position) {
            return item.contains("🇺🇸");
        }
    
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }
    
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position, final String item) {
            holder.tvName.setText(item);
            holder.tvName.setTextColor(Color.BLUE);
        }
    
        static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvName;
    
            public ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(android.R.id.text1);
            }
        }
    }
    ```

3. 注册委托Adapter

```
// 向Adapter中注册委托Adapter
delegationAdapter.addDelegate(new CNCompanyAdapterDelegate());
delegationAdapter.addDelegate(new USCompanyAdapterDelegate());
```

<img src="https://raw.githubusercontent.com/xuehuayous/DelegationAdapter/master/sample/pic/company_02.jpg" width="300" />

### 更多请看示例

## THANKS TO

1. [MultiItem](https://github.com/free46000/MultiItem) 委托思想来源
2. [AdapterDelegates](https://github.com/sockeqwe/AdapterDelegates) 委托架子来源
