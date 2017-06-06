# Android Modern Stack #


## Data Binding ##


Please read this [Codepath guide](https://guides.codepath.com/android/Applying-Data-Binding-for-Views) for further reference. Official Android guide for Data Binding can be found [here]( https://developer.android.com/topic/libraries/data-binding/index.html)

## Retrofit


Retrofit is a type-safe REST client for Android (or just Java) developed by Square. The library provides a powerful framework for authenticating and interacting with APIs and sending network requests with OkHttp. Refer to this [guide](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit) for further reference.

## Dagger 2 ##
Please read this [guide](https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2) for further reference. Make sure you understand what dependency injection is and why it's important.

## MVP ##
MVP, or Model-View-Presenter, is one method of refactoring your code to separate model and view logic. Please refer to this [guide](https://guides.codepath.com/android/Architecture-of-Android-Apps) and read up on the `Clean Architecture` section.


## Lab ## 
1. **Challenge** 
We will take our week 1 Flickster app and convert it using the above architecture and libraries, to create an app that leverages the most current Android libraries, such as `Dagger2`, `Retrofit`, `OkHttp`, etc. and a common app architecture, MVP (Model-View-Presentor).

Clone skeleton app: 
`git clone https://github.com/Codepath-ModernStack/Cliffnotes.git`

We have 5 branches at your disposal: 
`master`
`feature/1-databinding`
`feature/2-retrofit`
`feature/3-dagger2`
`feature/4-mvp`

Go over the code and make sure you understand all moving parts. The app has 2 activities (`MainActivity` & `MovieDetailsActivity`) and a simple `MovieRestClient` that uses `AsyncHttp`. You should be familiar with [Butter knife](http://jakewharton.github.io/butterknife/) and [Picasso](http://square.github.io/picasso/).

When loading, your app should look like this:

![Main Activity](http://i.imgur.com/tQ9XcJt.png?1)

# 2. Data Binding Challenge
**Note:** If you run into trouble, you can separately branch `feature/1-databinding` for reference.

### Data Binding lab 

We will start by integrating data binding in the Movie Details. Integrating data binding for the Main View is left as an exercise for the students.

- **2.1** Before implementing Data Binding make sure dataBinding is enabled in the app/build.gradle file:
```gradle
apply plugin: 'com.android.application'

android {
    // Previously there
    // Add this next line
    dataBinding.enabled = true // <----
    // ...
```
- **2.2** To eliminate view lookup's go to activity_details.xml add an outer `<layout>` tag as shown below. This will generate a class called ActivityDetailsBinding at compile time.
```xml
<layout xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android">
    <RelativeLayout xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <!-- .. rest of layout here -->
       </RelativeLayout>
</layout>
```
- **2.3** Though adding the `<layout>` tag will help eliminate the view lookups, we will go a step further and use one way data binding , explained [here](http://guides.codepath.com/android/Applying-Data-Binding-for-Views#one-way-data-binding), to load data from an object into the view. We do this by declaring `variable` nodes in the `data` section of the `<layout>`.
```xml
<layout xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable name="movie" type="com.codepath.flickster.models.Movie"/>
    </data>   
    <RelativeLayout xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <!-- .. rest of layout here -->
       </RelativeLayout>
</layout> ``
```
Zero or more `import` elements may be used inside the data element. These allow easy reference to classes inside your layout file, just like in Java. Import the `MovieImagePathUtils` class within the `data` element

```xml
<layout xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <import type="com.codepath.flickster.utils.MovieImagePathUtils"/>
        <variable name="movie" type="com.codepath.flickster.models.Movie"/>
    </data>   
    <RelativeLayout xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <!-- .. rest of layout here -->
       </RelativeLayout>
</layout> ``
```


- **2.4** We can now reference the `movie` object in our views using `@{variable.field}` syntax. Notice the `app:imageUrl` for `ImageView` and `android:text` for `TextView` shown below:
```xml
<layout xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <import type="com.codepath.flickster.utils.MovieImagePathUtils"/>
        <variable name="movie" type="com.codepath.flickster.models.Movie"/>
    </data>
    <RelativeLayout xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ImageView
            app:imageUrl = '@{MovieImagePathUtils.getBackdropImagePath(movie)}'
            android:id="@+id/ivBackdrop"
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:layout_alignParentLeft="true"
            android:layout_alignParentTop="true"
            android:scaleType="fitXY"
            tools:background="@android:color/holo_orange_dark" />

        <TextView
            android:id="@+id/tvTitle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentLeft="true"
            android:layout_below="@+id/ivBackdrop"
            android:layout_margin="20dp"
            android:text='@{movie.getOriginalTitle()}'
            android:textAppearance="@style/TextAppearance.AppCompat.Headline"
            tools:text="Arrival" />
    </RelativeLayout>
</layout>
```
**Note:** Unlike `TextViews`, you cannot bind values directly to `ImageViews`, we will need to create custom attribute like `app:imageUrl` . We then need to annotate a static method that maps the custom attribute:
```java 
public class BindingAdapterUtils {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).into(view);
    }
}
```
You can learn more about it [here](http://guides.codepath.com/android/Applying-Data-Binding-for-Views#image-loading) 
- **2.5** Go to `MovieDetailsActivity` and remove all the Butterknife references including `@BindView` and `ButterKnife.bind(this)`. Declare a private property `detailsBinding` and set the `movie` object to the binding:

```java
public class MovieDetailsActivity extends Activity {
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    ActivityDetailsBinding detailsBinding;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_details);
        movie = getIntent().getParcelableExtra("movie");
        detailsBinding.setMovie(movie);
    }
}
```

Make sure you have the necessary imports in place
```java 
import com.codepath.flickster.utils.MovieImagePathUtils;
import com.squareup.picasso.Picasso;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.codepath.flickster.R;
import com.codepath.flickster.databinding.ActivityDetailsBinding;
import com.codepath.flickster.models.Movie;
```
- **2.6** We now have fully functional Movie Details which uses data binding. In a similar way , implement data binding for `item_movie.xml` and `MoviesAdapter`. To learn how to use data binding inside RecyclerView, refer [here](http://guides.codepath.com/android/Applying-Data-Binding-for-Views#using-data-binding-inside-recyclerview)

# 3. Retrofit Challenge
**Note:** If you run into trouble, you can checkout branch `feature/2-retrofit` for reference.

To work with Retrofit, we need three basic classes:
1. Model class to map the API response
2. Interface to define the API endpoints
3. REST client class that creates the Retrofit instance and creates an implementation of the API endpoints defined in our interface


- **3.1** Let’s add the retrofit dependencies in our build.gradle file:
```gradle
compile 'com.squareup.retrofit2:retrofit:2.1.0'
compile 'com.squareup.retrofit2:converter-gson:2.1.0'
```
- **3.2** Let's create the classes to model the API response. Let's copy the files from https://drive.google.com/open?id=0B_VEFcmuRxT6RnU4Tm9Vcm42dDg and save them in the [/com/codepath/flickster/models](https://github.com/Codepath-ModernStack/Cliffnotes/tree/feature/2-retrofit/sample/Flickster/app/src/main/java/com/codepath/flickster/models) folder

- For reference, we can generate Java objects based on the JSON response:  http://guides.codepath.com/android/Consuming-APIs-with-Retrofit#create-java-classes-for-resources

- **3.3** Let’s define the endpoints inside of an Interface. Every method represents one API call and must have an HTTP annotation (e.g. GET, POST, etc) to specify the request type and the relative url.
- We will also be using special retrofit annotations to encode details about the parameters and request method. The return value wraps the response in a `Call` object with the type of the expected result and for our example, this will be of type `MovieResponse`.
- Let's create `MovieApiInterface.java` within the `com.codepath.flickster.networking` package.
```java
public interface MovieApiInterface {
   @GET("movie/now_playing")
   Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apiKey);
}
```

- **3.4** Let’s revisit `MovieRestClient.java` and instead of creating an AsyncHttpClient, let’s create a Retrofit instance.

Retrofit can be configured to use a specific converter, which handles data serialization/deserialization. If you recall from our [master](https://github.com/Codepath-ModernStack/Modern-Arch/blob/master/sample/Flickster/app/src/main/java/com/codepath/flickster/models/Movie.java#L39) branch, the `Movie` object is created by explicitly extracting values from the JSON response:

```java
public Movie(JSONObject jsonObject) throws JSONException {
   this.id = jsonObject.getString("id");
   this.posterPath = jsonObject.getString("poster_path");
   this.originalTitle = jsonObject.getString("original_title");
   this.overview = jsonObject.getString("overview");
   this.backdropPath = jsonObject.getString("backdrop_path");
}
```

But instead of explicitly having to do this, we'll use the Gson converter to do it for us:
```java
public MovieRestClient() {
   Gson gson = new GsonBuilder()
      .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
      .create();
}
```

Next, we’ll create Retrofit client and create a GsonConverterFactory passing in our Gson instance to handle serialization/deserialization of objects. 

```java
public MovieRestClient() {
   ...

   Retrofit client = new Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build();
}
```

Next, we'll create an implementation of the API endpoints defined by the Interface that we created.

```java
private MovieApiInterface apiInterface;

public MovieRestClient() {
   ...
   
   apiInterface = client.create(MovieApiInterface.class);
}
```
Lastly, putting it all together, we'll create a new method that invokes the API call we defined in our interface. The `enqueue` method is called to asynchronously send the request and notify the callback of its response or if any error occurred.
```java
public void nowPlaying(Callback<MovieResponse> responseHandler) {
   Call<MovieResponse> call = apiInterface.getNowPlayingMovies(API_KEY);
   call.enqueue(responseHandler);
}
```

From `MainActivity.java`, we can read the response if the request was successful or the exception if an error occurred.

```java
protected void populateMovieList() {
    // https://developers.themoviedb.org/3/movies/get-now-playing
    movieRestClient.nowPlaying(new Callback<MovieResponse>(){
        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
            Log.i(TAG, "Retrofit onResponse");
            if(response != null) {
                MovieResponse movieResponse = response.body();
                if(movieResponse != null && movieResponse.getResults() != null) {
                    updateMovieList(movieResponse.getResults());
                }
            }
        }

        @Override
        public void onFailure(Call<MovieResponse> call, Throwable t) {
            Log.e(TAG, "Retrofit onFailure", t);
        }
    });
}
```

# 4. Dagger2 Challenge
First, let's branch `feature/2-retrofit` and build this challenge on top of that branch.
**Note:** If you run into trouble, you can separately branch `feature/3-dagger2` for reference.

So... every time `MainActivity` starts, it instantiates a new Object of `MovieRestClient`:
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    ...
    movieRestClient = new MovieRestClient();
    initMovieList();
}
```
That's where Dagger2 comes into play :tada: 

**Step 4.1:** At this point, you should be familiar with Dependency Injection. We're going to add Dagger2 to our project.

Add this code to your `build.gradle` file (app gradle file, not project):
```gradle
dependencies {
    // apt command comes from the android-apt plugin
    compile "com.google.dagger:dagger:2.9"
    annotationProcessor "com.google.dagger:dagger-compiler:2.9"
    provided 'javax.annotation:jsr250-api:1.0'
}
```
Make sure to [upgrade](https://github.com/codepath/android_guides/wiki/Getting-Started-with-Gradle#upgrading-gradle) to the latest Gradle version to use the annotationProcessor syntax.

**Step 4.2:** Next thing we'll do is define a dependency module where we will define all our Singleton Objects that we want to have in the app (e.g we don't want to pass around the RestClient instance, and worry about memory leaks and multiple unnecessary object instantiations). 

Create a new class called `AppModule` within `com.codepath.flickster.networking` package, and add this code:
```java
@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides @Singleton
    Application providesApplication() {
        return mApplication;
    }
}
```

**Step 4.3:** We will need another module for networking. (It's a good practice to have different modules for different logic parts of your app)
```java
@Module
public class NetModule {

    private String mBaseUrl;

    // Constructor needs one parameter to instantiate.
    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
    }

    @Provides @Singleton
    // Gson argument is provided by the method above and it works the same
    // way for all other methods
    Retrofit providesRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides @Singleton
    MovieApiInterface providesApiInterface(Retrofit retrofit) {
        return retrofit.create(MovieApiInterface.class);
    }

    @Provides @Singleton
    MovieRestClient provideClient() {
        return new MovieRestClient();
    }
}
```

We pass along the base url when we create `NetModule` because we need it so we can build the `Retrofit` instance. Once retrofit recognizes a `@Inject` annotation, this is where it provides it from.

**Step 4.4:** After we defined the 2 modules, we need to have a centralized Dagger component. Lets create a class called `AppComponent.java`:
```java
@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface AppComponent {
    // We need these for every Activity/Fragment/View/Object that has a 
    // variable with the @Inject annotation
    
    // We need this for @Inject MovieRestClient inside MainActivity.java
    void inject(MainActivity activity);
    
    // We need this for @Inject MovieApiInterface in MovieRestClient.java
    void inject(MovieRestClient client);
}
```

An important aspect of Dagger 2 is that the library generates code for classes annotated with the @Component interface. You can use a class prefixed with Dagger (i.e. DaggerAppComponent.java) that will be responsible for instantiating an instance of our dependency graph and using it to perform the injection work for fields annotated with `@Inject`

**Step 4.5:** Now, make sure you extend your `Application` with a new `MovieApp`: 
```java
public class MovieApp extends Application {

    private AppComponent mAppComponent;
    private static Context context;

    public MovieApp() {
        context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger%COMPONENT_NAME%
        mAppComponent = DaggerAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule("http://api.themoviedb.org/3/"))
                .build();

        // If a Dagger 2 component does not have any constructor arguments for any of its modules,
        // then we can use .create() as a shortcut instead:
        //  mNetComponent = com.codepath.dagger.components.DaggerNetComponent.create();
    }

    public static Object getAppContext() {
        return context;
    }

    // We need this to inject with dagger inside custom objects
    public static MovieApp getApp() {
        return (MovieApp) getAppContext();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
```

**Step 4.6** Now, go to `AndroidManifest.xml` and add an `android:name=".MovieApp"` to the `<application>` tag. For reference, it should look like this now:
```xml
<application
    android:allowBackup="true"
    android:icon="@mipmap/ic_launcher"
    android:name=".MovieApp"
    android:label="@string/app_name"
    android:supportsRtl="true"
    android:theme="@style/AppTheme">
    <activity android:name=".activities.MainActivity">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />

            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
    <activity
        android:name=".activities.MovieDetailsActivity"
        android:label="@string/details_activity_label" />
</application>
```
**Step 4.7:** Now, we should go to `MainActivity` and remove the instantiation of `MovieRestClient` from `onCreate()`: ~~movieRestClient = new MovieRestClient();~~
Add this line under `super.onCreate()`:
`MovieApp.getApp().getAppComponent().inject(this);` to tell Dagger we're using injection in this class, and of course, add the `@Inject` annotation in front of `MovieRestClient movieRestClient`. 

The modified class should now have the following lines:
```java
@Inject MovieRestClient movieRestClient;

@Override
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   ...   
   MovieApp.getApp().getAppComponent().inject(this);
   ...
}
```
**Step 4.7:** Now, we can go to `MovieRestClient.java` and "ask" Dagger to inject a `MovieApiInterface` to the client and reduce the file from this:
```java
private MovieApiInterface apiInterface;

public MovieRestClient() {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    Retrofit client = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    apiInterface = client.create(MovieApiInterface.class);
}
```

to this:
```java
@Inject public MovieApiInterface apiInterface;

public MovieRestClient() {
    MovieApp.getApp().getAppComponent().inject(this);
}
```

And that's it! Now, every time you have an instance of a class that you think you should instantiate in multiple places, you just need to `@Inject` it!

For futher reading, try [this](http://frogermcs.github.io/dependency-injection-with-dagger-2-custom-scopes/) and [this](https://medium.com/@Miqubel/understanding-dagger-2-367ff1bd184f) and you absolutely must watch [this](https://youtu.be/plK0zyRLIP8) video by Jake Wharton.
# 5. MVP Challenge

**Note:** If you run into trouble, you can checkout branch `feature/4-mvp` for reference.

When creating an app, we often don't think about the architecture of the app before building it. And by default, we end up with an MVC or Model View Controller architecture. 

An MVC architecture can lead to huge activties/fragments, which make collaboration and testing difficult. In this challenge, we'll explore the MVP or Model View Presenter architecture (aka Clean Architecture). For more information on the difference between MVC and MVP as well as other types of architecture, please refer to [this guide](https://guides.codepath.com/android/Architecture-of-Android-Apps).

The MVP pattern has the project split into three parts.

**Model:** Data layer
**View:** The UI layer - Activites, Fragments, or android.view.View. The `View` is responsible for reacting to user's input and displaying data from the Presenter. 
**Presenter:** The logic layer. Fetches and manipulates Model objects, passes the results to Views, responds to actions performed on Views.

The `Preseneter` and `View` talk to each other via an Interface. 

To refactor our Flickster app into the MVP pattern, we first need to look at `MainActivity.java` and see what part of the code should be in the logic layer. Figuring out what code goes into the Presenter may be open to interpretation, but a general rule of thumb:
1. networking calls 
2. object manipulation 
3. code that contains `if` 

The method `populateMovieList` is a good candidate. Let’s create the `Presenter` and move that method. 
- **5.1** Create the MainPresenter Class. 

```
public class MainPresenter {
    private MainScreen mainScreen;
    private MovieRestClient movieRestClient;

    public MainPresenter(MainScreen mainView, MovieRestClient client) {
        this.mainScreen = mainView;
        this.movieRestClient = client;
    }
    
    //Todo: Add networking logic 
}
```
Since the movie client is injected in MainActivity via Dagger, we need to initiate MainPresenter with it. 

- **5.2** Move the populate movie logic to the presenter 
```
public class MainPresenter {
    private MainScreen mainScreen;
    private MovieRestClient movieRestClient;

    public MainPresenter(MainScreen mainView, MovieRestClient client) {
        this.mainScreen = mainView;
        this.movieRestClient = client;
    }

    public void populateMovieList() {
        this.movieRestClient.nowPlaying(new Callback() {
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.i("ContentValues", "onResponse");
                if(response != null) {
                    MovieResponse movieResponse = (MovieResponse)response.body();
                    if(movieResponse != null && movieResponse.getResults() != null) {
                        MainPresenter.this.mainScreen.updateMovieList(movieResponse.getResults());
                    }
                }

            }

            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("ContentValues", "onFailure");
            }
        });
    }
}
```
- **5.3** Delete the populate movie method, add a reference to the presenter, and call the presenter's populate movie method in `MainActivity`
```
public class MainActivity extends AppCompatActivity implements MainScreen {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mainBinding;

    @Inject
    MovieRestClient movieRestClient;

    private List<Movie> movieList;
    private MoviesAdapter adapter;
    private RecyclerView rvMovies;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApp.getApp().getAppComponent().inject(this);

        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        rvMovies = mainBinding.rvMovies;
        
        //Initiaing Presenter with the restclient. 
        mPresenter = new MainPresenter(this, movieRestClient);
        initMovieList();
    }

    private void initMovieList() {
        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(this, movieList);
        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvMovies.addItemDecoration(itemDecoration);

        ItemClickSupport.addTo(rvMovies).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Movie movie = movieList.get(position);
                        if (movie != null) {
                            gotoMovieDetails(movie);
                        } else {
                            Log.e(TAG, "Movie is NULL");
                        }
                    }
                });

        mPresenter.populateMovieList();
    }

    private void gotoMovieDetails(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
...
```
- **5.4** Create a `MainScreen` interface to connect the presenter to the activity. Put the `updateMovieList` method inside it. 

```
public interface MainScreen {
    void updateMovieList(List<Movie> movies);

}
```
Call this method in the presenter in the appropriate place 

```
public void populateMovieList() {

        // https://developers.themoviedb.org/3/movies/get-now-playing
        mClient.nowPlaying(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.i(TAG, "onResponse");
                if(response != null) {
                    MovieResponse movieResponse = response.body();
                    if(movieResponse != null && movieResponse.getResults() != null) {
                        mScreen.updateMovieList(movieResponse.getResults());
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure");
            }
        });
}
```
- **5.5** Great play the app!   

## Food for Thought
This current implmentation of MVP does not look into how Dagger can be used to inject the client into the `Presenter` directly and how to inject the `Presenter` into the `MainActivity`.

Look into how you can utilize Dagger in the MVP pattern. For some guidance, look at [this](https://adityaladwa.wordpress.com/2016/05/11/dagger-2-and-mvp-architecture/). 

**Dagger + MVP**

There are many ways to implement dagger in your project. Above, we used one component and had all the different parts be a module of that component. 

Another way is to create subcomponents for all the fragments/activities/views. The branch `feature/4-MVP-Dagger` shows how to implement Dagger with subcomponents and have the correct injections for the MVP pattern. 

A part that can be improved upon in this branch is the creation of a new instance of retrofit each time we make a network call. Think of ways we can store the instance of retrofit so we don't creat a new one every time we call get movie list! 
    





