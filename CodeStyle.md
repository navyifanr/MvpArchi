Android编程规范文档
--------------------------------------
## 前言
在软件开发中，没有规矩不成方圆，但规则是人制定的，没有标准，只有适合自己或团队的才是最好的，当然前提是整体上要符合编程语言的规范。以下规范是我之前在公司编写的Android编程规范文档。(规范都大同小异，参考修改自[【Android开发规范】-gityuan](http://gityuan.com/2015/08/10/android-arch-coding-style/))

## 一、命名规范
### 1.包命名

规则：包名全部小写，采用反域名命名规则，一级包名是顶级域名，通常为com, edu, gov, net, org等，二级包名，通常为公司名或部门名或者个人名，三级包名通常为项目名，四级包名为模块名或者层级名。以下是从层级包名来划分Android项目中采用的包划分结构：(在实际项目中可以采用包命名和模块命名结合的方式)

```
- com.domain.xxx.ui (用户界面相关)
    - com.domain.xxx.ui.activity        //所有的Activity类
    - com.domain.xxx.ui.fragment     //所有的Fragment类
    - com.domain.xxx.ui.dialog       //所有对话框
- com.domain.xxx.ui.adapter        //所有的Adapter类（适配器类）
- com.domain.xxx.view              //自定义的View类
- com.domain.xxx.service           //后台Service类
- com.domain.xxx.util              //常用的公共工具类（Json解析、数值处理、屏幕、日期处理、图片处理、日志等）
- com.domain.xxx.bean(or model)    //实体模型类
- com.domain.xxx.db                //数据库操作相关的类
- com.domain.xxx.config            //所有的配置相关的类
- com.domain.xxx.api               //网络api接口相关de 类
- com.domain.xxx.broadcast         //广播相关
```

另外，大层级下，可以按照模块分小层级，利于代码的阅读和查找，如自定义View层级下，可以新建一个下拉刷新控件相关的层级ptr，即com.domain.xxx.view.ptr

### 2.类命名

规则：采用大驼峰式命名法，首字母大写，尽量避免缩写，除非该缩写是众所周知的，比如HTML，URL,如果类名称包含单词缩写，则单词缩写的每个字母均应大写。以下列举的是Android中几种最为常用的类的命名:
```
class LoginActivity;     //activity类
class DiscoverFragment;  //fragment类
class AnalysisService;   //service类
class WakeupRankAdapter; //adapter类
class StringUtils;       //工具类
class UserEntity;  or UserModel  UserBean  //模型类;   Enitity后缀，如果原先命名太长可以不加，短的话，可以加，避免命名太过冗长
class ApiImpl;           //接口实现类
```

### 3.接口命名

规则：命名规则与类命名一样采用大驼峰式命名法，首字母大写，多以able, ible, er结尾
```
interface Comparable;
interface Accessible;
interface OnClickListener;
```

### 4.方法

规则：采用小驼峰命名法，首字母小写，方法名采用动词或动名词结构。方法的命名应该与方法的真正行为具有对应关系，下面给出一些方法名的动词前缀标示的建议。
```
方法名	描述
getXX()	获取某个属性的返回值 (getter、setter可以使用[GsonFormat插件](http://www.jianshu.com/p/40608ce179cb)自动生成)
setXX()	设置某个属性值
initXX()	初始化方法，如初始化布局initViews()，初始化控件点击事件 initEvent()
loadxxxData()  获取网络某个模块（页面）数据
isXX()	判断是否true的方法；
checkXX()	与isXX意义等价
processXX()	处理数据
updateXX()	更新数据
saveXX()	保存数据
addXX()	添加数据
deleteXX()	删除数据
resetXX()	重置数据
clearXX()	清除数据
removeXX()	移除数据或者视图等，如removeView();
drawXX()	绘制数据或者视图
```

### 5.变量

规则：采用小驼峰命名法，首字母小写。变量名应简短且能描述其用途，尽量避免拼音，无意义地缩写。除非是临时变量，否则不建议使用单个字符的变量名，如i, j, k。对于变量命名，还有一种风格是google的以字母m为前缀（m为member缩写），在android 源码中随处可见
```
private int userName;  //java的一般性风格
private int mUserName; //google的成员变量风格，m为member的缩写
```
还有，约定俗成的命名或缩写：
如对象的List或ArrayList，对象名（或含义名）后直接加List；对应对象的数组，在对象名（或含义名）后加s
```
List<User> userList;
ArrayList<MulCard> mulCardList;
List<String> titleList;
String[] titles;
Model[] models;
缩写：
String ----str，如jsonStr
image----img，
message---msg
```

### 6.常量

规则：常量使用全大写字母加下划线的方式命名。
```
public static final int TAG= "tag";
public static final int START_CLASS_NOT_FOUND = -2;
```

下面介绍的与Android关系更加紧密：

### 7.控件变量名

规则：首先需要满足第5条变量的规则， 模式：view缩写+逻辑名
```
Button btnSend, btnLogin
TextView tvName;
```
常用控件缩写：
```
TextView  tv
Button  btn
EditText et
ImageView iv
ImageButton ibtn
LinearLayout ll
RelativeLayout rl
FrameLayout fl
```

其他控件如果，当前页面使用次数只有2个以内，可以不按照上面view缩写+逻辑名的规则，直接用在原来类型名前面加个m的固定命名或功能模块+后缀如：(这样更直观简单点)
```
- ListView，包括封装的ListView，直接固定命名为：mListView  ，多个的话：msgListView, articleListView
- GridView，命名为：mGridView
- RecyclerView，命名为：mRecyclerView
- ScrollView，命名为：mScrollView
- TabLayout，命名为：mTabLayout
- ViewPager，命名为：mViewPager
```

同样，对应的适配器，一般一个页面只有适配器，命名为 mAdapter，多个可以根据功能模块+Adapter, 如：msgAdapter, articleAdapter

### 8.控件ID

规则：view缩写_模块名_逻辑名，全部小写，id名尽量保证唯一，便于在查找时，能够一次性找到

android:id="@+id/btn_news_send"  //样例
view缩写如下：

View     |    缩写
---------|-----------
TextView  |    tv
EditText  |    et
Button    |    btn
ImageButton   |   ibtn
ImageView  |   iv
LinearLayout  |   ll
RelativeLayout  |  rl
include    |   i

如：i_divider_line

其他控件，在一个页面只使用一次的，直接用控件名+模块名_逻辑名，如：
- list_view_wallet_invest_record 钱袋页面的投资记录
- scroll_view_wallet_invest_record


### 9.资源文件名

#### 9.1 layout的文件命名

规则：全部小写，采用下划线命名法。layout文件命名：组件类型_{模块_}功能.xml

act_news_title.xml //样例

命名规范     |     组件类型
-------------|-----------------
act_{模块_}功能  |   Activity命名格式
frg_{模块_}功能   |   Fragment命名格式
dialog_{模块_}功能   |   Dialog命名格式
pop_{模块_}功能      |   PopupWindow命名格式
item_list_{模块_}功能   |   ListView/RecyclerView的item命名格式
item_grid_{模块_}功能   |   GridView的item命名格式

其他类型或者封装成include复用的布局：view_{模块_}功能

当然，如果是特定自定义控件的，可以使用控件名_功能.xml，如，下拉刷新的头部：ptr_classic_header.xml

#### 9.2 drawable的文件命名

模式：前缀{_控件}{_范围}{_后缀}，控件、范围、后缀可选

bg_login_pressed.png //样例
drawable	命名细则
```
图标类	添加ic前缀
背景类	添加bg前缀
分隔类	添加div前缀
默认类	添加def前缀
区分状态时，默认状态	添加normal后缀
区分状态时，按下时的状态	添加pressed后缀
区分状态时，选中时的状态	添加selected后缀
区分状态时，不可用时的状态	添加disable后缀
多种状态的	添加selector后缀
```

#### 9.3 动画的文件命名

规则：{范围_}动画类型_动画方向。

login_fade_in.xml //样例

动画命名    |    描述
------------|------------
fade_in     |    淡入
fade_out    |    淡出
push_down_in  |  从下方推入
push_down_out   |  从下方推出
slide_in_from_top   |  从头部滑动进入
zoom_enter          |    变形进入
shrink_to_middle    |   中间缩小

### 10. 资源内的name命名

#### 10.1 strings.xml

模式：activity名_{范围_}逻辑名

<string name="login_username">用户名</string> //样例

#### 10.2 colors.xml

模式：前缀{_控件}{_范围}{_后缀}， 控件、范围、后缀可选，

<color name="bg_login">#FFFFFF</color> //样例

colors	命名细则
```
背景颜色	添加bg前缀
文本颜色	添加text前缀
分割线颜色	添加div前缀
区分状态时，默认状态的颜色	添加normal后缀
区分状态时，按下时的颜色	添加pressed后缀
区分状态时，选中时的颜色	添加selected后缀
区分状态时，不可用时的颜色	添加disable后缀
```

## 二、代码风格
### 1.代码整体排版风格
基本遵循代码的执行顺序，符合代码自上而下的阅读习惯，符合Activity、Fragment生命周期调用顺序等
如：Activity、Fragment中，变量基本按照以下顺序：(每一项分行隔开)

- a.静态变量，如Intent的传值
- b.布局的控件，按照布局自上而下，定义对应的控件
- c.适配器和对应的List<Model>的数据源
- d.请求数据需要的参数，如pageSize，pageNumber, type等
- e.返回的数据，需要全局定义的变量
- f.其他需要用到的变量

方法或内部类按照以下顺序：

- a.onCreate()
- b.initViews()
- c.initEvent()     控件不多时，可以直接setOnClickListener写匿名内部类的形式，不用再定义一个内部类
- d.onStart()/onReStart()/onResume()
- e.loadxxxData()  请求加载网络数据
- f.处理业务逻辑的封装类  (按照执行顺序写)
- g.onDestroy()
- h.自定义点击监听器的内部类/自定义广播接收器等

注意，需要使用到的控件尽量不要直接用findViewById来直接引用，最好定义一个类型

### 2.括号风格：不省略括号，左括号不换行
不提倡：
```
  if (condition)
      body();
  //或者：
  if (condition)
  {
  	body();
  }
```
正确做法：
```
  if (condition) {
      body();
  }
```
使用tab键来缩进

### 3.代码封装和模块化
a.建议使用Gson解析json数据成对应的Model的方式，而不是直接用JSONObject对一个个字段解析；
b.建议将具有复杂逻辑的ListView、GridView或Viewpager的适配器封装成单独的Adapter类处理逻辑，而不是直接在Activity或Fragment处理；
c.建议使用继承Base类，BaseActivity、BaseFragment等
d.复用的布局抽取出来，用include的方式；多次调用的代码，封装为一个控制器或工具类；
e.……

### 4.通用方法使用
TextUtils使用
a.判断字符串是否为空或null, TextUtils.isEmpty(str)  , 而不是写成 if(str==null||str.length==0)
b.判断两个字符串是否相等，TextUtils.equals(str1, str2)，而不是str1.equals(str2)   str1==null会空指针异常，而用前面方法，不会

match_parent使用
xml的layout布局中，统一使用match_parent而不是fill_parent，虽然功能一样，但fill_parent在api 8就被match_parent替换掉，而被标记过时了

## 三、注释
1.类注释

每个类完成后应该有作者姓名和联系方式的注释。只要在AS的Settings->File and Code Templates->Includes->File Header修改默认生成的模板为以下格式即可：
```
/**
* author xifan
* date: ${DATE}
* desc:
*/
```
如下：
```
/**
 * @author xifan
 * date: 2016/6/3
 * desc: 钱袋详情页面
 */
public class WalletActivity extends BaseBarFragmentActivity {

}
```
2.方法注释

每一个成员方法（包括自定义成员方法、覆盖方法、属性方法）的方法头都必须做方法头注释
```
 /**
 * 设置结算中的状态
 * @param isAccounting 是否处于结算中
 */
private void setAccountingState(boolean isAccounting){
}

```

3.快注释

直接在相应代码上一行或右边写注释
```
// 发送更新UI的消息
mHandler.sendMessage(mHandler.obtainMessage(0x2));
```

## 其他开发备注
1.水平分割线（没有margin的，margin尽量写在上下控件）统一使用
<include
    layout="@layout/view_divider_line"/>
对应布局为
```
<?xml version="1.0" encoding="utf-8"?>
<View xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="@dimen/divider_line_size"
    android:background="@color/divider_line_color">
</View>
```

虽然制定了开发的编程规范，但团队的成员如果不够重视，规范一般都不能真正落实，所以前期一定要说明规范的重要性，并互相做下代码规范的review, 不然规范文档只不过是形同虚设。


更多编程规范，可以查看：

[谷歌Android编程规范中文版](http://blog.csdn.net/lancees/article/details/7899138)

[Google Java编程风格指南](http://www.hawstein.com/posts/google-java-style.html)