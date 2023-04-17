package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class newActivity extends AppCompatActivity {

    //declare listview where news items will be displayed

    ListView newsList;

    //Array lists are created to store article information
    ArrayList<String> titles;
    ArrayList<String> links;

    ProgressBar pb;
    int counter =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        newsList = (ListView) findViewById(R.id.newsList);
        //set on item click listener for list responsiveness

        titles =new ArrayList<String>();
        links =new ArrayList<String>();
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //the int i represents the position of the link
                //And it allows to open web page when item is clicked

                Uri uri = Uri.parse(links.get(i)); // allows for connection to link
                Intent openWebpage = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(openWebpage);

            }
        });



        //to execute our  process in background where we read in data from XML link
         new ProcessInBackground().execute();



    }




    // this method is used to retreive and open url connection
    public InputStream getInputStream(URL url){

        try{
            return url.openConnection().getInputStream();

        }
        catch(IOException e){
            return null;

        }



    }

    //we use this to go into the background to read xml page
    //we extend AsyncTask interface
    public class ProcessInBackground extends AsyncTask<Integer,Void,Exception>{

        AlertDialog alertMessage;
        Exception exception =null;

        @Override
        // this section is used to show that the page is loading
        //will incorporate progress bar into this section
        protected void onPreExecute(){


          // alertMessage.setMessage("loading");
           // alertMessage.show();
        }


        @Override
        protected Exception doInBackground(Integer... integers) {

            try {// reading the link /url  we use the URL object

                URL url = new URL("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
                //XMLPull parser factory used to create new instance of XMLPull parser factory
                //this helps retrieve data from XML document
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false); //this will not provide support for sxml name spaces

                //creating XML pull parser using factory
                XmlPullParser xmlPP = factory.newPullParser();
                xmlPP.setInput(getInputStream(url), "UTF-8"); //defines input stream that we use to read data from and we specify the encoding

                boolean insideItem = false;//

                int eventType = xmlPP.getEventType(); //returns  position  of our tag /elements in the document
                //we use while loop to loop through xml tags
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        //checks whether the start tag is item tag
                        //the item tag contains all the information we need regarding the articles
                        if (xmlPP.getName().equalsIgnoreCase("item")) {
                            insideItem = true;

                        } else if (xmlPP.getName().equalsIgnoreCase("title")) {

                            if (insideItem) {
                                //in this stage of the if statement we are in the title tag
                                //we add the title into the array list of title

                                titles.add(xmlPP.nextText());

                            }

                        }

                        else if (xmlPP.getName().equalsIgnoreCase("link")) {

                            if (insideItem) {

                                links.add(xmlPP.nextText());

                            }

                        }


                    }
                    //checks to see whether the end tag is an item tag
                    else if (eventType == XmlPullParser.END_DOCUMENT && xmlPP.getName().equalsIgnoreCase("item"))
                    {
                        insideItem =false;

                    }
                    eventType = xmlPP.next(); // to increment while loop

                }
            }

            catch(MalformedURLException e) //if the url typed has an error
            {
                exception =e;
            }
            catch(XmlPullParserException e)//if there is an invalid character in the xml
            {
                exception =e;
            }
            catch(IOException e)// input output exception
            {
                exception =e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s){
            super.onPostExecute(s);

            //progress bar


            //in post we will set data to our list
            //this allows us to dsiplay on the list view
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(newActivity.this, android.R.layout.simple_list_item_1,titles);
            newsList.setAdapter(adapter);

            //alertMessage.dismiss(); // this removes the loading sign after info is loaded

        }

    }



}