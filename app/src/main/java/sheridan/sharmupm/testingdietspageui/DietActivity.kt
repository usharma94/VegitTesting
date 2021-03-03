package sheridan.sharmupm.testingdietspageui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.diet_recyclerview.*
import org.json.JSONArray
import org.json.JSONObject

class DietActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model = readFromAsset();

        val adapter = CustomAdapter(model, this)

        rcv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcv.adapter = adapter;

        adapter.setOnClickListener(object : CustomAdapter.ClickListener{
            override fun onClick(pos: Int, aView: View) {
                Toast.makeText(this@DietActivity, model.get(pos).name, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun readFromAsset(): List<DietModel> {

        val modeList = mutableListOf<DietModel>()

        val bufferReader = application.assets.open("android_version.json").bufferedReader()
        val json_string = bufferReader.use {
            it.readText()
        }

        val jsonArray = JSONArray(json_string);
        for (i in 0..jsonArray.length() - 1) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            val model = DietModel(
                jsonObject.getString("name"),
                jsonObject.getString("version")
            )
            modeList.add(model)
        }
        return modeList
    }

}