# 一种优雅的方式来使用RecyclerView

使得RecyclerView各种情况的多类型条目更简单！

[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0)
[![JCenter](https://img.shields.io/badge/%20JCenter%20-1.0.4-5bc0de.svg?style=flat-square)](https://bintray.com/xuehuayous/maven/DelegationAdapter/_latestVersion)
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
compile 'com.kevin:delegationadapter:1.0.4'
// 扩展库，扩展支持了item click、item long click、databinding
compile 'com.kevin:delegationadapter-extras:1.0.3'
```

## 用法

### 同一数据类型多种样式


```
// 设置LayoutManager
LinearLayoutManager layoutManager = new LinearLayoutManager(this);
recyclerView.setLayoutManager(layoutManager);
// 设置Adapter
delegationAdapter = new DelegationAdapter();
// 添加委托Adapter
delegationAdapter.addDelegate(new OnePicDelegateAdapter());
delegationAdapter.addDelegate(new ThreePicDelegateAdapter());
delegationAdapter.addDelegate(new MorePicDelegateAdapter());
delegationAdapter.addDelegate(new VideoDelegateAdapter());
recyclerView.setAdapter(delegationAdapter);
```

### 不同数据类型多种样式

```
// 设置LayoutManager
LinearLayoutManager layoutManager = new LinearLayoutManager(this);
mRecyclerView.setLayoutManager(layoutManager);
// 设置Adapter
mDelegationAdapter = new DelegationAdapter();
mDelegationAdapter.addDelegate(new StringAdapterDelegate());
mDelegationAdapter.addDelegate(new IntegerAdapterDelegate());
mDelegationAdapter.addDelegate(new FloatAdapterDelegate());
mDelegationAdapter.addDelegate(new DoubleAdapterDelegate());
// 添加委托Adapter
mRecyclerView.setAdapter(mDelegationAdapter);

// 设置数据
List<Object> dataList = new ArrayList<>();
dataList.add("今天天气怎么样？");  // 添加一个String类型数据
dataList.add("大晴天，有点热。");  // 添加一个String类型数据
dataList.add("温度多少度呢？");    // 添加一个String类型数据
dataList.add(29);                // 添加一个int类型数据
dataList.add("具体是多少？");      // 添加一个String类型数据
dataList.add(29.5F);             // 添加一个Float类型数据
dataList.add(29.527921364978D);  // 添加一个Double类型数据
mDelegationAdapter.setDataItems(dataList);
```

### 同一数据多种类型

<img src="https://raw.githubusercontent.com/xuehuayous/DelegationAdapter/master/sample/pic/02.png" width="320" />

```
// 设置LayoutManager
mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
// 设置LayoutManager
mDelegationAdapter = new DelegationAdapter();
// 添加委托Adapter
mDelegationAdapter.addDelegate(new ServiceInfoAdapterDelegate());
mDelegationAdapter.addDelegate(new BillItemAdapterDelegate());
mDelegationAdapter.addDelegate(new ChargeInfoAdapterDelegate());
mBinding.recyclerView.setAdapter(mDelegationAdapter);

// 设置数据
String newsListStr = LocalFileUtils.getStringFormAsset(this, "bill.json");
Bill bill = new Gson().fromJson(newsListStr, Bill.class);

List<Object> dataList = new ArrayList<>();
dataList.add(new ItemData(bill, ServiceInfoDelegateAdapter.TAG));
dataList.addAll(bill.details);
dataList.add(new ItemData(bill, ChargeInfoDelegateAdapter.TAG));
mDelegationAdapter.setDataItems(dataList);
```

### 添加头部

```
// 设置LayoutManager
LinearLayoutManager layoutManager = new LinearLayoutManager(this);
mRecyclerView.setLayoutManager(layoutManager);
// 设置Adapter
mDelegationAdapter = new DelegationAdapter();
// 添加委托Adapter
mDelegationAdapter.addDelegate(new TextAdapterDelegate());
mDelegationAdapter.addDelegate(new BannerAdapterDelegate());
mRecyclerView.setAdapter(mDelegationAdapter);

// 添加头部
mDelegationAdapter.addHeaderItem("这是添加的添加的头部数据");
```

### 添加尾部

```
// 设置LayoutManager
LinearLayoutManager layoutManager = new LinearLayoutManager(this);
mRecyclerView.setLayoutManager(layoutManager);
// 设置Adapter
mDelegationAdapter = new DelegationAdapter();
// 添加委托Adapter
mDelegationAdapter.addDelegate(new TextAdapterDelegate());
mDelegationAdapter.addDelegate(new BannerAdapterDelegate());
mRecyclerView.setAdapter(mDelegationAdapter);

// 添加尾部
mDelegationAdapter.addFotterItem("这是添加的添加的尾部数据");
```

### 带兜底的委托Adapter

```
// 设置LayoutManager
LinearLayoutManager layoutManager = new LinearLayoutManager(this);
mRecyclerView.setLayoutManager(layoutManager);
// 设置Adapter
mDelegationAdapter = new DelegationAdapter();
// 添加委托Adapter
mDelegationAdapter.addDelegate(new TextAdapterDelegate());
// 添加兜底的委托Adapter
mDelegationAdapter.setFallbackDelegate(new FallbackAdapterDelegate());
mRecyclerView.setAdapter(mDelegationAdapter);
```

## THANKS TO

1. [MultiItem](https://github.com/free46000/MultiItem) 委托思想来源
2. [AdapterDelegates](https://github.com/sockeqwe/AdapterDelegates) 委托架子来源