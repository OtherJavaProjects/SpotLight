package com.rahul.spotlight;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.Browser;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.rahul.spotlight.QuickAction.OnActionItemClickListener;

public class SpotlightActivity extends Activity {

    //
    // Quick action variables
    //
    private LinearLayout qasdcard = null;
    private LinearLayout qasearch = null;
    private LinearLayout qashopping = null;
    private LinearLayout qaimages = null;
    private LinearLayout qavideo = null;
    private LinearLayout qamaps = null;
    private LinearLayout qaanswers = null;
    private LinearLayout qaweather = null;
    private LinearLayout qadictionary = null;
    private LinearLayout qafinance = null;
    private LinearLayout qamusic = null;
    private LinearLayout qamoviereview = null;
    private LinearLayout qahealthreview = null;

    private static final int ID_GOOGLE = 8;
    private static final int ID_YAHOO = 15;
    private static final int ID_BING = 4;
    private static final int ID_AMAZON = 2;
    private static final int ID_EBAY = 6;
    private static final int ID_GOOGLE_IMAGES = 9;
    private static final int ID_FLICKR = 7;
    private static final int ID_BING_IMAGES = 25;
    private static final int ID_PHOTOBUCKET = 26;
    private static final int ID_YOUTUBE = 16;
    private static final int ID_DAILYMOTION = 19;
    private static final int ID_METACAFE = 27;
    private static final int ID_HULU = 28;
    private static final int ID_ITEMVN = 10;
    private static final int ID_GROOVES = 20;
    private static final int ID_LIVE365 = 29;
    private static final int ID_GOOGLEMAPS = 17;
    private static final int ID_BINGMAPS = 21;
    private static final int ID_ANSWERS = 3;
    private static final int ID_ASK = 18;
    private static final int ID_WEATHER = 13;
    private static final int ID_DICTIONARY = 5;
    private static final int ID_FREEDICTIONARY = 22;
    private static final int ID_OXFORD = 23;
    private static final int ID_MARRIAM = 24;
    private static final int ID_WIKI = 14;
    private static final int ID_YAHOO_FINANCE = 12;
    private static final int ID_GOOGLE_FINANCE = 30;
    private static final int ID_IMDB = 31;
    private static final int ID_ROTTENTOMATOES = 32;
    private static final int ID_CNN_MONEY = 33;
    private static final int ID_SOUNDCLOUD = 34;
    private static final int ID_MYSPACE_MUSIC = 35;
    private static final int ID_AOL = 36;
    private static final int ID_LYCOS = 37;
    private static final int ID_WEBMD = 38;
    private static final int ID_DRUG = 39;

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String INDEX_FILES = "index_files";

    private ActionItem googleItem = null;
    private ActionItem bingItem = null;
    private ActionItem yahooItem = null;
    private ActionItem amazonItem = null;
    private ActionItem ebayItem = null;
    private ActionItem googleImagesItem = null;
    private ActionItem flickrItem = null;
    private ActionItem youtubeItem = null;
    private ActionItem dailymotionItem = null;
    private ActionItem itemvnItem = null;
    private ActionItem groovesItem = null;
    private ActionItem gmapsItem = null;
    private ActionItem bingmapsItem = null;
    private ActionItem answersItem = null;
    private ActionItem askItem = null;
    private ActionItem dictionaryItem = null;
    private ActionItem freeDictionaryItem = null;
    private ActionItem oxfordItem = null;
    private ActionItem merriamItem = null;
    private ActionItem weatherItem = null;
    private ActionItem wikiItem = null;
    private ActionItem yahooFinanceItem = null;
    private ActionItem bingImagesItem = null;
    private ActionItem photobucketItem = null;
    private ActionItem metacafeItem = null;
    private ActionItem huluItem = null;
    private ActionItem live365Item = null;
    private ActionItem googleFinanceItem = null;
    private ActionItem imdbItem = null;
    private ActionItem rottentomatoesItem = null;
    private ActionItem cnnmoneyItem = null;
    private ActionItem soundcloudItem = null;
    private ActionItem myspacemusicItem = null;
    private ActionItem aolItem = null;
    private ActionItem lycosItem = null;
    private ActionItem webmdItem = null;
    private ActionItem drugItem = null;

    private QuickAction searchAction = null;
    private QuickAction shoppingAction = null;
    private QuickAction imagesAction = null;
    private QuickAction videoAction = null;
    private QuickAction musicAction = null;
    private QuickAction mapsAction = null;
    private QuickAction answersAction = null;
    private QuickAction dictionaryAction = null;
    private QuickAction weatherAction = null;
    private QuickAction financeAction = null;
    private QuickAction movieAction = null;
    private QuickAction healthAction = null;

    private LinearLayout sdCardButton = null;

    private SharedPreferences sharedPreferences;
    private SharedPreferences toIndexFiles;
    private ListView listView;
    private AutoCompleteTextView searchText;
    private LayoutInflater mInflater;
    private CustomAdapter adapter;

    private Vector<String> data;
    private Vector<String> dataAction;
    private ArrayList<String> browserDesc;
    private ArrayList<String> browserUrl;
    private ArrayList<String> songArtist;
    private ArrayList<String> songName;
    private ArrayList<String> songPath;
    private ArrayList<String> videoName;
    private ArrayList<String> videoPath;
    private ArrayList<String> imageName;
    private ArrayList<String> imagePath;
    private ArrayList<String> appName;
    private ArrayList<String> appPkg;
    private ArrayList<Drawable> globalAppIcon;
    private ArrayList<String> contactName;
    private ArrayList<String> contactId;
    private ArrayList<String> playAroundValueList;

    private ArrayList<String> otherName;
    private ArrayList<String> otherPath;

    private Drawable iconIdentifier;
    private BroadcastReceiver updatingReceiver;
    private BroadcastReceiver updatedReceiver;
    private BroadcastReceiver executeReceiver;
    ;
    private BroadcastReceiver updateSearchChooser;
    private BroadcastReceiver sdcardReceiver;
    private BroadcastReceiver updateSuggestionsReceiver;
    private String globalText;
    private ImageView googleSearch;

    private LinearLayout search;
    private LinearLayout clearSearchBar;

    private int visibleIndex = 0;
    private int oldSearchChooser = 0;
    private int searchChooser = 0;
    private int j = 0;

    private AdView adView;

    private ArrayList<String> suggestions = null;
    private ArrayAdapter<String> suggestionAdapter = null;
    private SearchChooserCustomAdapter searchAdapter = null;
    private InputMethodManager imm = null;
    private ViewFlipper flipper = null;
    private Animation slideIn = null;
    private Animation slideOut = null;
    private boolean mapsInstalled = false;
    private TextView noresults = null;

    private GetSuggestions getSuggestion = null;
    private GetSuggestions1 getSuggestions1 = null;

    static public Vector<Boolean> indexVector = null;
    static public Vector<String> otherIndexes = null;
    private Vector<Integer> iconsList = null;
    private Vector<String> titles = null;
    private Vector<Integer> newTransactions = null;

    private Intent executeIntent = new Intent();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView = (ListView) findViewById(R.id.listview);
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        adView = (AdView) findViewById(R.id.adView);
        search = (LinearLayout) findViewById(R.id.search);
        searchText = (AutoCompleteTextView) findViewById(R.id.searchtext);
        googleSearch = (ImageView) findViewById(R.id.googlesearch);
        clearSearchBar = (LinearLayout) findViewById(R.id.textView2);
        sdCardButton = (LinearLayout) findViewById(R.id.qasdcard);
        mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        noresults = (TextView) findViewById(R.id.noresults);
        noresults.setVisibility(View.GONE);

        mapsInstalled = checkForGoogleMaps();

        executeIntent.setAction("EXECUTE");
        adView.loadAd(new AdRequest());

        loadSharedPreferences();
        doKeyBoardStuff();
        loadItems();
        initializeActions();
        updateQuickActions();
        loadQuickActions();
        initializeArrayLists();
        loadAnimations();
        populateSearchChooser();

        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setSelector(R.drawable.dropdownselected);
        listView.setOverScrollMode(ListView.OVER_SCROLL_ALWAYS);

        searchText.addTextChangedListener(filterResults);
        searchText.setCompoundDrawables(null, null,
                getResources().getDrawable(R.drawable.clear), null);
        searchText.setImeActionLabel("Search",
                EditorInfo.IME_ACTION_UNSPECIFIED);

        suggestionAdapter.setNotifyOnChange(true);
        searchText.setAdapter(suggestionAdapter);

        searchText
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            if (searchChooser != 1)
                                performSearch();
                            return true;
                        }
                        return false;
                    }
                });

        updatingReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                searchText.removeTextChangedListener(filterResults);
                globalText = searchText.getText().toString();
                searchText.setText("");
                searchText.setHint("Indexing data...");
                searchText.setEnabled(false);
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("UPDATE_BEGIN");
        // registerReceiver(updatingReceiver, filter);

        updatedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                searchText.addTextChangedListener(filterResults);
                searchText.setText(globalText);
                updateSearchImage(searchChooser);
                searchText.setEnabled(true);
            }
        };
        filter = new IntentFilter();
        filter.addAction("UPDATE_FINISH");
        // registerReceiver(updatedReceiver, filter);

        executeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getSuggestions1.getStatus() != AsyncTask.Status.RUNNING) {
                    getSuggestions1 = new GetSuggestions1();
                    getSuggestions1.execute(searchText.getText().toString());
                    newTransactions.clear();
                } else {
                    newTransactions.add(0);
                }
            }
        };

        filter = new IntentFilter();
        filter.addAction("EXECUTE");
        registerReceiver(executeReceiver, filter);

        updateSearchChooser = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateSearchImage(searchChooser);
            }
        };
        filter = new IntentFilter();
        filter.addAction("UPDATE_SEARCH");
        registerReceiver(updateSearchChooser, filter);

        updateSuggestionsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                suggestionAdapter = new ArrayAdapter<String>(context,
                        R.layout.list, suggestions);
                searchText.setAdapter(suggestionAdapter);
            }
        };
        filter = new IntentFilter();
        filter.addAction("UPDATE_SUGGESTIONS");
        registerReceiver(updateSuggestionsReceiver, filter);

        sdcardReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("SD card mounted!!!");
                updateHistory();
            }
        };
        filter = new IntentFilter();
        filter.addAction("android.intent.action.MEDIA_MOUNTED");
        filter.addDataScheme("file");
        registerReceiver(sdcardReceiver, filter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos,
                                    long arg3) {
                listItemClicked(pos);
            }
        });

        updateSearchImage(searchChooser);
        googleSearch.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (oldSearchChooser > 1 && searchChooser == 1) {
                    noresults.setVisibility(View.GONE);
                    flipper.showPrevious();
                    searchChooser = oldSearchChooser;
                    oldSearchChooser = 1;
                    updateSearchImage(searchChooser);
                } else {
                    oldSearchChooser = searchChooser;
                    searchChooser = 1;
                    flipper.showNext();
                    updateSearchImage(searchChooser);
                    populateFilterResults(false);
                    updateUIForLocalData();
                }
            }
        });

        search.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                performSearch();
            }
        });

        sdCardButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                noresults.setVisibility(View.VISIBLE);
                updateSearchSelection(1);

            }
        });

        updateIndexTypes();

        updateHistory();

    }

    public void listItemClicked(int pos) {
        visibleIndex = listView.getFirstVisiblePosition();

        globalText = searchText.getText().toString();

        String action = dataAction.get(pos);

        if (action.equals("#CALL#")) {
            String number = playAroundValueList.get(pos);
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        }

        if (action.equals("#SMS#")) {
            String number = playAroundValueList.get(pos);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("vnd.android-dir/mms-sms");
            intent.putExtra("address", number);
            startActivity(intent);
        }

        if (action.equals("#ALARM#")) {
            String time = playAroundValueList.get(pos);
            String[] hrsmins = time.split(":");
            System.out.println(hrsmins[0] + "   " + hrsmins[1]);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SET_ALARM");
            intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(hrsmins[0]));
            intent.putExtra(AlarmClock.EXTRA_MINUTES,
                    Integer.parseInt(hrsmins[1]));
            intent.putExtra(AlarmClock.EXTRA_SKIP_UI, false);
            startActivity(intent);
        }

        if (action.equals("C")) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI,
                            "" + playAroundValueList.get(pos)));
            startActivity(intent);
            overridePendingTransition(R.anim.scalein, R.anim.rotationout1);
        }

        if (action.equals("S")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            ComponentName comp = new ComponentName("com.android.music",
                    "com.android.music.MediaPlaybackActivity");
            intent.setComponent(comp);

            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String songPath = playAroundValueList.get(pos);
            int index = songPath.lastIndexOf(".") + 1;
            String ext = songPath.substring(index).toLowerCase();
            String type = mime.getMimeTypeFromExtension(ext);
            intent.setDataAndType(Uri.fromFile(new File(songPath.toString())),
                    type);
            startActivity(intent);
            overridePendingTransition(R.anim.scalein, R.anim.rotationout1);
        }

        if (action.equals("V")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(playAroundValueList.get(pos)),
                    "video/*");
            startActivity(intent);
            overridePendingTransition(R.anim.scalein, R.anim.rotationout1);
        }

        if (action.equals("D") || action.equals("X") || action.equals("T")
                || action.equals("F") || action.equals("E")
                || action.equals("OT")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext = data.get(pos).substring(
                    data.get(pos).lastIndexOf(".") + 1);
            String type = mime.getMimeTypeFromExtension(ext);
            String filePath = playAroundValueList.get(pos);
            intent.setDataAndType(Uri.fromFile(new File(filePath)), type);
            try {
                startActivity(intent);
                overridePendingTransition(R.anim.scalein, R.anim.rotationout1);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(SpotlightActivity.this,
                        "No application found to open this file.", 2000).show();
            }
        }

        if (action.equals("I")) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String imagePath = playAroundValueList.get(pos);
            int index = imagePath.lastIndexOf(".") + 1;
            String ext = imagePath.substring(index).toLowerCase();
            String type = mime.getMimeTypeFromExtension(ext);
            intent.setDataAndType(Uri.fromFile(new File(imagePath.toString())),
                    type);
            startActivity(intent);
            overridePendingTransition(R.anim.scalein, R.anim.rotationout1);
        }

        if (action.equals("A")) {
            if (!data.get(pos).equals("Ultimate Search")) {
                Intent mIntent = getPackageManager().getLaunchIntentForPackage(
                        playAroundValueList.get(pos));
                if (mIntent != null) {
                    try {
                        startActivity(mIntent);
                        overridePendingTransition(R.anim.scalein,
                                R.anim.rotationout1);
                    } catch (ActivityNotFoundException err) {
                        Toast.makeText(getApplicationContext(),
                                "No app found to open the file",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(SpotlightActivity.this,
                        "App is already launched.", 1000).show();
            }
        }

        if (action.equals("B")) {
            String webQuery = playAroundValueList.get(pos);
            if (webQuery.toLowerCase().contains("http".toLowerCase())
                    || webQuery.toLowerCase().contains("www".toLowerCase())) {
            } else {
                webQuery = "https://www.google.com/search?q=" + webQuery;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(webQuery));
            startActivity(browserIntent);
            overridePendingTransition(R.anim.scalein, R.anim.rotationout1);
        }

    }

    private void loadSharedPreferences() {
        sharedPreferences = getSharedPreferences(PREFS_NAME,
                MODE_WORLD_READABLE);
        toIndexFiles = getSharedPreferences(INDEX_FILES, MODE_WORLD_READABLE);
    }

    private void doKeyBoardStuff() {
        imm = (InputMethodManager) this
                .getSystemService(Service.INPUT_METHOD_SERVICE);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void loadAnimations() {
        slideIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotationin);
        slideOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotationin1);
    }

    private boolean checkForGoogleMaps() {
        Intent mIntent = getPackageManager().getLaunchIntentForPackage(
                "com.google.android.apps.maps");
        if (mIntent != null)
            return true;
        return false;
    }

    private void initializeArrayLists() {

        data = new Vector<String>();
        adapter = new CustomAdapter(getApplicationContext(), 222, data);
        suggestionAdapter = new ArrayAdapter<String>(this, R.layout.list,
                suggestions);
        newTransactions = new Vector<Integer>();
        dataAction = new Vector<String>();
        otherIndexes = new Vector<String>();
        indexVector = new Vector<Boolean>();
        browserDesc = new ArrayList<String>();
        browserUrl = new ArrayList<String>();
        songArtist = new ArrayList<String>();
        songName = new ArrayList<String>();
        songPath = new ArrayList<String>();
        videoName = new ArrayList<String>();
        videoPath = new ArrayList<String>();
        imageName = new ArrayList<String>();
        imagePath = new ArrayList<String>();
        appName = new ArrayList<String>();
        appPkg = new ArrayList<String>();
        otherName = new ArrayList<String>();
        otherPath = new ArrayList<String>();
        globalAppIcon = new ArrayList<Drawable>();
        contactName = new ArrayList<String>();
        contactId = new ArrayList<String>();
        playAroundValueList = new ArrayList<String>();
        suggestions = new ArrayList<String>();
        getSuggestion = new GetSuggestions();
        getSuggestions1 = new GetSuggestions1();
    }

    private void initializeActions() {

        searchAction = new QuickAction(this);
        shoppingAction = new QuickAction(this);
        imagesAction = new QuickAction(this);
        videoAction = new QuickAction(this);
        musicAction = new QuickAction(this);
        mapsAction = new QuickAction(this);
        answersAction = new QuickAction(this);
        dictionaryAction = new QuickAction(this);
        weatherAction = new QuickAction(this);
        financeAction = new QuickAction(this);
        movieAction = new QuickAction(this);
        healthAction = new QuickAction(this);

        searchAction.addActionItem(googleItem);
        searchAction.addActionItem(bingItem);
        searchAction.addActionItem(yahooItem);
        searchAction.addActionItem(aolItem);
        searchAction.addActionItem(lycosItem);
        searchAction.addActionItem(wikiItem);
        shoppingAction.addActionItem(amazonItem);
        shoppingAction.addActionItem(ebayItem);
        imagesAction.addActionItem(googleImagesItem);
        imagesAction.addActionItem(flickrItem);
        imagesAction.addActionItem(bingImagesItem);
        imagesAction.addActionItem(photobucketItem);
        videoAction.addActionItem(youtubeItem);
        videoAction.addActionItem(dailymotionItem);
        videoAction.addActionItem(metacafeItem);
        musicAction.addActionItem(itemvnItem);
        musicAction.addActionItem(groovesItem);
        musicAction.addActionItem(soundcloudItem);
        // musicAction.addActionItem(myspacemusicItem);
        financeAction.addActionItem(yahooFinanceItem);
        financeAction.addActionItem(googleFinanceItem);
        financeAction.addActionItem(cnnmoneyItem);
        mapsAction.addActionItem(gmapsItem);
        mapsAction.addActionItem(bingmapsItem);
        answersAction.addActionItem(answersItem);
        answersAction.addActionItem(askItem);
        dictionaryAction.addActionItem(dictionaryItem);
        dictionaryAction.addActionItem(freeDictionaryItem);
        dictionaryAction.addActionItem(oxfordItem);
        dictionaryAction.addActionItem(merriamItem);
        weatherAction.addActionItem(weatherItem);
        movieAction.addActionItem(imdbItem);
        movieAction.addActionItem(rottentomatoesItem);
        healthAction.addActionItem(webmdItem);
        healthAction.addActionItem(drugItem);
    }

    private void loadItems() {
        googleItem = new ActionItem(ID_GOOGLE, "Google", getResources()
                .getDrawable(R.drawable.google));
        bingItem = new ActionItem(ID_BING, "Bing", getResources().getDrawable(
                R.drawable.bing));
        yahooItem = new ActionItem(ID_YAHOO, "Yahoo!", getResources()
                .getDrawable(R.drawable.yahoo));
        amazonItem = new ActionItem(ID_AMAZON, "Amazon", getResources()
                .getDrawable(R.drawable.amazon));
        ebayItem = new ActionItem(ID_EBAY, "ebay", getResources().getDrawable(
                R.drawable.ebay));
        googleImagesItem = new ActionItem(ID_GOOGLE_IMAGES, "Google images",
                getResources().getDrawable(R.drawable.google));
        flickrItem = new ActionItem(ID_FLICKR, "Flickr", getResources()
                .getDrawable(R.drawable.flickr));
        youtubeItem = new ActionItem(ID_YOUTUBE, "YouTube", getResources()
                .getDrawable(R.drawable.youtube));
        dailymotionItem = new ActionItem(ID_DAILYMOTION, "Dailymotion",
                getResources().getDrawable(R.drawable.dailymotion));
        itemvnItem = new ActionItem(ID_ITEMVN, "itemvn", getResources()
                .getDrawable(R.drawable.itemvn));
        groovesItem = new ActionItem(ID_GROOVES, "Grooveshark", getResources()
                .getDrawable(R.drawable.grooveshark));
        gmapsItem = new ActionItem(ID_GOOGLEMAPS, "Google maps", getResources()
                .getDrawable(R.drawable.maps));
        bingmapsItem = new ActionItem(ID_BINGMAPS, "Bing maps", getResources()
                .getDrawable(R.drawable.bingmaps));
        answersItem = new ActionItem(ID_ANSWERS, "Answers", getResources()
                .getDrawable(R.drawable.answers));
        askItem = new ActionItem(ID_ASK, "Ask", getResources().getDrawable(
                R.drawable.ask));
        dictionaryItem = new ActionItem(ID_DICTIONARY, "Dictionary",
                getResources().getDrawable(R.drawable.dictionary));
        freeDictionaryItem = new ActionItem(ID_FREEDICTIONARY,
                "Free Dictionary", getResources().getDrawable(
                R.drawable.freeonline));
        oxfordItem = new ActionItem(ID_OXFORD, "Oxford", getResources()
                .getDrawable(R.drawable.oxford));
        merriamItem = new ActionItem(ID_MARRIAM, "Merriam Webster",
                getResources().getDrawable(R.drawable.merriam));
        weatherItem = new ActionItem(ID_WEATHER, "Weather", getResources()
                .getDrawable(R.drawable.weatherunderground));
        wikiItem = new ActionItem(ID_WIKI, "Wiki", getResources().getDrawable(
                R.drawable.wiki));
        yahooFinanceItem = new ActionItem(ID_YAHOO_FINANCE, "Yahoo Quotes",
                getResources().getDrawable(R.drawable.finance));
        bingImagesItem = new ActionItem(ID_BING_IMAGES, "Bing images",
                getResources().getDrawable(R.drawable.bing));
        photobucketItem = new ActionItem(ID_PHOTOBUCKET, "Photobucket",
                getResources().getDrawable(R.drawable.photobucket));
        metacafeItem = new ActionItem(ID_METACAFE, "Metacafe", getResources()
                .getDrawable(R.drawable.metacafe));
        googleFinanceItem = new ActionItem(ID_GOOGLE_FINANCE, "Google Quotes",
                getResources().getDrawable(R.drawable.googlefinance));
        imdbItem = new ActionItem(ID_IMDB, "IMDb", getResources().getDrawable(
                R.drawable.imdb));
        rottentomatoesItem = new ActionItem(ID_ROTTENTOMATOES,
                "Rottentomatoes", getResources().getDrawable(R.drawable.rotten));
        cnnmoneyItem = new ActionItem(ID_CNN_MONEY, "CNN Money", getResources()
                .getDrawable(R.drawable.cnnmoney));
        soundcloudItem = new ActionItem(ID_SOUNDCLOUD, "Soundcloud",
                getResources().getDrawable(R.drawable.soundcloud));
        myspacemusicItem = new ActionItem(ID_MYSPACE_MUSIC, "myspace",
                getResources().getDrawable(R.drawable.myspacemusic));
        aolItem = new ActionItem(ID_AOL, "AOL", getResources().getDrawable(
                R.drawable.aol));
        lycosItem = new ActionItem(ID_LYCOS, "Lycos", getResources()
                .getDrawable(R.drawable.lycos));
        webmdItem = new ActionItem(ID_WEBMD, "Webmd", getResources()
                .getDrawable(R.drawable.webmd));
        drugItem = new ActionItem(ID_DRUG, "Drugs", getResources().getDrawable(
                R.drawable.drugs));

    }

    private void updateSearchSelection(int actionId) {
        oldSearchChooser = searchChooser;
        searchChooser = actionId;
        if (searchChooser != oldSearchChooser) {
            if (searchChooser == 1 && oldSearchChooser > 1) {
                flipper.setInAnimation(slideIn);
                flipper.setOutAnimation(slideOut);
                flipper.showNext();
            }
        }
        updateSearchImage(searchChooser);
        if (searchChooser == 1) {
            populateFilterResults(false);
            updateUIForLocalData();
        }
    }

    private void loadQuickActions() {

        searchAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });

        shoppingAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });

        imagesAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });
        videoAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });
        musicAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });
        mapsAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });
        answersAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });
        dictionaryAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });
        weatherAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });
        financeAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });
        movieAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });
        healthAction
                .setOnActionItemClickListener(new OnActionItemClickListener() {

                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        updateSearchSelection(actionId);
                    }
                });
    }

    private void updateQuickActions() {

        qasdcard = (LinearLayout) findViewById(R.id.qasdcard);
        qasearch = (LinearLayout) findViewById(R.id.qasearch);
        qashopping = (LinearLayout) findViewById(R.id.qashopping);
        qaimages = (LinearLayout) findViewById(R.id.qaimages);
        qavideo = (LinearLayout) findViewById(R.id.qavideo);
        qamaps = (LinearLayout) findViewById(R.id.qamaps);
        qaanswers = (LinearLayout) findViewById(R.id.qaanswers);
        qaweather = (LinearLayout) findViewById(R.id.qaweather);
        qadictionary = (LinearLayout) findViewById(R.id.qadict);
        qamusic = (LinearLayout) findViewById(R.id.qamusic);
        qafinance = (LinearLayout) findViewById(R.id.qayahoofinance);
        qamoviereview = (LinearLayout) findViewById(R.id.qamoviereview);
        qahealthreview = (LinearLayout) findViewById(R.id.qahealth);

        qasearch.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                searchAction.show(v);
            }
        });

        qashopping.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                shoppingAction.show(v);
            }
        });

        qaimages.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                imagesAction.show(v);
            }
        });

        qavideo.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                videoAction.show(v);
            }
        });

        qamusic.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                musicAction.show(v);
            }
        });

        qafinance.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                financeAction.show(v);
            }
        });

        qamaps.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                mapsAction.show(v);
            }
        });

        qaanswers.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                answersAction.show(v);
            }
        });

        qaweather.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                weatherAction.show(v);
            }
        });

        qadictionary.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                dictionaryAction.show(v);
            }
        });
        qamoviereview.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                movieAction.show(v);
            }
        });
        qahealthreview.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                healthAction.show(v);
            }
        });

    }

    private void updateIndexTypes() {
        if (toIndexFiles == null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("indextypes", "1,1,1,1,1,0,0,0,0,0,0");
            editor.commit();
        } else {
            String[] indexes = toIndexFiles.getString("indextypes",
                    "1,1,1,1,1,0,0,0,0,0,0").split(",");
            for (String s : indexes) {
                if (s.equals("1"))
                    indexVector.add(true);
                else
                    indexVector.add(false);
            }
            String[] otherExtensions = toIndexFiles.getString("otherindexes",
                    "").split(",");
            for (String s : otherExtensions) {
                otherIndexes.add(s);
            }
        }
    }

    private void populateSearchChooser() {
        mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // searchList = (GridView) findViewById(R.id.searchlist);
        iconsList = new Vector<Integer>();
        titles = new Vector<String>();

        iconsList.add(R.drawable.local);
        iconsList.add(R.drawable.amazon);
        iconsList.add(R.drawable.answers);
        iconsList.add(R.drawable.bing);
        iconsList.add(R.drawable.ebay);
        iconsList.add(R.drawable.flickr);
        iconsList.add(R.drawable.google);
        iconsList.add(R.drawable.song);
        iconsList.add(R.drawable.finance);
        iconsList.add(R.drawable.weather);
        iconsList.add(R.drawable.wiki);
        iconsList.add(R.drawable.yahoo);
        iconsList.add(R.drawable.youtube);
        iconsList.add(R.drawable.maps);
        iconsList.add(R.drawable.ask);

        titles.add("SD card");
        titles.add("amazon");
        titles.add("Answers");
        titles.add("bing");
        titles.add("Dictionary");
        titles.add("ebay");
        titles.add("flickr");
        titles.add("Google");
        titles.add("Google images");
        titles.add("Music Online");
        titles.add("Yahoo! finance");
        titles.add("Weather");
        titles.add("Wikipedia");
        titles.add("Yahoo!");
        titles.add("YouTube");
        titles.add("Google Maps");
        titles.add("Ask");

        searchAdapter = new SearchChooserCustomAdapter(getApplicationContext(),
                1, iconsList);
        /*
         * searchList.setAdapter(searchAdapter);
		 * searchList.setTextFilterEnabled(true);
		 * searchList.setSelector(R.drawable.freqselector);
		 * 
		 * searchList.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * arg2, long arg3) { oldSearchChooser = searchChooser; searchChooser =
		 * arg2 + 1; if (searchChooser != oldSearchChooser) { if (searchChooser
		 * == 1 && oldSearchChooser > 1) {
		 * 
		 * flipper.setInAnimation(slideIn); flipper.setOutAnimation(slideOut);
		 * 
		 * flipper.showNext(); } } updateSearchImage(searchChooser); if
		 * (searchChooser == 1) { populateFilterResults(false);
		 * updateUIForLocalData(); } } });
		 */
    }

    public void performSearch() {
        searchText.clearFocus();
        String searchTextQuery = null;
        if (searchChooser == 12 || searchChooser == 30 || searchChooser == 33) {
            searchTextQuery = searchText.getText().toString().split("  ")[0];
        } else {
            searchTextQuery = searchText.getText().toString();
        }
        globalText = searchTextQuery;

        if (searchTextQuery.length() > 0) {
            if (!searchTextQuery.toLowerCase().contains(".com".toLowerCase())) {
                int search = searchChooser;
                if (search == 0)
                    search = 8;

                if (search == 2) {
                    searchTextQuery = "http://www.amazon.com/gp/aw/s/ref=is_box_?k="
                            + searchTextQuery;
                } else if (search == 3) {
                    searchTextQuery.replace(" ", "_");
                    searchTextQuery = "http://www.answers.com/topic/"
                            + searchTextQuery;
                } else if (search == 4) {
                    searchTextQuery.replace(" ", "+");
                    searchTextQuery = "http://m.bing.com/search?q="
                            + searchTextQuery;
                } else if (search == 5) {
                    searchTextQuery.replace(" ", "+");
                    searchTextQuery = "http://m.dictionary.com/d/?q="
                            + searchTextQuery;
                } else if (search == 6) {
                    searchTextQuery = "http://www.ebay.com/sch/i.html?_nkw="
                            + searchTextQuery;
                } else if (search == 7) {
                    searchTextQuery.replace(" ", "+");
                    searchTextQuery = "http://www.flickr.com/search/?q="
                            + searchTextQuery + "&f=hp";
                } else if (search == 8) {
                    searchTextQuery = "https://www.google.com/search?q="
                            + searchTextQuery;
                } else if (search == 9) {
                    searchTextQuery = "http://images.google.com/search?q="
                            + searchTextQuery + "&tbm=isch&pbx=1&site=images";
                } else if (search == 10) {
                    searchTextQuery.replace(" ", "+");
                    searchTextQuery = "http://m.itemvn.com/listsong/?q="
                            + searchTextQuery + "&submit=search";
                } else if (search == 11) {
                    searchTextQuery = "https://play.google.com/store/search?q="
                            + searchTextQuery + "&c=apps";
                } else if (search == 12) {
                    searchTextQuery.replace(" ", "+");
                    searchTextQuery = "http://m.yahoo.com/w/legobpengine/finance/details/?.b=quotessearch%2F%3F.b%3Dindex&.sy="
                            + searchTextQuery + "&.lang=en-in";
                } else if (search == 13) {
                    searchTextQuery.replace(" ", "+");
                    searchTextQuery = "http://i.wund.com/cgi-bin/findweather/getForecast?brand=iphone&query="
                            + searchTextQuery;
                } else if (search == 14) {
                    searchTextQuery.replace(" ", "");
                    searchTextQuery = "http://en.wikipedia.org/wiki/"
                            + searchTextQuery;
                } else if (search == 15) {
                    searchTextQuery.replace(" ", "+");
                    searchTextQuery = "http://us.search.yahoo.com/mobile/s?submit=oneSearch&.tsrc=yahoo&.intl=in&.lang=en-in&.sep=fp&cat=&subcat=&p="
                            + searchTextQuery;
                } else if (search == 16) {
                    searchTextQuery.replace(" ", "+");
                    searchTextQuery = "http://m.youtube.com/results?q="
                            + searchTextQuery;
                } else if (search == 17) {
                    searchTextQuery = "https://maps.google.com/maps?hl=en&q="
                            + searchTextQuery;
                } else if (search == 18) {
                    searchTextQuery.replace(" ", "+");
                    searchTextQuery = "http://www.ask.com/web?q="
                            + searchTextQuery;
                } else if (search == 19) {
                    searchTextQuery.replace(" ", "+");
                    searchTextQuery = "http://www.dailymotion.com/us/relevance/search/"
                            + searchTextQuery + "/1";
                } else if (search == 20) {
                    searchTextQuery = "http://html5.grooveshark.com/#!/search/"
                            + searchTextQuery;
                } else if (search == 21) {
                    searchTextQuery = "http://www.bing.com/maps/?q="
                            + searchTextQuery;
                } else if (search == 22) {
                    searchTextQuery = "http://www.thefreedictionary.com/"
                            + searchTextQuery;
                } else if (search == 23) {
                    searchTextQuery = "http://oxforddictionaries.com/definition/english/"
                            + searchTextQuery;
                } else if (search == 24) {
                    searchTextQuery = "http://www.merriam-webster.com/dictionary/"
                            + searchTextQuery;
                } else if (search == 25) {
                    searchTextQuery = "http://www.bing.com/images/search?q="
                            + searchTextQuery;
                } else if (search == 26) {
                    searchTextQuery = "http://www.photobucket.com/images/"
                            + searchTextQuery;
                } else if (search == 27) {
                    searchTextQuery = "http://www.metacafe.com/topics/"
                            + searchTextQuery;
                } else if (search == 30) {
                    searchTextQuery = "https://www.google.com/m/finance#search/NASDAQ%3A"
                            + searchTextQuery;
                } else if (search == 31) {
                    searchTextQuery = "http://www.imdb.com/find?q="
                            + searchTextQuery;
                } else if (search == 32) {
                    searchTextQuery = "http://www.rottentomatoes.com/search/?search="
                            + searchTextQuery;
                } else if (search == 33) {
                    searchTextQuery = "http://cnnmoney.mobi/primary/stock_detail?symbol="
                            + searchTextQuery;
                } else if (search == 34) {
                    searchTextQuery = searchTextQuery.replace(" ", "+");
                    searchTextQuery = "http://m.soundcloud.com/tracks/search?q="
                            + searchTextQuery;
                } else if (search == 35) {
                    searchTextQuery = "http://www.myspace.com/search/music?q="
                            + searchTextQuery + "&ac=t";
                } else if (search == 36) {
                    searchTextQuery = "http://m.aol.com/search/aol/search?q="
                            + searchTextQuery
                            + "&icid=hp_mobile_srch&invocationType=srch_entr";
                } else if (search == 37) {
                    searchTextQuery = "http://www.search.lycos.com/web?q="
                            + searchTextQuery;
                } else if (search == 38) {
                    searchTextQuery = "http://www.m.webmd.com/mobile-search/default.htm?query="
                            + searchTextQuery;
                } else if (search == 39) {
                    searchTextQuery = "http://www.drugs.com/search.php?searchterm="
                            + searchTextQuery;
                }
            } else {
                if (!searchTextQuery.toLowerCase().contains(
                        "http://".toLowerCase())) {
                    searchTextQuery = "http://" + searchTextQuery;
                }
            }

            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(searchTextQuery));
            if (searchChooser == 17 && mapsInstalled) {
                browserIntent.setClassName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity");
                startActivity(browserIntent);
            } else /*
                     * if (searchChooser == 16 || searchChooser == 19 ||
					 * searchChooser == 27)
					 */ {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri
                        .parse(searchTextQuery)));
            } /*
             * else { Intent intent = new Intent(SpotlightActivity.this,
			 * Web.class); intent.putExtra("address", searchTextQuery);
			 * startActivity(intent); }
			 */
            overridePendingTransition(R.anim.scalein, R.anim.rotationout1);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (searchChooser == 1) {
                populateFilterResults(false);
                updateUIForLocalData();
            }
        } else if (requestCode == 987) {
            boolean update = data.getExtras().getBoolean("update");
            if (update)
                updateHistory();

            SharedPreferences.Editor editor = toIndexFiles.edit();
            String bool = "";
            for (boolean b : indexVector) {
                if (b)
                    bool += "1,";
                else
                    bool += "0,";
            }
            bool = bool.substring(0, bool.length() - 1);
            editor.putString("indextypes", bool);
            String str = "";
            for (String s : otherIndexes) {
                str += s + ",";
            }
            str = str.substring(0, str.length() - 1);
            editor.putString("otherindexes", str);
            editor.apply();
        }
    }

    private void updateSearchImage(int searchOption) {
        if (searchOption == 0) {
            searchChooser = 8;
            searchOption = 8;
        }

        if (searchOption == 1) {
            search.setEnabled(false);
            googleSearch.setBackgroundResource(R.drawable.local);
            searchText.setHint("Search SD card");
            if (appName.size() == 0 && indexVector.get(0)) {
                populateHistory();
            }
        } else {
            String tempSearchText = searchText.getText().toString();
            searchText.setText("");
            populateFilterResults(false);
            updateUIForLocalData();
            searchText.setText(tempSearchText);
            search.setEnabled(true);
        }
        if (searchOption == 2) {
            googleSearch.setBackgroundResource(R.drawable.amazon);
            searchText.setHint("Product name");
        } else if (searchOption == 3) {
            googleSearch.setBackgroundResource(R.drawable.answers);
            searchText.setHint("Ask your question");
        } else if (searchOption == 4) {
            googleSearch.setBackgroundResource(R.drawable.bing);
            searchText.setHint("Bing");
        } else if (searchOption == 5) {
            googleSearch.setBackgroundResource(R.drawable.dictionary);
            searchText.setHint("Search a word");
        } else if (searchOption == 6) {
            googleSearch.setBackgroundResource(R.drawable.ebay);
            searchText.setHint("Product name");
        } else if (searchOption == 7) {
            googleSearch.setBackgroundResource(R.drawable.flickr);
            searchText.setHint("Search an image");
        } else if (searchOption == 8) {
            googleSearch.setBackgroundResource(R.drawable.google);
            searchText.setHint("Google");
        } else if (searchOption == 9) {
            googleSearch.setBackgroundResource(R.drawable.google);
            searchText.setHint("Search an image");
        } else if (searchOption == 10) {
            googleSearch.setBackgroundResource(R.drawable.itemvn);
            searchText.setHint("Song name");
        } else if (searchOption == 12) {
            googleSearch.setBackgroundResource(R.drawable.finance);
            searchText.setHint("Symbol/Stock name");
        } else if (searchOption == 13) {
            googleSearch.setBackgroundResource(R.drawable.weatherunderground);
            searchText.setHint("City/country name");
        } else if (searchOption == 14) {
            googleSearch.setBackgroundResource(R.drawable.wiki);
            searchText.setHint("Wiki");
        } else if (searchOption == 15) {
            googleSearch.setBackgroundResource(R.drawable.yahoo);
            searchText.setHint("Yahoo!");
        } else if (searchOption == 16) {
            googleSearch.setBackgroundResource(R.drawable.youtube);
            searchText.setHint("YouTube");
        } else if (searchOption == 17) {
            googleSearch.setBackgroundResource(R.drawable.maps);
            searchText.setHint("Address, City...");
        } else if (searchOption == 18) {
            googleSearch.setBackgroundResource(R.drawable.ask);
            searchText.setHint("Ask your question");
        } else if (searchOption == 19) {
            googleSearch.setBackgroundResource(R.drawable.dailymotion);
            searchText.setHint("Search videos");
        } else if (searchOption == 20) {
            googleSearch.setBackgroundResource(R.drawable.grooveshark);
            searchText.setHint("Search songs, artists, genres");
        } else if (searchOption == 21) {
            googleSearch.setBackgroundResource(R.drawable.bingmaps);
            searchText.setHint("Search maps");
        } else if (searchOption == 22) {
            googleSearch.setBackgroundResource(R.drawable.freeonline);
            searchText.setHint("Search a word");
        } else if (searchOption == 23) {
            googleSearch.setBackgroundResource(R.drawable.oxford);
            searchText.setHint("Search a word");
        } else if (searchOption == 24) {
            googleSearch.setBackgroundResource(R.drawable.merriam);
            searchText.setHint("Search a word");
        } else if (searchOption == 25) {
            googleSearch.setBackgroundResource(R.drawable.bing);
            searchText.setHint("Search images");
        } else if (searchOption == 26) {
            googleSearch.setBackgroundResource(R.drawable.photobucket);
            searchText.setHint("Search images");
        } else if (searchOption == 27) {
            googleSearch.setBackgroundResource(R.drawable.metacafe);
            searchText.setHint("Search videos");
        } else if (searchOption == 30) {
            googleSearch.setBackgroundResource(R.drawable.googlefinance);
            searchText.setHint("Search quotes");
        } else if (searchOption == 31) {
            googleSearch.setBackgroundResource(R.drawable.imdb);
            searchText.setHint("Search movies/actors");
        } else if (searchOption == 32) {
            googleSearch.setBackgroundResource(R.drawable.rotten);
            searchText.setHint("Search movies");
        } else if (searchOption == 33) {
            googleSearch.setBackgroundResource(R.drawable.cnnmoney);
            searchText.setHint("Search quotes");
        } else if (searchOption == 34) {
            googleSearch.setBackgroundResource(R.drawable.soundcloud);
            searchText.setHint("Search song/artist");
        } else if (searchOption == 35) {
            googleSearch.setBackgroundResource(R.drawable.myspacemusic);
            searchText.setHint("Search song/artist");
        } else if (searchOption == 36) {
            googleSearch.setBackgroundResource(R.drawable.aol);
            searchText.setHint("AOL search");
        } else if (searchOption == 37) {
            googleSearch.setBackgroundResource(R.drawable.lycos);
            searchText.setHint("Lycos");
        } else if (searchOption == 38) {
            googleSearch.setBackgroundResource(R.drawable.webmd);
            searchText.setHint("Webmd");
        } else if (searchOption == 39) {
            googleSearch.setBackgroundResource(R.drawable.drugs);
            searchText.setHint("Search drugs");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("SearchOption", searchOption);
        editor.commit();

        Intent intent = new Intent();
        intent.setAction("com.rahul.UPDATE_SEARCH");
        sendBroadcast(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (globalText != null
                && globalText.equals(searchText.getText().toString())) {
            globalText = searchText.getText().toString();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SearchText", globalText);
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isMyServiceRunning())
            startService(new Intent(this, FileMonitorService.class));

        adView.loadAd(new AdRequest());
        searchChooser = sharedPreferences.getInt("SearchOption", 0);
        globalText = sharedPreferences.getString("SearchText", "");
		/*
		 * String s = searchText.getText().toString();
		 * searchText.setText(globalText); searchText.clearFocus(); if
		 * (searchChooser == 1) { populateFilterResults(false); }
		 */

        // updateUIForLocalData(); listView.setSelection(visibleIndex);

    }

    private void doSearch(String s) {
        synchronized (this) {
            suggestions.clear();
            s = s.replace(" ", "+");
            String URL = null;
            String xml = null;
            JSONArray json = null;

            URL = prepareURL(searchChooser, s);

            if (searchChooser == 4 || searchChooser == 2 || searchChooser == 6
                    || searchChooser == 16 || searchChooser == 14
                    || searchChooser == 5 || searchChooser == 13
                    || searchChooser == 17 || searchChooser == 19
                    || searchChooser == 22 || searchChooser == 23
                    || searchChooser == 24 || searchChooser == 21
                    || searchChooser == 12 || searchChooser == 30
                    || searchChooser == 33) {
                try {
                    json = getJsonFromUrl(URL);
                    if (json != null) {
                        try {
                            if (searchChooser == 12 || searchChooser == 30
                                    || searchChooser == 33) {
                                if (json.length() > 0) {
                                    for (int i = 0; i < json.length(); i++) {
                                        JSONObject o = json.getJSONObject(i);
                                        String add = o.getString("symbol")
                                                + "  " + o.getString("name");
                                        suggestions.add(add);
                                    }
                                }
                            } else {
                                JSONArray jArray = json.getJSONArray(1);
                                for (int i = 0; i < jArray.length(); i++) {
                                    if (searchChooser == 16
                                            || searchChooser == 19) {
                                        String tempStr = jArray.getString(i);
                                        String[] results = tempStr.split("\"");
                                        suggestions.add(results[1]);
                                    } else {
                                        suggestions.add(jArray.getString(i));
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                }
            } else {
                xml = getXmlFromUrl(URL);
                if (xml != null) {
                    if (searchChooser == 150) {
                        Document doc = getDomElement(xml);
                        NodeList nl = doc.getElementsByTagName("Result");
                        for (int i = 0; i < nl.getLength(); i++) {
                            Element suggestion = (Element) nl.item(i);
                            String name = getElementValue(suggestion);
                            System.out.println(name);
                            suggestions.add(name);
                        }
                    } else {
                        Document doc = getDomElement(xml);
                        NodeList nl = doc.getElementsByTagName("suggestion");

                        for (int i = 0; i < nl.getLength(); i++) {
                            Element suggestion = (Element) nl.item(i);
                            String name = suggestion.getAttribute("data");
                            suggestions.add(name);
                        }
                    }
                }
            }
        }
    }

    private String prepareURL(int searchOption, String query) {
        String URL = null;
        if (searchOption == 4) {
            URL = "http://api.bing.net/osjson.aspx?Query=" + query
                    + "&Market=en-us";
        } else if (searchOption == 2) {
            URL = "http://completion.amazon.com/search/complete?method=completion&q="
                    + query + "&search-alias=aps&mkt=1";
        } else if (searchOption == 6) {
            URL = "http://anywhere.ebay.com/services/suggest/?q=" + query
                    + "&s=0";
        } else if (searchOption == 14) {
            URL = "http://en.wikipedia.org/w/api.php?action=opensearch&search="
                    + query + "&limit=10&namespace=0&format=json";
        } /*
		 * else if (searchOption == 15) { URL =
		 * "http://search.yahooapis.com/WebSearchService/V1/relatedSuggestion?appid=YahooDemo&query="
		 * + query; }
		 */ else if (searchOption == 16 || searchOption == 19) {
            URL = "http://suggestqueries.google.com/complete/search?hl=en&ds=yt&client=youtube&hjson=t&cp=1&q="
                    + query + "&alt=json";
        } else if (searchOption == 5 || searchOption == 22
                || searchOption == 23 || searchOption == 24) {
            URL = "http://www.webster.com/autocomplete?query=" + query;
        } else if (searchOption == 13) {
            URL = "http://autocomplete.wunderground.com/aq?query=" + query;
        } else if (searchOption == 17 || searchOption == 21) {
            URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
                    + query
                    + "&sensor=true&key=AIzaSyCQJB9tEA2X-PElEE05wj0-Opg-0G0tQhA";
        } else if (searchOption == 12 || searchOption == 30
                || searchOption == 33) {
            URL = "http://d.yimg.com/autoc.finance.yahoo.com/autoc?query="
                    + query
                    + "&callback=YAHOO.Finance.SymbolSuggest.ssCallback";
        } else {
            URL = "http://google.com/complete/search?output=toolbar&q=" + query;
        }
        return URL;
    }

    public String getXmlFromUrl(String url) {
        String xml = null;

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                xml = out.toString();
            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

    public JSONArray getJsonFromUrl(String url) {

        InputStream is = null;
        JSONArray jArray = null;
        String json = "";

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (is != null) {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (searchChooser != 13 && searchChooser != 17
                            && searchChooser != 21) {
                        sb.append(line + "n");
                    } else {
                        sb.append(line);
                    }
                }
                is.close();
                json = sb.toString();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            try {

                if (searchChooser == 13) {
                    String s = "";
                    JSONObject jsonObj = new JSONObject(json);
                    jArray = jsonObj.getJSONArray("RESULTS");
                    for (int i = 0; i < jArray.length() && i < 10; i++) {
                        JSONObject jobj = jArray.getJSONObject(i);
                        s += "\"" + jobj.getString("name") + "\",";
                        System.out.println(s);
                    }
                    if (s.length() > 0) {
                        s = s.substring(0, s.length() - 1);
                        s = "[\"" + searchText.getText().toString() + "\",["
                                + s + "]]";
                        jArray = new JSONArray(s);

                    }

                } else if (searchChooser == 5 || searchChooser == 22
                        || searchChooser == 23 || searchChooser == 24) {
                    JSONObject jsonObj = new JSONObject(json);
                    jArray = jsonObj.getJSONArray("suggestions");
                    String s = "[\"" + searchText.getText().toString() + "\","
                            + jArray.toString() + "]";
                    jArray = new JSONArray(s);
                } else if (searchChooser == 17 || searchChooser == 21) {
                    JSONObject jsonObj = new JSONObject(json);
                    jArray = jsonObj.getJSONArray("predictions");
                    String str = "[\"query\",[";
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObj = jArray.getJSONObject(i);
                        str += "\"" + jObj.getString("description") + "\",";
                    }
                    if (str.length() > 0) {
                        str = str.substring(0, str.length() - 1);
                    }
                    str += "]]";
                    jArray = new JSONArray(str);
                } else if (searchChooser == 12 || searchChooser == 30
                        || searchChooser == 33) {
                    json = json.substring(json.indexOf("{\"ResultSet\""),
                            json.length() - 2);
                    JSONObject obj = new JSONObject(json);
                    obj = obj.getJSONObject("ResultSet");
                    jArray = obj.getJSONArray("Result");
                } else {
                    jArray = new JSONArray(json);
                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        }
        return jArray;
    }

    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        return doc;
    }

    public String getValue(Element item, String str) {
        String s = item.getAttribute(str);
        return s;
    }

    public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child
                        .getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    private void populateHistory() {

        contactName.clear();
        contactId.clear();
        appName.clear();
        appPkg.clear();
        songName.clear();
        songPath.clear();
        videoName.clear();
        videoPath.clear();
        imageName.clear();
        imagePath.clear();
        browserDesc.clear();
        browserUrl.clear();
        otherName.clear();
        otherPath.clear();

        Intent intent = new Intent();
        intent.setAction("UPDATE_BEGIN");
        sendBroadcast(intent);

        new Thread(new Runnable() {

            public void run() {
                if (indexVector.get(10))
                    populateBrowserHistory();
            }
        }).start();
        new Thread(new Runnable() {

            public void run() {
                if (indexVector.get(1))
                    populateSongsContent();
            }
        }).start();
        new Thread(new Runnable() {

            public void run() {
                if (indexVector.get(2))
                    populateVideoContent();
            }
        }).start();
        new Thread(new Runnable() {

            public void run() {
                if (indexVector.get(3))
                    populateImagesContent();
            }
        }).start();
        new Thread(new Runnable() {

            public void run() {
                if (indexVector.get(0))
                    populateAppContent();
            }
        }).start();
        new Thread(new Runnable() {

            public void run() {
                if (indexVector.get(4))
                    populateContacts();
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                updateHistory();
                return true;
            case R.id.settings:
                globalText = "";
                searchText.setText("");
                Intent intent = new Intent(SpotlightActivity.this, Settings.class);
                startActivityForResult(intent, 987);
                overridePendingTransition(R.anim.scalein, R.anim.rotationout1);
                return true;
            case R.id.rating:
                Intent ratingIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + "com.rahul.spotlight"));
                visibleIndex = listView.getFirstVisiblePosition();
                globalText = searchText.getText().toString();
                startActivity(ratingIntent);
                overridePendingTransition(R.anim.scalein, R.anim.rotationout1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateHistory() {
        new Thread(new Runnable() {

            public void run() {
                populateHistory();
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            globalText = searchText.getText().toString();
            searchText.setText("");
            populateFilterResults(true);
            updateUIForLocalData();
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private TextWatcher filterResults = new TextWatcher() {
        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            String str = s.toString();
            if (searchChooser != 1) {
                if (str.length() > 1 && str.charAt(str.length() - 1) != ' ') {

                    if (getSuggestion.getStatus() != AsyncTask.Status.RUNNING) {
                        getSuggestion = new GetSuggestions();
                        getSuggestion.execute(new String[]{str});
                    }
                }
            } else {
                if (getSuggestions1.getStatus() != AsyncTask.Status.RUNNING) {
                    System.out.println("Calling getSuggestions");
                    getSuggestions1 = new GetSuggestions1();
                    getSuggestions1.execute(new String[]{str});
                } else {
                    System.out.println("Updating counter");
                    newTransactions.add(0);
                }
            }
        }
    };

    private void populateContacts() {
        long st = System.currentTimeMillis();
        Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cur = getContentResolver().query(contactUri, null, null, null,
                Contacts.DISPLAY_NAME);
        if (cur != null && cur.getCount() > 0) {
            cur.moveToFirst();
            do {
                String name = cur.getString(cur
                        .getColumnIndex(Contacts.DISPLAY_NAME));
                String contactID = cur.getString(cur
                        .getColumnIndex(Contacts._ID));

                contactName.add(name);
                contactId.add(contactID);
            } while (cur.moveToNext());
        }
    }

    private void populateAppContent() {
        long st = System.currentTimeMillis();
        List<PackageInfo> apps = getPackageManager().getInstalledPackages(0);
        for (int i = 0; apps != null && i < apps.size(); i++) {
            PackageInfo p = apps.get(i);

            String tempAppName = p.applicationInfo.loadLabel(
                    getPackageManager()).toString();
            String pkgName = p.packageName;
            Intent mIntent = getPackageManager().getLaunchIntentForPackage(
                    pkgName);
            if (mIntent != null) {
                appName.add(tempAppName);
                appPkg.add(pkgName);
            }
        }
    }

    private void populateVideoContent() {
        long st = System.currentTimeMillis();
        Cursor c = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                do {
                    String videoTitle = c.getString(c
                            .getColumnIndex(MediaStore.Video.Media.TITLE));
                    String videoFullPath = c.getString(c
                            .getColumnIndex(MediaStore.Video.Media.DATA));
                    videoName.add(videoTitle);
                    videoPath.add(videoFullPath);
                } while (c.moveToNext());
            }
        }
    }

    private void populateSongsContent() {
        long st = System.currentTimeMillis();
        Cursor c = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                do {
                    String songsTitle = c.getString(c
                            .getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String songFullPath = c.getString(c
                            .getColumnIndex(MediaStore.Audio.Media.DATA));
                    songName.add(songsTitle);
                    songPath.add(songFullPath);
                    songArtist.add(c.getString(c
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                    System.out.println(c.getString(c
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                } while (c.moveToNext());
            }
        }
    }

    private void populateImagesContent() {
        long st = System.currentTimeMillis();
        Cursor c = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                do {
                    String imagesTitle = c.getString(c
                            .getColumnIndex(MediaStore.Images.Media.TITLE));
                    String imageFullPath = c.getString(c
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    imageName.add(imagesTitle);
                    imagePath.add(imageFullPath);
                } while (c.moveToNext());
            }
        }
    }

    private void populateBrowserHistory() {
        long st = System.currentTimeMillis();
        Cursor c = getContentResolver().query(Browser.BOOKMARKS_URI, null,
                null, null, null);
        if (c != null) {
            c.moveToFirst();
            do {
                String browserDsc = c
                        .getString(Browser.HISTORY_PROJECTION_URL_INDEX);
                String browserAdd = c
                        .getString(Browser.SEARCHES_PROJECTION_DATE_INDEX);
                if (browserDsc != null && !browserDsc.contains("http")
                        && browserDsc.length() < 50) {
                    browserDesc.add(browserDsc);
                    browserUrl.add(browserAdd);
                }
            } while (c.moveToNext());
        }
    }

    private void populateOthers(String pattern) {
        j++;

        otherName.clear();
        otherPath.clear();

        DBHandler db = new DBHandler(getApplicationContext());

        List<Files> files = db.getFiles(pattern);
        for (Files file : files) {
            otherName.add(file.getFileName());
            otherPath.add(file.getFilePath());
        }
    }

    public void populateFilterResults(boolean appExit) {

        data.clear();
        globalAppIcon.clear();
        dataAction.clear();
        playAroundValueList.clear();
        boolean available = false;
        Pattern p = null;

        String searchTextQuery = searchText.getText().toString();
        if (appExit == true) {
            searchTextQuery = "";
        }

        if (searchTextQuery.equals("*"))
            searchTextQuery = ".*";

        if (searchTextQuery.length() > 0) {

            //
            // Checking for events
            //
            p = Pattern
                    .compile("^[0-1]?[0-9]:[0-5][0-9]|^[0-2]?[0-3]:[0-5][0-9]");
            Matcher m = p.matcher(searchTextQuery);
            if (m.find()) {

                data.add("Set alarm: " + searchTextQuery);
                playAroundValueList.add(searchTextQuery);
                dataAction.add("#ALARM#");
                globalAppIcon.add(getResources().getDrawable(R.drawable.alarm));
            }

            //
            // Check for a phone number
            //
            p = Pattern.compile("^[0-9]{5,10}");
            m = p.matcher(searchTextQuery);
            if (m.find()) {
                data.add("Call: " + searchTextQuery);
                playAroundValueList.add(searchTextQuery);
                dataAction.add("#CALL#");
                globalAppIcon.add(getResources().getDrawable(R.drawable.phone));
                data.add("Message: " + searchTextQuery);
                playAroundValueList.add(searchTextQuery);
                dataAction.add("#SMS#");
                globalAppIcon.add(getResources()
                        .getDrawable(R.drawable.message));
            }

            available = false;

            p = Pattern.compile(searchTextQuery.toLowerCase());
            for (int i = 0; i < appName.size(); i++) {
                final int tempi = i;
                if ((appName.get(i).toLowerCase().contains(searchTextQuery))) {
                    if (available == false) {
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        data.add("NULL");
                        playAroundValueList.add("!@#$%Apps");
                        dataAction.add("NULL");
                        globalAppIcon.add(null);
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        available = true;
                    }
                    data.add(appName.get(i));
                    playAroundValueList.add(appPkg.get(i));
                    dataAction.add("A");

                    new Thread(new Runnable() {

                        public void run() {

                            try {
                                globalAppIcon.add(getPackageManager()
                                        .getApplicationIcon(appPkg.get(tempi)));
                            } catch (NameNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }).run();

                }
            }

            available = false;

            for (int i = 0; i < songName.size(); i++) {

                if (songName.get(i).toLowerCase()
                        .contains(searchTextQuery.toLowerCase())) {
                    if (available == false) {
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        data.add("NULL");
                        playAroundValueList.add("!@#$%Songs");
                        dataAction.add("NULL");
                        globalAppIcon.add(null);
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        available = true;
                    }
                    data.add(songName.get(i));
                    playAroundValueList.add(songPath.get(i));
                    dataAction.add("S");
                    globalAppIcon.add(null);
                }
            }

            available = false;

            for (int i = 0; i < videoName.size(); i++) {

                if (videoName.get(i).toLowerCase()
                        .contains(searchTextQuery.toLowerCase())) {
                    if (available == false) {
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        data.add("NULL");
                        playAroundValueList.add("!@#$%Videos");
                        dataAction.add("NULL");
                        globalAppIcon.add(null);
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        available = true;
                    }
                    data.add(videoName.get(i));
                    playAroundValueList.add(videoPath.get(i));
                    dataAction.add("V");
                    globalAppIcon.add(null);
                }
            }

            available = false;

            for (int i = 0; i < imageName.size(); i++) {

                if (imageName.get(i).toLowerCase()
                        .contains(searchTextQuery.toLowerCase())) {
                    if (available == false) {
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        data.add("NULL");
                        playAroundValueList.add("!@#$%Images");
                        dataAction.add("NULL");
                        globalAppIcon.add(null);
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        available = true;
                    }
                    data.add(imageName.get(i));
                    playAroundValueList.add(imagePath.get(i));
                    dataAction.add("I");
                    globalAppIcon.add(null);
                }
            }

            available = false;

            for (int i = 0; i < contactName.size(); i++) {

                if (contactName.get(i).toLowerCase()
                        .contains(searchTextQuery.toLowerCase())) {
                    if (available == false) {
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        data.add("NULL");
                        playAroundValueList.add("!@#$%Contacts");
                        dataAction.add("NULL");
                        globalAppIcon.add(null);
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        available = true;
                    }
                    data.add(contactName.get(i));
                    playAroundValueList.add(contactId.get(i));
                    dataAction.add("C");
                    globalAppIcon.add(getResources().getDrawable(
                            R.drawable.contact));
                }
            }
            available = false;

            for (int i = 0; i < browserDesc.size(); i++) {

                if (browserDesc.get(i).toLowerCase()
                        .contains(searchTextQuery.toLowerCase())) {

                    if (available == false) {
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        data.add("NULL");
                        playAroundValueList.add("!@#$%Browser history");
                        dataAction.add("NULL");
                        globalAppIcon.add(null);
                        data.add("PAD");
                        playAroundValueList.add(null);
                        dataAction.add("P");
                        globalAppIcon.add(null);
                        available = true;
                    }
                    data.add(browserDesc.get(i));
                    playAroundValueList.add(browserUrl.get(i));
                    dataAction.add("B");
                    globalAppIcon.add(null);
                }
            }
            available = false;

            populateOthers(searchTextQuery.toLowerCase().toString());
            for (int i = 0; i < otherName.size(); i++) {
                if (available == false) {
                    data.add("PAD");
                    playAroundValueList.add(null);
                    dataAction.add("P");
                    globalAppIcon.add(null);
                    data.add("NULL");
                    playAroundValueList.add("!@#$%Files");
                    dataAction.add("NULL");
                    globalAppIcon.add(null);
                    data.add("PAD");
                    playAroundValueList.add(null);
                    dataAction.add("P");
                    globalAppIcon.add(null);
                    available = true;
                }
                data.add(otherName.get(i));
                playAroundValueList.add(otherPath.get(i));
                dataAction.add("OT");
                globalAppIcon.add(null);
            }
            available = false;
        }
    }

    private void updateUIForLocalData() {
        if (data.size() > 0)
            noresults.setVisibility(View.GONE);
        else
            noresults.setVisibility(View.VISIBLE);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (position <= globalAppIcon.size()) {
                try {
                    iconIdentifier = globalAppIcon.get(position);
                } catch (Exception e) {
                    System.out.println("Rahul: array out of bounds");
                    e.printStackTrace();
                }
            }

            if (iconIdentifier == null) {
                if (dataAction.get(position).equals("E")) {
                    iconIdentifier = getResources().getDrawable(R.drawable.txt);
                } else if (dataAction.get(position).equals("F")) {
                    iconIdentifier = getResources().getDrawable(R.drawable.pdf);
                } else if (dataAction.get(position).equals("T")) {
                    iconIdentifier = getResources().getDrawable(R.drawable.ppt);
                } else if (dataAction.get(position).equals("X")) {
                    iconIdentifier = getResources().getDrawable(R.drawable.xls);
                } else if (dataAction.get(position).equals("D")) {
                    iconIdentifier = getResources().getDrawable(R.drawable.doc);
                } else if (dataAction.get(position).equals("I")) {
                    iconIdentifier = getResources().getDrawable(
                            R.drawable.image);
                } else if (dataAction.get(position).equals("V")) {
                    iconIdentifier = getResources().getDrawable(
                            R.drawable.movies);
                } else if (dataAction.get(position).equals("S")) {
                    iconIdentifier = getResources().getDrawable(
                            R.drawable.music);
                } else if (dataAction.get(position).equals("B")) {
                    iconIdentifier = getResources().getDrawable(R.drawable.web);
                } else if (dataAction.get(position).equals("OT")) {
                    iconIdentifier = getResources()
                            .getDrawable(R.drawable.file);
                }
            }

            String pathName = getItem(position);
            if (!pathName.equals("NULL") && !pathName.equals("PAD")) {

                convertView = mInflater.inflate(R.layout.item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
                holder = (ViewHolder) convertView.getTag();

                if (pathName != null) {
                    holder.getName().setText(pathName);
                }
                if (pathName != null) {
                    holder.getIcon().setBackgroundDrawable(iconIdentifier);
                }
                return convertView;
            } else if (pathName.equals("PAD")) {
                View v = mInflater.inflate(R.layout.padding, null);
                holder = new ViewHolder(v);
                v.setTag(holder);
                return v;
            } else {
                View v = null;

                v = mInflater.inflate(R.layout.seperator, null);

                holder = new ViewHolder(v);
                v.setTag(holder);
                holder = (ViewHolder) v.getTag();
                holder.getSeperator().setText(
                        playAroundValueList.get(position).substring(5));
                return v;
            }
        }

        private class ViewHolder {
            private View pathRow;
            private TextView path = null;
            private TextView seperator = null;
            private ImageView fileIcon = null;

            public ViewHolder(View row) {
                pathRow = row;
            }

            public TextView getName() {
                if (null == path) {
                    path = (TextView) pathRow.findViewById(R.id.itemtext);
					/*
					 * Typeface font = Typeface.createFromAsset(getAssets(),
					 * "fonts/helv.ttf"); path.setTypeface(font);
					 */
                }
                return path;
            }

            public ImageView getIcon() {
                if (null == fileIcon) {
                    fileIcon = (ImageView) pathRow.findViewById(R.id.icon);
                }
                return fileIcon;
            }

            public TextView getSeperator() {
                if (null == seperator) {
                    seperator = (TextView) pathRow.findViewById(R.id.seperator);
					/*
					 * Typeface font = Typeface.createFromAsset(getAssets(),
					 * "fonts/helv.ttf"); seperator.setTypeface(font);
					 * seperator.setTypeface(null, Typeface.BOLD);
					 */
                }
                return seperator;
            }
        }
    }

    private class GetSuggestions extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            doSearch(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            suggestionAdapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.list, suggestions);
            searchText.setAdapter(suggestionAdapter);
            suggestionAdapter.notifyDataSetChanged();
        }
    }

    private class GetSuggestions1 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            populateFilterResults(false);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            updateUIForLocalData();
            if (newTransactions.size() > 0) {
                sendBroadcast(executeIntent);
            }
            if (data.size() > 0)
                noresults.setVisibility(View.GONE);
            else
                noresults.setVisibility(View.VISIBLE);
        }
    }

    class SearchChooserCustomAdapter extends ArrayAdapter<Integer> {

        public SearchChooserCustomAdapter(Context context, int resource,
                                          List<Integer> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            Drawable searchIcon = getResources().getDrawable(
                    iconsList.get(position));

            convertView = mInflater.inflate(R.layout.searchitem, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            holder = (ViewHolder) convertView.getTag();

            holder.getAppIcon().setBackgroundDrawable(searchIcon);
            holder.getLayout().setTag(position + 1);
            holder.getTitleView().setText(titles.get(position));

            return convertView;
        }

        private class ViewHolder {
            private View pathRow;
            private ImageView appIconView = null;
            private TextView titleView = null;
            private LinearLayout linear = null;

            public ViewHolder(View row) {
                pathRow = row;
            }

            public ImageView getAppIcon() {
                if (null == appIconView) {
                    appIconView = (ImageView) pathRow
                            .findViewById(R.id.searchitem);
                }
                return appIconView;
            }

            public TextView getTitleView() {
                if (null == titleView) {
                    titleView = (TextView) pathRow.findViewById(R.id.titlename);
                }
                return titleView;
            }

            public LinearLayout getLayout() {
                if (null == linear) {
                    linear = (LinearLayout) pathRow.findViewById(R.id.ll);
                }
                return linear;
            }
        }
    }

    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (FileMonitorService.class.getName()
                    .equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}