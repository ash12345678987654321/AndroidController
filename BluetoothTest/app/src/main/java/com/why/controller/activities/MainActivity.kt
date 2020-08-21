package com.why.controller.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.why.bluetoothtouchpad2.bluetooth.BluetoothController
import com.why.bluetoothtouchpad2.bluetooth.MouseSender
import com.why.bluetoothtouchpad2.bluetooth.Sender
import com.why.controller.R
import com.why.controller.bluetooth.KeyboardSender
import com.why.controller.bluetooth.Main
import com.why.controller.bluetooth.Main.keyboard
import com.why.controller.bluetooth.Main.mouse
import java.io.File
import java.io.PrintWriter
import java.util.*

//TODO allow for no selection available

//TODO remove setttings
class MainActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var oldName: String? = null
    private var rename_btn: ImageButton? = null
    private var relativeLayout: RelativeLayout? = null
    private var layouts: Vector<String>? = null
    private var decorView: View? = null
    private var popupWindow = PopupWindow() //so we can access it easily
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
        rename_btn = findViewById(R.id.rename_btn)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.title = ""
        var path = File("$filesDir/macros/")
        if (!path.exists()) {
            path.mkdir()
        }
        if (path.listFiles().isNullOrEmpty()) {
            createMacros()
        }
        path = File("$filesDir/layouts/")
        layouts = Vector()
        //Log.d("ZZZ",path.toString());
        if (!path.exists()) {
            path.mkdir()
        }
        if (path.listFiles().isNullOrEmpty()) {
            createLayouts()
        }
        for (i in path.listFiles()!!) {
            //Log.d("ZZZ","File found: "+i.getName());
            layouts!!.add(i.name)
        }
        layouts?.sort()

        //add them to edittext
        editText?.setText(layouts!![0])
        editText?.setOnEditorActionListener { v, actionId, event -> false }
        relativeLayout = findViewById(R.id.relative_layout)
        val layoutParams = relativeLayout?.layoutParams as LinearLayout.LayoutParams
        layoutParams.height = Resources.getSystem().displayMetrics.heightPixels / 2 + 40
        layoutParams.width = Resources.getSystem().displayMetrics.widthPixels / 2 + 40
        relativeLayout?.layoutParams = layoutParams

        //code to make app bigger
        decorView = window.decorView
        decorView!!.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility == 0) {
                setHighVisibility()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            setHighVisibility()
        }
    }

    private fun setHighVisibility() { //hide nav bar and make app fullscreen
        decorView!!.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }

    public override fun onResume() {
        super.onResume()
        fillPreview()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingsBtn -> startActivity(Intent(this.applicationContext, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    fun start(view: View?) {
        if (editText!!.isFocusable) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show()
            return
        }
        if (editText!!.isFocusable) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, ControllerActivity::class.java)
        intent.putExtra("preset", editText!!.text.toString())
        startActivity(intent)
    }

    fun edit(view: View?) {
        if (editText!!.isFocusable) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("preset", editText!!.text.toString())
        startActivity(intent)
    }

    fun macro(view: View?) {
        if (editText!!.isFocusable) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, MacroActivity::class.java)
        startActivity(intent)
    }

    fun inflate(view: View?) {
        if (editText!!.isFocusable) return  //dont open if user is trying to rename a file
        if (popupWindow.isShowing) {
            val linearLayout = popupWindow.contentView.findViewById<LinearLayout>(R.id.linear_layout)
            linearLayout.removeAllViews()
            for (i in layouts!!.indices) {
                val textView = TextView(this)
                textView.minHeight = 0
                textView.minWidth = 0
                textView.text = layouts!![i]
                textView.textSize = 20f
                linearLayout.addView(textView, 0)
            }
        } else {
            val layoutInflater = baseContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = layoutInflater.inflate(R.layout.popup_choose, null)
            val linearLayout = popupView.findViewById<LinearLayout>(R.id.linear_layout)
            linearLayout.minimumWidth = editText!!.width
            for (i in layouts!!.indices) {
                val textView = TextView(this)
                textView.minHeight = 0
                textView.minWidth = 0
                textView.text = layouts!![i]
                textView.textSize = 20f
                textView.setOnClickListener { v ->
                    editText!!.setText((v as TextView).text.toString())
                    fillPreview()
                    popupWindow.dismiss()
                }
                linearLayout.addView(textView, i)
            }
            popupWindow = PopupWindow(
                    popupView,
                    editText!!.width,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            popupWindow.isFocusable = true
            popupWindow.update()
            popupWindow.showAsDropDown(view)
        }
    }

    fun add(view: View?) {
        if (editText!!.isFocusable) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show()
            return
        }
        var file: File
        var index = 1
        while (true) {
            file = File("$filesDir/layouts/Layout $index")
            if (!file.exists()) break
            index++
        }
        try {
            file.createNewFile()
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            return
        }
        layouts!!.add(file.name)
        layouts!!.sort()
        editText!!.setText(file.name)
        fillPreview()
    }

    fun del(view: View?) {
        if (editText!!.isFocusable) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show()
            return
        }
        if (layouts!!.size == 1) {
            Toast.makeText(this, "There must always be at least 1 layout!", Toast.LENGTH_SHORT).show()
            return
        }
        val file = File(filesDir.toString() + "/layouts/" + editText!!.text)
        try {
            file.delete()
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            return
        }
        for (i in layouts!!.indices) {
            if (layouts!![i] == file.name) {
                layouts!!.removeAt(i)
                editText!!.setText(layouts!![Math.min(i, layouts!!.size - 1)])
                break
            }
        }
        fillPreview()
    }

    fun rename(view: View?) {
        if (editText!!.isFocusable) { //check if renaming is valid
            val newName = editText!!.text.toString()
            if (newName != oldName) {
                val file = File("$filesDir/layouts/$newName")
                if (file.exists()) {
                    Toast.makeText(this, "Invalid file name", Toast.LENGTH_SHORT).show()
                    return
                }
                for (i in layouts!!.indices) {
                    if (layouts!![i] == oldName) layouts!![i] = newName
                }
                val file2 = File("$filesDir/layouts/$oldName")
                file2.renameTo(file)
                Collections.sort(layouts)
            }
            editText!!.isFocusable = false
            editText!!.isFocusableInTouchMode = false
            rename_btn!!.setImageResource(R.drawable.ic_rename)
        } else { //allow user to edit
            oldName = editText!!.text.toString()
            editText!!.isFocusable = true
            editText!!.isFocusableInTouchMode = true
            rename_btn!!.setImageResource(R.drawable.ic_tick)
        }
    }

    //wow we are gonna show a preview bcas thats cool
    private fun fillPreview() {
        relativeLayout!!.removeAllViews()
        try {
            val file = File(filesDir.toString() + "/layouts/" + editText!!.text.toString())
            val scanner = Scanner(file)
            while (scanner.hasNextLine()) {
                val args = scanner.nextLine().split("\u0000".toRegex()).toTypedArray()
                when (args[0]) {
                    "Btn" -> Btn(args[3].toInt(), args[4].toInt(), args[5].toInt(), args[6].toInt())
                    "Dpad" -> Dpad(args[5].toInt(), args[6].toInt(), args[7].toInt())
                    "Macro" -> Macro(args[4].toInt(), args[5].toInt(), args[6].toInt(), args[7].toInt())
                    "JoyStick" -> JoyStick(args[2].toInt(), args[3].toInt(), args[4].toInt())
                }
            }
            scanner.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "File corrupted >.<", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun Btn(height: Int, width: Int, marginTop: Int, marginLeft: Int) {
        var height = height
        var width = width
        var marginTop = marginTop
        var marginLeft = marginLeft
        height /= 2
        width /= 2
        marginTop /= 2
        marginLeft /= 2
        val btn = Button(this)
        btn.height = height
        btn.width = width
        btn.minimumHeight = 0
        btn.minimumWidth = 0
        btn.setBackgroundResource(R.drawable.button_up)
        relativeLayout!!.addView(btn)
        val layoutParams = btn.layoutParams as RelativeLayout.LayoutParams
        layoutParams.leftMargin = marginLeft
        layoutParams.topMargin = marginTop
        btn.layoutParams = layoutParams
    }

    private fun Dpad(diameter: Int, marginTop: Int, marginLeft: Int) {
        var diameter = diameter
        var marginTop = marginTop
        var marginLeft = marginLeft
        diameter /= 2
        marginTop /= 2
        marginLeft /= 2
        val btn = Button(this)
        btn.height = diameter
        btn.width = diameter
        btn.minimumHeight = 0
        btn.minimumWidth = 0
        btn.setBackgroundResource(R.drawable.dpad)
        relativeLayout!!.addView(btn)
        val layoutParams = btn.layoutParams as RelativeLayout.LayoutParams
        layoutParams.leftMargin = marginLeft
        layoutParams.topMargin = marginTop
        btn.layoutParams = layoutParams
    }

    private fun Macro(height: Int, width: Int, marginTop: Int, marginLeft: Int) {
        var height = height
        var width = width
        var marginTop = marginTop
        var marginLeft = marginLeft
        height /= 2
        width /= 2
        marginTop /= 2
        marginLeft /= 2
        val btn = ImageView(this)
        btn.maxHeight = height
        btn.maxWidth = width
        btn.minimumHeight = height
        btn.minimumWidth = width
        btn.setBackgroundResource(R.drawable.button_macro)
        btn.setImageResource(R.drawable.ic_m)
        btn.scaleType = ImageView.ScaleType.FIT_CENTER
        btn.adjustViewBounds = false
        relativeLayout!!.addView(btn)
        val layoutParams = btn.layoutParams as RelativeLayout.LayoutParams
        layoutParams.leftMargin = marginLeft
        layoutParams.topMargin = marginTop
        btn.layoutParams = layoutParams
    }

    private fun JoyStick(diameter: Int, marginTop: Int, marginLeft: Int) {
        var diameter = diameter
        var marginTop = marginTop
        var marginLeft = marginLeft
        diameter /= 2
        marginTop /= 2
        marginLeft /= 2
        val btn = Button(this)
        btn.height = diameter
        btn.width = diameter
        btn.minimumHeight = 0
        btn.minimumWidth = 0
        btn.setBackgroundResource(R.drawable.dpad)
        relativeLayout!!.addView(btn)
        val layoutParams = btn.layoutParams as RelativeLayout.LayoutParams
        layoutParams.leftMargin = marginLeft
        layoutParams.topMargin = marginTop
        btn.layoutParams = layoutParams
    }

    //someone suggested that i create presets for users
    private fun createLayouts() {
        try {
            val file = File("$filesDir/layouts/Layout")
            val pw = PrintWriter(file)
            pw.println("Dpad" + "\u0000" + "w" + "\u0000" + "s" + "\u0000" + "a" + "\u0000" + "d" + "\u0000" + resources.displayMetrics.heightPixels + "\u0000" + "0" + "\u0000" + "0")
            pw.println("Btn" + "\u0000" + "Button" + "\u0000" + "space" + "\u0000" + resources.displayMetrics.heightPixels / 2 + "\u0000" + resources.displayMetrics.heightPixels / 2 + "\u0000" + resources.displayMetrics.heightPixels / 2 + "\u0000" + resources.displayMetrics.heightPixels)
            pw.println("Macro" + "\u0000" + "Macro" + "\u0000" + filesDir + "/macros/" + "25268206-7a75-11ea-bc55-0242ac130003" + "\u0000" + false + "\u0000" + resources.displayMetrics.heightPixels / 2 + "\u0000" + resources.displayMetrics.heightPixels / 2 + "\u0000" + "0" + "\u0000" + resources.displayMetrics.heightPixels)
            pw.flush()
            pw.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createMacros() {
        try {
            val file = File("$filesDir/macros/25268206-7a75-11ea-bc55-0242ac130003")
            val pw = PrintWriter(file)
            pw.println("Jitter click")
            pw.println("KeyStroke" + "\u0000" + "m1" + "\u0000" + true + "\u0000" + false + "\u0000" + "367ccb0c-d582-42e0-b004-da3d9d2159e1")
            pw.println("KeyStroke" + "\u0000" + "m1" + "\u0000" + false + "\u0000" + true + "\u0000" + "367ccb0c-d582-42e0-b004-da3d9d2159e1")
            pw.println("Delay" + "\u0000" + "200" + "\u0000" + false + "\u0000" + false + "\u0000" + "71089e51-d764-4e28-b25c-2f2d8a8f4800")
            pw.flush()
            pw.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var m: Sender? = null


    @ExperimentalUnsignedTypes
    @SuppressLint("ClickableViewAccessibility")
    override fun onStart() {
        super.onStart()
        Main.init(this.applicationContext)
        Log.d("ZZZ", "eoiugneoigrng")
        BluetoothController.getSender { hid, device ->
            Log.d("ZZZ", "BL very good")
            Main.connected = true

            val mainHandler = Handler(this.mainLooper)
            mainHandler.post {
                mouse = MouseSender(hid, device)
                keyboard = KeyboardSender(hid, device)
                m = Sender(mouse!!, hid, device)
                Toast.makeText(applicationContext, "Bluetooth is connected!", Toast.LENGTH_LONG).show()

            }

        }

        BluetoothController.getDisconnector {
            val mainHandler = Handler(this.mainLooper)
            mainHandler.post {
                println("byebyebye")
                Main.connected = false
            }
        }


    }
}