package vn.timtro.timtroproject.until;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import vn.timtro.timtroproject.R;

public class GenericTextWatcher implements TextWatcher {
    private final EditText[] editTexts;
    private View view;

    public GenericTextWatcher(EditText[] editTexts, View view) {
        this.editTexts = editTexts;
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        switch (view.getId()) {
            case R.id.edt_veryfy_phone_1:
                if (text.length() == 1) {
                    editTexts[1].requestFocus();
                }
                break;
            case R.id.edt_veryfy_phone_2:
                if (text.length() == 1) {
                    editTexts[2].requestFocus();
                } else if (text.length() == 0) {
                    editTexts[0].requestFocus();
                }
                break;
            case R.id.edt_veryfy_phone_3:
                if (text.length() == 1) {
                    editTexts[3].requestFocus();
                } else if (text.length() == 0) {
                    editTexts[1].requestFocus();
                }
                break;
            case R.id.edt_veryfy_phone_4:
                if (text.length() == 1) {
                    editTexts[4].requestFocus();
                } else if (text.length() == 0) {
                    editTexts[2].requestFocus();
                }
                break;
            case R.id.edt_veryfy_phone_5:
                if (text.length() == 1) {
                    editTexts[5].requestFocus();
                } else if (text.length() == 0) {
                    editTexts[3].requestFocus();
                }
                break;
            case R.id.edt_veryfy_phone_6:
                if (text.length() == 0) {
                    editTexts[4].requestFocus();
                }
                break;

        }
    }
}
