package ro.pub.cs.systems.eim.lab03.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PhoneDialerActivity extends AppCompatActivity {
    private final static int CONTACTS_MANAGER_REQUEST_CODE = 2020;

    private EditText phoneNumberEditText;
    private ImageButton callImageButton;
    private ImageButton hangupImageButton;
    private ImageButton backspaceImageButton;
    private Button genericButton;
    private ImageButton saveButton;

    private CallImageButtonClickListener callImageButtonClickListener = new CallImageButtonClickListener();
    private class CallImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private HangupImageButtonClickListener hangupImageButtonClickListener = new HangupImageButtonClickListener();
    private class HangupImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private BackspaceButtonClickListener backspaceButtonClickListener = new BackspaceButtonClickListener();
    private class BackspaceButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.length() > 0) {
                phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                phoneNumberEditText.setText(phoneNumber);
            }
        }
    }

    private GenericButtonClickListener genericButtonClickListener = new GenericButtonClickListener();
    private class GenericButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + ((Button)view).getText().toString());
        }
    }


    private SaveContactButtonClickListener saveContactButtonClickListener = new SaveContactButtonClickListener();
    private class SaveContactButtonClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.length() > 0) {
                Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity");
                intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", phoneNumber);
                startActivityForResult(intent, CONTACTS_MANAGER_REQUEST_CODE);
            } else {
                Toast.makeText(getApplication(), "Phone error", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);
        int[] buttonIds = {
                R.id.number_0_button,
                R.id.number_1_button,
                R.id.number_2_button,
                R.id.number_3_button,
                R.id.number_4_button,
                R.id.number_5_button,
                R.id.number_6_button,
                R.id.number_7_button,
                R.id.number_8_button,
                R.id.number_9_button,
                R.id.star_button,
                R.id.pound_button};

        phoneNumberEditText = (EditText)findViewById(R.id.phone_number_edit_text);
        callImageButton = (ImageButton)findViewById(R.id.call_image_button);
        callImageButton.setOnClickListener(callImageButtonClickListener);
        hangupImageButton = (ImageButton)findViewById(R.id.hangup_image_button);
        hangupImageButton.setOnClickListener(hangupImageButtonClickListener);
        backspaceImageButton = (ImageButton)findViewById(R.id.backspace_image_button);
        backspaceImageButton.setOnClickListener(backspaceButtonClickListener);
        for (int index = 0; index < buttonIds.length; index++) {
            genericButton = (Button)findViewById(buttonIds[index]);
            genericButton.setOnClickListener(genericButtonClickListener);
        }

        saveButton = findViewById(R.id.save_image_button);
        saveButton.setOnClickListener(saveContactButtonClickListener);

    }
}
