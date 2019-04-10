# MvpLibary
mvp的应用方式：
	allprojects {
	
		repositories {
		
			...
			maven { url 'https://jitpack.io' }
			
		}
		
	}
	
  dependencies {
  
	        implementation 'com.github.raotongGitup:MvpLibary:v1.1'
		
	}

具体实现方式：

#  协议类（自己根据项目自定义）：

 public class ViewContract {
 
public interface View  extends BaseView{

        // 网络返回数据
        void onLoading();
	void onError();
	void onSuccent();
} 
public interface Pressenter {

       // 获取网络请求
       
        void getdata();
}

public interface Moudle {

        // 网络返回的数据
	
        Object getData();
	
 }
# moudle的使用：

public class UseInfoMoudle extends BaseMoudle implements ViewContract.Moudle {

    @Override
    public Object getData() {
    
        // 网络请求
	
        return null;
    }
    
}
# presenter使用（moudle,个view不用再创建了）：


public class UserInfoPresenetr extends BasePresenter<ViewContract.View, UseInfoMoudle> implements ViewContract.Pressenter {
     @Override
     
    public void getdata() {
    
        // 获取网络返回的数据
	  // 获取网络返回的数据
        getMoudle()// 直接调用moudle(不用在创建)
        getView()//直接调用view()（不用在创建)）
       
    }
 # view的使用（mpresenetr不用再去创建）（：
 
    public class MainActivity extends AppCompatActivity {
    
    @InjectPresenter
    
    private UserInfoPresenetr mpresenetr;

    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
