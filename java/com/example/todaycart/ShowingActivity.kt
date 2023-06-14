package com.example.todaycart

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class ShowingActivity : AppCompatActivity() {
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null
    private var intentFiltersArray: Array<IntentFilter>? = null
    private var techListsArray: Array<Array<String>>? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val splashScreenDuration: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showing)
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val thread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(splashScreenDuration)
                    val intent = Intent(this@ShowingActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this,"NFC를 지원하지 않는 기기입니다! 직원에게 문의 바랍니다!",Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val ndefIntentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            ndefIntentFilter.addDataType("*/*")
            intentFiltersArray = arrayOf(ndefIntentFilter)
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            e.printStackTrace()
        }
        techListsArray = arrayOf(arrayOf(Ndef::class.java.name))
    }
    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            if (tag != null) {
                readNfcTagData(tag)
            }
        }
    }

    private fun readNfcTagData(tag: Tag) {
        val ndef = Ndef.get(tag)
        ndef.connect()

        val ndefMessage = ndef.ndefMessage
        val records = ndefMessage.records
        for (record in records) {
            val payload = record.payload
            val text = String(payload)
            val cartNumber = "cart01" // 예시로 스티커에서 읽어온 데이터를 변수에 저장
            saveCartNumber(cartNumber)
            val savedCartNumber = getSavedCartNumber()
            Toast.makeText(this, "저장된 카트 번호: $savedCartNumber", Toast.LENGTH_SHORT).show()
            Log.d("cartNumber",cartNumber)
        }
        ndef.close()
    }
    private fun saveCartNumber(cartNumber: String) {
        val editor = sharedPreferences.edit()
        editor.putString("cartNumber", cartNumber)
        editor.apply()
        Log.d("cartNumber",cartNumber)
    }
    private fun getSavedCartNumber(): String? {
        return sharedPreferences.getString("cartNumber", null)
    }
}