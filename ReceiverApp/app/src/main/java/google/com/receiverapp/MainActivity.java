package google.com.receiverapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView contTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contTextView=findViewById(R.id.textView);

    }

    public void getData(View view) {
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (action.equals("google.com.connectingwebservice") && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra("numRows");
                contTextView.setText(sharedText);
            }
        }
    }
}
