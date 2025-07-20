package jp.sane.openforhatebu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
        }
        
        setContentView(R.layout.activity_main)
    }
}
