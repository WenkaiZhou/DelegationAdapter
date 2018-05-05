# DelegationAdapter
一种优雅的方式来使用RecyclerView

## 核心思想

　　想必大家都遇到过，在一个列表中显示不同样式的需求。在RecyclerView中可以通过ViewType进行区分，如果样式特别多的时候就会使得代码非常冗余，不利于开发及维护。那么有没有一种优雅的方法解决这个问题呢？

　　技术经理给你说，接下来的项目由你负责，明天下班前把排期同步处理。这时你应该怎么做？由于你说Android端的研发，你对Android的排期是比较了解的，但是iOS端、FE端、Server端的排期怎么办呢？聪明的你立即把任务派发下去了，给每个端的负责人说，明天中午之前把排期汇总给我。

　　没错，在多样式列表的设计中也可以采用这种策略，给RecyclerView设置的Adapter不做具体的处理，而是由它派发出去。

## 实现方案

1. **addDelegate** 向Adapter中注册委托Adapter;
2. **addDatas** 设置数据；
3. **layout** 渲染布局，Adapter查找到对应的委托Adapter，由委托Adapter去做具体渲染。

![](https://raw.githubusercontent.com/xuehuayous/DelegationAdapter/master/show.gif)

## 如何使用

### 基本用法

1. 

## THANKS TO

1. [MultiItem](https://github.com/free46000/MultiItem) 委托思想来源
2. [AdapterDelegates](https://github.com/sockeqwe/AdapterDelegates) 委托架子来源