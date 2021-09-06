package com.example.webbrowserexample


import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.annotation.RequiresApi
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var wv1: WebView
    lateinit var et1: EditText
    lateinit var ib1: ImageButton
    lateinit var ib2: ImageButton
    lateinit var ib3: ImageButton
    lateinit var b1: ImageButton
    lateinit var l1: ListView
    lateinit var filterList : ArrayList<String>
    lateinit var list: ArrayList<String>
    lateinit var adapter: ArrayAdapter<String>
//    lateinit var sharedPref: SharedPreferences
//    lateinit var gson: Gson
//    lateinit var Json: String
//    lateinit var suggestion1: Suggestion


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        b1 = findViewById(R.id.B1)
        et1 = findViewById(R.id.ET1)
        ib1 = findViewById(R.id.IB1)
        ib3 = findViewById(R.id.IB3)
        ib2 = findViewById(R.id.IB2)
        wv1 = findViewById(R.id.WV1)
        ib2.isEnabled = false


//        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
//        val editor = sharedPref.edit()


//        suggestion1 = Suggestion()
//        suggestion1.al = ArrayList()
//        var sug = Suggestion("apple")
//        suggestion1.al.add(sug)

        var bool = false

//       val helper = MyDBHelper(applicationContext)
//        val db = helper.readableDatabase


        wv1.webViewClient = MyWebViewClient(et1)

        ib1.setOnClickListener({
            wv1.reload()
        })

        filterList = ArrayList()
        list = ArrayList()

//        gson = Gson()


//        val suggestion = sharedPref.getString("TAG", "")
//        val sList = gson.fromJson(suggestion, Suggestion::class.java)
//        if (sList.al.isNotEmpty() && sList.al.size > 0) {
//            print(sList.al.size)
//            for (item in sList.al.indices) {
//                print("item = " + item)
//            }
//        }


//        Json = sharedPref.getString("TAG", "")!!
//        if (Json.isEmpty()) {
//            Toast.makeText(this, "There is something error", Toast.LENGTH_LONG).show();
//     }
//        else {
//            var type = TypeToken<List<String>>() {
//            }.getType();
//            var arrPackageData = gson.fromJson(Json, type);
//            for(String data:arrPackageData) {
//                result.setText(data);
//            }


        list.add("android")
        list.add("java")
        list.add("apple")
        list.add("kotlin")



        l1 = findViewById(R.id.LV1)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        l1.adapter = adapter
        l1.visibility = View.GONE




        et1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s?.length!! >= 1 && bool == false) {
                    l1.visibility = View.VISIBLE

                    l1.setOnItemClickListener { parent, view, position, id ->
                        val element = adapter.getItem(position)
                        wv1.loadUrl("https://www.google.com/search?q=" + element)
                        bool = true
                        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                        ib2.isEnabled = true
                    }
                } else
                    l1.visibility = View.GONE

            }

            override fun afterTextChanged(s: Editable?) {
                filterList.clear()
                if (s.toString().isEmpty()){
                    l1.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    filter(s.toString())
                }
            }

        })

        b1.setOnClickListener({

            val text = et1.text.toString()
            wv1.loadUrl("https://www.google.com/search?q=" + text)
            bool = true
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

            et1.setText(wv1.url)
           ib2.isEnabled = true
            if (list.contains(text)) {
                list.add("")
            } else
                list.add(text)


//            var suggestion = Suggestion(text)
//
//            print("suggestion = " + suggestion)
//
//            suggestion1.al.add(suggestion)
//            Json = gson.toJson(suggestion1)
//            editor.putString("TAG", Json)
//            editor.apply()

            adapter.notifyDataSetChanged()

//            val cv = ContentValues()
//            cv.put("USERENTRY",et1.text.toString())
//            db.insert("USERS",null,cv)

//            Toast.makeText(this,"DATA ENTER SUCCESSFULLY",Toast.LENGTH_SHORT).show()
        })

        ib2.setOnClickListener({
            if (wv1.canGoBack()) {
                wv1.goBack()
            }
            else
                wv1.loadUrl("https://www.google.com/")
                ib2.isEnabled = false
        })

        et1.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
              if (actionId == EditorInfo.IME_ACTION_SEARCH){
                  performsearch()
                  bool = true
                  return true
              }
                return false
            }

        })

    }

    private fun filter(text : String) {
        for (a in list){
            if (a.equals(text)){
                filterList.add(a)
            }
        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, filterList)
        l1.adapter = adapter
    }

    fun  performsearch() {
          val text = et1.text.toString()
          wv1.loadUrl("https://www.google.com/search?q=" + text)

          val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
          imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)



          et1.setText(wv1.url)
          ib2.isEnabled = true

    }


    class MyWebViewClient(et1: EditText) : WebViewClient() {

            var et2 = et1

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                et2.setText(view?.url)
//          Log.d("qwerty","it's running " + string)
            }
        }

        fun editText(view: View) {
            val text = et1.text.toString()

            et1.setSelection(text.length)

//            if ( text.isNotEmpty()) {
//                et1.setSelectAllOnFocus(true)
//           }
            //            else {
//                et1.requestFocus()
//            }

            if (et1.text.toString() != null) {
                clearVisible()
            }
        }

        private fun clearVisible() {
            ib3.visibility = View.VISIBLE
            ib3.setOnClickListener {
                et1.setText("")
                ib3.visibility = View.GONE
            }
        }
    }










